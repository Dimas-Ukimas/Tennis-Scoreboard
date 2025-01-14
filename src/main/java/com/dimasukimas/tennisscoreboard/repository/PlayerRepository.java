package com.dimasukimas.tennisscoreboard.repository;

import com.dimasukimas.tennisscoreboard.model.Player;
import org.hibernate.Session;

import java.util.Optional;

public class PlayerRepository extends BaseRepository<Player> {

    public Optional<Player> findPlayerByName(Session session, String name) {

        return session.createQuery("select p from Player p where p.name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResultOptional();
    }

}
