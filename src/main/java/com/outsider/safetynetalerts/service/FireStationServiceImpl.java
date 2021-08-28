package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireStationAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonDTO;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.dataTransferObject.mapper.Mapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.MapperImpl;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FireStationServiceImpl implements IFireStationService {

    private final FireStationRepository fireStationRepository;
    private final MedicalRecordServiceImpl medicalRecordService;

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

    @Override
    public List<Person> getPersonCoverBy(List<Integer> stationNumbers) {
        List<Person> personList = new ArrayList<>();
        stationNumbers.forEach(s -> personList.addAll(getPersonsCoverBy(s)));
        return personList;
    }

    @Override
    public FireStationAlertDTO getFireStationAlert(int stationNumber) {
        Mapper mapper = new MapperImpl();
        List<PersonDTO> personDTOList = new ArrayList<>();
        AtomicInteger nAdults = new AtomicInteger();
        AtomicInteger nChildren = new AtomicInteger();

        getPersonsCoverBy(stationNumber).forEach(p -> {
            personDTOList.add(mapper.personToPersonDTO(p));
            if (medicalRecordService.isAnAdult(p.getMedicalRecord())) {
                nAdults.getAndIncrement();
            } else {
                nChildren.getAndIncrement();
            }
        });

        return new FireStationAlertDTO(personDTOList, nAdults.get(),
                nChildren.get());
    }

}


