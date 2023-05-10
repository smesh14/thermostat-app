package com.thermostat.user.service.repository;

import com.thermostat.user.service.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByName(String name);

    void deleteAll();

}
