package services;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Document;
import model.SignEventResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.HttpClientUtil;
import utilities.JsonParser;

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
        logger.info("Fetching documents...");
        String jwtToken = authService.getJwtToken() != null ? authService.getJwtToken().getToken() : null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Custom-Header", "value"); // Need to add token here
        String response = HttpClientUtil.sendGetRequestWithRetries("https://ru.wiremockapi.cloud/api/signEvent", jwtToken, headers, authService,1);
        SignEventResponse signEventResponse = parseSignEventResponse(response);
        return signEventResponse.getTaskList();
    }

    private List<Document> parseDocuments(String jsonResponse) {
        return JsonParser.returnObject(jsonResponse, new TypeReference<List<Document>>() {});
    }

    private SignEventResponse parseSignEventResponse(String jsonResponse){
        return JsonParser.returnObject(jsonResponse, SignEventResponse.class);
    }

}
