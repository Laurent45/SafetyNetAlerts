package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.FireAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonChildDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonFireDTO;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }

    @Override
    public boolean isAnAdult(MedicalRecord medicalRecord) {
        return calculationOfAge(medicalRecord) > 18;
    }

    @Override
    public int calculationOfAge(MedicalRecord medicalRecord) {
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
        return personList.stream()
                .filter(person -> !(isAnAdult(person.getMedicalRecord())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getOnlyAdultPersonList(List<Person> personList) {
        return personList.stream()
                .filter(person -> isAnAdult(person.getMedicalRecord()))
                .collect(Collectors.toList());
    }

    @Override
    public ChildAlertDTO getChildAlertDTO(List<Person> persons) {
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
    public Map<String, List<PersonFireDTO>> getFloodAlert(List<Person> person) {
        ChildAlertMapper mapper = new ChildAlertMapperImpl();
        return person.stream()
                .collect(Collectors.groupingBy(Person::getAddress))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        v -> v.getValue().stream()
                                .map(p -> mapper.personToPersonFireDTO(p, calculationOfAge(p.getMedicalRecord())))
                                .collect(Collectors.toList())));
    }
}