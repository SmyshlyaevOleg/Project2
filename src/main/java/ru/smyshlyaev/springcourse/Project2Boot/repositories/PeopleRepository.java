package ru.smyshlyaev.springcourse.Project2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.smyshlyaev.springcourse.Project2Boot.models.Person;
//import ru.smyshlyaev.springcourse.models.Book;


import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
  // public List<Book> findBooksByPersonId(int id); // ложится программа из-за него
}
