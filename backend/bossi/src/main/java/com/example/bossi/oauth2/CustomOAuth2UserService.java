package com.example.bossi.oauth2;

import com.example.bossi.config.auth.PrincipalDetails;
import com.example.bossi.entity.Role;
import com.example.bossi.entity.SocialType;
import com.example.bossi.entity.User;
import com.example.bossi.oauth2.UserInfo.OAuth2UserInfo;
import com.example.bossi.oauth2.UserInfo.OAuth2UserInfoFactory;
import com.example.bossi.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser() ");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        log.info("loadUser: {}", oAuth2User.getAttributes());
        //System.out.println("attributes" + super.loadUser(userRequest).getAttributes());
        OAuth2User member = delegate.loadUser(userRequest);
        //여기서 attriutes를 찍어보면 모두 각기 다른 이름으로 데이터가 들어오는 것을 확인할 수 있다.
        try{
            return process(userRequest, member);
        } catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        SocialType providerType = SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

        Optional<User> savedUser = userRepository.findByEmail(userInfo.getEmail());
        //log.info("saveUser process(): {}", savedUser.getEmail());

        User userEntity;

        if (savedUser.isPresent()) {
            userEntity = userRepository.findUserByEmail(userInfo.getEmail());
            updateUser(userEntity, userInfo);
        } else {
            userEntity = createUser(userInfo, providerType);
        }

        return new PrincipalDetails(userEntity, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, SocialType socialType) {
        UUID tempPassword = UUID.randomUUID();
        String referralCode = RandomStringUtils.randomAlphanumeric(10);

        User member = User.builder()
                .nickName(userInfo.getName())
                .email(userInfo.getEmail())
                .password(String.valueOf(tempPassword))
                .role(Role.USER)
                .phoneNum(socialType.name())
                .imageUrl("bossi_basic_profile_img.png")
                .registerStatus(Boolean.FALSE)
                .referralCode(referralCode)
                .socialType(socialType).build();

        return userRepository.save(member);
    }

    private void updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getLastName() != null && !user.getNickName().equals(userInfo.getName())) {
            user.setNickName(userInfo.getName());
        }
    }

}
