package edu.controllers;

import edu.MainApp;
import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.model.Address;
import edu.model.City;
import edu.model.Country;
import edu.model.Customer;
import java.net.URL;
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
//     @FXML
    private CustomerListController customerListController;
    private final AppointmentDAO appointmentDao = new AppointmentDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

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
//
//        Address custAddr = (Address) session.createQuery("from Address WHERE addressId=:addressId")
//                .setParameter("addressId", customer.getAddressId())
//                .getSingleResult();
//
//        City custCity = (City) session.createQuery("from City WHERE cityId=:cityId")
//                .setParameter("cityId", custAddr.getCityId())
//                .getSingleResult();
//        Country custCountry = (Country) session.createQuery(
//                "from Country WHERE countryId=:countryId")
//                .setParameter("countryId", custCity.getCountryId())
//                .getSingleResult();
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
//
//                custAddress = (Address) session.createQuery(
//                        "from Address WHERE addressId=:addressId")
//                        .setParameter("addressId", customer.getAddressId())
//                        .getSingleResult();
//
//                
//                custCity = (City) session.createQuery("from City WHERE cityId=:cityId")
//                        .setParameter("cityId", custAddress.getCityId())
//                        .getSingleResult();
//                custCountry = (Country) session.createQuery(
//                        "from Country WHERE countryId=:countryId")
//                        .setParameter("countryId", custCity.getCountryId())
//                        .getSingleResult();
        } else {
            custCountry = new Country();
            custCity = new City();
            custAddress = new Address();
            customer = new Customer();
        }
        try {
            //  Country custCountry = new Country();
//            Address address = new Address();
         
            custCountry.setCountry(country.getText());
            custCountry.setCreateDate(new Date());
            custCountry.setCreatedBy(currentUserId);
            custCountry.setLastUpdate(new Date());
            custCountry.setLastUpdateBy(currentUserId);
           

            custCity.setCity(city.getText());
            custCity.setCountry(custCountry);
            custCity.setCreateDate(new Date());
            custCity.setCreatedBy(currentUserId);
            custCity.setLastUpdate(new Date());
            custCity.setLastUpdateBy(currentUserId);
          
//
//        Address custAddress = new Address();
            custAddress.setAddress(address.getText());
            custAddress.setAddress2(address2.getText());
            custAddress.setCity(custCity);
            custAddress.setCreateDate(new Date());
            custAddress.setCreatedBy(currentUserId);
            custAddress.setLastUpdateBy(currentUserId);
            custAddress.setLastUpdate(new Date());
            custAddress.setPhone(phone.getText());
            custAddress.setPostalCode(postalCode.getText());
           
          
//
//            session.save(custAddress);
//            System.out.println("***********************************addressId: " + custAddress.
//                    getAddressId());
//
//            customer.setCustomerName(customerName.getText());
            customer.setCustomerName(customerName.getText());
            customer.setCreateDate(new Date());
            customer.setActive(true);
            customer.setLastUpdate(new Date());
            customer.setCreatedBy(currentUserId);
            customer.setLastUpdateBy(currentUserId);
            customer.setAddress(custAddress);
//            customer.setAddressId(custAddress.getAddressId());

            customerDAO.editCustomer(customer);
        } catch (Exception ex) {
            Logger.getLogger(CustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
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
