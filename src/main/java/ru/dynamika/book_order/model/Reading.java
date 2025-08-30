package ru.dynamika.book_order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "readings",
        indexes = {
                @Index(name = "idx_readings_client_uuid", columnList = "client_uuid"),
                @Index(name = "idx_readings_book_uuid", columnList = "book_uuid"),
                @Index(name = "idx_readings_taken_at", columnList = "taken_at")
        })
public class Reading {

    @Id
    @GeneratedValue
    @Column(name = "uuid", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "client_uuid",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_readings_client")
    )
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "book_uuid",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_readings_book")
    )
    private Book book;

    @Column(name = "taken_at", nullable = false)
    private OffsetDateTime takenAt = OffsetDateTime.now();
}
