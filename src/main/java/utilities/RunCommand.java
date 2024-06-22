package utilities;

import customExceptions.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RunCommand {
    private static final Logger logger = LoggerFactory.getLogger(RunCommand.class);
    private static final String SUCCESS_CODE = "[ErrorCode: 0x00000000]";

    public static String execute(String[] cmd, boolean isVerification) throws SignatureException {
        logger.debug(String.format("Command for signature :: %s", Arrays.toString(cmd)));
        List<String> result = new ArrayList<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));) {
                result = reader.lines().collect(Collectors.toList());
            } catch (IOException e) {
                process.destroy();
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);

            if (isVerification) {
                final Optional<String> successCode = result.stream().filter(l -> l.contains(SUCCESS_CODE)).findFirst();
                if (successCode.isPresent()) {
                    return "Document signed successfully.";
                } else {
                    throw new SignatureException("Document signature failed: " + result.toString(), "INTERNAL_FAILURE");
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.debug("result.toString::" + result.toString());
        return result.toString();
    }
}
