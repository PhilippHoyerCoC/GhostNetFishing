package com.iu.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iu.model.Country;
import com.iu.model.PhoneNumber;
import com.iu.model.User;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Getter
@Setter
public class UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    private User user = new User();
    private List<Country> countries;

    public UserDAO() {
        user.setPhone(new PhoneNumber());
    }

    @PostConstruct
    public void init() {
        InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/CountryCodes.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            countries = mapper.readValue(is, new TypeReference<List<Country>>() {});
        } catch (IOException e) {
            logger.error("Error reading country codes", e);
        }
    }

    @PersistenceContext(unitName = "default")
    EntityManager entityManager;

    @Transactional
    public void createUser() {
        entityManager.persist(user);
        logger.info("User ID: {}", user.getUserId());
        logger.info("User Username: {}", user.getUserName());
        logger.info("User Password: {}", user.getPassword());
        logger.info("User Forename: {}", user.getForename());
        logger.info("User Lastname: {}", user.getLastname());
        logger.info("User Email: {}", user.getEmail());
        logger.info("User Phone: {}", user.getPhone());
        logger.info("Finished setting User from UserDAO Class");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You registered successfully!"));
    }

    public User getUserByUsername(String userName) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :userName", User.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
