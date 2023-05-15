package com.group8.appuser;

import com.group8.Resource.*;
import com.group8.announcement.Announcement;
import com.group8.announcement.AnnouncementRepository;
import com.group8.announcement.AnnouncementService;
import com.group8.registration.RegistrationRequest;
import com.group8.registration.RegistrationService;
import com.group8.task.Task;
import com.group8.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping( "/admin")
@AllArgsConstructor
public class AdminController {
    private AppUserRepository appUserRepository;
    private RegistrationService registrationService;
    private ResourceRequestRepository resourceRequestRepository;
    private LeaveRequestRepository leaveRequestRepository;
    private IncentiveRequestRepository incentiveRequestRepository;
    private AnnouncementRepository announcementRepository;
    private AnnouncementService announcementService;
    private final TaskService taskService;
    private AppUserService appUserService;

    @GetMapping("/home")
    public String showHomePage(Model model) {
        // retrieve the ResourceRequest records for the specific employee
        List<ResourceRequest> resourceRequests = resourceRequestRepository.findByStatus("Pending");
        List<IncentiveRequest> incentiveRequests=incentiveRequestRepository.findByStatus("Pending");
        List<LeaveRequest> leaveRequests=leaveRequestRepository.findByStatus("Pending");
        List<Announcement> announcements=announcementRepository.findByOrderByIdDesc();
        List<Task> tasks=taskService.getAllTasks();
        // add the user's information and ResourceRequest records to the model
        model.addAttribute("resourceRequests", resourceRequests);
        model.addAttribute("incentiveRequests", incentiveRequests);
        model.addAttribute("leaveRequests", leaveRequests);
        model.addAttribute("announcements", announcements);
        model.addAttribute("tasks", tasks);
        return "adminHome"; // returns the name of your home page HTML template
    }
    @GetMapping("/tasks/create")
    public String showCreateTaskForm(Model model) {
        List<Employee> enabledEmployees = appUserRepository.findByEnabledTrue();
        model.addAttribute("task", new Task());
        model.addAttribute("employees", enabledEmployees);
        return "createTask";
    }
    @PostMapping("/tasks/create")
    public String createTask(@ModelAttribute Task task, @RequestParam("selectedEmail") String selectedEmail) {
        task.setStatus("Pending");
        UserDetails userDetails = appUserService.loadUserByUsername(selectedEmail);
        AppUser appUser = (AppUser) userDetails;
        task.setAppUser(appUser);
        taskService.createTask(task);
        return "redirect:/admin/home";
    }
    @PostMapping("/delete-task")
    public String deleteTask(@RequestParam("id") Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/admin/home";
    }

