package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonChildDTO;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

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
}
