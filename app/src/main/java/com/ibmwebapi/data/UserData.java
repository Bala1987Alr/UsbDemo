package com.ibmwebapi.data;

import com.google.gson.annotations.SerializedName;

public class UserData {

    public String getDeclarationID() {
        return declarationID;
    }

    public void setDeclarationID(String declarationID) {
        this.declarationID = declarationID;
    }

    public String getIndividualType() {
        return individualType;
    }

    public void setIndividualType(String individualType) {
        this.individualType = individualType;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationOthers() {
        return locationOthers;
    }

    public void setLocationOthers(String locationOthers) {
        this.locationOthers = locationOthers;
    }

    public String getIndividualName() {
        return individualName;
    }

    public void setIndividualName(String individualName) {
        this.individualName = individualName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public String getIDNo() {
        return IDNo;
    }

    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getChinaReturn() {
        return chinaReturn;
    }

    public void setChinaReturn(String chinaReturn) {
        this.chinaReturn = chinaReturn;
    }

    public String getChinaSickSymptom() {
        return chinaSickSymptom;
    }

    public void setChinaSickSymptom(String chinaSickSymptom) {
        this.chinaSickSymptom = chinaSickSymptom;
    }

    public String getChinaContact() {
        return chinaContact;
    }

    public void setChinaContact(String chinaContact) {
        this.chinaContact = chinaContact;
    }

    public String getMiddleEastReturn() {
        return middleEastReturn;
    }

    public void setMiddleEastReturn(String middleEastReturn) {
        this.middleEastReturn = middleEastReturn;
    }

    public String getMiddleEastSickSymptom() {
        return middleEastSickSymptom;
    }

    public void setMiddleEastSickSymptom(String middleEastSickSymptom) {
        this.middleEastSickSymptom = middleEastSickSymptom;
    }

    public String getMiddleEastContact() {
        return middleEastContact;
    }

    public void setMiddleEastContact(String middleEastContact) {
        this.middleEastContact = middleEastContact;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    @SerializedName("DeclarationID")
    private String declarationID = "";
    @SerializedName("IndividualType")
    private String individualType = "";
    @SerializedName("SubmissionDate")
    private String submissionDate = "";
    @SerializedName("Location")
    private String location = "";
    @SerializedName("LocationOthers")
    private String locationOthers = "";
    @SerializedName("IndividualName")
    private String individualName = "";
    @SerializedName("PatientName")
    private String patientName = "";
    @SerializedName("IDType")
    private String IDType = "";
    @SerializedName("IDNo")
    private String IDNo = "";
    @SerializedName("MobileNo")
    private String mobileNo = "";
    @SerializedName("ChinaReturn")
    private String chinaReturn = "";
    @SerializedName("ChinaSickSymptom")
    private String chinaSickSymptom = "";
    @SerializedName("ChinaContact")
    private String chinaContact = "";
    @SerializedName("MiddleEastReturn")
    private String middleEastReturn = "";
    @SerializedName("MiddleEastSickSymptom")
    private String middleEastSickSymptom = "";
    @SerializedName("MiddleEastContact")
    private String middleEastContact = "";
    @SerializedName("InstitutionCode")
    private String institutionCode = "";
    @SerializedName("ActionTaken")
    private String actionTaken = "";
    @SerializedName("Mask")
    private String mask = "";
    @SerializedName("Entry")
    private String entry = "";


}
