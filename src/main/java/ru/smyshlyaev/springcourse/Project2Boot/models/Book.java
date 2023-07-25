package ru.smyshlyaev.springcourse.Project2Boot.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "author")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String author;
    @Column(name = "title")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String title;
    @Column(name = "year")
    @Min(value = 1500, message = "Год книги должен быть больше чем 1500")
    private int year;
    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;
   /* @Temporal(TemporalType.TIMESTAMP)
    private Date DateOTakeBooks; // переменная отображает время взятия книги
    @Transient
    private boolean expired; // булевое значения для определения просрочки

    */

    public Book () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book(String title, String author, int year) {
        this.author = author;
        this.title = title;
        this.year = year;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}

