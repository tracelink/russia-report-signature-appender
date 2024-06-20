package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S3FileReader {
    private static final Logger logger = LoggerFactory.getLogger(S3FileReader.class);

    public static String getFileContentFromS3(String s3PresignedUrl) throws Exception {
        String response = HttpClientUtil.sendGetRequest(s3PresignedUrl, null, null);
        return response;
    }

}
