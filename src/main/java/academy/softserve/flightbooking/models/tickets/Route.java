package academy.softserve.flightbooking.models.tickets;

import academy.softserve.flightbooking.models.components.City;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "routes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "fromCity_name")
    private City fromCity;
    @OneToOne
    @JoinColumn(name = "toCity_name")
    private City toCity;
    private Long duration;
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flight> flights;
    @ManyToOne
    private Ticket ticket;
}
