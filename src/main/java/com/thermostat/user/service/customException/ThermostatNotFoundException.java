package com.thermostat.user.service.customException;

    public class ThermostatNotFoundException extends RuntimeException {
        public ThermostatNotFoundException(String message) {
            super(message);
        }
    }

