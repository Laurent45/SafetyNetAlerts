package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Data
@AllArgsConstructor
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> getPersons() {
        return personRepository.getPersons();
    }

    public boolean savePerson(Person person) {
        return personRepository.savePerson(person);
    }

    public Person updatePerson(int idPerson, Person person) {
        Optional<Person> p = personRepository.getPerson(idPerson);
        if (p.isPresent()){
            Person currentPerson = p.get();
            if (person.getAddress() != null){
                currentPerson.setAddress(person.getAddress());
            }
            if (person.getCity() != null){
                currentPerson.setCity(person.getCity());
            }
            if (person.getZip() != null){
                currentPerson.setZip(person.getZip());
            }
            if (person.getPhone() != null){
                currentPerson.setPhone(person.getPhone());
            }
            if (person.getEmail() != null){
                currentPerson.setEmail(person.getEmail());
            }
            return currentPerson;
        } else {
            return null;
        }
    }

    public boolean deletePerson(String firstName, String lastName) {
        Optional<Person> person = personRepository.getPerson(firstName, lastName);
        return person.filter(value -> personRepository.deletePerson(value)).isPresent();
    }
}
