package com.domed.domain.transportDomain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "VEHICLE_WORKFLOW_AUDIT", uniqueConstraints = @UniqueConstraint(columnNames = "ACCEPTED_VIN"))
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
public class VehicleWorkflowAudit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "EVENT_ID", length = 36)
    private String eventId;

    @Column(name = "TEMP_ID")
    private BigInteger tempId;

    @Column(name = "VEHICLE_ID", length = 36)
    private String vehicleId;

    @Column(name = "LETTER_GID", length = 36)
    private String letterGid;

    @Column(name = "ACTOR_ID", length = 36)
    private String actorId;

    @Column(name = "ACTION", length = 30)
    private String action;

    @Column(name = "FROM_STATE")
    private Integer fromState;

    @Column(name = "TO_STATE")
    private Integer toState;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVENT_TIME")
    private Date eventTime;

    @Column(name = "DETAIL", length = 1000)
    private String detail;

    // Integration-owned uniqueness guard; populated only for a successful first issue.
    @Column(name = "ACCEPTED_VIN", length = 17)
    private String acceptedVin;

    public String getEventId() { return eventId; }
    public void setEventId(String value) { this.eventId = value; }
    public BigInteger getTempId() { return tempId; }
    public void setTempId(BigInteger value) { this.tempId = value; }
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String value) { this.vehicleId = value; }
    public String getLetterGid() { return letterGid; }
    public void setLetterGid(String value) { this.letterGid = value; }
    public String getActorId() { return actorId; }
    public void setActorId(String value) { this.actorId = value; }
    public String getAction() { return action; }
    public void setAction(String value) { this.action = value; }
    public Integer getFromState() { return fromState; }
    public void setFromState(Integer value) { this.fromState = value; }
    public Integer getToState() { return toState; }
    public void setToState(Integer value) { this.toState = value; }
    public Date getEventTime() { return eventTime; }
    public void setEventTime(Date value) { this.eventTime = value; }
    public String getDetail() { return detail; }
    public void setDetail(String value) { this.detail = value; }
    public String getAcceptedVin() { return acceptedVin; }
    public void setAcceptedVin(String value) { this.acceptedVin = value; }
}

