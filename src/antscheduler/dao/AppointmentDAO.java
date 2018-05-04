/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antscheduler.dao;

import antscheduler.jpacontrollers.AppointmentJpaController;
import antscheduler.model.Appointment;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author JoelHarris
 */
public class AppointmentDAO {

    private final AppointmentJpaController con;
    private final EntityManagerFactory emf;

    public AppointmentDAO() {
        emf = Persistence.createEntityManagerFactory("AntSchedulerPU");
        con = new AppointmentJpaController(emf);
    }

    public void addAppointment(Appointment app) throws Exception {
        con.create(app);
    }

    public void editAppointment(Appointment app) throws Exception {
        con.edit(app);
    }

    public void deleteAppointment(int appId) throws Exception {
        con.destroy(appId);
    }

    public List<Appointment> getAllAppointments() {
        return con.findAppointmentEntities();
    }

    public Appointment getAppointmentById(int appId) {
        return con.findAppointment(appId);
    }

}
