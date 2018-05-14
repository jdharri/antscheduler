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
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
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
    private TextArea appointmentDescription;
    @FXML
    private ComboBox<String> appointmentTitle;
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
        appointmentTitle.getItems().setAll("Initial Meeting", "Follow-up Appointment", "Referal Appointment", "Closing Meeting");

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
     * Utility method to get a list of hours to populate the drop-downs on the
     * form
     *
     * @return List of String
     */
    private List<String> getHours() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime nextDay = ldt.plus(1, ChronoUnit.DAYS);
        List<String> hourSelections = new ArrayList<>();
        while (ldt.isBefore(nextDay.minus(15, ChronoUnit.MINUTES))) {
            ldt = ldt.plus(15, ChronoUnit.MINUTES);
            hourSelections.add(ldt.format(DateTimeFormatter.ofPattern("h:mm a")));
        }

        return hourSelections;
    }

    /**
     * Cancel the addition of an appointment
     *
     * @throws IOException
     */
    public void cancelAppointment() throws IOException {
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

    public boolean appointmentOutsideWorkingHours(String startTime) {
        boolean saveAnyway = true;
        LocalTime lt = LocalTime.parse(appointmentStartTime.getValue(), DateTimeFormatter.ofPattern("h:mm a"));
        if (lt.isBefore(LocalTime.of(9, 0)) || lt.isAfter(LocalTime.of(17, 0))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are scheduling an appointment for a time outside of working hours. "
                    + "Are you sure you want to save this appointment?");

            alert.setTitle("Appointment outside working hours");
            alert.setHeaderText("Appointment outside working hours");
            Optional<ButtonType> act = alert.showAndWait();
            if (act.get() == ButtonType.CANCEL) {
                saveAnyway = false;
            }

        }
        return saveAnyway;
    }

    public boolean appointmentOverlap(Instant start, Instant end) {
        boolean overlaps = false;
        List<Appointment> overlappingAppointments = appointmentDAO.getOverlap(start, end, new Integer(currentUser.getUserId()).toString());
        if (overlappingAppointments.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are scheduling an appointment for a time overlaps another appointment. "
                    + "Are you sure you want to save this appointment?");
            alert.setTitle("Appointment overlaps another");
            alert.setHeaderText("Appointment overlaps another");
            Optional<ButtonType> act = alert.showAndWait();
            if (act.get() == ButtonType.CANCEL) {
                overlaps = true;
            }
            overlaps = true;
        }

        return overlaps;

    }

    /**
     * Saves an appointment from data entered into the appointment form
     */
    @FXML
    public void saveAppointment() throws ParseException {
        Instant start = formatDateTime(appointmentDate.getValue(),
                appointmentStartTime.getValue());
        Instant end = formatDateTime(appointmentDate.getValue(), appointmentEndTime.
                getValue());
        if (appointmentOutsideWorkingHours(appointmentStartTime.getValue()) && !(appointmentOverlap(start, end))) {
            final String currentUserId = new Integer(MainApp.getCurrentUser().getUserId()).toString();

            Customer customer = appointmentCustomer.getValue();
            Appointment appt = new Appointment();

            appt.setCustomerId(customer.getCustomerId());
            appt.setStart(start);
            appt.setEnd(end);
            appt.setCreateDate(Instant.now());
            appt.setCreatedBy(currentUserId);
            appt.setLastUpdate(Instant.now());
            appt.setLastUpdateBy(currentUserId);

            appt.setDescription(appointmentDescription.getText());
            appt.setLocation(appointmentLocation.getText());
            //appt.setTitle(String.format("apointment with %s with regard to %s",
            //        customer.getCustomerName(), appt.getDescription()));
            appt.setTitle(appointmentTitle.getValue());
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
    }

    /**
     * converts a {@link LocalDate} and time {@link String} to a Java
     * {@link Date} for persistence as a mysql compatible TIMESTAMP
     *
     * @param ld
     * @param time
     * @return
     */
    public Instant formatDateTime(LocalDate ld, String time) {

        LocalTime lt = LocalTime.parse(time, DateTimeFormatter.ofPattern("h:mm a"));
        LocalDateTime localDateTime = LocalDateTime.of(ld, lt);
        ZonedDateTime localZoned = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = localZoned.withZoneSameInstant(ZoneOffset.UTC);
        return utcZoned.toInstant();

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
