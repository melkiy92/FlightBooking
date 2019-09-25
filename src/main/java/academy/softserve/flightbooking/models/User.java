package academy.softserve.flightbooking.models;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String encodedPassword;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}