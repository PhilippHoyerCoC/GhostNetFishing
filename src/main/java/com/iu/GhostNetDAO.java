package com.iu;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
@Transactional
@Named
//@jakarta.ejb.Stateless
public class GhostNetDAO {
//    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//    @PersistenceContext(unitName = "default")
//    EntityManager entityManager = entityManagerFactory.createEntityManager();
    @PersistenceContext(unitName = "default")
    EntityManager entityManager;

//    @PersistenceContext
//    private EntityManager em;

    public void setGhostNet(Long id) {
        GhostNet ghostNet = new GhostNet();
        ghostNet.setId(id);
        ghostNet.setSize(23847);
        ghostNet.setStatus("Gefunden");
        System.out.println("Start setting Ghostnet from GhostNetDAO Class");

        entityManager.getTransaction().begin();
        entityManager.persist(ghostNet);
        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();

        System.out.println("Finished setting Ghostnet from GhostNetDAO Class");
    }

    public String getGhostNetStatus(Integer id) {
        GhostNet ghostNet = entityManager.find(GhostNet.class, id);
        return ghostNet.getStatus();
    }

//    public String getGhostNetStatus(Long id) {
//        GhostNet ghostNet = em.find(GhostNet.class, id);
//        return ghostNet.getStatus();
//    }

//    public List<GhostNet> getAllGhostNets() {
//        return em.createQuery("SELECT c FROM GhostNet c", GhostNet.class).getResultList();
//    }

}
