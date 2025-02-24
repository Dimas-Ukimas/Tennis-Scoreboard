package com.dimasukimas.tennisscoreboard.repository;

import com.dimasukimas.tennisscoreboard.exception.DataBaseException;
import com.dimasukimas.tennisscoreboard.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class BaseRepository<E> implements Repository<E> {

    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public E persist(E entity) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();

            return entity;
        }catch (HibernateException e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
