package edu.controllers;

import edu.MainApp;
import edu.dao.AddressDAO;
import edu.dao.AppointmentDAO;
import edu.dao.CityDAO;
import edu.dao.CountryDAO;
import edu.dao.CustomerDAO;
import edu.model.Address;
import edu.model.City;
import edu.model.Country;
import edu.model.Customer;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public void SaveCustomer(ActionEvent event) throws IOException {
        final String currentUserId = new Integer(MainApp.getCurrentUser().getUserId()).toString();

        Customer customer;
        Country custCountry = null;
        City custCity = null;
        Address custAddress = null;
        if (this.customerId != null) {
            customer = customerDAO.getCustomerById(customerId);
            custCountry = customer.getAddress().getCity().getCountry();
            custCity = customer.getAddress().getCity();
            custAddress = customer.getAddress();
        } else {
            custCountry = new Country();

            custCity = new City();
            custAddress = new Address();
            customer = new Customer();
        }
        try {

            custCountry.setCountry(country.getText());
            custCountry.setCreateDate(Instant.now());
            custCountry.setCreatedBy(currentUserId);
            custCountry.setLastUpdate(Instant.now());
            custCountry.setLastUpdateBy(currentUserId);
            custCity.setCity(city.getText());
            custCity.setCreateDate(Instant.now());
            custCity.setCreatedBy(currentUserId);
            custCity.setLastUpdate(Instant.now());
            custCity.setLastUpdateBy(currentUserId);
            custCity.setCountry(custCountry);
            custAddress.setAddress(address.getText());
            custAddress.setAddress2(address2.getText());
            custAddress.setCreateDate(Instant.now());
            custAddress.setCreatedBy(currentUserId);
            custAddress.setLastUpdateBy(currentUserId);
            custAddress.setLastUpdate(Instant.now());
            custAddress.setPhone(phone.getText());
            custAddress.setPostalCode(postalCode.getText());
            custAddress.setCity(custCity);
            customer.setCustomerName(customerName.getText());
            customer.setCreateDate(Instant.now());
            customer.setActive(true);
            customer.setLastUpdate(Instant.now());
            customer.setCreatedBy(currentUserId);
            customer.setLastUpdateBy(currentUserId);

            customer.setAddress(custAddress);
            if (customer.getCustomerId() != null) {
                customerDAO.editCustomer(customer);
            } else {
                customerDAO.addCustomer(customer);
            }

        } catch (Exception ex) {
            logger.log(Level.WARNING, "Problem saving customer{0}", ex);
        }

        this.cancel.fire();
        customerListController.refresh();
        FXMLLoader loader;

        loader = new FXMLLoader(getClass().getResource("/edu/fxml/AppointmentForm.fxml"));
        AnchorPane appointmentPane = loader.load();
        appointmentPane.setVisible(false);

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
