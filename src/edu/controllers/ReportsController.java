package edu.controllers;

import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.dao.UserDAO;
import edu.model.Appointment;
import edu.model.Customer;
import edu.model.User;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author Joel Harris
 */
public class ReportsController implements Initializable {

    @FXML
    private TextArea reportTextArea;
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Generates monthly report by appointment type
     */
    public void generateMonthlyReport() {
        LocalDate initial = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        StringBuilder rb = new StringBuilder("May Report By Appointment Type\n");
        rb.append("\n");
        rb.append("=============================");
        rb.append("\n");
        List<Appointment> appointments = appointmentDAO.getAppointmentsBetweenDates(start.atStartOfDay()
                .toInstant(ZoneOffset.UTC), end.atTime(23, 59)
                .toInstant(ZoneOffset.UTC));
        Integer initialMeeting = 0;
        Integer followUp = 0;
        Integer referal = 0;
        Integer closing = 0;

        for (Appointment ap : appointments) {
            switch (ap.getTitle()) {
                case "Initial Meeting":
                    initialMeeting++;
                    break;
                case "Follow-up Appointment":
                    followUp++;
                    break;
                case "Referal Appointment":
                    referal++;
                    break;
                case "Closing Meeting":
                    closing++;
                    break;
            }
        }
        rb.append("*****************************************\n");

        rb.append("Initial meetings: ");
        rb.append(initialMeeting);
        rb.append("\n");
        rb.append("*****************************************\n");
        rb.append("Follow-up Appointment: ");
        rb.append(followUp);
        rb.append("\n");
        rb.append("*****************************************\n");
        rb.append("Referal Appointment: ");
        rb.append(referal);
        rb.append("\n");
        rb.append("*****************************************\n");
        rb.append("Closing Meeting: ");
        rb.append(closing);
        rb.append("\n");
        rb.append("*****************************************\n");
        reportTextArea.setText("");
        reportTextArea.setText(rb.toString());
    }

    /**
     * Generates Consultant schedule for the day
     */
    public void generateConsultantSchedule() {

        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        StringBuilder rb = new StringBuilder("Todays Consultant Shedules\n\n");
        rb.append("\n");
        rb.append("=============================");
        rb.append("\n");
        rb.append(LocalDate.now());
        rb.append("\n");
        rb.append("=============================");
        rb.append("\n");
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {

            List<Appointment> appointments = appointmentDAO.getAppointmentsBetweenDatesForUser(start.toInstant(OffsetDateTime.now().getOffset()), end.toInstant(OffsetDateTime.now().getOffset()), u.getUserId().toString());
            rb.append("\n");
            rb.append("-------------------------------------");
            rb.append("\n");
            rb.append("Appointments for user: ");
            rb.append(u.getUserName());
            rb.append("\n");
            rb.append("-------------------------------------");
            rb.append("\n");

            appointments.forEach(ap -> {
                rb.append("\n");
                rb.append(ap.toString());
                rb.append("\n");
                rb.append("*****************************************\n");
            });
            rb.append("\n");
        }

        reportTextArea.setText("");
        reportTextArea.setText(rb.toString());
    }

    /**
     * Generates total number of appointments per customer
     */
    public void generateCustom() {
        StringBuilder rb = new StringBuilder("Total number of appointments per customer\n\n");
        rb.append("=============================");
        List<Customer> customers = customerDAO.getAllCustomers();
        for (Customer c : customers) {
            List<Appointment> appointments = appointmentDAO.getAppointmentsByCustomerId(c.getCustomerId());
            rb.append("\n");
            rb.append("-------------------------------------");
            rb.append("\n");
            rb.append(c.getCustomerName());
            rb.append(" : ");
            rb.append(appointments.size());
            rb.append(" appointments");
            rb.append("\n");
            rb.append("-------------------------------------");
            rb.append("\n");

        }
        reportTextArea.setText("");
        reportTextArea.setText(rb.toString());
    }
}
