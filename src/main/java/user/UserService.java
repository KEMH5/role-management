package user;

import org.springframework.security.core.userdetails.UserDetailsService;
import user.request.ChangePasswordRequest;
import user.request.ProfileUpdateRequest;

public interface UserService extends UserDetailsService {
    void updateProfileInfo(ProfileUpdateRequest request, String userId);

    void changePassword(ChangePasswordRequest request, String userId);

    void deactivateAccount(String userId);

    void reactivateAccount(String userId);

    void deleteAccount(String userId);
}
