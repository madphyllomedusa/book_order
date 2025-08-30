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
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients",
        indexes = {
                @Index(name = "idx_clients_last_name", columnList = "last_name"),
                @Index(name = "idx_clients_first_name", columnList = "first_name")
        })
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "uuid", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "middle_name", length = 100)
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;


    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (lastName != null && !lastName.isEmpty()) {
            sb.append(lastName);
        }
        if (firstName != null && !firstName.isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(middleName);
        }
        return sb.toString();
    }
}
