package user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.User;
import user.UserMapper;
import user.UserRepository;
import user.UserService;
import user.exception.BusinessException;
import user.exception.ErrorCode;
import user.request.ChangePasswordRequest;
import user.request.ProfileUpdateRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private  final PasswordEncoder passwordEncoder;
    private final UserMapper usermapper;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(userEmail).
                orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + userEmail));
    }

    @Override
    public void updateProfileInfo(ProfileUpdateRequest request, String userId) {
        final User savedUser = userRepository.findById(userId)
                .orElseThrow(() ->
                   new BusinessException(ErrorCode.USER_NOT_FOUND, userId)
                );

        this.usermapper.mergeUserInfo(savedUser, request);
        this.userRepository.save(savedUser);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())){
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }
        final User savedUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));
        if(!this.passwordEncoder.matches(request.getCurrentPassword(),
                savedUser.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }

        final String encoded = passwordEncoder.encode(request.getNewPassword());
        savedUser.setPassword(encoded);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deactivateAccount(final String userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(!user.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        user.setEnabled(false);
        this.userRepository.save(user);
    }

    @Override
    public void reactivateAccount(String userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if(user.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(String userId) {

    }

}
