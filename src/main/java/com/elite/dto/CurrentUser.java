package com.elite.dto;

import com.elite.dto.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private long id;
    private EnumSet<UserRole> roles;
    private long lastTeamUpdate;
    private Set<Long> leadTeamIds;
    private Date createdDate;
    private String userToken;

    public CurrentUser(long id, String username, String password, boolean enabled, EnumSet<UserRole> roles) {
        super(username, password, enabled, true, true, true,
                roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(toList()));
        this.id = id;
        this.roles = roles;

    }

    public CurrentUser(long id, String username, String password, boolean enabled, boolean locked, EnumSet<UserRole> roles, Date createdDate, String userToken) {
        super(username, password, enabled, true, true, !locked,
                roles.stream().map(r -> new SimpleGrantedAuthority(r.name())).collect(toList()));
        this.id = id;
        this.roles = roles;
        this.createdDate = createdDate;
        this.userToken = userToken;
    }


    public long getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }

    @JsonIgnore
    public EnumSet<UserRole> getRoles() {
        return roles;
    }



    public Set<Long> getLeadTeamIds() {
        return leadTeamIds != null ? leadTeamIds : Collections.emptySet();
    }

    public void setLeadTeamIds(final Set<Long> leadTeamIds) {
        lastTeamUpdate = System.currentTimeMillis();
        this.leadTeamIds = Collections.unmodifiableSet(leadTeamIds);
    }

    public long getLastTeamUpdateTimestamp() {
        return lastTeamUpdate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
