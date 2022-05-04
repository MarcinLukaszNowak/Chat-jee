package com.example.chatjee.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//@ApplicationScoped
public class ChatEntityManagerFactory {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Chat");

    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
