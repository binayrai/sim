package com.elite.service.impl;


import com.elite.controller.exceptions.ForbiddenException;
import com.elite.dto.CurrentUser;
import com.elite.service.SecurityService;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final String SENDER_TOKEN_SALT =
            "f9f925667b9a815dad7c1943dfe0d48dd0ca36e79719294c6dc0290c48d61cfd" +
                    "d6bea5d2b35e449a088f906a8d50b687d140a64967a89bd5fa1f57b6a2fc3b72" +
                    "3fa66664e8e0cbc8d26ad5d8722d154ed6a4c200773f82adb4e55807b318d120" +
                    "8a6507f3ee60adca90be739e8bee309165fcfee245011523eb64ba051396fab1" +
                    "dc74675d95b14bd2f4c0825b3edcbe607de217250fb2eebff049c2070baeca73" +
                    "3072823bfa43eed2eb9bb2e52b70bfe9c887a0ade340ebd81222e6533c53ee1e" +
                    "e5f47e7a177a2c3ccea531fa846c85d86c9961ffa0029e2c3b0e9dce9554ee4c" +
                    "af8827de88cbccfee7591260172d705042d83ea86cf1df4a76f1ddb411e0f874" +
                    "bd9f16cc56f1a833aa61190b56391b3d527d3a64e9e370789d1a09bacdd835a6" +
                    "04174d9b60ca9088bed4ec5d4547c6199939e0a7c6574e8c8c2014ae2bbc32d9" +
                    "4195f183954af8a937d059c52df09681a5229feeec66b9702c2977505454cdb2" +
                    "47bffd0c0f78036eb00023324dfb2504fdf913ad10a600868f8568a30d4d19ed" +
                    "0e0c498d451b1e78a0efe04455df3c2a934c5376adf4b4cb2c0f9eb92f3ab874" +
                    "5191ad74aca1c203b3662983402e3822d537a19330f6d2870e6754e7f4cc6deb" +
                    "6cf7f3853697f1bee56d83c7b43a1c2c3806004053e71098d25fb98aa4eb13dd" +
                    "2acd361537d50d7ac97f0960d2ed09718cc83e94ec329566c1220f69cdb4fa24";
    private final ShaPasswordEncoder encoder;

    public SecurityServiceImpl() {
        this.encoder = new ShaPasswordEncoder(512);
    }

    @Override
    public CurrentUser getCurrentUser() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication auth = securityContext.getAuthentication();
        //getAuthentication may return null if no auth info is available
        if (auth != null) {
            final Object principal = auth.getPrincipal();
            //getPrincipal returns a string object for anonymous users
            if (principal instanceof CurrentUser) {
                CurrentUser currentUser = (CurrentUser) principal;


                return currentUser;
            }
        }
        return null;
    }

   /* @Override
    public boolean hasTeams() {
        final CurrentUser currentUser = getCurrentUser();
        return currentUser != null && (currentUser.getCompanyId() != null || !currentUser.getLeadTeamIds().isEmpty());
    }*/

   /* @Override
    public long getUserIdOrThrow() {
        final CurrentUser currentUser = getCurrentUser();
        if (currentUser == null || currentUser.getUsername() == null) {
            throw new ForbiddenException("no valid user");
        }
        return currentUser.getId();
    }*/

  /*  @Override
    public long getCompanyIdOrThrow() {
        final CurrentUser currentUser = getCurrentUser();
        if (currentUser == null || currentUser.getCompanyId() == null) {
            throw new ForbiddenException("company required");
        }
        return currentUser.getCompanyId();
    }
*/
    @Override
    public String getUserToken(long userId, Date createdDate) {
        return encoder.encodePassword(String.valueOf(new Date(createdDate.getTime()).getTime()) +
                String.valueOf(userId), SENDER_TOKEN_SALT);

    }
}
