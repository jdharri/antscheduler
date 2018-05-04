/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dao;

import edu.jpacontrollers.AppointmentJpaController;
import edu.jpacontrollers.CustomerJpaController;
import edu.model.Appointment;
import edu.model.Customer;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author JoelHarris
 */
public class CustomerDAO {

    private final CustomerJpaController con;
    private final EntityManagerFactory emf;

    public CustomerDAO() {
        emf = Persistence.createEntityManagerFactory("AntSchedulerPU");
        con = new CustomerJpaController(emf);
    }

    public void addCustomer(Customer customer) throws Exception {
        con.create(customer);
    }

    public void editCustomer(Customer customer) throws Exception {
        con.edit(customer);
    }

    public void deleteCustomer(int customerId) throws Exception {
        con.destroy(customerId);
    }

    public List<Customer> getAllCustomers() {
        return con.findCustomerEntities();
    }

    public Customer getCustomerById(int customerId) {
        return con.findCustomer(customerId);
    }

}
