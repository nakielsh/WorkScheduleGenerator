package pw.edu.wsg.appuser;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

@Service
public class AppUserRepositoryService implements AppUserService {

    private static final Logger LOG = Logger.getLogger(AppUserRepositoryService.class.getName());
    private final AppUserRepository appUserRepository;

    public AppUserRepositoryService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    @Override
    public Long createAppUser(CreateAppUserRequest request) {
        LOG.info(format("Saving appUser: %s", request));
        AppUser newAppUser = new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getUsername()
        );
        AppUser savedAppUser = appUserRepository.save(newAppUser);
        Long newAppUserId = savedAppUser.getId();

        LOG.info(format("Saved appUser: {id: %d}", newAppUserId));
        return newAppUserId;
    }

    @Override
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
