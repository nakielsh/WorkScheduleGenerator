package pw.edu.wsg.appuser;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;

@Service
public class AppUserRepositoryService implements AppUserService {

    private static final Logger LOG = Logger.getLogger(AppUserRepositoryService.class.getName());
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String USER_NOT_FOUND_MSG = "User with username %s not found";

    public AppUserRepositoryService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public Long createAppUser(CreateAppUserRequest request) {
        LOG.info(format("Saving appUser: %s", request));
        AppUser newAppUser = new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getUsername(),
                AppUserRole.USER

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MSG, username)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByUsername(appUser.getUsername())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);
        return "";
    }

    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AppUser principal;
        String username;
        String name = "";
        if (null != securityContext.getAuthentication()) {
            principal = (AppUser) securityContext.getAuthentication().getPrincipal();
            username = securityContext.getAuthentication().getName();
            name = principal.getFirstName() + " " + principal.getLastName() + " (" + username + ") ";

        }
        return name;
    }

    public Long getId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AppUser principal;
        Long id = null;

        if (null != securityContext.getAuthentication()) {
            principal = (AppUser) securityContext.getAuthentication().getPrincipal();
            id = principal.getId();
        }
        return id;
    }

}
