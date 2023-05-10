package com.thermostat.user.service.service;

import com.thermostat.user.service.model.ThermostatHistory;
import com.thermostat.user.service.repository.ThermostatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ThermostatHistoryServiceImpl implements ThermostatHistoryService {

    private final ThermostatHistoryRepository thermostatHistoryRepository;
    @Override
    public ThermostatHistory addNewRecord(ThermostatHistory thermostatHistory) {
        return thermostatHistoryRepository.save(thermostatHistory);
    }
}
