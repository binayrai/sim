package com.elite.service.impl;


import com.elite.RequestFetcher;
import com.elite.service.ApplicationService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Profile("default")
public class ApplicationServiceImpl implements ApplicationService {
    @Override
    public String getBaseUrl() {
        HttpServletRequest httpServletRequest = RequestFetcher.getCurrentRequest();
        return String.format("%s://%s:%d", httpServletRequest.getScheme(), httpServletRequest.getServerName(),
                httpServletRequest
                        .getServerPort());
    }
}
