package com.domed.domain.transportDomain.transportTemp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "HY_CLB_TEMP")
@Access(AccessType.FIELD)
@DynamicInsert
@DynamicUpdate
public class HyClbTemp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "vehicle_hy_clb_temp_seq", sequenceName = "HY_CLB_TEMP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_hy_clb_temp_seq")
    @Column(name = "ID")
    private BigInteger id;

    @Column(name = "U_CLBM")
    private String vehicleId;

    @Column(name = "LETTER_GID")
    private String letterGid;

    @Column(name = "U_YHBM")
    private String enterpriseId;

    @Column(name = "C_CH")
    private String plateNumber;

    @Column(name = "C_CJH")
    private String vin;

    @Column(name = "C_CPYS")
    private String plateColor;

    @Column(name = "C_CLLX")
    private String vehicleType;

    @Column(name = "C_FDJH")
    private String engineNumber;

    @Column(name = "N_CP")
    private String manufacturer;

    @Column(name = "N_XH")
    private String model;

    @Column(name = "N_YWLX")
    private String businessType;

    @Column(name = "N_SXMC")
    private String businessTitle;

    @Column(name = "C_XKZH")
    private String licenseNumber;

    @Column(name = "C_YYZH")
    private String permitNumber;

    @Column(name = "XZQH")
    private String region;

    @Column(name = "C_LRR")
    private String ownerId;

    @Column(name = "C_LRBM")
    private String department;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_LRSJ")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "D_XGSJ")
    private Date updatedAt;

    @Column(name = "C_XGR")
    private String updatedBy;

    @Column(name = "T_ZT")
    private Integer status;

    @Column(name = "DBJLX")
    private String queueType;

    @Column(name = "DO_THING_STATUS")
    private String doThingStatus;

    @Column(name = "CLB_ID")
    private BigInteger formalId;

    @Temporal(TemporalType.DATE)
    @Column(name = "D_CCRQ")
    private Date registrationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "D_MSQR")
    private Date inspectionExpiry;

    @Column(name = "N_BZ")
    private String remarks;

    @Column(name = "SLYJ")
    private String acceptOpinion;

    @Column(name = "SLRXM")
    private String acceptOfficer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SLSJ")
    private Date acceptedAt;

    @Column(name = "ISDAILY")
    private String isDaily;

    public BigInteger getId() { return id; }
    public void setId(BigInteger value) { this.id = value; }
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String value) { this.vehicleId = value; }
    public String getLetterGid() { return letterGid; }
    public void setLetterGid(String value) { this.letterGid = value; }
    public String getEnterpriseId() { return enterpriseId; }
    public void setEnterpriseId(String value) { this.enterpriseId = value; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String value) { this.plateNumber = value; }
    public String getVin() { return vin; }
    public void setVin(String value) { this.vin = value; }
    public String getPlateColor() { return plateColor; }
    public void setPlateColor(String value) { this.plateColor = value; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String value) { this.vehicleType = value; }
    public String getEngineNumber() { return engineNumber; }
    public void setEngineNumber(String value) { this.engineNumber = value; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String value) { this.manufacturer = value; }
    public String getModel() { return model; }
    public void setModel(String value) { this.model = value; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String value) { this.businessType = value; }
    public String getBusinessTitle() { return businessTitle; }
    public void setBusinessTitle(String value) { this.businessTitle = value; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getPermitNumber() { return permitNumber; }
    public void setPermitNumber(String value) { this.permitNumber = value; }
    public void setLicenseNumber(String value) { this.licenseNumber = value; }
    public String getRegion() { return region; }
    public void setRegion(String value) { this.region = value; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String value) { this.ownerId = value; }
    public String getDepartment() { return department; }
    public void setDepartment(String value) { this.department = value; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date value) { this.createdAt = value; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date value) { this.updatedAt = value; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String value) { this.updatedBy = value; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer value) { this.status = value; }
    public String getQueueType() { return queueType; }
    public void setQueueType(String value) { this.queueType = value; }
    public String getDoThingStatus() { return doThingStatus; }
    public void setDoThingStatus(String value) { this.doThingStatus = value; }
    public BigInteger getFormalId() { return formalId; }
    public void setFormalId(BigInteger value) { this.formalId = value; }
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date value) { this.registrationDate = value; }
    public Date getInspectionExpiry() { return inspectionExpiry; }
    public void setInspectionExpiry(Date value) { this.inspectionExpiry = value; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String value) { this.remarks = value; }
    public String getAcceptOpinion() { return acceptOpinion; }
    public void setAcceptOpinion(String value) { this.acceptOpinion = value; }
    public String getAcceptOfficer() { return acceptOfficer; }
    public void setAcceptOfficer(String value) { this.acceptOfficer = value; }
    public Date getAcceptedAt() { return acceptedAt; }
    public void setAcceptedAt(Date value) { this.acceptedAt = value; }
    public String getIsDaily() { return isDaily; }
    public void setIsDaily(String value) { this.isDaily = value; }
}

