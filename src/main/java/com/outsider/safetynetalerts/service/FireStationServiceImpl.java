package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireStationAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonDTO;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FireStationServiceImpl implements IFireStationService {

    private static final Logger logger =
            LogManager.getLogger(FireStationServiceImpl.class);

    private final FireStationRepository fireStationRepository;
    private final MedicalRecordServiceImpl medicalRecordService;

    @Override
    public Iterable<FireStation> getFireStations() {
        logger.debug("getFireStation has been called");
        return fireStationRepository.getFireStations();
    }

    @Override
    public List<Person> getPersonsCoverBy(int stationNumber) throws NotFoundException {
        logger.debug("getPersonCoverBy has been called, parameter -> " +
                "stationNumber = " + stationNumber);

        List<Person> persons = this.fireStationRepository
                .getFireStationsWith(stationNumber).stream()
                .map(FireStation::getPersons)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (persons.isEmpty()) {
            logger.error(String.format("number of station -> " +
                    "%d has been not found", stationNumber));
            throw new NotFoundException(String.format("number of station -> " +
                    "%d has been not found", stationNumber));
        }

        return persons;
    }

    @Override
    public List<Person> getPersonCoverBy(List<Integer> stationNumbers) {
        logger.debug("getPersonCoverBy has been called, parameter -> " +
                "stationNumber list = " + stationNumbers);

        List<Person> personList = new ArrayList<>();
        stationNumbers.forEach(s -> {
            try {
                personList.addAll(getPersonsCoverBy(s));
            } catch (NotFoundException e) {
                logger.error(String.format("number of station -> %d has been not found", s));
            }
        });

        return personList;
    }

    @Override
    public List<String> getPhonePersonsCoverBy(int stationNumber) {
        logger.debug("getPhonePersonCoverBy has been called, parameter -> " +
                "stationNumber = " + stationNumber);

        List<String> phoneNumber = null;
        try {
            phoneNumber = getPersonsCoverBy(stationNumber).stream()
                            .map(Person::getPhone)
                            .collect(Collectors.toList());
        } catch (NotFoundException e) {
            logger.error(String.format("number of station -> %d has been not " +
                    "found", stationNumber));
        }

        return phoneNumber;
    }

    @Override
    public FireStationAlertDTO getFireStationAlert(int stationNumber) {
        logger.debug("getFireStationAlert has been called, parameter -> " +
                "stationNumber = " + stationNumber);
        ChildAlertMapper mapper = new ChildAlertMapperImpl();
        List<PersonDTO> personDTOList = new ArrayList<>();
        AtomicInteger nAdults = new AtomicInteger();
        AtomicInteger nChildren = new AtomicInteger();

        try {
            getPersonsCoverBy(stationNumber).forEach(p -> {
                personDTOList.add(mapper.personToPersonDTO(p));
                if (medicalRecordService.isAnAdult(p.getMedicalRecord())) {
                    nAdults.getAndIncrement();
                } else {
                    nChildren.getAndIncrement();
                }
            });
        } catch (NotFoundException e) {
            logger.error(String.format("number of station -> %d has been not " +
                    "found", stationNumber));
        }

        return new FireStationAlertDTO(personDTOList, nAdults.get(),
                nChildren.get());
    }


    @Override
    public FireStation updateFireStation(FireStation fireStation) throws NotFoundException {
        logger.debug("updateFireStation has been called, parameter " +
                "fireStation = " + fireStation);

        Optional<FireStation> fR =
                fireStationRepository.getFireStationByAddress(fireStation.getAddress());
        if (fR.isEmpty()) {
            logger.error("fireStation object has been not found");
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
        logger.debug("saveFireStation has been called, parameter -> " +
                "fireStation = " + fireStation);

        if (!(fireStationRepository.saveFireStation(fireStation))) {
            logger.error("error while saving object -> " + fireStation);
            throw new RuntimeException("error while saving");
        }
        return true;
    }

    @Override
    public void deleteFireStation(String address, int stationNumber) throws NotFoundException {
        logger.debug(String.format("deleteFireStation has been called, parameter " +
                "-> address = %s, stationNumber = %d", address, stationNumber));

        Optional<FireStation> fireStation =
                fireStationRepository.getFireStationByAddressAndStationNumber(address, stationNumber);
        if (fireStation.isEmpty()) {
            logger.error("fireStation object has been not found");
            throw new NotFoundException("fireStation object has benn not " +
                    "found");
        }

        fireStationRepository.deleteFireStation(fireStation.get());
    }

}


