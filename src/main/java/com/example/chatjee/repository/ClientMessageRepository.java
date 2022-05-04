package com.example.chatjee.repository;

import com.example.chatjee.domain.ClientMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@ApplicationScoped
public class ClientMessageRepository {

    @Inject
    private ChatEntityManagerFactory entityManagerFactory = new ChatEntityManagerFactory();

    public void saveMessage(ClientMessage clientMessage) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Chat");


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(clientMessage);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<ClientMessage> findMessagesByRoomId(String roomId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select m from ClientMessage m", ClientMessage.class).getResultList();
    }

}
