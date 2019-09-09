package academy.softserve.flightbooking.models.components;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "providers")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
