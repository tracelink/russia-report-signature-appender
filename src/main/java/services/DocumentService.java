package services;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Document;
import model.SignEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.HttpClientUtil;
import utilities.JsonParser;
import utilities.StaticMessages;
import utilities.StaticProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);
    private final AuthService authService;

    public DocumentService(AuthService authService) {
        this.authService = authService;
    }

    public List<Document> fetchDocuments() throws Exception {
        try{
            logger.info("Fetching documents...");
            String jwtToken = authService.getJwtToken() != null ? authService.getJwtToken().getToken() : null;
            Map<String, String> headers = new HashMap<>();
            headers.put("authorization", jwtToken);
            String response = HttpClientUtil.sendGetRequestWithRetries(StaticProperties.properties.getProperty("baseUri")+StaticProperties.properties.getProperty("taskPath"), jwtToken, headers, authService,1);
            SignEventResponse signEventResponse = parseSignEventResponse(response);
            return signEventResponse.getTaskList();
        }catch (Exception e){
            logger.error(StaticMessages.GET_DOCUMENT_ERROR_GENERIC, e);
        }
        return null;
    }

    private List<Document> parseDocuments(String jsonResponse) {
        return JsonParser.returnObject(jsonResponse, new TypeReference<List<Document>>() {});
    }

    private SignEventResponse parseSignEventResponse(String jsonResponse){
        return JsonParser.returnObject(jsonResponse, SignEventResponse.class);
    }

}
