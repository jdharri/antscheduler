package edu.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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

    /**
     *
     */
    public Appointment() {
    }

    /**
     *
     * @param appointmentId
     */
    public Appointment(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     * @param appointmentId
     * @param customerId
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param url
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
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

    /**
     *
     * @return
     */
    public Integer getAppointmentId() {
        return appointmentId;
    }

    /**
     *
     * @param appointmentId
     */
    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getContact() {
        return contact;
    }

    /**
     *
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     */
    public Instant getStart() {
        return start;
    }

    /**
     *
     * @param start
     */
    public void setStart(Instant start) {
        this.start = start;
    }

    /**
     *
     * @return
     */
    public Instant getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */
    public void setEnd(Instant end) {
        this.end = end;
    }

    /**
     *
     * @return
     */
    public Instant getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     */
    public Instant getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     *
     * @param lastUpdateBy
     */
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
                format("%s\nCustomer: %s\n%s - %s\n%s", this.title, this.customerId, start, end,
                        this.description);
    }

}
