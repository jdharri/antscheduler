package edu.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JoelHarris
 */
@Entity
@Table(name = "incrementtypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Incrementtypes.findAll", query = "SELECT i FROM Incrementtypes i")
    ,
        @NamedQuery(name = "Incrementtypes.findByIncrementTypeId", query
            = "SELECT i FROM Incrementtypes i WHERE i.incrementTypeId = :incrementTypeId")
    ,
        @NamedQuery(name = "Incrementtypes.findByIncrementTypeDescription", query
            = "SELECT i FROM Incrementtypes i WHERE i.incrementTypeDescription = :incrementTypeDescription")})
public class Incrementtypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "incrementTypeId")
    private Integer incrementTypeId;
    @Basic(optional = false)
    @Column(name = "incrementTypeDescription")
    private String incrementTypeDescription;

    /**
     *
     */
    public Incrementtypes() {
    }

    /**
     *
     * @param incrementTypeId
     */
    public Incrementtypes(Integer incrementTypeId) {
        this.incrementTypeId = incrementTypeId;
    }

    /**
     *
     * @param incrementTypeId
     * @param incrementTypeDescription
     */
    public Incrementtypes(Integer incrementTypeId, String incrementTypeDescription) {
        this.incrementTypeId = incrementTypeId;
        this.incrementTypeDescription = incrementTypeDescription;
    }

    /**
     *
     * @return
     */
    public Integer getIncrementTypeId() {
        return incrementTypeId;
    }

    /**
     *
     * @param incrementTypeId
     */
    public void setIncrementTypeId(Integer incrementTypeId) {
        this.incrementTypeId = incrementTypeId;
    }

    /**
     *
     * @return
     */
    public String getIncrementTypeDescription() {
        return incrementTypeDescription;
    }

    /**
     *
     * @param incrementTypeDescription
     */
    public void setIncrementTypeDescription(String incrementTypeDescription) {
        this.incrementTypeDescription = incrementTypeDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incrementTypeId != null ? incrementTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Incrementtypes)) {
            return false;
        }
        Incrementtypes other = (Incrementtypes) object;
        if ((this.incrementTypeId == null && other.incrementTypeId != null)
                || (this.incrementTypeId != null && !this.incrementTypeId.equals(other.incrementTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "antscheduler.model.Incrementtypes[ incrementTypeId=" + incrementTypeId + " ]";
    }

}
