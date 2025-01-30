package com.dimasukimas.tennisscoreboard.util;

import com.dimasukimas.tennisscoreboard.model.match.FinishedMatch;
import com.dimasukimas.tennisscoreboard.model.Player;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Player.class);
        configuration.addAnnotatedClass(FinishedMatch.class);
        configuration.configure();

        sessionFactory = configuration.buildSessionFactory();
    }
}
