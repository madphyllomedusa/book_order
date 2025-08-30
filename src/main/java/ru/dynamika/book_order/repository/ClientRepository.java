package ru.dynamika.book_order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dynamika.book_order.model.Client;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("select c from Client c " +
            "where lower(c.lastName) like :lastName " +
            "  and lower(c.firstName) like :firstName " +
            "  and (:birthFrom is null or c.birthDate >= :birthFrom) " +
            "  and (:birthTo is null or c.birthDate <= :birthTo)")
    Page<Client> search(@Param("lastName") String lastName,
                        @Param("firstName") String firstName,
                        @Param("birthFrom") LocalDate birthFrom,
                        @Param("birthTo") LocalDate birthTo,
                        Pageable pageable);
}
