package com.dimasukimas.tennisscoreboard.util;

import com.dimasukimas.tennisscoreboard.exception.DataBaseException;
import com.dimasukimas.tennisscoreboard.model.entity.FinishedMatch;
import com.dimasukimas.tennisscoreboard.model.entity.Player;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
@Slf4j
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Player.class);
        configuration.addAnnotatedClass(FinishedMatch.class);
        configuration.configure();

        try {
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            log.error("Failed to create session factory");
            throw new DataBaseException("Database connection failure", e);
        }
    }
}
