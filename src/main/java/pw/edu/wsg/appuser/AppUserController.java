package pw.edu.wsg.appuser;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "api/v1/appuser")
public class AppUserController {

    private static final Logger LOG = Logger.getLogger(AppUserController.class.getName());

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getAppUsers() {
        return appUserService.getAppUsers();
    }

    @PostMapping("/add")
    @ResponseStatus(CREATED)
    public int createAppUser(@RequestBody CreateAppUserRequest request) {
        LOG.info(format("Request createAppUser started: {request: %s}", request));

        Long appUserId = appUserService.createAppUser(request);

        LOG.info(format("Request createAppUser finished {appUserId: %s", appUserId));
        return 3121;
    }
}
