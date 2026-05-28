package ra.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.demo.model.entity.Book;

import java.util.List;

@Repository
public interface BookResporitory extends JpaRepository<Book,Long> {
    List<Book> getAllByTitleContaining(String title);
}
