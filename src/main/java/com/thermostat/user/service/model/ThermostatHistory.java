package com.thermostat.user.service.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThermostatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date scheduledDate;
    private BigDecimal scheduledTemperature;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "thermostat_id")
    private Thermostat thermostat;

}
