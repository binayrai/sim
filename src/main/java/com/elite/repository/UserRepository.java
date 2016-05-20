package com.elite.repository;


import com.elite.domain.User;
import com.elite.dto.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = ?1")
    User findOneByUsername(String username);

    List<User> findAllByUsernameInIgnoreCase(Collection<String> username);

    @Query("SELECT distinct u From User u")
    Page<User> findAll(Pageable p);


    @Query("select  u FROM User u INNER JOIN u.roles r WHERE (null IS ?1 OR u.username like ?1)")
    Page<User> findAllByUsernameLikeAndRolesIn(String userName, Pageable p);


}
