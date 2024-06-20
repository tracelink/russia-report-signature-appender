package model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignedResponse {
    private String signedData;
    private String signingPayload;
    private String taskId;
    private String status;
    private String error;
    private String errorDescription;

    public SignedResponse(String signedData, String signingPayload, String taskId, String status, String error, String errorDescription) {
        this.signedData = signedData;
        this.signingPayload = signingPayload;
        this.taskId = taskId;
        this.status = status;
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public SignedResponse(String signedData, String signingPayload, String taskId, String status) {
        this.signedData = signedData;
        this.signingPayload = signingPayload;
        this.taskId = taskId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "SignedResponse{" +
                "signedData='" + signedData + '\'' +
                ", signingPayload='" + signingPayload + '\'' +
                ", taskId='" + taskId + '\'' +
                ", status='" + status + '\'' +
                ", error='" + error + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }

    public String getSignedData() {
        return signedData;
    }

    public void setSignedData(String signedData) {
        this.signedData = signedData;
    }

    public String getSigningPayload() {
        return signingPayload;
    }

    public void setSigningPayload(String signingPayload) {
        this.signingPayload = signingPayload;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
