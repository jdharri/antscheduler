/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dao;

import edu.jpacontrollers.AppointmentJpaController;
import edu.model.Appointment;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    public List<Appointment> getAppointmentsBetweenDates(Instant from, Instant to) {
        Query q = con.getEntityManager().createQuery(
                "FROM Appointment AS a WHERE a.start BETWEEN :firstOfWeek AND :lastOfWeek");
        q.setParameter("firstOfWeek", from);
        q.setParameter("lastOfWeek", to);
        return q.getResultList();
    }

    public List<Appointment> getAppointmentsBetweenDatesForUser(Instant from, Instant to, String userId) {
        Query q = con.getEntityManager().createQuery(
                "FROM Appointment AS a WHERE a.start BETWEEN :firstOfWeek AND :lastOfWeek AND a.contact = :contact");
        q.setParameter("firstOfWeek", from);
        q.setParameter("lastOfWeek", to);
        q.setParameter("contact", userId);
        return q.getResultList();
    }

}
