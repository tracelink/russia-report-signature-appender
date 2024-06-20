package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
    public static String signDocument(boolean isAttachedSignature,String thumbprint,String payload){
        String processed_request = StringManipulator.trimWhiteSpaces(payload);
        String pathForExecutable = StaticProperties.properties.getProperty("pathForExecutable");
        String inputFilePath =  StaticProperties.properties.getProperty("inputFilePath");
        String outputFilePath =  StaticProperties.properties.getProperty("outputFilePath");

        //Writing the processed_request to InputFile for Signing
        FileUtils.emptyFile(outputFilePath);
        FileUtils.emptyFile(inputFilePath);
        FileUtils.writeToFile(inputFilePath,processed_request);

        //Signing the Request using the Detached Signature command
        if(isAttachedSignature){
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

            RunCommand.execute(command, false);
        } else{
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

            RunCommand.execute(command, false);
        }

        //Reading the file content from the output file
        String signature = FileUtils.readFromFile(outputFilePath);
        assert signature != null;
        return StringManipulator.trimWhiteSpaces(signature);
    }
}
