package com.backend.helpdesk.service;

import com.backend.helpdesk.DTO.ProblemDTO;
import com.backend.helpdesk.common.Constants;
import com.backend.helpdesk.common.Email;
import com.backend.helpdesk.controller.EmailController;
import com.backend.helpdesk.converters.problem.ConvertProblemDTOToProblem;
import com.backend.helpdesk.converters.problem.ConvertProblemToProblemDTO;
import com.backend.helpdesk.entity.ProblemEntity;
import com.backend.helpdesk.entity.RoleEntity;
import com.backend.helpdesk.repository.ProblemRepository;
import com.backend.helpdesk.repository.ProblemTypeRepository;
import com.backend.helpdesk.repository.StatusRepository;
import com.backend.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @Autowired
    private ConvertProblemDTOToProblem convertProblemDTOToProblem;

    @Autowired
    private ConvertProblemToProblemDTO convertProblemToProblemDTO;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailController emailController;

    public List<ProblemDTO> getAllProblem(){
        return convertProblemToProblemDTO.convert(problemRepository.findAll());
    }

    public List<ProblemDTO> searchProblemAndPagination(int page, int items, String sortBy, String search){

        List<ProblemEntity> problemEntities =  this.search(search);

        List<ProblemEntity> result = new ArrayList<>();

        if(sortBy.equals("Email")){
            this.sortByEmail(problemEntities);
        }
        if(sortBy.equals("Status")){
            this.sortByStatus(problemEntities);
        }
        if(sortBy.equals("Problem Type")){
            this.sortByProblemType(problemEntities);
        }

        int n = (page+1)*items;
        if(n<problemEntities.size()) n= problemEntities.size();

        for(int i=page*items; i<n; i++){
            result.add(problemEntities.get(i));
        }

        return convertProblemToProblemDTO.convert(result);
    }

    public ProblemDTO addProblem(ProblemDTO problemDTO){
        Email email = new Email();
        List<String> emails = new ArrayList<>();
        emails.add(Constants.MY_EMAIL);
        email.setSendToEmail(emails);
        email.setSubject(problemTypeRepository.findById(problemDTO.getIdProblemType()).get().getName());
        email.setText(problemDTO.getDescription());

        problemDTO.setId(0);
        problemDTO.setIdStatus(statusRepository.findByName("STATUS_WAITING").get().getId());
        return convertProblemToProblemDTO.convert(problemRepository.save(convertProblemDTOToProblem.convert(problemDTO)));
    }

    public ProblemDTO putProblem(ProblemDTO problemDTO){
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
            emails.add(userRepository.findById(problemDTO.getIdUser()).get().getEmail());
            email.setSendToEmail(emails);
            email.setSubject(problemTypeRepository.findById(problemDTO.getIdProblemType()).get().getName());
            email.setText(statusRepository.findById(problemDTO.getIdStatus()).get().getName());
            emailController.sendEmail(email);
            return convertProblemToProblemDTO.convert(problemRepository.save(convertProblemDTOToProblem.convert(problemDTO)));
        }
        else {
            if(problemDTO.getIdStatus()==statusRepository.findByName("APPROVED").get().getId())
                problemDTO.setIdStatus(statusRepository.findByName("WAITING").get().getId());
            return convertProblemToProblemDTO.convert(problemRepository.save(convertProblemDTOToProblem.convert(problemDTO)));
        }
    }

    public void removeProblem(@RequestParam int id){
        problemRepository.deleteById(id);
    }

    private List<ProblemEntity> search(String keySearch){
        if(keySearch.equals("")) return problemRepository.findAll();

        List<ProblemEntity> problemEntitiesByProblemType = problemRepository.findByProblemTypeName(keySearch);
        List<ProblemEntity> problemEntitiesByUser = problemRepository.findByUserEmail(keySearch);
        List<ProblemEntity> problemEntitiesByStatus = problemRepository.findByStatusName(keySearch);

        List<ProblemEntity> results = new ArrayList<>();
        results.addAll(problemEntitiesByProblemType);

        for(ProblemEntity problemEntityByUser : problemEntitiesByUser){
            Boolean add = true;
            for(ProblemEntity result : results){
                if(problemEntityByUser.getId() == result.getId()){
                    add = false;
                    break;
                }
            }
            if(add){
                results.add(problemEntityByUser);
            }
        }

        for(ProblemEntity problemEntityByStatus : problemEntitiesByStatus){
            Boolean add = true;
            for(ProblemEntity result : results){
                if(problemEntityByStatus.getId() == result.getId()){
                    add = false;
                    break;
                }
            }
            if(add){
                results.add(problemEntityByStatus);
            }
        }

        return results;
    }

    private void sortByEmail(List<ProblemEntity> problemEntitys){
        for(int i=0; i<problemEntitys.size()-1; i++){
            for(int j=i+1; j<problemEntitys.size(); j++){
                if(problemEntitys.get(i).getUser().getEmail().compareTo(problemEntitys.get(j).getUser().getEmail())>0){
                    ProblemEntity swap = problemEntitys.get(i);
                    problemEntitys.set(i,problemEntitys.get(j));
                    problemEntitys.set(j,swap);
                }
            }
        }
    }

    private void sortByStatus(List<ProblemEntity> problemEntitys){
        for(int i=0; i<problemEntitys.size()-1; i++){
            for(int j=i+1; j<problemEntitys.size(); j++){
                if(problemEntitys.get(i).getStatus().getName().compareTo(problemEntitys.get(j).getStatus().getName())>0){
                    ProblemEntity swap = problemEntitys.get(i);
                    problemEntitys.set(i,problemEntitys.get(j));
                    problemEntitys.set(j,swap);
                }
            }
        }
    }

    private void sortByProblemType(List<ProblemEntity> problemEntitys){
        for(int i=0; i<problemEntitys.size()-1; i++){
            for(int j=i+1; j<problemEntitys.size(); j++){
                if(problemEntitys.get(i).getProblemType().getName().compareTo(problemEntitys.get(j).getProblemType().getName())>0){
                    ProblemEntity swap = problemEntitys.get(i);
                    problemEntitys.set(i,problemEntitys.get(j));
                    problemEntitys.set(j,swap);
                }
            }
        }
    }
}
