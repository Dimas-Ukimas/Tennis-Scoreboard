package com.dimasukimas.tennisscoreboard.repository;

import com.dimasukimas.tennisscoreboard.exception.DataBaseException;
import com.dimasukimas.tennisscoreboard.exception.PlayerAlreadyExistsException;
import com.dimasukimas.tennisscoreboard.model.entity.Player;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerRepository extends BaseRepository<Player> {
    private static final String FIND_PLAYER_BY_NAME_QUERY = "select p from Player p where p.name = :name";

    @Getter
    private static final PlayerRepository instance = new PlayerRepository();

    public Optional<Player> findPlayerByName(String name) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_PLAYER_BY_NAME_QUERY, Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Player persist(Player player) {
        try {
            return super.persist(player);

        } catch (ConstraintViolationException e) {
            log.warn("Failed to persist player {} due to name is not unique", player.getName(), e);
            throw new PlayerAlreadyExistsException("Player with this name already exists", e);

        } catch (HibernateException e) {
            log.error("Failed to persist player: {}", player, e);
            throw new DataBaseException("Database connection failure", e);
        }
    }

}
