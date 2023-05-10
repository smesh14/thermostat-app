package com.thermostat.user.service.api;


import java.util.List;
import com.thermostat.user.service.model.Thermostat;
import com.thermostat.user.service.service.ThermostatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ThermostatResource {


    private final ThermostatService thermostatService;


    @GetMapping("/thermostats")
    public ResponseEntity<List<Thermostat>> getAllThermostat(){
        return ResponseEntity.ok().body(thermostatService.getActiveThermostats());
    }

    @PostMapping("/thermostat/save")
    public ResponseEntity<Thermostat> addThermostat(@RequestBody Thermostat thermostat, @RequestParam Long creatorId){
        return ResponseEntity.ok().body(thermostatService.addThermostat(thermostat,creatorId));
    }

    @GetMapping("/thermostat/{name}")
    public ResponseEntity<Thermostat> getThermostats(@PathVariable String name){
        return ResponseEntity.ok().body(thermostatService.getThermostat(name));
    }

    @PostMapping("/thermostat/update/{id}")
    public ResponseEntity<Void> updateThermostat(@RequestBody Thermostat thermostat,@PathVariable Long id){
        thermostatService.updateThermostat(id, thermostat);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/thermostat/delete/{id}")
    public ResponseEntity<Void> deleteThermostat(@PathVariable Long id){
        thermostatService.deleteThermostat(id);
         return ResponseEntity.ok().build();
    }
}
