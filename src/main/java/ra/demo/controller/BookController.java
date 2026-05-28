package ra.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.demo.model.dto.request.BookDTO;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.entity.Book;
import ra.demo.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<Book>>> getBooks(@RequestParam(required = false) String title) {
        List<Book> books = (title == null || title.isBlank())
                ? bookService.getBooks()
                : bookService.searchBooksByTitle(title);

        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách sách thành công!",
                books,
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiDataResponse<List<Book>>> searchBooksByTitle(@RequestParam String title) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Tìm kiếm sách theo tiêu đề thành công!",
                bookService.searchBooksByTitle(title),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Book>> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy thông tin sách thành công!",
                bookService.getBookById(id),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Book>> insertBook(@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sách thành công!",
                bookService.insertBook(bookDTO),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Book>> updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sách thành công!",
                bookService.updateBook(id, bookDTO),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Book>> updatePartialBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sách thành công!",
                bookService.updatePartialBook(id, bookDTO),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteBook(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa thông tin sách thành công!",
                bookService.deleteBook(id),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

}
