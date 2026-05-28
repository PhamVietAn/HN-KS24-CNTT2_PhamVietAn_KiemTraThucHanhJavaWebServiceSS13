package ra.demo.service;


import ra.demo.model.dto.request.BookDTO;
import ra.demo.model.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();
    List<Book> searchBooksByTitle(String title);
    Book getBookById(Long id);
    Book insertBook(BookDTO bookDTO);
    Book updateBook(Long id, BookDTO bookDTO);
    boolean deleteBook(Long id);
    Book updatePartialBook(Long id, BookDTO bookDTO);
}
