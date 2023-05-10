package com.thermostat.user.service.repository;

import com.thermostat.user.service.model.Thermostat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThermoRepository extends JpaRepository<Thermostat,Long> {
    Thermostat findByName(String name);
}
