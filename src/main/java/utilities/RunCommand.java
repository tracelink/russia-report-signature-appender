package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RunCommand {
    private static final Logger logger = LoggerFactory.getLogger(RunCommand.class);
    private static final String SUCCESS_CODE = "[ErrorCode: 0x00000000]";
    private static final String VERIFICATION_SUCCESS_LINE = "Signature was verified OK";

    public static String execute(String[] cmd, boolean isVerification){

        List<String> result = new ArrayList<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));){
                result = reader.lines().collect(Collectors.toList());
            } catch (IOException e) {
                process.destroy();
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Process exited with code " + exitCode);

            if(isVerification) {
                final Optional<String> successLine = result.stream().filter(l -> l.contains(VERIFICATION_SUCCESS_LINE)).findFirst();
                final Optional<String> successCode = result.stream().filter(l -> l.contains(SUCCESS_CODE)).findFirst();
                if(successLine.isPresent() && successCode.isPresent()) {
                    return VERIFICATION_SUCCESS_LINE;
                } else {
                    return "Signature Verification Failed";
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
