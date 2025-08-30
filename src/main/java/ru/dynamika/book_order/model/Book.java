package ru.dynamika.book_order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "books",
        uniqueConstraints = @UniqueConstraint(name = "uq_books_isbn", columnNames = "isbn")
)
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "uuid", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

}
