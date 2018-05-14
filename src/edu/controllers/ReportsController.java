package edu.controllers;

import edu.dao.AppointmentDAO;
import edu.dao.CustomerDAO;
import edu.dao.UserDAO;
import edu.model.Appointment;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author jdharri
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

    public void generateMonthlyReport() {
        LocalDate initial = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        StringBuilder reportBuilder = new StringBuilder("May Report By Appointment Type\n");
        List<Appointment> appointments = appointmentDAO.getAppointmentsBetweenDates(start.atStartOfDay()
                .toInstant(ZoneOffset.UTC), end.atTime(23, 59)
                .toInstant(ZoneOffset.UTC));
        Integer initialMeeting = 0;
        Integer followUp = 0;
        Integer referal = 0;
        Integer closing = 0;
//        "Initial Meeting"
//        , "Follow-up Appointment", "Referal Appointment", "Closing Meeting"
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
        reportBuilder.append("Initial meetings: ");
        reportBuilder.append(initialMeeting);
        reportBuilder.append("\nFollow-up Appointment: ");
        reportBuilder.append(followUp);
        reportBuilder.append("\nReferal Appointment: ");
        reportBuilder.append(referal);
        reportBuilder.append("\nClosing Meeting: ");
        reportBuilder.append(closing);
        reportTextArea.setText("");
        reportTextArea.setText(reportBuilder.toString());
    }

    public void generateConsultantSchedule() {
//        LocalDate initial = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime end = LocalDateTime.of(start, LocalTime.MIDNIGHT);
        StringBuilder reportBuilder = new StringBuilder("Todays Consultant Shedules\n");
        List<User> users = user
        List<Appointment> appointments = appointmentDAO.getAppointmentsBetweenDatesForUser(start.atStartOfDay()
                .toInstant(ZoneOffset.UTC), end.toInstant(ZoneOffset.UTC));
      
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
        reportBuilder.append("Initial meetings: ");
        reportBuilder.append(initialMeeting);
        reportBuilder.append("\nFollow-up Appointment: ");
        reportBuilder.append(followUp);
        reportBuilder.append("\nReferal Appointment: ");
        reportBuilder.append(referal);
        reportBuilder.append("\nClosing Meeting: ");
        reportBuilder.append(closing);
        reportTextArea.setText("");
        reportTextArea.setText(reportBuilder.toString());
    }

    public void generateCustom() {
         LocalDate initial = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        StringBuilder reportBuilder = new StringBuilder("May Report By Appointment Type\n");
        List<Appointment> appointments = appointmentDAO.getAppointmentsBetweenDates(start.atStartOfDay()
                .toInstant(ZoneOffset.UTC), end.atTime(23, 59)
                .toInstant(ZoneOffset.UTC));
        Integer initialMeeting = 0;
        Integer followUp = 0;
        Integer referal = 0;
        Integer closing = 0;
//        "Initial Meeting"
//        , "Follow-up Appointment", "Referal Appointment", "Closing Meeting"
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
        reportBuilder.append("Initial meetings: ");
        reportBuilder.append(initialMeeting);
        reportBuilder.append("\nFollow-up Appointment: ");
        reportBuilder.append(followUp);
        reportBuilder.append("\nReferal Appointment: ");
        reportBuilder.append(referal);
        reportBuilder.append("\nClosing Meeting: ");
        reportBuilder.append(closing);
        reportTextArea.setText("");
        reportTextArea.setText(reportBuilder.toString());
    }
}
