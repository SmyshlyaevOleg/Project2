package ru.smyshlyaev.springcourse.Project2Boot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import ru.smyshlyaev.springcourse.Project2Boot.models.Book;
import ru.smyshlyaev.springcourse.Project2Boot.models.Person;
import ru.smyshlyaev.springcourse.Project2Boot.service.BookService;
import ru.smyshlyaev.springcourse.Project2Boot.service.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private  final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || booksPerPage == null)
            model.addAttribute("library", bookService.findAll(sortByYear)); // выдача всех книг
        else
            model.addAttribute("library", bookService.findWithPagination(page, booksPerPage, sortByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
// ищем владельца-метод получает владельца по id книги
       Optional<Person> bookOwner = bookService.findByBookOwner(id);

        if (bookOwner.isPresent()) // есть владелец
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());// нет владельца показываем выпадающий список

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook (@ModelAttribute("book") Book book) {
        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
    // освободжает кингу
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.updateById(id); //удаляет владельца по id
        return "redirect:/books/" + id;
    }
//назначает книгу
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.updateByIdAndOwner(id, selectedPerson);
        return "redirect:/books/" + id;
    }


}



