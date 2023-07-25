package ru.smyshlyaev.springcourse.Project2Boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.smyshlyaev.springcourse.Project2Boot.models.Book;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
/*
    public void findByBookOwner(int id) ;
    public void updateById(int id);

    public void updateByIdAndAndOwner(int id, Person owner);

 */



}

