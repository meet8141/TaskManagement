import java.util.Date;

public class Task {
    private int taskId;
    private String name;
    private String description;
    private String priority;
    private String status;
    private Date dueDate;
    private User assignedUser;


    public Task(int taskId, String name, String description, String priority, String status, Date dueDate, User assignedUser) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedUser = assignedUser;
    }

    public int getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public Date getDueDate() { return dueDate; }
    public User getAssignedUser() { return assignedUser; }
}