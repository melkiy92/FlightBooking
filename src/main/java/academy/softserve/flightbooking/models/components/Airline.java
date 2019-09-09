package academy.softserve.flightbooking.models.components;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "airlines")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; //IATA code
    private String callSign;
    private String name;
    @ManyToOne
    private Country country;
}
