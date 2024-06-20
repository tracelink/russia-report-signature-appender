package model;

import java.util.Comparator;

public class DocumentComparator implements Comparator<Document> {

    @Override
    public int compare(Document doc1, Document doc2) {
        if ("oms_token_request".equals(doc1.getTaskId()) || "mdlp_token_request".equals(doc1.getTaskId())) {
            return -1;
        } else if ("oms_token_request".equals(doc2.getTaskId()) || "mdlp_token_request".equals(doc2.getTaskId())) {
            return 1;
        } else {
            return 0;
        }
    }
}
