
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
@Table(name = "address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
    ,
        @NamedQuery(name = "Address.findByAddressId", query
            = "SELECT a FROM Address a WHERE a.addressId = :addressId")
    ,
        @NamedQuery(name = "Address.findByAddress", query
            = "SELECT a FROM Address a WHERE a.address = :address")
    ,
        @NamedQuery(name = "Address.findByAddress2", query
            = "SELECT a FROM Address a WHERE a.address2 = :address2")
    ,
//        @NamedQuery(name = "Address.findByCityId", query
//            = "SELECT a FROM Address a WHERE a.cityId = :cityId")
//    ,
        @NamedQuery(name = "Address.findByPostalCode", query
            = "SELECT a FROM Address a WHERE a.postalCode = :postalCode")
    ,
        @NamedQuery(name = "Address.findByPhone", query
            = "SELECT a FROM Address a WHERE a.phone = :phone")
    ,
        @NamedQuery(name = "Address.findByCreateDate", query
            = "SELECT a FROM Address a WHERE a.createDate = :createDate")
    ,
        @NamedQuery(name = "Address.findByCreatedBy", query
            = "SELECT a FROM Address a WHERE a.createdBy = :createdBy")
    ,
        @NamedQuery(name = "Address.findByLastUpdate", query
            = "SELECT a FROM Address a WHERE a.lastUpdate = :lastUpdate")
    ,
        @NamedQuery(name = "Address.findByLastUpdateBy", query
            = "SELECT a FROM Address a WHERE a.lastUpdateBy = :lastUpdateBy")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private Integer addressId;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "address2")
    private String address2;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cityId", referencedColumnName = "cityId", unique = true, nullable = true, insertable = true,
            updatable = true)
    private City city;
    @Basic(optional = false)
    @Column(name = "postalCode")
    private String postalCode;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
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
    public Address() {
    }

    /**
     *
     * @param addressId
     */
    public Address(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @param addressId
     * @param address
     * @param address2
     * @param city
     * @param postalCode
     * @param phone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Address(Integer addressId, String address, String address2, City city, String postalCode,
            String phone, Instant createDate, String createdBy, Instant lastUpdate, String lastUpdateBy) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     *
     * @return
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     *
     * @param addressId
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getAddress2() {
        return address2;
    }

    /**
     *
     * @param address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     *
     * @return
     */
    public City getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
        hash += (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.addressId == null && other.addressId != null)
                || (this.addressId != null && !this.addressId.equals(other.addressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "antscheduler.model.Address[ addressId=" + addressId + " ]";
    }

}
