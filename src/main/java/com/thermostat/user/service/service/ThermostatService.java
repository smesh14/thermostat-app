package com.thermostat.user.service.service;

import java.util.List;
import com.thermostat.user.service.model.Thermostat;

public interface ThermostatService {

    Thermostat addThermostat(Thermostat thermostat,Long creatorId);

    void deleteThermostat(Long id);

    void updateThermostat(Long id,Thermostat thermostat);

    List<Thermostat> getAllThermostat();

    Thermostat getThermostat(String name);


    List<Thermostat> getActiveThermostats();
}
