package edu.controllers;

import edu.MainApp;
import edu.dao.AddressDAO;
import edu.dao.AppointmentDAO;
import edu.dao.CityDAO;
import edu.dao.CountryDAO;
import edu.dao.CustomerDAO;
import edu.model.Address;
import edu.model.Appointment;
import edu.model.City;
import edu.model.Country;
import edu.model.Customer;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class CustomerFormController implements Initializable {

    @FXML
    AnchorPane customerForm;
    @FXML
    TextField customerName;
    @FXML
    private TextField address;
    @FXML
    private TextField address2;
    @FXML
    private TextField city;
    @FXML
    private TextField country;
    @FXML
    private TextField phone;
    @FXML
    private TextField postalCode;
    @FXML
    private Button cancel;
    @FXML
    private Button saveUserButton;
    @FXML
    private Label customerFormLabel;

    private Integer customerId;
    @FXML
    private Button addNewUser;

    private CustomerListController customerListController;
    private final AppointmentDAO appointmentDao = new AppointmentDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final CountryDAO countryDAO = new CountryDAO();
    private final AddressDAO addressDAO = new AddressDAO();

    private final Logger logger = Logger.getLogger(CustomerFormController.class.getName());

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Sets the {@link CustomerListController}
     *
     * @param con
     */
    public void setCustomerListController(final CustomerListController con) {
        this.customerListController = con;
    }

    /**
     * Constructor
     */
    public CustomerFormController() {
    }

    /**
     * populates the customer form
     *
     * @param customer
     */
    public void populateCustomer(final Customer customer) {

        this.customerId = customer.getCustomerId();
        this.customerName.setText(customer.getCustomerName());
        this.address.setText(customer.getAddress().getAddress());
        this.address2.setText(customer.getAddress().getAddress2());
        this.city.setText(customer.getAddress().getCity().getCity());
        this.country.setText(customer.getAddress().getCity().getCountry().getCountry());
        this.postalCode.setText(customer.getAddress().getPostalCode());
        this.phone.setText(customer.getAddress().getPhone());

    }

    /**
     * Save user
     *
     * @param event
     */
    @FXML
    public void SaveCustomer(ActionEvent event) {
        final String currentUserId = new Integer(MainApp.getCurrentUser().getUserId()).toString();

        Customer customer;
        Country custCountry = null;
        City custCity = null;
        Address custAddress = null;
        if (this.customerId != null) {
            customer = customerDAO.getCustomerById(customerId);

        } else {
            custCountry = new Country();

            custCity = new City();
            custAddress = new Address();
            customer = new Customer();
        }
        try {

            custCountry.setCountry(country.getText());
            custCountry.setCreateDate(new Date());
            custCountry.setCreatedBy(currentUserId);
            custCountry.setLastUpdate(new Date());
            custCountry.setLastUpdateBy(currentUserId);
            // custCountry = countryDAO.addCountry(custCountry);

            custCity.setCity(city.getText());
            custCity.setCreateDate(new Date());
            custCity.setCreatedBy(currentUserId);
            custCity.setLastUpdate(new Date());
            custCity.setLastUpdateBy(currentUserId);

            custCity.setCountry(custCountry);
            // custCity = cityDAO.addCity(custCity);

            //  cityDAO.editCity(custCity);
            custAddress.setAddress(address.getText());
            custAddress.setAddress2(address2.getText());
            custAddress.setCreateDate(new Date());
            custAddress.setCreatedBy(currentUserId);
            custAddress.setLastUpdateBy(currentUserId);
            custAddress.setLastUpdate(new Date());
            custAddress.setPhone(phone.getText());
            custAddress.setPostalCode(postalCode.getText());

            custAddress.setCity(custCity);
            // custAddress = addressDAO.addAddress(custAddress);

            customer.setCustomerName(customerName.getText());
            customer.setCreateDate(new Date());
            customer.setActive(true);
            customer.setLastUpdate(new Date());
            customer.setCreatedBy(currentUserId);
            customer.setLastUpdateBy(currentUserId);
            // customer = customerDAO.addCustomer(customer);
            customer.setAddress(custAddress);
            System.out.println("88888888888888888888");
            customerDAO.addCustomer(customer);

        } catch (Exception ex) {
            logger.log(Level.WARNING, "Problem saving customer{0}", ex);
        }

        this.cancel.fire();
        customerListController.refresh();

    }

    /**
     *
     * @param event
     */
    @FXML
    private void clearUserId(ActionEvent event) {

        this.cancel.fire();
        this.customerId = null;
    }

    /**
     *
     * @param event
     */
    @FXML
    private void CancelUser(ActionEvent event) {

        customerName.clear();
        address.clear();
        address2.clear();
        city.clear();
        country.clear();
        phone.clear();
        postalCode.clear();

    }

   
}
