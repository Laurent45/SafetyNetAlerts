package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import com.outsider.safetynetalerts.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
    }


    public Iterable<FireStation> getFireStations() {
        return fireStationRepository.getAllFireStations();
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

    public List<Person> getPersonsCoverByStationNumber(int stationNumber) {
        List<Integer> idPersonList = fireStationRepository.getIdPersonCoverByStationNumber(stationNumber);
        return idPersonList.stream()
                .map(personRepository::getPerson)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}


