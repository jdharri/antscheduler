
package edu.model;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
    ,
        @NamedQuery(name = "Country.findByCountryId", query
            = "SELECT c FROM Country c WHERE c.countryId = :countryId")
    ,
        @NamedQuery(name = "Country.findByCountry", query
            = "SELECT c FROM Country c WHERE c.country = :country")
    ,
        @NamedQuery(name = "Country.findByCreateDate", query
            = "SELECT c FROM Country c WHERE c.createDate = :createDate")
    ,
        @NamedQuery(name = "Country.findByCreatedBy", query
            = "SELECT c FROM Country c WHERE c.createdBy = :createdBy")
    ,
        @NamedQuery(name = "Country.findByLastUpdate", query
            = "SELECT c FROM Country c WHERE c.lastUpdate = :lastUpdate")
    ,
        @NamedQuery(name = "Country.findByLastUpdateBy", query
            = "SELECT c FROM Country c WHERE c.lastUpdateBy = :lastUpdateBy")})
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countryId")
    private Integer countryId;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
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
    public Country() {
    }

    /**
     *
     * @param countryId
     */
    public Country(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @param countryId
     * @param country
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Country(Integer countryId, String country, Instant createDate, String createdBy,
            Instant lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
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
    public Integer getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
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
        hash += (countryId != null ? countryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
      
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryId == null && other.countryId != null)
                || (this.countryId != null && !this.countryId.equals(other.countryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "antscheduler.model.Country[ countryId=" + countryId + " ]";
    }

}
