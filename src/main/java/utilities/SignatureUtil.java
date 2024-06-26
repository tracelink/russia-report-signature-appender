package utilities;

import customExceptions.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class SignatureUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneOffset.UTC);
    private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);

    public static String signDocument(boolean isAttachedSignature, String thumbprint, String payload, String taskId) throws SignatureException {
        Instant startTime = Instant.now();
        logger.info(String.format("Signing Task ID: %s, Start Time (GMT): %s", taskId, formatter.format(startTime)));
        String encodedString = Base64.getEncoder().encodeToString(payload.getBytes());
        logger.debug("Base64 value of the payload :: "+ encodedString);
        try {
            String pathForExecutable = StaticProperties.properties.getProperty("pathForExecutable");
            String inputFilePath = StaticProperties.properties.getProperty("inputFilePath");
            String outputFilePath = StaticProperties.properties.getProperty("outputFilePath");

            //Writing the processed_request to InputFile for Signing
            FileUtils.emptyFile(outputFilePath);
            FileUtils.emptyFile(inputFilePath);
            FileUtils.writeToFile(inputFilePath, payload);

            //Signing the Request using the Detached Signature command
            if (isAttachedSignature) {
                String[] command = {
                        pathForExecutable,
                        "-sfsign",
                        "-sign",
                        "-in",
                        inputFilePath,
                        "-out",
                        outputFilePath,
                        "-my",
                        thumbprint,
                        "-base64",
                        "-add"
                };

                RunCommand.execute(command, true);
            } else {
                String[] command = {
                        pathForExecutable,
                        "-sfsign",
                        "-sign",
                        "-detached",
                        "-in",
                        inputFilePath,
                        "-out",
                        outputFilePath,
                        "-my",
                        thumbprint,
                        "-base64",
                        "-add"
                };

                RunCommand.execute(command, true);
            }

            //Reading the file content from the output file
            String signature = FileUtils.readFromFile(outputFilePath);
            assert signature != null;
            String processedSignature = StringManipulator.trimWhiteSpaces(signature);
            logger.debug("Generated signature :: " + processedSignature);
            return processedSignature;
        } finally {
            Instant endTime = Instant.now();
            long timeTakenMillis = endTime.toEpochMilli() - startTime.toEpochMilli();
            logger.info(String.format("Signing Task ID: %s, End Time (GMT): %s, Time Taken (milliseconds): %d",
                    taskId, formatter.format(endTime), timeTakenMillis));
        }

    }
}
