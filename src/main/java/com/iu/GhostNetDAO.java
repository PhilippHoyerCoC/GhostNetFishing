package com.iu;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@ViewScoped
@RequestScoped
@Named
@Getter
@Setter
public class GhostNetDAO implements Serializable{

    //private String status;
    private GhostNetStatusEnum status;
    private int size;
    private GhostNetStatusEnum[] statusValues = GhostNetStatusEnum.values();
    private Coordinates coordinates = new Coordinates();
    private boolean assignToMe;

    private GhostNetStatusEnum filterStatus;
    private GhostNetStatusEnum newStatus;

    private Map<Long, GhostNetStatusEnum> newStatuses = new HashMap<>();

    @Inject
    private UserDAO userDAO;

    @PersistenceContext(unitName = "default")
    EntityManager entityManager;

    @Transactional
    public void createGhostNet(int size, GhostNetStatusEnum status) {
        GhostNet ghostNet = new GhostNet();
        HttpSession session = SessionUtils.getSession();
        String userName = (String) session.getAttribute("userName");
        User user = userDAO.getUserByUsername(userName);
        ghostNet.setSize(size);
        ghostNet.setStatus(status);
        ghostNet.setCoordinates(coordinates);
        if (assignToMe) {
            ghostNet.setUser(user);
        }
        entityManager.persist(ghostNet);
        System.out.println("Ghostnet ID: " + ghostNet.getId());
        System.out.println("Ghostnet Size: " + ghostNet.getSize());
        System.out.println("Ghostnet Status: " + ghostNet.getStatus());
        System.out.println("Ghostnet Coordinates: " + ghostNet.getCoordinates());
        System.out.println("Ghostnet User: " + ghostNet.getUser());
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
    public void assignUserToGhostNet(GhostNet ghostNet) {
        HttpSession session = SessionUtils.getSession();
        String userName = (String) session.getAttribute("userName");
        User user = userDAO.getUserByUsername(userName);
        ghostNet.setUser(user);
        entityManager.merge(ghostNet);
        System.out.println("User " + userName + " assigned to ghostnet: " + ghostNet.getId());
    }

    @Transactional
    public void unassignUserFromGhostNet(GhostNet ghostNet) {
        String userName = ghostNet.getUser().getUserName();
        ghostNet.setUser(null);
        entityManager.merge(ghostNet);
        System.out.println("User " + userName + " unassigned from ghostnet: " + ghostNet.getId());
    }

    @Transactional
    public List<GhostNet> getAllGhostNets() {
        return entityManager.createQuery("SELECT c FROM GhostNet c", GhostNet.class).getResultList();
    }

    @Transactional
    public void printAllGhostNets() {
        List<GhostNet> ghostNets = entityManager.createQuery("SELECT c FROM GhostNet c", GhostNet.class).getResultList();
    }

    @Transactional
    public void updateGhostNetStatus(GhostNet ghostNet) {
        GhostNetStatusEnum newStatusFromMap = newStatuses.get(ghostNet.getId());
        if (newStatusFromMap != null) {
            ghostNet.setStatus(newStatusFromMap);
            entityManager.merge(ghostNet);
            System.out.println("Ghostnet " + ghostNet.getId() + " status updated to " + newStatusFromMap);
            newStatuses.clear();
        }
    }

    public void setNewStatus(GhostNetStatusEnum newStatus) {
        this.newStatus = newStatus;
        System.out.println("New Ghostnet Status: " + newStatus);
    }

    public List<GhostNet> filteredGhostNets() {
        if (filterStatus == null) {
            return getAllGhostNets();
        } else {
            TypedQuery<GhostNet> query = entityManager.createQuery("SELECT g FROM GhostNet g WHERE g.status = :status", GhostNet.class);
            query.setParameter("status", filterStatus);
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
