package com.group8.Resource;

import com.group8.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, Long> {
    List<ResourceRequest> findByAppUser(AppUser appUser);
    List<ResourceRequest> findByStatus(String status);
    Optional<ResourceRequest> findById(Long id);

    List<ResourceRequest> findByStatusIsNot(String status);
}
