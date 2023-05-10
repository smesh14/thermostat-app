package com.thermostat.user.service.service;

import java.util.*;
import java.util.stream.Collectors;
import com.thermostat.user.service.customException.ThermostatNotFoundException;
import com.thermostat.user.service.customException.UserNotFoundException;
import com.thermostat.user.service.model.AppUser;
import com.thermostat.user.service.model.Thermostat;
import com.thermostat.user.service.repository.ThermoRepository;
import com.thermostat.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ThermostatServiceImpl implements ThermostatService {

    private final ThermoRepository thermoRepository;
    private final UserRepository userRepository;

    @Override
    public Thermostat addThermostat(Thermostat thermostat, Long creatorId) {
        Optional<AppUser> appUserOptional = userRepository.findById(creatorId);
        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();
            thermostat.setCreatedDate(new Date());
            thermostat.setCreator(appUser);
            thermostat.setActive(true);
        } else {
            log.error("could not find creator  {}", creatorId);
            throw new UserNotFoundException("user with id " + creatorId + " not found");
        }
        log.info("new thermostat created");
        return thermoRepository.save(thermostat);
    }

    @Override
    public void deleteThermostat(Long id) {
        Optional<Thermostat> existingThermostatOptional = thermoRepository.findById(id);

        if (existingThermostatOptional.isPresent()) {
            Thermostat existingThermostat = existingThermostatOptional.get();
            existingThermostat.setActive(false);
            thermoRepository.save(existingThermostat);
        } else {
            log.error("Could not find thermostat in DB with ID: {}", id);
            throw new ThermostatNotFoundException("Thermostat with ID " + id + " not found");
        }
    }

    @Override
    public void updateThermostat(Long id, Thermostat thermostat) {
        Optional<Thermostat> existingThermostatOptional = thermoRepository.findById(id);

        if (existingThermostatOptional.isPresent()) {
            Thermostat existingThermostat = existingThermostatOptional.get();
            existingThermostat.setName(thermostat.getName());
            existingThermostat.setInitialTemperature(thermostat.getInitialTemperature());
            existingThermostat.setActive(true);
            thermoRepository.save(existingThermostat);
        } else {
            log.error("could not find thermostat in db with id :{}", id);
            throw new ThermostatNotFoundException("Thermostat with id " + id + " not found");
        }
    }

    @Override
    public List<Thermostat> getAllThermostat() {
        log.info("fetching all thermostats from db");
        return thermoRepository.findAll();
    }

    @Override
    public Thermostat getThermostat(String name) {
        log.info("fetching  thermostat : {}  from db", name);
        return thermoRepository.findByName(name);
    }

    @Override
    public List<Thermostat> getActiveThermostats() {
        List<Thermostat> activeThermostats = thermoRepository.findAll();
        return activeThermostats.stream()
                .filter(t -> t != null && t.getActive())
                .collect(Collectors.toList());
    }
}
