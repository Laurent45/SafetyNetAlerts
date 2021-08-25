package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationServiceImpl implements IFireStationService {

    private final FireStationRepository fireStationRepository;

    public FireStationServiceImpl(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    @Override
    public Iterable<FireStation> getFireStations() {
        return fireStationRepository.getFireStations();
    }

    @Override
    public List<Person> getPersonsCoverBy(int stationNumber) {
        return this.fireStationRepository.getFireStationsWith(stationNumber).stream()
                .map(FireStation::getPersons)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}