    @GetMapping("/announcements/create")
    public String showCreateAnnouncementForm(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "createAnnouncement";
    }
    @PostMapping("/announcements/create")
    public String submitAnnouncementForm(@ModelAttribute Announcement announcement) {
        announcementService.saveAnnouncement(announcement);
        return "redirect:/admin/home";
    }
    @PostMapping("/delete-announcement")
    public String deleteAnnouncement(@RequestParam Long id) {
        announcementRepository.deleteById(id);
        return "redirect:/admin/home";
    }
    @GetMapping("/view-past-requests")
    public String showpastPage(Model model) {
        // retrieve the ResourceRequest records for the specific employee
        List<ResourceRequest> resourceRequests = resourceRequestRepository.findByStatusIsNot("Pending");
        List<IncentiveRequest> incentiveRequests=incentiveRequestRepository.findByStatusIsNot("Pending");
        List<LeaveRequest> leaveRequests=leaveRequestRepository.findByStatusIsNot("Pending");

        // add the user's information and ResourceRequest records to the model
        model.addAttribute("resourceRequests", resourceRequests);
        model.addAttribute("incentiveRequests", incentiveRequests);
        model.addAttribute("leaveRequests", leaveRequests);

        return "pastRecord"; // returns the name of your home page HTML template
    }
    @GetMapping("/registration")
    public String showRegistration(Model model) {
        model.addAttribute("request", new RegistrationRequest());

        return "registration";
    }
    @GetMapping("/disable-user")
    public String showDisableUserPage(Model model) {
        // retrieve the ResourceRequest records for the specific employee
        List<Employee> appUsers = appUserRepository.findByEnabledTrue();

        model.addAttribute("appUsers", appUsers);

        return "disableUser"; // returns the name of your home page HTML template
    }
    @PostMapping("/disable-user")
    public String disableUser(@RequestParam("id") String email) {
        appUserRepository.disableAppUser(email);

        return "redirect:/admin/disable-user"; // returns the name of your home page HTML template
    }
    @GetMapping("/enable-user")
    public String showEnableUserPage(Model model) {
        // retrieve the ResourceRequest records for the specific employee
        List<Employee> appUsers = appUserRepository.findByEnabledFalse();

        model.addAttribute("appUsers", appUsers);

        return "enableUser"; // returns the name of your home page HTML template
    }
    @PostMapping("/enable-user")
    public String enableUser(@RequestParam("id") String email) {
        appUserRepository.enableAppUser(email);

        return "redirect:/admin/enable-user"; // returns the name of your home page HTML template
    }
    @PostMapping("/process")
    public String register( @ModelAttribute RegistrationRequest request){
        return registrationService.register("employee", request);
    }
    @PostMapping("/approve-resource")
    public String approveResourceRequest(@RequestParam("id") Long id) {
        // set the AppUser object associated with the ResourceRequest object
        Optional<ResourceRequest> optResourceRequest = resourceRequestRepository.findById(id);
        if (!optResourceRequest.isPresent()) {
            return "redirect:/admin/home";
        }
        ResourceRequest resourceRequest = optResourceRequest.get();
        resourceRequest.setStatus("Approved");
        resourceRequestRepository.save(resourceRequest);
        return "redirect:/admin/home";
    }
    @PostMapping("/reject-resource")
    public String rejectResourceRequest(@RequestParam("id") Long id) {
        Optional<ResourceRequest> optResourceRequest = resourceRequestRepository.findById(id);
        if (!optResourceRequest.isPresent()) {
            return "redirect:/admin/home";
        }
        ResourceRequest resourceRequest = optResourceRequest.get();
        resourceRequest.setStatus("Rejected");
        resourceRequestRepository.save(resourceRequest);
        return "redirect:/admin/home";
    }
    @PostMapping("/approve-leave")
    public String approveLeaveRequest(@RequestParam("id") Long id) {
        Optional<LeaveRequest> optLeaveRequest = leaveRequestRepository.findById(id);
        if (!optLeaveRequest.isPresent()) {
            return "redirect:/admin/home";
        }
        LeaveRequest leaveRequest = optLeaveRequest.get();
        leaveRequest.setStatus("Approved");
        leaveRequestRepository.save(leaveRequest);
        return "redirect:/admin/home";
    }
    @PostMapping("/reject-leave")
    public String rejectLeaveRequest(@RequestParam("id") Long id) {
        Optional<LeaveRequest> optLeaveRequest = leaveRequestRepository.findById(id);
        if (!optLeaveRequest.isPresent()) {
            return "redirect:/admin/home";
        }
        LeaveRequest leaveRequest = optLeaveRequest.get();
        leaveRequest.setStatus("Rejected");
        leaveRequestRepository.save(leaveRequest);
        return "redirect:/admin/home";
    }
    @PostMapping("/approve-incentive")
    public String approveIncentiveRequest(@RequestParam("id") Long id) {
        Optional<IncentiveRequest> optIncentiveRequest = incentiveRequestRepository.findById(id);
        if (!optIncentiveRequest.isPresent()) {
            return "redirect:/admin/home";
        }
        IncentiveRequest incentiveRequest = optIncentiveRequest.get();
        incentiveRequest.setStatus("Approved");
        incentiveRequestRepository.save(incentiveRequest);
        return "redirect:/admin/home";
    }
    @PostMapping("/reject-incentive")
    public String rejectIncentiveRequest(@RequestParam("id") Long id) {
        Optional<IncentiveRequest> optIncentiveRequest = incentiveRequestRepository.findById(id);
        if (!optIncentiveRequest.isPresent()) {
            return "redirect:/admin/home";
        }
        IncentiveRequest incentiveRequest = optIncentiveRequest.get();
        incentiveRequest.setStatus("Rejected");
        incentiveRequestRepository.save(incentiveRequest);
        return "redirect:/admin/home";
    }
}