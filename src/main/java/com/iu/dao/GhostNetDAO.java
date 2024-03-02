package com.iu.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.iu.model.Coordinates;
import com.iu.model.GhostNet;
import com.iu.model.GhostNetStatusEnum;
import com.iu.model.User;
import com.iu.session.SessionUtils;


@RequestScoped
@Named
@Getter
@Setter
public class GhostNetDAO {

    private static final Logger logger = LogManager.getLogger(GhostNetDAO.class);
    private static final String USER_NAME = "userName";

    private GhostNet ghostNet = new GhostNet();

    private GhostNetStatusEnum[] statusValues = GhostNetStatusEnum.values();
    private boolean assignToMe;
    private GhostNetStatusEnum filterStatus;
    private Map<Long, GhostNetStatusEnum> newStatuses = new HashMap<>();

    @Inject
    private UserDAO userDAO;

    @PersistenceContext(unitName = "default")
    EntityManager entityManager;

    public GhostNetDAO() {
        ghostNet.setCoordinates(new Coordinates());
    }

    @Transactional
    public void createGhostNet() {
        HttpSession session = SessionUtils.getSession();
        String userName = (String) session.getAttribute(USER_NAME);
        User user = userDAO.getUserByUsername(userName);
        if (assignToMe) {
            ghostNet.setUser(user);
        }
        entityManager.persist(ghostNet);
        logger.info("Ghostnet ID: {}", ghostNet.getId());
        logger.info("Ghostnet Size: {}", ghostNet.getSize());
        logger.info("Ghostnet Status: {}", ghostNet.getStatus());
        logger.info("Ghostnet Coordinates: {}", ghostNet.getCoordinates());
        logger.info("Ghostnet User: {}", ghostNet.getUser());
        logger.info("Finished setting Ghostnet from GhostNetDAO Class");
        // Reset the ghostNet object after persisting it
        ghostNet = new GhostNet();
    }

    @Transactional
    public void createGhostNetWithoutStatus() {
        ghostNet.setStatus(GhostNetStatusEnum.REPORTED);
        entityManager.persist(ghostNet);
        logger.info("Ghostnet ID: {}", ghostNet.getId());
        logger.info("Ghostnet Size: {}", ghostNet.getSize());
        logger.info("Ghostnet Status: {}", ghostNet.getStatus());
        logger.info("Ghostnet Coordinates: {}", ghostNet.getCoordinates());
        logger.info("Finished setting Ghostnet from GhostNetDAO Class");
        // Reset the ghostNet object after persisting it
        ghostNet = new GhostNet();
    }

    @Transactional
    public void assignUserToGhostNet(GhostNet ghostNet) {
        HttpSession session = SessionUtils.getSession();
        String userName = (String) session.getAttribute(USER_NAME);
        User user = userDAO.getUserByUsername(userName);
        ghostNet.setUser(user);
        entityManager.merge(ghostNet);
        logger.info("User {} assigned to ghostnet: {}", userName, ghostNet.getId());
    }

    @Transactional
    public void unassignUserFromGhostNet(GhostNet ghostNet) {
        String userName = ghostNet.getUser().getUserName();
        ghostNet.setUser(null);
        entityManager.merge(ghostNet);
        logger.info("User {} unassigned from ghostnet: {}", userName, ghostNet.getId());
    }

    @Transactional
    public List<GhostNet> getAllGhostNets() {
        return entityManager.createQuery("SELECT c FROM GhostNet c", GhostNet.class).getResultList();
    }

    @Transactional
    public void updateGhostNetStatus(GhostNet ghostNet) {
        GhostNetStatusEnum newStatusFromMap = newStatuses.get(ghostNet.getId());
        if (newStatusFromMap != null) {
            ghostNet.setStatus(newStatusFromMap);
            entityManager.merge(ghostNet);
            logger.info("Ghostnet {} status updated to {}", ghostNet.getId(), newStatusFromMap);
            newStatuses.clear();
        }
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
