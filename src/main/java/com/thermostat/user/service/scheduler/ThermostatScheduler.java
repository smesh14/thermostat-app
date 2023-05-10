package com.thermostat.user.service.scheduler;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.thermostat.user.service.model.Thermostat;
import com.thermostat.user.service.model.ThermostatHistory;
import com.thermostat.user.service.service.ThermostatHistoryService;
import com.thermostat.user.service.service.ThermostatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThermostatScheduler {


    private final ThermostatHistoryService thermostatHistoryService;
    private final ThermostatService thermostatService;

    private static final int MAX_LIMIT = 10;

    private static final int MIN_LIMIT = -10;

    private static final int SCHEDULER_TIME = 5000;

    @Scheduled(fixedRate = SCHEDULER_TIME)
    @Transactional
    public void updateThermostat() {
        List<Thermostat> activeThermostats = thermostatService.getActiveThermostats();
        if (activeThermostats != null && !activeThermostats.isEmpty()) {
            for (Thermostat thermostat : activeThermostats) {
                if (thermostat != null) {
                    BigDecimal scheduledTemperature = calculateScheduledTemperature(thermostat);
                    ThermostatHistory thermostatHistory = createThermostatHistory(scheduledTemperature);
                    thermostat.setInitialTemperature(scheduledTemperature);
                    thermostat.setCritical(scheduledTemperature.compareTo(thermostat.getMaxTemperature()) > 0);
                    thermostat.addThermostatHistory(thermostatHistory);
                    thermostat.setActive(true);

                    thermostatService.updateThermostat(thermostat.getId(), thermostat);
                    thermostatHistoryService.addNewRecord(thermostatHistory);
                }
            }
        }
    }

    private BigDecimal calculateScheduledTemperature(Thermostat thermostat) {
        Random random = new Random();
        int randomNumber = random.nextInt(MAX_LIMIT - MIN_LIMIT);
        return BigDecimal.valueOf(thermostat.getInitialTemperature().intValue() + randomNumber);
    }


    private ThermostatHistory createThermostatHistory(BigDecimal scheduledTemperature) {
        Date scheduledDate = new Date();
        ThermostatHistory thermostatHistory = new ThermostatHistory();
        thermostatHistory.setScheduledDate(scheduledDate);
        thermostatHistory.setScheduledTemperature(scheduledTemperature);
        return thermostatHistory;
    }
}
