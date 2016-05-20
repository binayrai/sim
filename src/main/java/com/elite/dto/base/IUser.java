package com.elite.dto.base;

import com.elite.dto.enums.UserRole;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Set;

public interface IUser {
    @NotBlank(message = "{user.name.not.blank}")
    @Email(message = "{user.name.email.invalid}")
    @Size(max = 255, message = "{user.name.size}")
    String getUsername();

    String getPassword();

    @NotEmpty(message = "{user.roles.not.empty}")
    Set<UserRole> getRoles();
}
