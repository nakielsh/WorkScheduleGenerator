package pw.edu.wsg.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pw.edu.wsg.appuser.AppUser;
import pw.edu.wsg.appuser.AppUserRepositoryService;

import java.util.logging.Logger;

import static java.lang.String.format;

@Controller
@RequestMapping(path = "/registration")
public class RegistrationController {

    private static final Logger LOG = Logger.getLogger(RegistrationService.class.getName());

    private final AppUserRepositoryService appUserRepositoryService;

    public RegistrationController(AppUserRepositoryService appUserRepositoryService) {
        this.appUserRepositoryService = appUserRepositoryService;
    }

    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("app_user", new AppUser());
        return "registration-form";
    }

    @PostMapping("/success")
    public String processRegistration(@Validated AppUser app_user, RedirectAttributes redirectAttributes) {

        try {
            appUserRepositoryService.signUpUser(app_user);
        } catch (Exception e) {
            LOG.info(format("Error appUser: %s", app_user.getFirstName()));
            redirectAttributes.addAttribute("failed", true);
            return "redirect:/registration";
        }
        return "register-success";
    }

}
