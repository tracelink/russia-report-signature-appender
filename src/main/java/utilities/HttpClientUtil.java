package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String sendPostRequest(String requestUrl, String payload, String jwtToken, Map<String, String> headers, AuthService authService, int retryCounts) throws Exception {
        logger.info("Sending POST request to URL: " + requestUrl);
        logger.debug("Request payload: " + payload);
        HttpURLConnection conn = getHttpURLConnection(requestUrl, jwtToken, headers);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = payload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        logger.info("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String responseStr = response.toString();
                logger.debug(String.format("POST Response Body :: %s", responseStr));
                return responseStr;
            }
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED && retryCounts > 0) {
            authService.refreshToken();
            jwtToken = authService.getJwtToken().getToken();
            return sendPostRequest(requestUrl, payload, jwtToken, headers, authService, retryCounts - 1);
        } else {
            throw new Exception("POST request failed with response code: " + responseCode);
        }
    }

    private static HttpURLConnection getHttpURLConnection(String requestUrl, String jwtToken, Map<String, String> headers) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        if (jwtToken != null && !jwtToken.isEmpty()) {
            conn.setRequestProperty("authorization", jwtToken);
            logger.debug("Authorization header set: " + jwtToken);
        }
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
                logger.debug("Header: " + entry.getKey() + " = " + entry.getValue());
            }
        }
        conn.setDoOutput(true);
        return conn;
    }

    public static String sendGetRequest(String requestUrl, String jwtToken, Map<String, String> headers) throws Exception {
        return sendGetRequestWithRetries(requestUrl, jwtToken, headers, null, 0);
    }

    public static String sendGetRequestWithRetries(String requestUrl, String jwtToken, Map<String, String> headers, AuthService authService, int retryCount) throws Exception {
        logger.info(String.format("Sending GET for :: %s", requestUrl));
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (jwtToken != null && !jwtToken.isEmpty()) {
            conn.setRequestProperty("authorization", jwtToken);
        }
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        int responseCode = conn.getResponseCode();
        logger.info("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                String responseStr = response.toString();
                logger.debug("GET Response Body :: " + response.toString());
                return responseStr;
            }
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED && retryCount > 0) {
            authService.refreshToken();
            jwtToken = authService.getJwtToken().getToken();
            return sendGetRequestWithRetries(requestUrl, jwtToken, headers, authService, retryCount - 1);
        } else {
            throw new Exception("GET request failed with response code: " + responseCode);
        }
    }
}
