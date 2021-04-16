package pw.edu.wsg.appuser;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService extends UserDetailsService {

    public Long createAppUser(CreateAppUserRequest request);
    public List<AppUser> getAppUsers();
}
