package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "matches", schema = "public")
public class FinishedMatch extends Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Player winner;

}
