package com.ibmwebapi.data;

import com.google.gson.annotations.SerializedName;

public class PostTemperatureResponse {

    @SerializedName("DeclarationTemperatureID")
    private String declarationTemperatureID;

    public String getDeclarationTemperatureID() {
        return declarationTemperatureID;
    }

    public void setDeclarationTemperatureID(String declarationTemperatureID) {
        this.declarationTemperatureID = declarationTemperatureID;
    }

    public String getDeclarationID() {
        return declarationID;
    }

    public void setDeclarationID(String declarationID) {
        this.declarationID = declarationID;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getManualTemperature() {
        return manualTemperature;
    }

    public void setManualTemperature(String manualTemperature) {
        this.manualTemperature = manualTemperature;
    }

    public String getAmbient() {
        return ambient;
    }

    public void setAmbient(String ambient) {
        this.ambient = ambient;
    }

    public String getLocationAPIKey() {
        return locationAPIKey;
    }

    public void setLocationAPIKey(String locationAPIKey) {
        this.locationAPIKey = locationAPIKey;
    }

    public String getScannedDateTime() {
        return scannedDateTime;
    }

    public void setScannedDateTime(String scannedDateTime) {
        this.scannedDateTime = scannedDateTime;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    @SerializedName("DeclarationID")
    private String declarationID;
    @SerializedName("Temperature")
    private String temperature;
    @SerializedName("ManualTemperature")
    private String manualTemperature;
    @SerializedName("Ambient")
    private String ambient;
    @SerializedName("LocationAPIKey")
    private String locationAPIKey;
    @SerializedName("ScannedDateTime")
    private String scannedDateTime;
    @SerializedName("ActionResult")
    private String actionResult;

}
