package pw.edu.wsg.registration;

import org.springframework.stereotype.Service;
import pw.edu.wsg.appuser.AppUser;
import pw.edu.wsg.appuser.AppUserRepositoryService;
import pw.edu.wsg.appuser.AppUserRole;

@Service
public class RegistrationService {

    private final AppUserRepositoryService appUserRepositoryService;

    public RegistrationService(AppUserRepositoryService appUserRepositoryService) {
        this.appUserRepositoryService = appUserRepositoryService;
    }

    public String register(RegistrationRequest request) {

        return appUserRepositoryService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getPassword(),
                        request.getUsername(),
                        AppUserRole.USER
                )
        );
    }
}
