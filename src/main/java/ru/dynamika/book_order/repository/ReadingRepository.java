package ru.dynamika.book_order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dynamika.book_order.model.Reading;

import java.util.UUID;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, UUID> {

    Page<Reading> findAllByClientUuid(UUID clientUuid, Pageable pageable);

    Page<Reading> findAllByBookUuid(UUID bookUuid, Pageable pageable);

    Page<Reading> findAll(Specification<Reading> spec, Pageable pageable);
}
