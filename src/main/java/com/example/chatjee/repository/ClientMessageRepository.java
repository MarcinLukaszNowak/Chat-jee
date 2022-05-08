package com.example.chatjee.repository;

import com.example.chatjee.domain.ClientMessage;
import com.example.chatjee.jms.ChatEntityManagerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        Query query = entityManager.createQuery("select m from ClientMessage m where m.roomId = :roomId", ClientMessage.class);
        query.setParameter("roomId", roomId);
        return query.getResultList();
    }

}
