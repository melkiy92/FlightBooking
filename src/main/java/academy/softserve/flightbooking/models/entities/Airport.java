package academy.softserve.flightbooking.models.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @ManyToOne
    private City city;
}
