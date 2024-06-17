package model;

public class Document {
    private String taskId;
    private String signatureType;
    private String signingPayload;
    private String signingTaskContentPreSignedUrl;

    // Default constructor
    public Document() {
    }

    public Document(String taskId, String signatureType, String signingPayload, String signingTaskContentPreSignedUrl) {
        this.taskId = taskId;
        this.signatureType = signatureType;
        this.signingPayload = signingPayload;
        this.signingTaskContentPreSignedUrl = signingTaskContentPreSignedUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getSigningPayload() {
        return signingPayload;
    }

    public void setSigningPayload(String signingPayload) {
        this.signingPayload = signingPayload;
    }

    public String getSigningTaskContentPreSignedUrl() {
        return signingTaskContentPreSignedUrl;
    }

    public void setSigningTaskContentPreSignedUrl(String signingTaskContentPreSignedUrl) {
        this.signingTaskContentPreSignedUrl = signingTaskContentPreSignedUrl;
    }
}
