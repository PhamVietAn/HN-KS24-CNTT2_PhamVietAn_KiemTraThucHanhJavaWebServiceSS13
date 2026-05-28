package ra.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ra.demo.exception.BookNotFound;
import ra.demo.model.entity.Book;
import ra.demo.repository.BookResporitory;
import ra.demo.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookResporitory bookResporitory;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .category("Category 1")
                .quantity(10)
                .build();

        book2 = Book.builder()
                .id(2L)
                .title("Book 2")
                .author("Author 2")
                .category("Category 2")
                .quantity(20)
                .build();
    }

    @Test
    void getAllBooks_returnList() {
        when(bookResporitory.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.getBooks();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());
        verify(bookResporitory, times(1)).findAll();
    }

    @Test
    void getBookById_found() {
        when(bookResporitory.findById(1L)).thenReturn(Optional.of(book1));

        Book result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Book 1", result.getTitle());
        verify(bookResporitory, times(1)).findById(1L);
    }

    @Test
    void getBookById_notFound() {
        when(bookResporitory.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFound.class, () -> bookService.getBookById(99L));
        verify(bookResporitory, times(1)).findById(99L);
    }
}