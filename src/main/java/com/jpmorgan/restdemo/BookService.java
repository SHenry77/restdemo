package com.jpmorgan.restdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }


    /*
        retrieves all the books from the database
        return: a list of books. The list will be empty if none found.
     */
    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    /* saves a new book into the database or updates an existing book
    return: the saved book
    throws: IllegalArgumentException if booktitle/author are null or blank.
     */
    public Book save(Book book) throws IllegalArgumentException {
        if(book.getTitle() == null || book.getTitle().isBlank()){
            throw new IllegalArgumentException("Title must be provided.");
        }
        if(book.getAuthor() == null || book.getAuthor().isBlank()){
            throw new IllegalArgumentException("Author must be provided.");
        }
        return bookRepository.save(book);
    }
    /*
        retrieves a book from the database with the given id
        return: book if present or null if no book with that id exists
        throws: IllegalArgumentException if id is null.
     */
    public Book findByID(Long id) throws IllegalArgumentException {
        if(id == null) throw new IllegalArgumentException("Id must not be null.");
        Optional<Book> result = bookRepository.findById(id);
        return result.isPresent()? result.get(): null;
    }
}
