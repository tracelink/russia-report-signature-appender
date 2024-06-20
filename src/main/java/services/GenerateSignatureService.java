package services;

import model.Document;
import model.SignedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.S3FileReader;
import utilities.SignatureUtil;
import java.util.List;


public class GenerateSignatureService {
    private static final Logger logger = LoggerFactory.getLogger(GenerateSignatureService.class);
    private final AuthService authService;
    private static String omsTask = "oms_token_request";
    private static String mdlpTask = "mdlp_token_request";

    public GenerateSignatureService(AuthService authService){
        this.authService = authService;
    }

    public void signAndSubmitDocuments(List<Document> documents) {
        for (Document document : documents) {
            try{
                String signedPayload = signDocument(document);
                submitSignedDocument(createSignedResponse(document,signedPayload));
            }catch(Exception e){
                submitSignedDocument(createSignedResponse(document,null));
            }
        }
    }

    private SignedResponse createSignedResponse(Document document,String signedPayload){
        return new SignedResponse(signedPayload,
                document.getSigningPayload(),
                document.getTaskId(),"SUCCESS",null,null);
    }

    private String signDocument(Document document) {
        //Decide on if an attached/detached signature is needed
        boolean isAttachedSignature = "ATTACHED".equals(document.getSignatureType());
        boolean isOMSorMDLP = (omsTask.equals(document.getTaskId()) || mdlpTask.equals(document.getTaskId()));
        String payload = null;
        try{
            payload = isOMSorMDLP ? document.getSigningPayload() : S3FileReader.getFileContentFromS3(document.getSigningTaskContentPreSignedUrl());
        }catch(Exception e){
            e.printStackTrace();
        }

        return SignatureUtil.signDocument(isAttachedSignature,
                authService.getJwtToken().getCertThumbprint(),payload);
    }

    private void submitSignedDocument(SignedResponse signedResponse) {
        // Convert signed document to JSON (assuming Jackson library is being used)
        System.out.println(signedResponse);
    }
}
