package schedulers;

import model.Document;
import model.DocumentComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AuthService;
import services.DocumentService;
import services.GenerateSignatureService;
import utilities.StaticProperties;

import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

public class DocumentSchedulerService extends BaseSchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(BaseSchedulerService.class);
    private final DocumentService documentService;
    private final GenerateSignatureService generateSignatureService;

    public DocumentSchedulerService(AuthService authService) {
        super(authService);
        this.documentService = new DocumentService(authService);
        this.generateSignatureService = new GenerateSignatureService(authService);
    }

    @Override
    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    List<Document> documents = documentService.fetchDocuments();
                    Collections.sort(documents, new DocumentComparator());
                    logger.info("Documents Received :: " + documents);
                    generateSignatureService.signAndSubmitDocuments(documents);
                    logger.info("Batch Complete!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        long initialDelay = 300;
        long period = Long.parseLong(StaticProperties.properties.getProperty("generateSignatureFrequency")) * 60000;
        scheduleAt(task, initialDelay, period);
    }

    @Override
    public void stop() {
    }
}
