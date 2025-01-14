package com.dimasukimas.tennisscoreboard.model.match;

import com.dimasukimas.tennisscoreboard.model.Player;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Match {

    @ManyToOne
    private Player player1;

    @ManyToOne
    private Player player2;

}
