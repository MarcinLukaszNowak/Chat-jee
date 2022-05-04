package com.example.chatjee.repository;

import com.example.chatjee.domain.ClientMessage;
import com.example.chatjee.jms.ChatEntityManagerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ClientMessageRepository {

    @Inject
    private ChatEntityManagerFactory entityManagerFactory = new ChatEntityManagerFactory();

    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void saveMessage(ClientMessage clientMessage) {
        entityManager.getTransaction().begin();
        entityManager.persist(clientMessage);
        entityManager.getTransaction().commit();
    }

    public List<ClientMessage> findMessagesByRoomId(String roomId) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.createQuery("select m from ClientMessage m", ClientMessage.class).getResultList();
//        entityManager.close();
    }

}
