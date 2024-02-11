package com.iu;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@RequestScoped
@Named
@Getter
@Setter
public class GhostNetDAO {

    private String status;
    private int size;
    private GhostNetStatusEnum[] statusValues = GhostNetStatusEnum.values();
    private Coordinates coordinates = new Coordinates();

    private GhostNetStatusEnum selectedStatus;

    @PersistenceContext(unitName = "default")
    EntityManager entityManager;

    @Transactional
    public void createGhostNet(int size, GhostNetStatusEnum status) {
        GhostNet ghostNet = new GhostNet();
        ghostNet.setSize(size);
        ghostNet.setStatus(status);
        ghostNet.setCoordinates(coordinates);
        entityManager.persist(ghostNet);
        System.out.println("Ghostnet ID: " + ghostNet.getId());
        System.out.println("Ghostnet Size: " + ghostNet.getSize());
        System.out.println("Ghostnet Status: " + ghostNet.getStatus());
        System.out.println("Ghostnet Coordinates: " + ghostNet.getCoordinates());
        System.out.println("Finished setting Ghostnet from GhostNetDAO Class");
    }

    @Transactional
    public void createGhostNetWithoutStatus(int size) {
        GhostNet ghostNet = new GhostNet();
        ghostNet.setSize(size);
        ghostNet.setStatus(GhostNetStatusEnum.REPORTED);
        ghostNet.setCoordinates(coordinates);
        entityManager.persist(ghostNet);
        System.out.println("Ghostnet ID: " + ghostNet.getId());
        System.out.println("Ghostnet Size: " + ghostNet.getSize());
        System.out.println("Ghostnet Status: " + ghostNet.getStatus());
        System.out.println("Ghostnet Coordinates: " + ghostNet.getCoordinates());
        System.out.println("Finished setting Ghostnet from GhostNetDAO Class");
    }

    @Transactional
    public List<GhostNet> getAllGhostNets() {
        return entityManager.createQuery("SELECT c FROM GhostNet c", GhostNet.class).getResultList();
    }

    @Transactional
    public void printAllGhostNets() {
        List<GhostNet> ghostNets = entityManager.createQuery("SELECT c FROM GhostNet c", GhostNet.class).getResultList();
    }

    public List<GhostNet> filteredGhostNets() {
        if (selectedStatus == null) {
            return getAllGhostNets();
        } else {
            TypedQuery<GhostNet> query = entityManager.createQuery("SELECT g FROM GhostNet g WHERE g.status = :status", GhostNet.class);
            query.setParameter("status", selectedStatus);
            return query.getResultList();         
        }
    }

    public GhostNetStatusEnum getGhostNetStatus(Long id) {
        GhostNet ghostNet = entityManager.find(GhostNet.class, id);
        return ghostNet.getStatus();
    }

    public GhostNetStatusEnum[] getGhostNetStatusValues() {
        return statusValues;
    }

    public void validateGhostNetSize(FacesContext context, UIComponent component, Object value) {
        Integer netSize = (Integer) value;
        if (netSize <= 0) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ghostnet size must be greater than 0", null)
            );
        }
    }

}
