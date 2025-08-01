package user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import user.request.ProfileUpdateRequest;

@Service
public class UserMapper {

    public void mergeUserInfo(final User savedUser, final ProfileUpdateRequest request){
        if(StringUtils.isNotBlank(request.getFirstName())
            && !savedUser.getFirstName().equals(request.getFirstName()))
        {
            savedUser.setFirstName(request.getFirstName());
        }
        if(StringUtils.isNotBlank(request.getLastName())
        && !savedUser.getLastName().equals(request.getLastName())){
            savedUser.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null
        && !savedUser.getDateOfBirth().equals(request.getDateOfBirth())
            && !request.getDateOfBirth().equals(savedUser.getDateOfBirth()))
        {
            savedUser.setDateOfBirth(request.getDateOfBirth());
        }



    }
}
