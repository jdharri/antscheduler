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
@Table(name = "city")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
    ,
        @NamedQuery(name = "City.findByCityId", query
            = "SELECT c FROM City c WHERE c.cityId = :cityId")
    ,
        @NamedQuery(name = "City.findByCity", query = "SELECT c FROM City c WHERE c.city = :city")
    ,

        @NamedQuery(name = "City.findByCreateDate", query
            = "SELECT c FROM City c WHERE c.createDate = :createDate")
    ,
        @NamedQuery(name = "City.findByCreatedBy", query
            = "SELECT c FROM City c WHERE c.createdBy = :createdBy")
    ,
        @NamedQuery(name = "City.findByLastUpdate", query
            = "SELECT c FROM City c WHERE c.lastUpdate = :lastUpdate")
    ,
        @NamedQuery(name = "City.findByLastUpdateBy", query
            = "SELECT c FROM City c WHERE c.lastUpdateBy = :lastUpdateBy")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cityId")
    private Integer cityId;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "countryId", referencedColumnName = "countryId", unique = true, nullable = true, insertable = true,
            updatable = false)
    private Country country;
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
    public City() {
    }

    /**
     *
     * @param cityId
     */
    public City(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @param cityId
     * @param city
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public City(Integer cityId, String city, Country country, Instant createDate, String createdBy,
            Instant lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.city = city;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     *
     * @return
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public Country getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
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
        hash += (cityId != null ? cityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.cityId == null && other.cityId != null)
                || (this.cityId != null && !this.cityId.equals(other.cityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "antscheduler.model.City[ cityId=" + cityId + " ]";
    }

}
