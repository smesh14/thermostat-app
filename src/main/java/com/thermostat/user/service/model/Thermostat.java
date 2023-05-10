package com.thermostat.user.service.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thermostat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "app_user_id")
    private AppUser creator;
    private Date createdDate;
    private BigDecimal initialTemperature;
    @OneToMany(mappedBy = "thermostat",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ThermostatHistory> thermostatHistoryList;
    private BigDecimal maxTemperature;
    private Boolean critical;

    private Boolean active = true;


    public void addThermostatHistory(ThermostatHistory thermostatHistory) {
        if (thermostatHistoryList == null) {
            thermostatHistoryList = new ArrayList<>();
        }
        thermostatHistoryList.add(thermostatHistory);
        thermostatHistory.setThermostat(this);
    }
}
