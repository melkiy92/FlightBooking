package academy.softserve.flightbooking.models.tickets;

import academy.softserve.flightbooking.models.entities.Provider;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private String bookingToken; // link to buy ticket
    private Provider ticketProvider;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes;
}
