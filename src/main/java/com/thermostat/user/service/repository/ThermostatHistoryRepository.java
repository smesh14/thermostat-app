package com.thermostat.user.service.repository;

import com.thermostat.user.service.model.ThermostatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ThermostatHistoryRepository extends JpaRepository<ThermostatHistory,Long> {
}
