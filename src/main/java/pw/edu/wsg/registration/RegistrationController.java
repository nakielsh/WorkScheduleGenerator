package pw.edu.wsg.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pw.edu.wsg.appuser.AppUser;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

//    @PostMapping("/cos")
//    public String register(@RequestBody RegistrationRequest request){
//        return registrationService.register(request);
//    }

    @GetMapping("")
    public  String showRegistrationForm(Model model){
        model.addAttribute("app_user", new AppUser());
        return "registration-form";
    }

    @PostMapping("/process_register")
    public String processRegistration(AppUser appUser){
        registrationService.register1(appUser);
        return "register-success";
    }

}
