package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class PersonServiceImpl implements IPersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> getPersons() {
        return personRepository.getPersons();
    }

    @Override
    public boolean savePerson(Person person) {
        return personRepository.savePerson(person);
    }

    @Override
    public List<Person> getPersonsBy(String address) {
        return personRepository.getPersonsBy(address);
    }
}
