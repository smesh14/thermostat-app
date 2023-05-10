package com.thermostat.user.service.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String userName;
    private String password;
    private UserRole userRole;
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Thermostat> thermostats = new ArrayList<>();
}
