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
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public FireStation updateFireStation(FireStation fireStation) throws NotFoundException {
        Optional<FireStation> fR =
                fireStationRepository.getFireStationByAddress(fireStation.getAddress());
        if (fR.isEmpty()) {
            throw new NotFoundException("fireStation object has been not " +
                    "found");
        }
        if (fireStation.getStation() != 0) {
            fR.get().setStation(fireStation.getStation());
        }
        return fR.get();
    }

    @Override
    public boolean saveFireStation(FireStation fireStation) throws RuntimeException {
        boolean ret = fireStationRepository.saveFireStation(fireStation);
        if (!ret) {
            throw new RuntimeException("error while saving");
        }
        return true;
    }

    @Override
    public void deleteFireStation(String address, int stationNumber) throws NotFoundException {
        Optional<FireStation> fireStation =
                fireStationRepository.getFireStationByAddressAndStationNumber(address, stationNumber);
        if (fireStation.isEmpty()) {
            throw new NotFoundException("fireStation object has benn not " +
                    "found");
        }
        fireStationRepository.deleteFireStation(fireStation.get());
    }

}


