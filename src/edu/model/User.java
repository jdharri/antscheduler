package edu.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    ,
        @NamedQuery(name = "User.findByUserId", query
            = "SELECT u FROM User u WHERE u.userId = :userId")
    ,
        @NamedQuery(name = "User.findByUserName", query
            = "SELECT u FROM User u WHERE u.userName = :userName")
    ,
        @NamedQuery(name = "User.findByPassword", query
            = "SELECT u FROM User u WHERE u.password = :password")
    ,
        @NamedQuery(name = "User.findByActive", query
            = "SELECT u FROM User u WHERE u.active = :active")
    ,
        @NamedQuery(name = "User.findByCreateBy", query
            = "SELECT u FROM User u WHERE u.createBy = :createBy")
    ,
        @NamedQuery(name = "User.findByCreateDate", query
            = "SELECT u FROM User u WHERE u.createDate = :createDate")
    ,
        @NamedQuery(name = "User.findByLastUpdate", query
            = "SELECT u FROM User u WHERE u.lastUpdate = :lastUpdate")
    ,
        @NamedQuery(name = "User.findByLastUpdatedBy", query
            = "SELECT u FROM User u WHERE u.lastUpdatedBy = :lastUpdatedBy")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userId")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "userName")
    private String userName;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "active")
    private short active;
    @Basic(optional = false)
    @Column(name = "createBy")
    private String createBy;
    @Basic(optional = false)
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @Column(name = "lastUpdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Basic(optional = false)
    @Column(name = "lastUpdatedBy")
    private String lastUpdatedBy;

    /**
     *
     */
    public User() {
    }

    /**
     *
     * @param userId
     */
    public User(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @param userId
     * @param userName
     * @param password
     * @param active
     * @param createBy
     * @param createDate
     * @param lastUpdate
     * @param lastUpdatedBy
     */
    public User(Integer userId, String userName, String password, short active, String createBy,
            Date createDate, Date lastUpdate, String lastUpdatedBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createBy = createBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public short getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(short active) {
        this.active = active;
    }

    /**
     *
     * @return
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     *
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     *
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null)
                || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "antscheduler.model.User[ userId=" + userId + " ]";
    }

}
