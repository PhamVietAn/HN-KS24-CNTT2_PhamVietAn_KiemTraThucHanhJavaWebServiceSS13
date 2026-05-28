package ra.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ra.demo.exception.BookNotFound;
import ra.demo.model.entity.Book;
import ra.demo.service.BookService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void getBooks_return200AndJsonList() throws Exception {
        List<Book> books = List.of(
                Book.builder()
                        .id(1L)
                        .title("Book 1")
                        .author("Author 1")
                        .category("Cat 1")
                        .quantity(10)
                        .build(),
                Book.builder()
                        .id(2L)
                        .title("Book 2")
                        .author("Author 2")
                        .category("Cat 2")
                        .quantity(20)
                        .build()
        );

        when(bookService.getBooks()).thenReturn(books);

        mockMvc.perform(get("/api/books")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Lấy danh sách sách thành công!"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].title").value("Book 1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].title").value("Book 2"))
                .andExpect(jsonPath("$.httpStatus").value("200 OK"));
    }

    @Test
    void getBookById_found_return200AndCorrectJson() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("Book 1")
                .author("Author 1")
                .category("Cat 1")
                .quantity(10)
                .build();

        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Lấy thông tin sách thành công!"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Book 1"))
                .andExpect(jsonPath("$.data.author").value("Author 1"))
                .andExpect(jsonPath("$.httpStatus").value("200 OK"));
    }

    @Test
    void getBookById_notFound_return404() throws Exception {
        when(bookService.getBookById(99L))
                .thenThrow(new BookNotFound("Không tồn tại sách có mã 99"));

        mockMvc.perform(get("/api/books/{id}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Có lỗi xảy ra"))
                .andExpect(jsonPath("$.errors").value("Không tồn tại sách có mã 99"))
                .andExpect(jsonPath("$.httpStatus").value("404 NOT_FOUND"));
    }

        @Test
        void searchBooksByTitle_returnFilteredBooks() throws Exception {
                List<Book> books = List.of(
                                Book.builder()
                                                .id(1L)
                                                .title("Spring in Action")
                                                .author("Author 1")
                                                .category("Tech")
                                                .quantity(3)
                                                .build()
                );

                when(bookService.searchBooksByTitle("Spring")).thenReturn(books);

                mockMvc.perform(get("/api/books/search")
                                                .param("title", "Spring")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Tìm kiếm sách theo tiêu đề thành công!"))
                                .andExpect(jsonPath("$.data.length()").value(1))
                                .andExpect(jsonPath("$.data[0].title").value("Spring in Action"))
                                .andExpect(jsonPath("$.httpStatus").value("200 OK"));
        }

        @Test
        void deleteBook_return200AndSuccess() throws Exception {
                when(bookService.deleteBook(1L)).thenReturn(true);

                mockMvc.perform(delete("/api/books/{id}", 1L)
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.success").value(true))
                                .andExpect(jsonPath("$.message").value("Xóa thông tin sách thành công!"))
                                .andExpect(jsonPath("$.data").value(true))
                                .andExpect(jsonPath("$.httpStatus").value("200 OK"));

                verify(bookService).deleteBook(1L);
        }
}