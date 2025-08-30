package ru.dynamika.book_order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dynamika.book_order.model.Book;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query("select b from Book b " +
            "where (:title is null or lower(b.title) like lower(concat('%', :title, '%'))) " +
            "  and (:author is null or lower(b.author) like lower(concat('%', :author, '%'))) " +
            "  and (:isbn is null or lower(b.isbn) like lower(concat('%', :isbn, '%')))")
    Page<Book> search(@Param("title") String title,
                      @Param("author") String author,
                      @Param("isbn") String isbn,
                      Pageable pageable);


}
