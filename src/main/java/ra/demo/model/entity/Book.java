package ra.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title",length = 100, nullable = false)
    private String title;
    @Column(name = "author", length = 70, nullable = false)
    private String author;
    @Column(name = "category", length = 100, nullable = false)
    private String category;
    private Integer quantity;
}
