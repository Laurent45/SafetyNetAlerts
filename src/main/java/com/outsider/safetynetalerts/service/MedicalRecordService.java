package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class MedicalRecordService implements IMedicalRecordService{

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
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
}
