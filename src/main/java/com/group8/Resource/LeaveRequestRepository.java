package com.group8.Resource;

import com.group8.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByAppUser(AppUser appUser);
    List<LeaveRequest> findByStatus(String status);
    Optional<LeaveRequest> findById(Long id);

    List<LeaveRequest> findByStatusIsNot(String status);
}
