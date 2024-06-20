package services;

import customExceptions.SignatureException;
import model.Document;
import model.SignedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.HttpClientUtil;
import utilities.JsonParser;
import utilities.S3FileReader;
import utilities.SignatureUtil;
import utilities.StaticProperties;

import java.util.List;

public class GenerateSignatureService {
    private static final Logger logger = LoggerFactory.getLogger(GenerateSignatureService.class);
    private static String omsTask = "oms_token_request";
    private static String mdlpTask = "mdlp_token_request";
    private final AuthService authService;

    public GenerateSignatureService(AuthService authService) {
        this.authService = authService;
    }

    public void signAndSubmitDocuments(List<Document> documents) {
        for (Document document : documents) {
            try {
                String signedPayload = signDocument(document);
                submitSignedDocument(createSignedResponse(document, signedPayload));
            } catch (Exception e) {
                logger.error(String.format("There was an error Signing the document. Skipping the Submission for this Task. Error :: %s", e.getMessage()));
            }
        }
    }

    private SignedResponse createSignedResponse(Document document, String signedPayload) {
        return new SignedResponse(signedPayload,
                document.getSigningPayload(),
                document.getTaskId(), "SUCCESS");
    }

    private String signDocument(Document document) throws SignatureException {
        //Decide on if an attached/detached signature is needed
        boolean isAttachedSignature = "ATTACHED".equals(document.getSignatureType());
        boolean isOMSorMDLP = (omsTask.equals(document.getTaskId()) || mdlpTask.equals(document.getTaskId()));
        String payload = null;
        try {
            payload = isOMSorMDLP ? document.getSigningPayload() : S3FileReader.getFileContentFromS3(document.getSigningTaskContentPreSignedUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SignatureUtil.signDocument(isAttachedSignature,
                authService.getJwtToken().getCertThumbprint(), payload, document.getTaskId());
    }

    private void submitSignedDocument(SignedResponse signedResponse) {
        // Convert signed document to JSON (assuming Jackson library is being used)
        logger.info(String.format("Submitting TaskId : %s", signedResponse.getTaskId()));
        logger.debug(String.format("Signed Response Payload : %s", signedResponse));
        String str_signedResponse = JsonParser.returnJson(signedResponse);
        try {
            HttpClientUtil.sendPostRequest(StaticProperties.properties.getProperty("baseUri") + StaticProperties.properties.getProperty("taskResultPath"),
                    str_signedResponse, authService.getJwtToken().getToken(), null, authService, 1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
