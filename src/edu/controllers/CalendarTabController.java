/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.controllers;

import edu.dao.AppointmentDAO;
import edu.model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author jdharri
 */
public class CalendarTabController implements Initializable {

    @FXML
    private AnchorPane calendarTab;

    @FXML
    private ListView monthList;
    ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    private AppointmentFormController appointmentFormController;

    private final SimpleDateFormat queryFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AnchorPane appointmentPane;
    @FXML
    private RadioButton monthToggle;
    @FXML
    private RadioButton weekToggle;
    private final AppointmentDAO appointmentDao = new AppointmentDAO();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        try {
            FXMLLoader loader;
//            ToggleGroup weekMonthRadioGroup = new ToggleGroup();
            // weekToggle.setSelected(true);
//            monthToggle.setToggleGroup(weekMonthRadioGroup);
            loader = new FXMLLoader(getClass().getResource("/fxml/AppointmentForm.fxml"));
            appointmentPane = loader.load();
            appointmentFormController = loader.<AppointmentFormController>getController();

            appointmentFormController.setCalendarTabController(this);

            this.displayWeeklyAppointments();
        } catch (IOException ex) {

            Logger.getLogger(CalendarTabController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Makes the appointment pane visible
     *
     * @param event
     */
    public void createAppointment(final Event event) {

        calendarTab.getChildren().forEach(node -> node.setVisible(false));
        if (!calendarTab.getChildren().contains(appointmentPane)) {
            System.out.println("************ not a child adding");
            calendarTab.getChildren().add(appointmentPane);
        }
        appointmentPane.setVisible(true);
    }

    /**
     * Makes the calendar pane visible
     */
    public void showCalendar() {
        System.out.println("****************showCalendar");
        calendarTab.getChildren().forEach(node -> node.setVisible(true));

    }

    /**
     * Displays the appointments by week
     */
    public void displayWeeklyAppointments() {
        System.out.println("*****************displayWeeklyAppointments");

        LocalDate first = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate last = LocalDate.now().with(DayOfWeek.SUNDAY);
        Date firstOfWeek = Date.from(first.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
        Date lastOfWeek = Date.from(last.atTime(23, 59)
                .atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("query from: " + first + " to: " + last);
        monthList.getItems().removeAll(monthList.getItems());
        List<Appointment> appointments = appointmentDao.getAppointmentsBetweenDates(firstOfWeek.
                toInstant(),
                lastOfWeek.toInstant());

        appointments.forEach(appt -> monthList.getItems().add(appt));

    }

    /**
     * Displays appointments by month
     */
    public void displayMonthlyAppointments() {
        System.out.println("*****************displayMonthlyAppointments");
        LocalDate first = LocalDate.now().withDayOfMonth(1);
        LocalDate last = LocalDate.now().withDayOfMonth(first.lengthOfMonth());
        Date firstOfMonth = Date.from(first.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("query from: " + first + " to: " + last);
        Date lastOfMonth = Date.from(last.atTime(23, 59)
                .atZone(ZoneId.systemDefault()).toInstant());
        monthList.getItems().removeAll(monthList.getItems());

        List<Appointment> appointments = appointmentDao.getAppointmentsBetweenDates(firstOfMonth.
                toInstant(),
                lastOfMonth.toInstant());

        appointments.forEach(appt -> monthList.getItems().add(appt));

    }

    /**
     * Refreshes the appointment list
     */
    public void refresh() {
        displayMonthlyAppointments();
    }

}
