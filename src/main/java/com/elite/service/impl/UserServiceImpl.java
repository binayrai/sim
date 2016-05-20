package com.elite.service.impl;

import com.elite.domain.User;
import com.elite.dto.CurrentUser;
import com.elite.dto.PagedResultDTO;
import com.elite.dto.UserDTO;
import com.elite.dto.UserSearchRequest;
import com.elite.dto.enums.UserRole;
import com.elite.repository.UserRepository;
import com.elite.service.SecurityService;
import com.elite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllByUsernames(Collection<String> usernames) {
        return userRepository.findAllByUsernameInIgnoreCase(usernames);
    }

    @Override
    public PagedResultDTO<UserDTO> list(UserSearchRequest userSearchRequest) {
        Page<User> page = userRepository.findAllByUsernameLikeAndRolesIn(
                "%" + userSearchRequest.getName() + "%",
                new PageRequest(userSearchRequest.getPage(), userSearchRequest.getSize()));
        List<UserDTO> dtos = page.getContent().stream().map(u -> convert(u)).collect(toList());
        return new PagedResultDTO<>(dtos, page.getTotalElements());
    }


    private Boolean getTrueOrFalseOrNull(byte enabled) {
        if (enabled == 0) {
            return false;
        }
        if (enabled > 0) {
            return true;
        }
        return null;
    }

    @Override
    public void save(UserDTO dto) {
        User entity = new User();
        if (dto.getId() > 0) {
            entity = userRepository.getOne(dto.getId());
        }
        entity.setUsername(dto.getUsername());
        if (dto.isChangePassword() || entity.getId() <= 0) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity.setEnabled(true);
        }
        entity.setLocked(dto.isLocked());
        if (dto.isLocked()) {
            entity.setLockReason(dto.getLockReason());
            entity.setLockDate(new Date());
        }
        entity.setCreatedDate(new Date());
        entity.setLastModifiedDate(new Date());
        entity.setRoles(dto.getRoles());
        userRepository.save(entity);
    }

    @Override
    public UserDTO get(long id) {
        final User entity = userRepository.findOne(id);
        return convert(entity);
    }

    @Override
    public boolean deleteUser(long userId) {
        User entity = userRepository.findOne(userId);
        userRepository.delete(entity);
        return true;

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(s + " does not exist");
        }
        return convertToCurrentUser(user);
    }

    private CurrentUser convertToCurrentUser(User user) {
        final EnumSet<UserRole> roles = EnumSet.copyOf(user.getRoles());
        final String userToken = securityService.getUserToken(user.getId(), user.getCreatedDate());
        return new CurrentUser(user.getId(), user.getUsername(), user.getPassword(), user.isEnabled(),
                user.isLocked(), roles, user.getCreatedDate(), userToken);
    }

    private UserDTO convert(User entity) {
        UserDTO dto = convertWithoutCompanyOrRoles(entity);
        if (dto != null) {
            dto.setRoles(new HashSet<>(entity.getRoles()));

        }
        return dto;
    }

    private UserDTO convertWithoutCompanyOrRoles(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEnabled(entity.isEnabled());

        dto.setLocked(entity.isLocked());
        dto.setLockReason(entity.getLockReason());

        return dto;
    }
}
