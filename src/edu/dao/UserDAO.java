/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dao;

import edu.jpacontrollers.UserJpaController;
import edu.model.User;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author JoelHarris
 */
public class UserDAO {

    private final UserJpaController con;
    private final EntityManagerFactory emf;

    public UserDAO() {
        emf = Persistence.createEntityManagerFactory("AntSchedulerPU");
        con = new UserJpaController(emf);
    }

    public void addUser(User user) throws Exception {
        con.create(user);
    }

    public void editUser(User user) throws Exception {
        con.edit(user);
    }

    public void deleteUser(int userId) throws Exception {
        con.destroy(userId);
    }

    public List<User> getAllUsers() {
        return con.findUserEntities();
    }

    public User getUserById(int userId) {

        return con.findUser(userId);
    }

    public User findByUserName(String userName) {
        return (User) con.getEntityManager().createNamedQuery("User.findByUserName").getSingleResult();
    }
}
