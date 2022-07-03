package com.jpmorgan.restdemo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest("BookService.class")
public class BookServiceTest {

    @MockBean
    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    /***************** test findAll() ******************/
    @Test
    public void testFindAllEmptyListReturned(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(bookService.findAll().isEmpty());
    }

    @Test
    public void testFindAllValuesReturned(){
        when(bookRepository.findAll()).thenReturn(generateBooksWithIDs(3));
        List<Book> result = bookService.findAll();
        assertEquals(3, result.size());
        assertThat(result.contains(generateBookWithID(0L, "title0", "author0", "blurb0")));
        assertThat(result.contains(generateBookWithID(1L, "title1", "author1", "blurb1")));
        assertThat(result.contains(generateBookWithID(2L, "title2", "author2", "blurb2")));
    }


    /*********************** Test FindByID ***************************/
    @Test
    public void testFindByIDThrowsExceptionForNullID() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bookService.findByID(null));
        assertEquals("Id must not be null.", exception.getMessage());
    }

    @Test
    public void testFindByIDReturnsNullIfNoBookFound(){
        when(bookRepository.findById(5L)).thenReturn(Optional.empty());
        assertNull(bookService.findByID(5L));
    }

    @Test
    public void testFindByIDReturnsCorrectBook(){
        when(bookRepository.findById(9L)).thenReturn(Optional.of(generateBookWithID(9L, "A Book", "Anon", "um")));
        Book expected = new Book("A Book", "Anon", "um");
        expected.setId(9L);
        assertEquals(expected, bookService.findByID(9L));
    }


    /********************* Test Save Method ************************/
    @Test
    public void testSaveWithExistingIDUpdates(){
        // test setup
        Book book = generateBookWithID(1L, "title", "author1", "b");
        List<Book> bookListInitial = generateBooksWithIDs(2);
        List<Book> bookListSecond = new ArrayList<>();
        bookListSecond.add(bookListInitial.get(0));
        bookListSecond.add(book);

        when(bookRepository.findAll()).thenReturn(bookListInitial);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findAll()).thenReturn(bookListSecond);

        Book bookToUpdate = new Book("title", "author1", "b");
        bookToUpdate.setId(1L);
        Book result = bookService.save(bookToUpdate);
        assertEquals(generateBookWithID(1L, "title", "author1", "b"), result);
        List<Book> booksExpected = bookService.findAll();
        assertThat(booksExpected.contains(result));
    }

    @Test
    public void testSaveNewBookSucceeds(){
        Book book = generateBookWithID( 3, "t", "a", "b");
        when(bookRepository.save(new Book("t", "a", "b"))).thenReturn(book);
        Book result = bookService.save(new Book("t", "a", "b"));
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("t", result.getTitle());
        assertEquals("a", result.getAuthor());
        assertEquals("b", result.getBlurb());
    }

    @Test
    public void testExceptionThrownForEmptyOrNullTitle(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bookService.save(new Book( null, "a", "b")));
        assertEquals("Title must be provided.", exception.getMessage());
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> bookService.save(new Book( "", "a", "b")));
        assertEquals("Title must be provided.", exception2.getMessage());
    }

    @Test
    public void testExceptionThrownForEmptyAuthor(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bookService.save(new Book( "t", null, "b")));
        assertEquals("Author must be provided.", exception.getMessage());
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> bookService.save(new Book( "t", "", "b")));
        assertEquals("Author must be provided.", exception2.getMessage());
    }
    /******** private utility methods **************************/
    private List<Book> generateBooksWithIDs(int numBooks){
        List<Book> bookList = new ArrayList<>();
        Book book;
        for(int i=0; i<numBooks; i++){
            bookList.add(generateBookWithID((long) i,"title"+i, "author"+i, "blurb"+i));
        }
        return bookList;
    }

    private Book generateBookWithID(long id, String title, String author, String blurb){
        Book book = new Book(title, author, blurb);
        book.setId(id);
        return book;
    }

}
