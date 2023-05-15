package com.group8.appuser;

import com.group8.Resource.*;
import com.group8.announcement.Announcement;
import com.group8.announcement.AnnouncementRepository;
import com.group8.task.Task;
import com.group8.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping( "/employee")
@AllArgsConstructor
public class EmployeeController {
    private AppUserService appUserService;
    private ResourceRequestRepository resourceRequestRepository;
    private LeaveRequestRepository leaveRequestRepository;
    private IncentiveRequestRepository incentiveRequestRepository;
    private AnnouncementRepository announcementRepository;
    private TaskService taskService;

    @GetMapping("/home")
    public String showHomePage(Authentication authentication, Model model) {
        // retrieve the user's information from the Authentication object
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;
        // retrieve the ResourceRequest records for the specific employee
        List<ResourceRequest> resourceRequests = resourceRequestRepository.findByAppUser(appUser);
        List<IncentiveRequest> incentiveRequests=incentiveRequestRepository.findByAppUser(appUser);
        List<LeaveRequest> leaveRequests=leaveRequestRepository.findByAppUser(appUser);
        List<Announcement> announcements=announcementRepository.findByOrderByIdDesc();
        List<Task> tasks=taskService.getTasksForEmployee(username);

        // add the user's information and ResourceRequest records to the model
        model.addAttribute("fName", appUser.getFirstName());
        model.addAttribute("resourceRequests", resourceRequests);
        model.addAttribute("incentiveRequests", incentiveRequests);
        model.addAttribute("leaveRequests", leaveRequests);
        model.addAttribute("announcements", announcements);
        model.addAttribute("tasks", tasks);

        return "employeeHome"; // returns the name of your home page HTML template
    }
    @PostMapping("/mark-done")
    public String markTaskAsDone(@RequestParam("id") Long taskId) {
        Task task = taskService.getTaskById(taskId);

        task.setStatus("Done");
        taskService.saveTask(task);

        // Redirect to the employee's task list page
        return "redirect:/employee/home";
    }

    @GetMapping("/resourceRequest")
    public String showResourceRequestForm(Authentication authentication,Model model) {
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;
        model.addAttribute("employee", appUser);
        model.addAttribute("resourceRequest", new ResourceRequest());
        return "resource-request-form";
    }
    @PostMapping("/resourceRequest")
    public String submitResourceRequestForm(@ModelAttribute ResourceRequest resourceRequest, Authentication authentication) {
        resourceRequest.setRequestDate(LocalDate.now());

        // retrieve the user's information from the Authentication object
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;

        // set the AppUser object associated with the ResourceRequest object
        resourceRequest.setAppUser(appUser);

        resourceRequestRepository.save(resourceRequest);
        return "resource-request-success";
    }
    @GetMapping("/leaveRequest")
    public String showLeaveRequestForm(Authentication authentication,Model model) {
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;
        model.addAttribute("employee", appUser);
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "leave-request-form";
    }
    @PostMapping("/leaveRequest")
    public String submitLeaveRequestForm(@ModelAttribute LeaveRequest leaveRequest, Authentication authentication) {
        leaveRequest.setRequestDate(LocalDate.now());

        // retrieve the user's information from the Authentication object
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;

        // set the AppUser object associated with the ResourceRequest object
        leaveRequest.setAppUser(appUser);

        leaveRequestRepository.save(leaveRequest);
        return "resource-request-success";
    }
    @GetMapping("/incentiveRequest")
    public String showIncentiveRequestForm(Authentication authentication,Model model) {
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;
        model.addAttribute("employee", appUser);
        model.addAttribute("incentiveRequest", new IncentiveRequest());
        return "incentive-request-form";
    }
    @PostMapping("/incentiveRequest")
    public String submitIncentiveRequestForm(@ModelAttribute IncentiveRequest incentiveRequest, Authentication authentication) {
        incentiveRequest.setRequestDate(LocalDate.now());

        // retrieve the user's information from the Authentication object
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;

        // set the AppUser object associated with the ResourceRequest object
        incentiveRequest.setAppUser(appUser);

        incentiveRequestRepository.save(incentiveRequest);
        return "resource-request-success";
    }
}