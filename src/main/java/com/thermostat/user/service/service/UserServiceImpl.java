package com.thermostat.user.service.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.thermostat.user.service.model.AppUser;
import com.thermostat.user.service.repository.ThermoRepository;
import com.thermostat.user.service.repository.ThermostatHistoryRepository;
import com.thermostat.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser save(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        log.info("saving new user {} in db ",  appUser.getName());
        return userRepository.save(appUser);
    }

    @Override
    public AppUser getUser(String userName) {
        log.info("fetching user {} from db ",userName);
        return userRepository.findAppUserByName(userName);
    }

    @Override
    public List<AppUser> getAllUsers() {
        log.info("fetching all users");
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByName(userName);
        if(user == null){
            log.error("user not found in db");
            throw  new UsernameNotFoundException("user not found in db");
        }else {
            log.info("user  found in db : {}",userName);
        }
        Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
    }
}
