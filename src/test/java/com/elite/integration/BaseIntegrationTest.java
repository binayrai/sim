package com.elite.integration;

import com.elite.Application;
import com.elite.domain.User;
import com.elite.dto.UserDTO;
import com.elite.dto.base.IUser;
import com.elite.dto.enums.UserRole;
import com.elite.repository.UserRepository;
import com.elite.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles(profiles = "test")
@WebIntegrationTest(randomPort = true)
public abstract class BaseIntegrationTest {

    protected MockMvc mockMvc;
    @Value("${local.server.port}")
    protected int port;
    protected URL base;

    @Autowired
    protected UserDetailsService userDetailsService;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private WebApplicationContext webappContext;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private MockHttpSession session;

    @Before
    public void baseSetup() throws Exception {
        this.base = new URL("http://localhost:" + port);
        mockMvc = MockMvcBuilders.webAppContextSetup(webappContext)
                .addFilters(springSecurityFilterChain)
                .build();

    }

    protected UserDTO createOrGetUserForRole(String userName, String password, UserRole userRole) {
        return createOrGetUserForRole(userName, password, userRole, userName);
    }

    protected UserDTO createOrGetUserForRole(String userName, String password, UserRole userRole, String
            newCompanyName) {
        List<User> allByUsernames = userService.findAllByUsernames(Collections.singleton(userName));
        if (allByUsernames.size() == 1) {
            return userService.get(allByUsernames.get(0).getId());
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userName);
        userDTO.setPassword(password);
        userDTO.setRoles(EnumSet.of(userRole));
        userDTO.setNewCompanyName(newCompanyName);
        userService.save(userDTO);
        return userDTO;
    }




    public MockHttpSession logIn(IUser userDTO) throws Exception {
        UsernamePasswordAuthenticationToken principal = this.getPrincipal(userDTO.getUsername());
        session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, new
                MockSecurityContext(principal));
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository
                .SPRING_SECURITY_CONTEXT_KEY);
        SecurityContextHolder.setContext(securityContext);
        return session;
    }

    protected UsernamePasswordAuthenticationToken getPrincipal(String username) {
        UserDetails user = this.userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());

        return authentication;
    }

    private class MockSecurityContext implements SecurityContext {
        private static final long serialVersionUID = -1386535243513362694L;
        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }
}
