/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.TABLE;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JoelHarris
 */
@Entity
@Table(name = "appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a")
    ,
        @NamedQuery(name = "Appointment.findByAppointmentId", query
            = "SELECT a FROM Appointment a WHERE a.appointmentId = :appointmentId")
    ,
        @NamedQuery(name = "Appointment.findByCustomerId", query
            = "SELECT a FROM Appointment a WHERE a.customerId = :customerId")
    ,
        @NamedQuery(name = "Appointment.findByTitle", query
            = "SELECT a FROM Appointment a WHERE a.title = :title")
    ,
        @NamedQuery(name = "Appointment.findByUrl", query
            = "SELECT a FROM Appointment a WHERE a.url = :url")
    ,
        @NamedQuery(name = "Appointment.findByStart", query
            = "SELECT a FROM Appointment a WHERE a.start = :start")
    ,
        @NamedQuery(name = "Appointment.findByEnd", query
            = "SELECT a FROM Appointment a WHERE a.end = :end")
    ,
        @NamedQuery(name = "Appointment.findByCreateDate", query
            = "SELECT a FROM Appointment a WHERE a.createDate = :createDate")
    ,
        @NamedQuery(name = "Appointment.findByCreatedBy", query
            = "SELECT a FROM Appointment a WHERE a.createdBy = :createdBy")
    ,
        @NamedQuery(name = "Appointment.findByLastUpdate", query
            = "SELECT a FROM Appointment a WHERE a.lastUpdate = :lastUpdate")
    ,
        @NamedQuery(name = "Appointment.findByLastUpdateBy", query
            = "SELECT a FROM Appointment a WHERE a.lastUpdateBy = :lastUpdateBy")})
//@SequenceGenerator(name = "seq" , initialValue=1, allocationSize=100)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentId")
    private Integer appointmentId;
    @Basic(optional = false)
    @Column(name = "customerId")
    private int customerId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Lob
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Lob
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "start")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = InstantAttributeConverter.class)
    private Instant start;
    @Basic(optional = false)
    @Convert(converter = InstantAttributeConverter.class)
    @Column(name = "end")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant end;
    @Convert(converter = InstantAttributeConverter.class)
    @Basic(optional = false)
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createDate;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private String createdBy;
    @Convert(converter = InstantAttributeConverter.class)
    @Basic(optional = false)
    @Column(name = "lastUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant lastUpdate;
    @Basic(optional = false)
    @Column(name = "lastUpdateBy")
    private String lastUpdateBy;

    public Appointment() {
    }

    public Appointment(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Appointment(Integer appointmentId, int customerId, String title, String description,
            String location, String contact, String url, Instant start, Instant end,
            Instant createDate,
            String createdBy, Instant lastUpdate, String lastUpdateBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentId != null ? appointmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.appointmentId == null && other.appointmentId != null) || (this.appointmentId
                != null && !this.appointmentId.equals(other.appointmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String start = LocalDateTime.ofInstant(this.getStart(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        String end = LocalDateTime.ofInstant(this.getEnd(), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));

        return String.
                format("%s \n%s - %s\n%s", this.title, start, end,
                        this.description);
    }

}
