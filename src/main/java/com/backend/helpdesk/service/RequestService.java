package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.common.Email;
import com.backend.helpdesk.configurations.TokenProvider;
import com.backend.helpdesk.controller.EmailController;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.converters.requestConverter.ConvertRequestToRequestDTO;
import com.backend.helpdesk.converters.statusConverter.ConvertStatusToStatusDTO;
import com.backend.helpdesk.entity.RequestEntity;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.entity.UserEntity;
import com.backend.helpdesk.repository.RequestRepository;
import com.backend.helpdesk.repository.RequestTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private Converter<RequestDTO, RequestEntity> requestDTORequestEntityConverter;

    @Autowired
    private ConvertRequestToRequestDTO convertRequestToRequestDTO;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailController emailController;

    @Autowired
    private ConvertStatusToStatusDTO convertStatusToStatusDTO;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("#{'${emailAdmins}'.split(',')}")
    private List<String> emailAdmins;

    public List<RequestDTO> getAllRequest() {
        return convertRequestToRequestDTO.convert(requestRepository.findAll());
    }

    public int getSize(String search) {
        return requestRepository.findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContaining(search, search, search, search).size();
    }

    public List<RequestDTO> getAllRequestOfUserLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return convertRequestToRequestDTO.convert(requestRepository.findByUserEmail(auth.getName()));
    }

    public List<RequestDTO> searchRequestAndPagination(int page, int items, String sortBy, String search) {

        List<RequestEntity> requestEntities = this.search(search);

        List<RequestEntity> result = new ArrayList<>();

        if (sortBy.equals("Email")) {
            requestEntities.clear();
            requestEntities = this.requestRepository.findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByUserEmailAsc(search, search, search, search);
        }
        if (sortBy.equals("Status")) {
            requestEntities.clear();
            requestEntities = this.requestRepository.findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByStatusNameAsc(search, search, search, search);
        }
        if (sortBy.equals("Request Type")) {
            requestEntities.clear();
            requestEntities = this.requestRepository.findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByRequestTypeNameAsc(search, search, search, search);
        }
        if (sortBy.equals("Create At")) {
            requestEntities.clear();
            requestEntities = this.requestRepository.findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByCreateAtAsc(search, search, search, search);
        }

        int n = (page + 1) * items;
        if (n > requestEntities.size()) n = requestEntities.size();

        for (int i = page * items; i < n; i++) {
            result.add(requestEntities.get(i));
        }

        return convertRequestToRequestDTO.convert(result);
    }

    public RequestDTO addRequest(RequestDTO requestDTO) {

        requestDTO.setStatus(convertStatusToStatusDTO.convert(statusRepository.findByName("PENDING").get()));
        requestDTO.setCreateAt(new Date());
        RequestEntity requestEntity = requestDTORequestEntityConverter.convert(requestDTO);
        this.requestRepository.save(requestEntity);

        sendEmailToAdminWithButtonApproveAndReject(requestEntity.getUser().getEmail(), requestEntity);

        return convertRequestToRequestDTO.convert(requestEntity);
    }

    public void sendEmailToAdminWithButtonApproveAndReject(String emailUserRequest, RequestEntity requestEntity){
        for(String emailAdmin : emailAdmins) {
            String html =
                    "<table style=\"width:100%\">" +
                    "  <tr>" +
                    "    <th>Request by email</th>" +
                    "    <td>" + requestEntity.getUser().getEmail() + "</td>" +
                    "  </tr>" +
                    "  <tr>" +
                    "    <th>Request type</th>" +
                    "    <td>" + requestEntity.getRequestType().getName() + "</td>" +
                    "  </tr>" +
                    "  <tr>" +
                    "    <th>Day request</th>" +
                    "    <td>" + requestEntity.getDayRequest() + "</td>" +
                    "  </tr>" +
                    "  <tr>" +
                    "    <th>Day Description</th>" +
                    "    <td>" + requestEntity.getDescription() + "</td>" +
                    "  </tr>" +
                    "</table>" +
                    "<form method=\"post\" action=\"https://helpdesk-kunlez-novahub.herokuapp.com/api/requests/approveRequest/" + requestEntity.getId() + "/" + tokenProvider.genTokenAdmin(emailAdmin) + "\">" +
                    "   <button type=\"submit\">APPROVE</button>" +
                    "</form>"+
                    "<form method=\"post\" action=\"https://helpdesk-kunlez-novahub.herokuapp.com/api/requests/rejectRequest/" + requestEntity.getId() + "/" + tokenProvider.genTokenAdmin(emailAdmin) + "\">" +
                    "   <button type=\"submit\">REJECT</button>" +
                    "</form>";
            Email email = new Email();
            List<String> emails = new ArrayList<>();
            emails.add(emailAdmin);
            email.setSendToEmail(emails);
            email.setSubject(emailUserRequest);
            email.setText(html);
            emailController.sendEmail(email);
        }
    }

    public RequestDTO putRequest(RequestDTO requestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName()).get();
        RequestEntity request = requestRepository.findById(requestDTO.getId()).get();

        boolean isAdmin = false;

        for(RoleEntity roleEntity : userEntity.getRoleEntities()){
            if(roleEntity.getName().equals("ROLE_ADMIN")){
                isAdmin=true;
            }
        }

        if (request.getUser().getId() == userEntity.getId() || isAdmin) {
            RequestEntity requestEntity = requestRepository.save(requestDTORequestEntityConverter.convert(requestDTO));
            Email email = new Email();
            List<String> emails = new ArrayList<>();
            emails.add(request.getUser().getEmail());
            email.setSendToEmail(emails);
            email.setSubject(requestEntity.getRequestType().getName());
            email.setText(requestEntity.getStatus().getName());

            emailController.sendEmail(email);

            return convertRequestToRequestDTO.convert(requestEntity);
        }
        return convertRequestToRequestDTO.convert(request);
    }

    public RequestDTO approvedOrRejectRequest(int id, String keyAdmin, String status){
        Claims claims = tokenProvider.decodeJWTAdmin(keyAdmin);

        String email1 = claims.get("email").toString();

        UserEntity userEntity = userRepository.findByEmail(email1).get();

        boolean isAdmin = false;

        for(RoleEntity roleEntity : userEntity.getRoleEntities()){
            if(roleEntity.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }
        RequestEntity requestEntity = requestRepository.findById(id).get();

        if(isAdmin) {
            String html = status + " BY ADMIN: " + userEntity.getEmail() +
                    "<table style=\"width:100%\">" +
                    "  <tr>" +
                    "    <th>Request by email</th>" +
                    "    <td>"+ requestEntity.getUser().getEmail() +"</td>" +
                    "  </tr>"+
                    "  <tr>" +
                    "    <th>Request type</th>" +
                    "    <td>"+ requestEntity.getRequestType().getName() +"</td>" +
                    "  </tr>"+
                    "  <tr>" +
                    "    <th>Day request</th>" +
                    "    <td>"+ requestEntity.getDayRequest() +"</td>" +
                    "  </tr>"+
                    "  <tr>" +
                    "    <th>Day Description</th>" +
                    "    <td>"+ requestEntity.getDescription() +"</td>" +
                    "  </tr>"+
                    "</table>";

            requestEntity.setStatus(statusRepository.findByName(status).get());
            requestEntity = requestRepository.save(requestEntity);

            Email email = new Email();

            List<String> sendToEmail = new ArrayList<>();
            sendToEmail.add(requestEntity.getUser().getEmail());
            sendToEmail.addAll(emailAdmins);

            email.setSendToEmail(sendToEmail);
            email.setSubject("["+ status +" Request]");
            email.setText(html);
            emailController.sendEmail(email);
        }

        return convertRequestToRequestDTO.convert(requestEntity);
    }

    public void removeRequest(@RequestParam int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findByEmail(auth.getName()).get();

        boolean isAdmin = false;

        for(RoleEntity roleEntity : userEntity.getRoleEntities()){
            if(roleEntity.getName().equals("ROLE_ADMIN")){
                isAdmin = true;
                break;
            }
        }

        RequestEntity requestEntity = requestRepository.findById(id).get();
        if (requestEntity.getUser().getId() == userEntity.getId() || isAdmin) {
            requestRepository.deleteById(id);
        }
    }

    private List<RequestEntity> search(String keySearch) {
        if (keySearch.equals("")) return requestRepository.findAll();

        List<RequestEntity> RequestEntitiesByRequestType = requestRepository.findByRequestTypeName(keySearch);
        List<RequestEntity> RequestEntitiesByUser = requestRepository.findByUserEmail(keySearch);
        List<RequestEntity> RequestEntitiesByStatus = requestRepository.findByStatusName(keySearch);

        List<RequestEntity> results = new ArrayList<>();
        results.addAll(RequestEntitiesByRequestType);

        for (RequestEntity requestEntityByUser : RequestEntitiesByUser) {
            Boolean add = true;
            for (RequestEntity result : results) {
                if (requestEntityByUser.getId() == result.getId()) {
                    add = false;
                    break;
                }
            }
            if (add) {
                results.add(requestEntityByUser);
            }
        }

        for (RequestEntity requestEntityByStatus : RequestEntitiesByStatus) {
            Boolean add = true;
            for (RequestEntity result : results) {
                if (requestEntityByStatus.getId() == result.getId()) {
                    add = false;
                    break;
                }
            }
            if (add) {
                results.add(requestEntityByStatus);
            }
        }

        return results;
    }
}
