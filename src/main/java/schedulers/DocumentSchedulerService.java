package schedulers;

import model.Document;
import model.DocumentComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AuthService;
import services.DocumentService;
import services.GenerateSignatureService;
import utilities.StaticProperties;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

public class DocumentSchedulerService extends BaseSchedulerService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneOffset.UTC);
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
                    Instant startTime = Instant.now();
                    logger.debug(String.format("Starting with document processing, Time (GMT): %s", formatter.format(startTime)));
                    List<Document> documents = documentService.fetchDocuments();
                    logger.info("Documents list received :: " + documents);

                    if (documents.isEmpty()) {
                        logger.info("No tasks received for signing!!");
                        return;
                    }
                    Collections.sort(documents, new DocumentComparator());
                    logger.debug("Documents list after sorting :: " + documents);
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
