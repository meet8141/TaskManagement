import java.sql.*;

public class TaskLog {
    private int logId;
    private int taskId;
    private String action;
    private int actionByUserId;
    private Timestamp actionTimestamp;
    private String details;

    // Constructor, getters, and setters
    public TaskLog(int logId, int taskId, String action, int actionByUserId, Timestamp actionTimestamp, String details) {
        this.logId = logId;
        this.taskId = taskId;
        this.action = action;
        this.actionByUserId = actionByUserId;
        this.actionTimestamp = actionTimestamp;
        this.details = details;
    }

    public int getLogId() { return logId; }
    public int getTaskId() { return taskId; }
    public String getAction() { return action; }
    public Timestamp getActionTimestamp() { return actionTimestamp; }
    public String getDetails() { return details; }

}