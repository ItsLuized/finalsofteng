package com.softeng.finalsofteng.service;

import org.springframework.security.core.Authentication;

public interface IAuthService {

    Authentication getAuthentication();
}
