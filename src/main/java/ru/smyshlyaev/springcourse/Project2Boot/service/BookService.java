package ru.smyshlyaev.springcourse.Project2Boot.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import jakarta.persistence.EntityManager;
import ru.smyshlyaev.springcourse.Project2Boot.models.Book;
import ru.smyshlyaev.springcourse.Project2Boot.models.Person;
import ru.smyshlyaev.springcourse.Project2Boot.repositories.BookRepository;
import ru.smyshlyaev.springcourse.Project2Boot.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;
    private final EntityManager entityManager;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
        this.entityManager = entityManager;
    }

    public List<Book> findAll(boolean sortByYear) {

            if (sortByYear)
                return bookRepository.findAll(Sort.by("year"));
            else
                return bookRepository.findAll();

    }
    public Book findOne(int id) {
        Optional<Book> foundPerson = bookRepository.findById(id);

        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Book saveBook) {
        bookRepository.save(saveBook);

    }
    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Optional<Person> findByBookOwner(int id) {
        Session session= entityManager.unwrap(Session.class);
        Book book=session.get(Book.class,id);
        return Optional.ofNullable(book.getOwner());
    }

    // метод для назначения книги пользователя
    @Transactional
    public void updateByIdAndOwner(int id, Person owner) {
        Session session=entityManager.unwrap(Session.class);
        Book updateBook=session.get(Book.class,id);
        updateBook.setOwner(owner);
    }

    // метод для удаления книги у пользователя- получаем книгу по id из контроллера, находим эту книгу в таблице
    // и назначаем ей владельца null
@Transactional
    public void updateById(int id) {
        Session session=entityManager.unwrap(Session.class);
        Book updateBook=session.get(Book.class,id);
        updateBook.setOwner(null);
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }




}
