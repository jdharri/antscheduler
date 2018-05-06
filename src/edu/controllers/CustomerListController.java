package edu.controllers;

import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.model.Appointment;
import edu.model.Customer;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
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
                    customerForm.setVisible(true);
                    customerFormController.populateCustomer(newValue);
                }
            }

        });

    }

    public void queryForAppointments() {
        Instant now = Instant.now();
        List<Appointment> appointments = appointmentDao.getAppointmentsBetweenDates(now, now.plus(15, ChronoUnit.MINUTES));

        createAppointmentAlerts(appointments);
    }

    public void createAppointmentAlerts(final List<Appointment> appointments) {
       
        for (Appointment appt : appointments) {
            long timeTillMetting = ChronoUnit.MINUTES.between(Instant.now(), appt.getStart());
            Customer customer = customerDAO.getCustomerById(appt.getCustomerId());
            Alert alert = new Alert(AlertType.INFORMATION, String.format(
                    "Customer: %s \nDescription: %s ", customer.getCustomerName(),
                    appt.getDescription()));

            alert.setTitle("Upcoming appointment starts in " + timeTillMetting + " minutes");
            String start = LocalDateTime.ofInstant(appt.getStart(), ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            String end = LocalDateTime.ofInstant(appt.getEnd(), ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

            alert.setHeaderText(String.format("%s %s - %s", appt.getTitle(), start, end));
            alert.show();
        }
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
