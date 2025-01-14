package com.dimasukimas.tennisscoreboard.repository;

import org.hibernate.Session;

public abstract class BaseRepository <E> implements Repository<E> {

    @Override
    public void persist(Session session, E entity) {
        session.persist(entity);

    }
}
