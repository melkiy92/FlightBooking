package academy.softserve.flightbooking.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cities")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @ManyToOne
    private Country country;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Airport> airports;
}
