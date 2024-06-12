package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class SignEventResponse {
    private List<Document> taskList;
    private String errorMessage;
    private long errorCode;

    @JsonCreator
    public SignEventResponse(
            @JsonProperty("taskList") List<Document> taskList,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("errorCode") long errorCode) {
        this.taskList = taskList;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public List<Document> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Document> taskList) {
        this.taskList = taskList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }
}
