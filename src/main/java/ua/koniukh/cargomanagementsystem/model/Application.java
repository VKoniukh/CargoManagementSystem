package ua.koniukh.cargomanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //todo
//    private LocalDate startDate;
//    private LocalDate finishDate;
//
//    boolean approved = false;

    //todo price,


}