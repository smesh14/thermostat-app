package com.thermostat.user.service.service;

import java.util.List;
import com.thermostat.user.service.model.AppUser;

public interface UserService {
    AppUser save(AppUser appUser);
    AppUser getUser(String userName);
    List<AppUser> getAllUsers();

}
