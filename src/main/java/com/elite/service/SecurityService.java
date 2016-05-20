package com.elite.service;


import com.elite.dto.CurrentUser;

import java.util.Date;

public interface SecurityService {

    CurrentUser getCurrentUser();

    String getUserToken(long userId, Date createdDate);
}
