/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controllers;

import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.model.Appointment;
import edu.model.Customer;
import java.net.URL;
import java.time.Duration;

import java.time.LocalDateTime;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class CustomerListController implements Initializable {

    @FXML
    private ListView<Customer> customerList;
    ObservableList<Customer> customers = FXCollections.observableArrayList();

    @FXML
    private Tab CustomerTab;
    @FXML
    private Parent customerForm;
    @FXML
    private CustomerFormController customerFormController;
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AppointmentDAO appointmentDao = new AppointmentDAO();
//    @FXML
//    private Pane customerFormPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerFormController.setCustomerListController(this);

        customerForm.setVisible(false);

        this.populateUserList();
//        Alert alert = new Alert(AlertType.INFORMATION, "This is an alert");
//        alert.show();
        this.queryForAppointments();
        customerList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue,
                    Customer newValue) {
                if (null != newValue) {
                    System.out.println(newValue.getCustomerName() + " Id: " + newValue.
                            getCustomerId());
                    customerForm.setVisible(true);
                    customerFormController.populateCustomer(newValue);
                }
            }

        });

    }

    public void queryForAppointments() {
        System.out.println("querying for appointments in the next 15 minutes");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutesFromNow = now.plus(Duration.ofMinutes(15));
        Date start = Date.from(now.toInstant(ZoneOffset.UTC));
        Date end = Date.from(fifteenMinutesFromNow.toInstant(ZoneOffset.UTC));
        System.out.println("***********query start: " + start + "  query end: " + end);

        List<Appointment> appointments = appointmentDao.getAppointmentsBetweenDates(start.
                toInstant(), end.toInstant());
        System.out.println(appointments.size() + " appointments found");
        createAppointmentAlerts(appointments);
    }

    public void createAppointmentAlerts(final List<Appointment> appointments) {
        System.out.println("creating appointment alerts");
        for (Appointment appt : appointments) {
            Alert alert = new Alert(AlertType.INFORMATION, String.format(
                    "%s You have an appointment with %s at %s", appt.getTitle(), appt.getContact(),
                    appt.getStart()));
            alert.show();
        }
    }

    public void test() {
        System.out.println("***********************************test");
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Populates the user list
     */
    public void populateUserList() {

        List<Customer> customerListResults = customerDAO.getAllCustomers();

        customers.addAll(customerListResults);
        customerList.setItems(customers);

    }

    /**
     * Used to refresh the customer list if a customer is added or edited
     */
    public void refresh() {
        customerList.getItems().clear();
        this.populateUserList();
    }


}
