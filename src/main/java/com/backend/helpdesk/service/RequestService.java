package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.common.Email;
import com.backend.helpdesk.controller.EmailController;
import com.backend.helpdesk.converters.request.ConvertRequestDTOToRequest;
import com.backend.helpdesk.converters.request.ConvertRequestToRequestDTO;
import com.backend.helpdesk.entity.RequestEntity;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.repository.RequestRepository;
import com.backend.helpdesk.repository.RequestTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestTypeRepository requestTypeRepository;

    @Autowired
    private ConvertRequestDTOToRequest convertRequestDTOToRequest;

    @Autowired
    private ConvertRequestToRequestDTO convertRequestToRequestDTO;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailController emailController;

    public List<RequestDTO> getAllRequest(){
        return convertRequestToRequestDTO.convert(requestRepository.findAll());
    }

    public List<RequestDTO> searchRequestAndPagination(int page, int items, String sortBy, String search){

        List<RequestEntity> requestEntities =  this.search(search);

        List<RequestEntity> result = new ArrayList<>();

        if(sortBy.equals("Email")){
            requestEntities.clear();
            requestEntities = this.requestRepository.findByOrderByUserEmailAsc();
        }
        if(sortBy.equals("Status")){
            requestEntities.clear();
            requestEntities = this.requestRepository.findByOrderByUserEmailAsc();
        }
        if(sortBy.equals("Request Type")){
            requestEntities.clear();
            requestEntities = this.requestRepository.findByOrderByUserEmailAsc();
        }

        int n = (page+1)*items;
        if(n<requestEntities.size()) n= requestEntities.size();

        for(int i=page*items; i<n; i++){
            result.add(requestEntities.get(i));
        }

        return convertRequestToRequestDTO.convert(result);
    }

    public RequestDTO addRequest(RequestDTO requestDTO){
        Email email = new Email();
        List<String> emails = new ArrayList<>();
        emails.add("${spring.mail.username}");
        email.setSendToEmail(emails);
        email.setSubject(requestTypeRepository.findById(requestDTO.getIdRequestType()).get().getName());
        email.setText(requestDTO.getDescription());

        requestDTO.setId(0);
        requestDTO.setIdStatus(statusRepository.findByName("WAITING").get().getId());
        return convertRequestToRequestDTO.convert(requestRepository.save(convertRequestDTOToRequest.convert(requestDTO)));
    }

    public RequestDTO putRequest(RequestDTO requestDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isAdmin = false;

        for(RoleEntity role: userRepository.findByEmail(username).get().getRoleEntities()){
            if(role.getName()=="ROLE_ADMIN"){
                isAdmin=true;
                break;
            }
        }

        if (isAdmin){
            Email email = new Email();
            List<String> emails = new ArrayList<>();
            emails.add(userRepository.findById(requestDTO.getIdUser()).get().getEmail());
            email.setSendToEmail(emails);
            email.setSubject(requestTypeRepository.findById(requestDTO.getIdRequestType()).get().getName());
            email.setText(statusRepository.findById(requestDTO.getIdStatus()).get().getName());
            emailController.sendEmail(email);
            return convertRequestToRequestDTO.convert(requestRepository.save(convertRequestDTOToRequest.convert(requestDTO)));
        }
        else {
            if(requestDTO.getIdStatus()==statusRepository.findByName("APPROVED").get().getId())
                requestDTO.setIdStatus(statusRepository.findByName("WAITING").get().getId());
            return convertRequestToRequestDTO.convert(requestRepository.save(convertRequestDTOToRequest.convert(requestDTO)));
        }
    }

    public void removeRequest(@RequestParam int id){
        requestRepository.deleteById(id);
    }

    private List<RequestEntity> search(String keySearch){
        if(keySearch.equals("")) return requestRepository.findAll();

        List<RequestEntity> RequestEntitiesByRequestType = requestRepository.findByRequestTypeName(keySearch);
        List<RequestEntity> RequestEntitiesByUser = requestRepository.findByUserEmail(keySearch);
        List<RequestEntity> RequestEntitiesByStatus = requestRepository.findByStatusName(keySearch);

        List<RequestEntity> results = new ArrayList<>();
        results.addAll(RequestEntitiesByRequestType);

        for(RequestEntity requestEntityByUser : RequestEntitiesByUser){
            Boolean add = true;
            for(RequestEntity result : results){
                if(requestEntityByUser.getId() == result.getId()){
                    add = false;
                    break;
                }
            }
            if(add){
                results.add(requestEntityByUser);
            }
        }

        for(RequestEntity requestEntityByStatus : RequestEntitiesByStatus){
            Boolean add = true;
            for(RequestEntity result : results){
                if(requestEntityByStatus.getId() == result.getId()){
                    add = false;
                    break;
                }
            }
            if(add){
                results.add(requestEntityByStatus);
            }
        }

        return results;
    }
}
