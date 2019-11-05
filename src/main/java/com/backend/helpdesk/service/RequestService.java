package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.RequestDTO;
import com.backend.helpdesk.common.Email;
import com.backend.helpdesk.controller.EmailController;
import com.backend.helpdesk.converters.bases.Converter;
import com.backend.helpdesk.converters.requestConverter.ConvertRequestToRequestDTO;
import com.backend.helpdesk.converters.statusConverter.ConvertStatusToStatusDTO;
import com.backend.helpdesk.entity.RequestEntity;
import com.backend.helpdesk.repository.RequestRepository;
import com.backend.helpdesk.repository.RequestTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.mail.username}")
    String emailAdmin;

    public List<RequestDTO> getAllRequest() {
        return convertRequestToRequestDTO.convert(requestRepository.findAll());
    }

    public int getSize(String search) {
        return requestRepository.findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContaining(search, search, search, search).size();
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

        Email email = new Email();
        List<String> emails = new ArrayList<>();
        emails.add(emailAdmin);
        email.setSendToEmail(emails);
        email.setSubject(requestEntity.getRequestType().getName());
        email.setText("Request by email: " + requestEntity.getUser().getEmail() +
                "\nRequest type: " + requestEntity.getRequestType().getName() +
                "\nCreate At: " + requestEntity.getCreateAt() +
                "\nDay request: " + requestEntity.getDayRequest() +
                "\nDescription: " + requestEntity.getDescription());
        emailController.sendEmail(email);

        return convertRequestToRequestDTO.convert(requestEntity);
    }

    public RequestDTO putRequest(RequestDTO requestDTO) {
        RequestEntity requestEntity = requestRepository.save(requestDTORequestEntityConverter.convert(requestDTO));

        Email email = new Email();
        List<String> emails = new ArrayList<>();
        emails.add(requestEntity.getUser().getEmail());

        email.setSendToEmail(emails);
        email.setSubject(requestEntity.getRequestType().getName());
        email.setText(requestEntity.getStatus().getName());

        emailController.sendEmail(email);

        return convertRequestToRequestDTO.convert(requestEntity);
    }

    public void removeRequest(@RequestParam int id) {
        requestRepository.deleteById(id);
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
