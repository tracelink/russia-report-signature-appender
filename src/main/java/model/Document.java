package model;

public class Document {
    private String requestId;
    private String signatureType;
    private String tokenRequestPayload;
    private String reportPreSignedUrl;

    // Default constructor
    public Document() {
    }

    public Document(String requestId, String signatureType, String tokenRequestPayload, String reportPreSignedUrl) {
        this.requestId = requestId;
        this.signatureType = signatureType;
        this.tokenRequestPayload = tokenRequestPayload;
        this.reportPreSignedUrl = reportPreSignedUrl;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getTokenRequestPayload() {
        return tokenRequestPayload;
    }

    public void setTokenRequestPayload(String tokenRequestPayload) {
        this.tokenRequestPayload = tokenRequestPayload;
    }

    public String getReportPreSignedUrl() {
        return reportPreSignedUrl;
    }

    public void setReportPreSignedUrl(String reportPreSignedUrl) {
        this.reportPreSignedUrl = reportPreSignedUrl;
    }
}
