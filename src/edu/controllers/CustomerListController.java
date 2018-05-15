package edu.controllers;

import edu.MainApp;
import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.model.Address;
import edu.model.Appointment;
import edu.model.City;
import edu.model.Country;
import edu.model.Customer;
import java.net.URL;
import java.time.Instant;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerFormController.setCustomerListController(this);

        customerForm.setVisible(false);

        this.populateUserList();

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

    /**
     *
     */
    public void queryForAppointments() {
        Instant now = Instant.now();
        Instant fifteenMinutesFromNow = now.plus(15, ChronoUnit.MINUTES);

        List<Appointment> appointments = appointmentDao
                .getAppointmentsBetweenDatesForUser(now, fifteenMinutesFromNow,
                        new Integer(MainApp.getCurrentUser().getUserId()).toString());

        createAppointmentAlerts(appointments);
    }

    /**
     *
     */
    public void addNewCustomer() {
        customerForm.setVisible(true);
    }

    /**
     *
     * @param appointments
     */
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

    /**
     *
     * @param customer
     */
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

    /**
     *
     */
    public void seedAppointments() {
        for (int i = 1; i < 11; i++) {
            try {
                Appointment ap1 = new Appointment();
                ap1.setContact("1");
                ap1.setCreateDate(Instant.now());
                ap1.setCreatedBy("1");
                ap1.setCustomerId(1);
                ap1.setDescription("Follow-up appointment to discuss proposed contract");

                Instant start = Instant.now().plus((i * 15), ChronoUnit.MINUTES);
                ap1.setStart(start);
                ap1.setEnd(start.plus(30, ChronoUnit.MINUTES));
                ap1.setLastUpdate(Instant.now());
                ap1.setLastUpdateBy("1");
                ap1.setLocation("Local Starbucks");
                ap1.setTitle("Contract proposal follow-up");
                ap1.setUrl("http://localhost");
                appointmentDao.addAppointment(ap1);
            } catch (Exception ex) {
                Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     *
     */
    public void seedCustomers() {

        List<String> names = Arrays.asList("Ella Newton", "Jo Bowman",
                "Micheal Becker", "Alyssa Taylor", "Barbara Keller",
                "Alvin Barnes", "Jeanne Massey", "Irvin Chandler",
                "Levi Lindsey", "Minnie Stevens");
        names.forEach(n -> {

            try {
                //create address
                Address addy = new Address();
                addy.setAddress("123 Someplace st.");
                addy.setAddress2("STE #5");

                //create city
                City city = new City();
                city.setCity("Shaboygan");

                city.setCreateDate(Instant.now());
                city.setCreatedBy("1");
                city.setLastUpdate(Instant.now());
                city.setLastUpdateBy("1");

                //create Country
                Country country = new Country();
                country.setCountry("USA");
                country.setCreateDate(Instant.now());
                country.setCreatedBy("1");
                country.setLastUpdate(Instant.now());
                country.setLastUpdateBy("1");

                city.setCountry(country);
                addy.setCity(city);
                addy.setCreateDate(Instant.now());
                addy.setLastUpdateBy("1");
                addy.setPostalCode("12345");
                addy.setPhone("555-867-5309");
                addy.setCreatedBy("1");
                addy.setLastUpdate(Instant.now());

                //create customer
                Customer c = new Customer();
                c.setCustomerName(n);
                c.setActive(true);
                c.setAddress(addy);
                c.setCreateDate(Instant.now());
                c.setCreatedBy("1");
                c.setLastUpdateBy("1");
                c.setLastUpdate(Instant.now());
                customerDAO.addCustomer(c);
            } catch (Exception ex) {
                Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
}
