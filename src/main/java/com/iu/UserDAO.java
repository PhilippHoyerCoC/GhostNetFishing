package com.iu;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @PostConstruct
    public void init() {
        InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/CountryCodes.json");
        // parse the JSON and create a list of Country objects
        System.out.println("is: " + is);
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
        System.out.println("User ID: " + user.getUserId());
        System.out.println("User Username: " + user.getUserName());
        System.out.println("User Password: " + user.getPassword());
        System.out.println("User Forename: " + user.getForename());
        System.out.println("User Lastname: " + user.getLastname());
        System.out.println("User Email: " + user.getEmail());
        System.out.println("User Phone: " + user.getPhone());
        System.out.println("Finished setting User from UserDAO Class");
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