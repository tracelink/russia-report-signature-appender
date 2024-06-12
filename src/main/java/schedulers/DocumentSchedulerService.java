package schedulers;

import model.Document;
import services.AuthService;
import services.DocumentService;

import java.util.List;
import java.util.TimerTask;

public class DocumentSchedulerService extends BaseSchedulerService {
    private final DocumentService documentService;
    public DocumentSchedulerService(AuthService authService) {
        super(authService);
        this.documentService = new DocumentService(authService);
    }

    @Override
    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try{
                    List<Document> documents = documentService.fetchDocuments();
                    System.out.println(documents);
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
