package com.elite.dto;

import com.elite.dto.base.IUser;
import com.elite.dto.enums.UserRole;

import javax.validation.constraints.Size;
import java.util.Set;

public class UserDTO extends DTO implements IUser {
    private String username;
    private String password;
    private Set<UserRole> roles;
    private String newCompanyName;
    private boolean changePassword;
    private boolean companyLeader;
    private boolean enabled;
    private boolean invalid;
    private String language;
    private boolean locked;
    private String lockReason;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }


    public String getNewCompanyName() {
        return newCompanyName;
    }

    public void setNewCompanyName(String newCompanyName) {
        this.newCompanyName = newCompanyName;
    }

    public void setChangePassword(final boolean changePassword) {
        this.changePassword = changePassword;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public boolean isCompanyLeader() {
        return companyLeader;
    }

    public void setCompanyLeader(boolean companyLeader) {
        this.companyLeader = companyLeader;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

}
