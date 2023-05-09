package com.group8.Resource;

import com.group8.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncentiveRequestRepository extends JpaRepository<IncentiveRequest, Long> {
    List<IncentiveRequest> findByAppUser(AppUser appUser);
    List<IncentiveRequest> findByStatus(String status);
}
