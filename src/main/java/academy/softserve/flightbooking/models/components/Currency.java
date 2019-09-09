package academy.softserve.flightbooking.models.components;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "currencies")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
}
