package model;

import java.util.Comparator;

public class DocumentComparator implements Comparator<Document> {

    @Override
    public int compare(Document doc1, Document doc2) {
        String taskId1 = doc1.getTaskId();
        String taskId2 = doc2.getTaskId();

        if ("oms_token_request".equals(doc1.getTaskId()) || "mdlp_token_request".equals(doc1.getTaskId())) {
            return -1;
        } else if ("oms_token_request".equals(doc2.getTaskId()) || "mdlp_token_request".equals(doc2.getTaskId())) {
            return 1;
        } else {
            return Long.compare(extractTimestamp(taskId1), extractTimestamp(taskId2));
        }
    }

    private long extractTimestamp(String taskId) {
        // Assuming the taskId format is timestamp_GUID
        int underscoreIndex = taskId.indexOf('_');
        if (underscoreIndex != -1) {
            try {
                return Long.parseLong(taskId.substring(0, underscoreIndex));
            } catch (NumberFormatException e) {
                // Handle the case where the timestamp is not a valid number
                e.printStackTrace();
            }
        }
        return 0L; // Default value or handle differently if needed
    }
}
