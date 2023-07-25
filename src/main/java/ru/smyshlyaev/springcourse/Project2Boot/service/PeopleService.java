package ru.smyshlyaev.springcourse.Project2Boot.service;

import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.smyshlyaev.springcourse.Project2Boot.models.Book;
import ru.smyshlyaev.springcourse.Project2Boot.models.Person;


import jakarta.persistence.EntityManager;
import ru.smyshlyaev.springcourse.Project2Boot.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, EntityManager entityManager) {
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Object findOne(int id) {
        Optional<Person> foundPerson=peopleRepository.findById(id);

        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(@Valid Person savePerson) {
        peopleRepository.save(savePerson);

    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }


    @Transactional
    //получаю id из запроса-нахожу человека по этому id-пролучаю книги из объединенной таблицы с этим id
    public List<Book> findBooksByPersonId(int id) {
        Optional<Person> person=peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            return person.get().getBooks();
        }

        else {
            return Collections.emptyList();
        }
    }

}
