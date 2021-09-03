package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.*;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService{
    private static final Logger logger =
            LogManager.getLogger(MedicalRecordServiceImpl.class);

    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public Iterable<MedicalRecord> getMedicalRecords() {
        logger.debug("getMedicalRecord has been called");

        return medicalRecordRepository.getAllMedicalRecords();
    }

    @Override
    public boolean isAnAdult(MedicalRecord medicalRecord) {
        logger.debug("isAdult has been called, parameter -> medicalRecord = " + medicalRecord);

        return calculationOfAge(medicalRecord) > 18;
    }

    @Override
    public int calculationOfAge(MedicalRecord medicalRecord) {
        logger.debug("calculationOfAge has been called, parameter -> " +
                "medicalRecord = " + medicalRecord);

        LocalDate today = LocalDate.now();
        String[] dayMonthYear = medicalRecord.getBirthdate().split("/");
        LocalDate bithdate = LocalDate.of(Integer.parseInt(dayMonthYear[2]),
                Integer.parseInt(dayMonthYear[1]),
                Integer.parseInt(dayMonthYear[0]));
        Period period = Period.between(bithdate, today);

        return period.getYears();
    }

    @Override
    public List<Person> getOnlyChildInPersonList(List<Person> personList) {
        logger.debug("getOnlyChildPersonList has been called, parameter-> " +
                "list of person = " + personList);

        return personList.stream()
                .filter(person -> !(isAnAdult(person.getMedicalRecord())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getOnlyAdultPersonList(List<Person> personList) {
        logger.debug("getOnlyAdultPersonList has been called, parameter -> a " +
                "list of person= " + personList);

        return personList.stream()
                .filter(person -> isAnAdult(person.getMedicalRecord()))
                .collect(Collectors.toList());
    }

    @Override
    public ChildAlertDTO getChildAlertDTO(List<Person> persons) {
        logger.debug("getChildAlertDTO has been called, parameter -> a list " +
                "of person = " + persons);

        ChildAlertMapper mapper = new ChildAlertMapperImpl();
        List<PersonChildDTO> personChildren =
                getOnlyChildInPersonList(persons).stream()
                        .map(p -> mapper.personToPersonChildDTO(p,
                                calculationOfAge(p.getMedicalRecord())))
                        .collect(Collectors.toList());
        List<Person> personOthers = getOnlyAdultPersonList(persons);

        return mapper.toChildAlertDTO(personChildren, personOthers);
    }

    @Override
    public FireAlertDTO getFireAlert(List<Person> persons) {
        logger.debug("getFireAlert has been called, parameter -> a list of " +
                        "person = " + persons);

        ChildAlertMapper mapper = new ChildAlertMapperImpl();
        List<PersonFireDTO> personFireDTOS = persons.stream()
                .map(p -> mapper.personToPersonFireDTO(p,
                        calculationOfAge(p.getMedicalRecord())))
                .collect(Collectors.toList());
        List<Integer> stationNumbers = persons.get(0).getFireStations().stream()
                .map(FireStation::getStation)
                .collect(Collectors.toList());

        return new FireAlertDTO(personFireDTOS, stationNumbers);
    }

    @Override
    public Map<String, List<PersonFireDTO>> getFloodAlert(List<Person> persons) {
        logger.debug("getFloodAlert has been called, parameter -> a list of " +
                        "person = " + persons);

        ChildAlertMapper mapper = new ChildAlertMapperImpl();

        return persons.stream()
                .collect(Collectors.groupingBy(Person::getAddress))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        v -> v.getValue().stream()
                                .map(p -> mapper.personToPersonFireDTO(p, calculationOfAge(p.getMedicalRecord())))
                                .collect(Collectors.toList())));
    }

    @Override
    public boolean saveMedicalRecord(MedicalRecord medicalRecord) throws RuntimeException {
        logger.debug("saveMedicalRecord has been called, parameter -> " +
                "medicalRecord = " + medicalRecord);

        if (!(medicalRecordRepository.saveMedicalRecord(medicalRecord))) {
            logger.error("error while saved");
            throw new RuntimeException("error while saved");
        }

        return true;
    }

    @Override
    public MedicalRecord updateMedicalRecord(int id, MedicalRecordUpdateDTO mR) throws NotFoundException {
        logger.debug(String.format("updateMedicalRecord has been called, " +
                "parameters -> id = %d, medicalRecord = %s", id, mR));

        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.getMedicalRecordById(id);
        if (medicalRecord.isEmpty()) {
            logger.error(String.format("medical record with id = %d has been " +
                    "not found", id));
            throw new NotFoundException("medical record id not found");
        }

        if (!(mR.getBirthdate().isEmpty())) {
            medicalRecord.get().setBirthdate(mR.getBirthdate());
        }
        if (!(mR.getMedications().isEmpty())) {
            medicalRecord.get().setMedications(mR.getMedications());
        }
        if (!(mR.getAllergies().isEmpty())) {
            medicalRecord.get().setAllergies(mR.getAllergies());
        }

        return medicalRecord.get();
    }

    @Override
    public void deleteMedicalRecord(String lastName, String firstName) throws NotFoundException {
        logger.debug(String.format("deleteMedicalRecord has been called, " +
                "parameters -> lastName = %s, firstName = %s", lastName, firstName));

        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.getPersonByLastNameAndFirstName(lastName, firstName);
        if (medicalRecord.isEmpty()) {
            logger.error("medical record has been not found");
            throw new NotFoundException("medical record not found");
        }

        medicalRecordRepository.deleteMedicalRecord(medicalRecord.get());
    }
}
