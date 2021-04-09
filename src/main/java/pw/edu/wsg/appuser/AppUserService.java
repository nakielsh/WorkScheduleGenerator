package pw.edu.wsg.appuser;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService {

    public Long createAppUser(CreateAppUserRequest request);
    public List<AppUser> getAppUsers();
}
