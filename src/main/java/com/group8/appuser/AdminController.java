package com.group8.appuser;

import com.group8.Resource.ResourceRequest;
import com.group8.Resource.ResourceRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping( "/admin")
@AllArgsConstructor
public class AdminController {
    private AppUserService appUserService;
    private ResourceRequestRepository resourceRequestRepository;
    @GetMapping("/home")
    public String showHomePage(Authentication authentication, Model model) {
        // retrieve the user's information from the Authentication object
        String username = authentication.getName();

        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;

        // retrieve the ResourceRequest records for the specific employee
        List<ResourceRequest> resourceRequests = resourceRequestRepository.findAll();

        // add the user's information and ResourceRequest records to the model
        model.addAttribute("fName", appUser.getFirstName());
        model.addAttribute("resourceRequests", resourceRequests);

        return "adminHome"; // returns the name of your home page HTML template
    }

    @GetMapping("/request")
    public String showResourceRequestForm(Authentication authentication,Model model) {
        String username = authentication.getName();
        UserDetails userDetails = appUserService.loadUserByUsername(username);
        AppUser appUser = (AppUser) userDetails;
        model.addAttribute("employee", appUser);
        model.addAttribute("resourceRequest", new ResourceRequest());
        return "resource-request-form";
    }
    @PostMapping("/request")
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
}