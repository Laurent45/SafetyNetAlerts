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

    private static final Logger LOGGER =
            LogManager.getLogger(FireStationServiceImpl.class);

    private final FireStationRepository fireStationRepository;
    private final MedicalRecordServiceImpl medicalRecordService;

    @Override
    public Iterable<FireStation> getFireStations() {
        LOGGER.debug("getFireStation has been called");
        return fireStationRepository.getFireStations();
    }

    @Override
    public List<Person> getPersonsCoverBy(final int stationNumber)
            throws NotFoundException {
        LOGGER.debug("getPersonCoverBy has been called, parameter -> "
                + "stationNumber = " + stationNumber);

        List<Person> persons = this.fireStationRepository
                .getFireStationsWith(stationNumber).stream()
                .map(FireStation::getPersons)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (persons.isEmpty()) {
            LOGGER.error(String.format("number of station -> "
                    + "%d has been not found", stationNumber));
            throw new NotFoundException(String.format("number of station -> "
                    + "%d has been not found", stationNumber));
        }

        return persons;
    }

    @Override
    public List<Person> getPersonCoverBy(final List<Integer> stationNumbers)
            throws NotFoundException {
        LOGGER.debug("getPersonCoverBy has been called, parameter -> "
                + "stationNumber list = " + stationNumbers);

        List<Person> personList = new ArrayList<>();
        for (Integer s : stationNumbers) {
            try {
                personList.addAll(getPersonsCoverBy(s));
            } catch (NotFoundException e) {
                LOGGER.error(e.getMessage());
                throw new NotFoundException(e.getMessage());
            }
        }

        return personList;
    }

    @Override
    public List<String> getPhonePersonsCoverBy(final int stationNumber)
            throws NotFoundException {
        LOGGER.debug("getPhonePersonCoverBy has been called, parameter -> "
                + "stationNumber = " + stationNumber);

        try {
            return getPersonsCoverBy(stationNumber).stream()
                    .map(Person::getPhone)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (NotFoundException e) {
            LOGGER.error(String.format("number of station -> %d has been not "
                    + "found", stationNumber));
            throw new NotFoundException(String.format("number of station -> "
                    + "%d has been not found", stationNumber));
        }
    }

    @Override
    public FireStationAlertDTO getFireStationAlert(final int stationNumber)
            throws NotFoundException {
        LOGGER.debug("getFireStationAlert has been called, parameter -> "
                + "stationNumber = " + stationNumber);
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
            LOGGER.error(String.format("number of station -> %d has been not "
                    + "found", stationNumber));
            throw new NotFoundException(String.format("number of station -> "
                    + "%d has been not found", stationNumber));
        }

        return new FireStationAlertDTO(personDTOList, nAdults.get(),
                nChildren.get());
    }


    @Override
    public FireStation updateFireStation(final FireStation fireStation)
            throws NotFoundException {
        LOGGER.debug("updateFireStation has been called, parameter "
                + "fireStation = " + fireStation);

        Optional<FireStation> fR =
                fireStationRepository
                        .getFireStationByAddress(fireStation.getAddress());
        if (fR.isEmpty()) {
            LOGGER.error("fireStation object has been not found");
            throw new NotFoundException("fireStation object has been not "
                    + "found");
        }

        if (fireStation.getStation() != 0) {
            fR.get().setStation(fireStation.getStation());
        }

        return fR.get();
    }

    @Override
    public boolean saveFireStation(final FireStation fireStation)
            throws RuntimeException {
        LOGGER.debug("saveFireStation has been called, parameter -> "
                + "fireStation = " + fireStation);

        if (!(fireStationRepository.saveFireStation(fireStation))) {
            LOGGER.error("error while saving object -> " + fireStation);
            throw new RuntimeException("error while saving");
        }
        return true;
    }

    @Override
    public void deleteFireStation(final String address, final int stationNumber)
            throws NotFoundException {
        LOGGER.debug(String.format("deleteFireStation has been called, "
                        + "parameter -> address = %s, stationNumber = %d",
                address, stationNumber));

        Optional<FireStation> fireStation =
                fireStationRepository
                        .getFireStationByAddressAndStationNumber(address,
                                stationNumber);
        if (fireStation.isEmpty()) {
            LOGGER.error("fireStation object has been not found");
            throw new NotFoundException("fireStation object has benn not "
                    + "found");
        }

        fireStationRepository.deleteFireStation(fireStation.get());
    }

}


