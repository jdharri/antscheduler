package edu.controllers;

import edu.MainApp;
import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.model.Appointment;
import edu.model.Customer;
import edu.model.User;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class AppointmentFormController implements Initializable {

    @FXML
    private DatePicker appointmentDate;
    @FXML
    private ComboBox<Customer> appointmentCustomer;
    @FXML
    private ComboBox<String> appointmentStartTime;
    @FXML
    private ComboBox<String> appointmentEndTime;
    @FXML
    private Button createAppointmentButton;
    @FXML
    private Button cancelAppointmentButton;
    @FXML
    private TextField appointmentLocation;
    @FXML
    private TextField appointmentDescription;
    final ObservableList hours = FXCollections.observableArrayList();
   
    private final User currentUser = MainApp.getCurrentUser();
    Stage stage;
    @FXML
    private AnchorPane appointmentForm;
//    @FXML
    private CalendarTabController calendarTabController;
    @FXML
    private AnchorPane calendarTab;
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // stage = (Stage) appointmentDate.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/edu/fxml/CalendarTab.fxml"));
        calendarTabController = loader.getController();
       
        this.populateCustomers();
        hours.addAll(this.getHours());
        appointmentStartTime.getItems().clear();
        appointmentStartTime.getItems().addAll(hours);
        appointmentEndTime.getItems().clear();
        appointmentEndTime.getItems().addAll(hours);
        createAppointmentButton.setOnAction((event) -> {
            try {
                saveAppointment();
            } catch (ParseException ex) {
                Logger.getLogger(AppointmentFormController.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        });
        cancelAppointmentButton.setOnAction((event) -> {
            try {
                cancelAppointment();
            } catch (IOException ex) {
                Logger.getLogger(AppointmentFormController.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        });

    }

    /**
     * Utility method to get a list of hours to populate the drop-downs on the form
     *
     * @return List of String
     */
    private List<String> getHours() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime nextDay = ldt.plus(1, ChronoUnit.DAYS);
        List<String> hourSelections = new ArrayList<>();
        while (ldt.isBefore(nextDay.minus(15, ChronoUnit.MINUTES))) {
            ldt = ldt.plus(15, ChronoUnit.MINUTES);
            hourSelections.add(ldt.format(DateTimeFormatter.ofPattern("hh:mm a")));
        }

        return hourSelections;
    }

    /**
     * Cancel the addition of an appointment
     *
     * @throws IOException
     */
    public void cancelAppointment() throws IOException {
        System.out.println("************ cancel appointment");
        calendarTabController.showCalendar();

        this.clearForm();

    }

    /**
     * Reset the form
     */
    public void clearForm() {
        appointmentLocation.clear();
        appointmentDescription.clear();
        appointmentDate.getEditor().clear();
        appointmentEndTime.getEditor().clear();
        appointmentStartTime.getEditor().clear();
        appointmentForm.setVisible(false);
    }
    SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

//Date date = isoFormat.parse("2010-05-23T09:01:02");
    /**
     * Saves an appointment from data entered into the appointment form
     */
    @FXML
    public void saveAppointment() throws ParseException {

        final String currentUserId = new Integer(MainApp.getCurrentUser().getUserId()).toString();
        System.out.println("Save Appointment");
        Customer customer = appointmentCustomer.getValue();
        Appointment appt = new Appointment();
        System.out.println("***CUSTOMER ID: "+customer.getCustomerId());
        appt.setCustomerId(customer.getCustomerId());
        appt.setStart(formatDateTime(appointmentDate.getValue(),
                appointmentStartTime.getValue()));
        appt.setEnd(formatDateTime(appointmentDate.getValue(), appointmentEndTime.
                getValue()));
        appt.setCreateDate(Instant.now());
        appt.setCreatedBy(currentUserId);
      appt.setLastUpdate(Instant.now());
        appt.setLastUpdateBy(currentUserId);

        appt.setDescription(appointmentDescription.getText());
        appt.setLocation(appointmentLocation.getText());
        //appt.setTitle(String.format("apointment with %s with regard to %s",
        //        customer.getCustomerName(), appt.getDescription()));
        appt.setTitle("blahh title");
        appt.setUrl("http://localhost");
        appt.setContact(currentUserId);
      
        try {
            appointmentDAO.addAppointment(appt);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        calendarTabController.showCalendar();
        this.clearForm();
        calendarTabController.refresh();

    }

    /**
     * converts a {@link LocalDate} and time {@link String} to a Java {@link Date} for persistence
     * as a mysql compatible TIMESTAMP
     *
     * @param ld
     * @param time
     * @return
     */
    public Instant formatDateTime(LocalDate ld, String time) {

        LocalTime lt = LocalTime.parse(time, DateTimeFormatter.ofPattern("hh:mm a"));
        LocalDateTime localDateTime = LocalDateTime.of(ld, lt);
        ZonedDateTime localZoned = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = localZoned.withZoneSameInstant(ZoneOffset.UTC);
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return utcZoned.toInstant();
        // return  utcZoned.toInstant().toString().replaceAll("Z", "");

        // return null;
    }

    public void populateCustomers() {

      
        List<Customer> customerListResults = customerDAO.getAllCustomers();

        appointmentCustomer.getItems().addAll(customerListResults);

    }

 

    @FXML
    public void validateCustomerSelection() {

    }

    @FXML
    public void validateDateSelection() {

    }

    @FXML
    public void validateStartTimeSelection() {

    }

    @FXML
    public void validateEndTimeSelection() {

    }

    public void setCalendarTabController(CalendarTabController con) {
        this.calendarTabController = con;
    }

  
}
