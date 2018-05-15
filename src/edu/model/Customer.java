package edu.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JoelHarris
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    ,
        @NamedQuery(name = "Customer.findByCustomerId", query
            = "SELECT c FROM Customer c WHERE c.customerId = :customerId")
    ,
        @NamedQuery(name = "Customer.findByCustomerName", query
            = "SELECT c FROM Customer c WHERE c.customerName = :customerName")
    ,
        @NamedQuery(name = "Customer.findByActive", query
            = "SELECT c FROM Customer c WHERE c.active = :active")
    ,
        @NamedQuery(name = "Customer.findByCreateDate", query
            = "SELECT c FROM Customer c WHERE c.createDate = :createDate")
    ,
        @NamedQuery(name = "Customer.findByCreatedBy", query
            = "SELECT c FROM Customer c WHERE c.createdBy = :createdBy")
    ,
        @NamedQuery(name = "Customer.findByLastUpdate", query
            = "SELECT c FROM Customer c WHERE c.lastUpdate = :lastUpdate")
    ,
        @NamedQuery(name = "Customer.findByLastUpdateBy", query
            = "SELECT c FROM Customer c WHERE c.lastUpdateBy = :lastUpdateBy")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Integer customerId;
    @Basic(optional = false)
    @Column(name = "customerName")
    private String customerName;
//    @Basic(optional = false)
    //@OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "address_addressId")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", referencedColumnName = "addressId", unique = true, nullable = true, insertable = true,
            updatable = true)
    private Address address;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = InstantAttributeConverter.class)
    private Instant createDate;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private String createdBy;
    @Basic(optional = false)
    @Column(name = "lastUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    @Convert(converter = InstantAttributeConverter.class)
    private Instant lastUpdate;
    @Basic(optional = false)
    @Column(name = "lastUpdateBy")
    private String lastUpdateBy;

    /**
     *
     */
    public Customer() {
    }

    /**
     *
     * @param customerId
     */
    public Customer(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @param customerId
     * @param customerName
     * @param address
     * @param active
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Customer(Integer customerId, String customerName, Address address, boolean active,
            Instant createDate, String createdBy, Instant lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     *
     * @return
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
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
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null
                && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.customerName;
    }

}
