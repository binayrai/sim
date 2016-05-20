package com.elite.service;


import com.elite.domain.User;
import com.elite.dto.PagedResultDTO;
import com.elite.dto.UserDTO;
import com.elite.dto.UserSearchRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllByUsernames(Collection<String> usernames);
    PagedResultDTO<UserDTO> list(UserSearchRequest userSearchRequest);

    void save(UserDTO dto);

    UserDTO get(long id);

    boolean deleteUser(long userId);


}
