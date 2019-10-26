package com.backend.helpdesk.repository;

import com.backend.helpdesk.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Integer> {
    List<RequestEntity> findByRequestTypeName(String problemType);
    List<RequestEntity> findByUserEmail(String email);
    List<RequestEntity> findByStatusName(String statusName);
    List<RequestEntity> findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByUserEmailAsc (String email, String status, String requestType, String description);
    List<RequestEntity> findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByStatusNameAsc (String email, String status, String requestType, String description);
    List<RequestEntity> findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByRequestTypeNameAsc (String email, String status, String requestType, String description);
    List<RequestEntity> findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContainingOrderByCreateAtAsc (String email, String status, String requestType, String description);
    List<RequestEntity> findByOrderByRequestTypeAsc();
    List<RequestEntity> findByOrderByCreateAtAsc();
    List<RequestEntity> findByOrderByStatusNameAsc();
    List<RequestEntity> findByUserEmailContainingOrStatusNameContainingOrRequestTypeNameContainingOrDescriptionContaining(String email, String status, String requestType, String description);
}
