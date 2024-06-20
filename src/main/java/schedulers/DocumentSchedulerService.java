package schedulers;

import model.Document;
import model.DocumentComparator;
import services.AuthService;
import services.DocumentService;
import services.GenerateSignatureService;

import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

public class DocumentSchedulerService extends BaseSchedulerService {
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
                try{
                    List<Document> documents = documentService.fetchDocuments();
                    Collections.sort(documents, new DocumentComparator());
                    generateSignatureService.signAndSubmitDocuments(documents);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        long initialDelay = 300;
        long period = 6000; // 1 minute
        scheduleAt(task, initialDelay, period);
    }

    @Override
    public void stop() {
    }
}
