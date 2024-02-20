package com.iu;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String username;
    private String password;
    private String forename;
    private String lastname;
    private String email;
    private PhoneNumber phone = new PhoneNumber();

    private List<Country> countries;

    private static final Logger logger = Logger.getLogger(UserDAO.class);

    @PostConstruct
    public void init() {
        InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/CountryCodes.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            countries = mapper.readValue(is, new TypeReference<List<Country>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PersistenceContext(unitName = "default")
    EntityManager entityManager;

    @Transactional
    public void createUser(String userName, String password, String forename, String lastname, String email) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setForename(forename);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPhone(phone);
        entityManager.persist(user);
        logger.info("User ID: " + user.getUserId());
        logger.info("User Username: " + user.getUserName());
        logger.info("User Password: " + user.getPassword());
        logger.info("User Forename: " + user.getForename());
        logger.info("User Lastname: " + user.getLastname());
        logger.info("User Email: " + user.getEmail());
        logger.info("User Phone: " + user.getPhone());
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
