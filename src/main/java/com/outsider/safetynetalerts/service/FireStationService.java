package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;

    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }


    public Iterable<FireStation> getFireStations() {
        return fireStationRepository.getFireStations();
    }

    public void saveFireStation(FireStation fireStation) {
        fireStationRepository.saveFireStation(fireStation);
    }

    public FireStation updateNumberOfStation(String address, int station) {
        Optional<FireStation> fireStationToUpdate =
                fireStationRepository.getFireStation(address);
        if (fireStationToUpdate.isPresent()) {
            FireStation currentFireStation = fireStationToUpdate.get();
            currentFireStation.setStation(station);
            return currentFireStation;
        }
        return null;
    }

    public void deleteMappingFireStationAddress(String address) {
        fireStationRepository.deleteFireStation(address);
    }

    public void deleteMappingFireStationAddress(int station) {
        fireStationRepository.deleteFireStation(station);
    }
}


