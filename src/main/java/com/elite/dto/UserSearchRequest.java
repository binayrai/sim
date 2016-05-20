package com.elite.dto;


import com.elite.dto.enums.UserRole;

import java.util.EnumSet;
import java.util.Set;

public class UserSearchRequest extends SearchRequest {

    public UserSearchRequest() {
        super(0, 10);
        this.name = "";
        this.roles = EnumSet.of(UserRole.ROLE_ADMIN);
        this.enabled = -1;
        this.locked = -1;
    }

    public UserSearchRequest(String name,  Set<UserRole> userRoles, int page, int size) {
        super(page, size);
        this.name = name;

        this.roles = userRoles;
        this.enabled = -1;
        this.locked = -1;
    }

    private String name;
    private Set<UserRole> roles;
    private byte enabled; //-1 -> both 0 -> enabled, 1-> disabled
    private byte locked; //-1 -> both 0 -> unlocked, 1 -> locked

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public byte getEnabled() {
        return enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    public byte getLocked() {
        return locked;
    }

    public void setLocked(byte locked) {
        this.locked = locked;
    }
}
