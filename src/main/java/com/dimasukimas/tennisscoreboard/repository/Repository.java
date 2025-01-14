package com.dimasukimas.tennisscoreboard.repository;

import org.hibernate.Session;

public interface Repository < E> {

   void persist(Session session, E entity);

}
