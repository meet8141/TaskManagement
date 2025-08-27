import java.sql.Connection;
import java.util.*;

public class TMSApp {
    private static List<String> Request = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        OperationManager OManager = new OperationManager();
        User currentUser = null;
        Connection con = DBConnection.getConnection();
        TaskQueue taskQueue = new TaskQueue();
        System.out.println("=== Task Management System ===");
        boolean exitSignIn = false;
        try{
            while (!exitSignIn){

                System.out.println("1. Sign Up");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                String choice = sc.nextLine();


                switch (choice) {
                    case "1": {
                        boolean created = auth.signUp(con);
                        if (created) {
                            System.out.println("Sign up successful! Please log in.");
                            currentUser = auth.login(con);
                        } else {
                            System.out.println("Sign up failed.");
                            return;
                        }
                        if (currentUser!=null){
                            exitSignIn=true;
                        }

                        break;
                    }

                    case "2": {
                        currentUser = auth.login(con);
                        if (currentUser!=null){
                            exitSignIn=true;
                        }
                        break;
                    }
                    case "3":{
                        exitSignIn=true;
                        break;
                    }
                    default:{
                        System.out.println("Invalid choice.");
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        if (currentUser != null) {
            System.out.println("\nHello 👋, " + currentUser.getUsername());
            showPendingTasks(currentUser, OManager);


            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== Menu ===");
                System.out.println("1. 📌 View Your Pinboard");
                System.out.println("2. ➕ Add Task");
                System.out.println("3. 👍  Update Task");
                System.out.println("4. ➖ Remove Task");
                System.out.println("5. 📜 View History ");
                System.out.println("6. ✅ Mark Task as Completed");
                System.out.println("7. 📝 See pending task");
                System.out.println("8. 🔄 Assign Task to Other User");
                System.out.println("9. ⛔ Exit");
                System.out.print("Choose an option: ");
                String option = sc.nextLine();

                switch (option) {
                    case "1":{
                        List<Task> pinboard = OManager.getTasksByUserId(currentUser.getUserId());
                        System.out.println("\n 📌 Your Pinboard");
                        for (Task task : pinboard) {
                            System.out.println("Task: " + task.getName() + ", Priority: " + task.getPriority() + ", Status: " + task.getStatus());
                            System.out.println("Description: "+ task.getDescription() + ", Your decide Deadline: "+ task.getDueDate());
                        }
                        break;}
                    case "2":
                        try {

                            System.out.println("Enter Task Name: ");
                            String name = sc.nextLine();
                            System.out.println("Enter Description: ");
                            String desc = sc.nextLine();
                            System.out.println("Enter Priority (Low/Medium/High): ");
                            String priority = sc.nextLine();
                            System.out.println("Enter Status (Pending/In Progress): ");
                            String status = sc.nextLine();
                            System.out.println("Enter Due Date (YYYY-MM-DD): ");
                            String dueDate = sc.nextLine();
                            OManager.addTask(name, desc, priority, status, dueDate, currentUser.getUserId());
                            break;
                        } catch (Exception e) {
                            System.out.println("Try again");
                        }
                    case "3":{
                        try {
                            List<Task> AllTasks = OManager.DisplayAllTask(currentUser.getUserId());
                            TaskQueue mainQueue = new TaskQueue();

                            for (Task t : AllTasks) {
                                if (!t.getStatus().equalsIgnoreCase("")) {
                                    System.out.println("Task Details: " +" Task ID: "+t.getTaskId() +", Task Name: "+ t.getName()); // for debug
                                    mainQueue.enqueue(t);
                                }
                            }

                            if (mainQueue.isEmpty()) {
                                System.out.println("⚠️ No tasks is available to Update.");
                                break;
                            }
                            System.out.println("Enter Task name in which you want to change: ");
                            String name=sc.nextLine();
                            System.out.println("Enter Updated Description: ");
                            String desc=sc.nextLine();
                            System.out.println("Enter Priority (Low/Medium/High)");
                            String priority=sc.nextLine();
                            OManager.updateTask(name,desc,priority,currentUser.getUserId());
                            System.out.println("Task Updated Successfully");
                            break;
                        }
                        catch(Exception e) {
                            System.out.println("Try again");
                        }

                    }
                    case "4":{
                        List<Task> AllTasks = OManager.DisplayAllTask(currentUser.getUserId());
                        TaskQueue mainQueue = new TaskQueue();

                        for (Task t : AllTasks) {
                            if (!t.getStatus().equalsIgnoreCase("")) {
                                System.out.println("Task Details: " +" Task ID: "+t.getTaskId() +", Task Name: "+ t.getName()); // for debug
                                mainQueue.enqueue(t);
                            }
                        }

                        if (mainQueue.isEmpty()) {
                            System.out.println("⚠️ No tasks available to Remove.");
                            break;
                        }
                        System.out.println("Enter Task ID to Remove: ");
                        int taskId = sc.nextInt(); sc.nextLine();
                        boolean removed = OManager.removeTask(taskId, currentUser.getUserId());
                        if (removed) {
                            System.out.println("Task Removed Successfully.");
                        } else {
                            System.out.println("Task Not Found or Access Denied.");
                        }
                        break;}
                    case "5":{
                        showHistoryAndRecovery(currentUser, OManager);
                        break;
                    }

                    case "6": {
                        List<Task> tasks = OManager.getTasksByUserId(currentUser.getUserId());
                        TaskQueue manualQueue = new TaskQueue();

                        for (Task t : tasks) {
                            if (!t.getStatus().equalsIgnoreCase("Completed")) {
                                System.out.println("Enqueuing task: " +" task name: "+t.getName()+", task_id: "+t.getTaskId()); // for debug
                                manualQueue.enqueue(t);
                            }
                        }

                        if (manualQueue.isEmpty()) {
                            System.out.println("⚠️ No pending tasks available to complete.");
                            break;
                        }

                        System.out.print("Enter the name of the task to mark as completed: ");
                        String targetTaskName = sc.nextLine();

                        manualQueue.processSingleTaskByName(OManager, targetTaskName);
                        break;
                    }

                    case "7":{
                        showPendingTasks(currentUser, OManager);
                        break;

                    }
                    case "8": {
                        if (currentUser.getUserId() == 111) {
                            showAssignmentRequests(OManager);
                            System.out.println("Do you want to assign the task.(y/n)");
                            String yes_no = sc.nextLine().toLowerCase();
                            switch (yes_no)
                            {
                                case "y":{
                                    assignTaskToUser(sc, OManager);
                                    break;
                                }
                                case "n":{
                                    System.out.println("Returning to main menu.");
                                    break;
                                }
                                default:{
                                    System.out.println("Invalid option. Returning to main menu.");
                                    break;
                                }
                            }

                        } else {
                            System.out.println("Requesting admin to assign task to other user.");
                            List<Task> DispalyTasks = OManager.DisplayAllTask(currentUser.getUserId());
                            TaskQueue QueueOfTask = new TaskQueue();

                            for (Task t :DispalyTasks) {
                                if (!t.getStatus().equalsIgnoreCase("")) {
                                    System.out.println("Task Details: " +" Task ID: "+t.getTaskId() +", Task Name: "+ t.getName()); // for debug
                                    QueueOfTask.enqueue(t);
                                }
                            }

                            if (QueueOfTask.isEmpty()) {
                                System.out.println("⚠️ No tasks available to Remove.");
                                break;
                            }
                            requestTaskAssignment(currentUser, sc,OManager);
                        }
                        break;
                    }
                    case "9":{
                        System.out.println("======Thank-you=======");
                        System.out.println("Exiting...");
                        exit = true;
                        break;}

                    default:
                        System.out.println("Invalid Option.");
                }
            }
        }
    }
    private static void showPendingTasks(User user, OperationManager OManager) {
        List<Task> pendingTasks = OManager.getPendingTasksByUser(user.getUserId());

        if (pendingTasks.isEmpty()) {
            System.out.println("\n✅ No pending tasks! Good job.");
        } else {
            System.out.println("\n📌 Your Pending/In-Progress Tasks:");
            for (Task task : pendingTasks) {
                System.out.println(
                        " - [" + task.getStatus() + "] "
                                + task.getName() + " (Due: " + task.getDueDate() + ")"
                );
            }
        }
    }
    private static void assignTaskToUser(Scanner sc, OperationManager OManager) {
        System.out.print("Enter Task ID to assign: ");
        int taskId = sc.nextInt();
        System.out.print("Enter User ID to assign to: ");
        int userId = sc.nextInt();
        boolean assigned = OManager.assignTaskToUser(taskId, userId);
        if (assigned) {
              OManager.clearAssignmentRequests(taskId);
            System.out.println("Done!");
        } else {
            System.out.println("Failed to assign task.");
        }
    }
    private static void requestTaskAssignment(User user, Scanner sc, OperationManager OManager) {
        System.out.print("Enter Task ID you want assigned: ");
        int taskId = sc.nextInt();
        System.out.println("to whom you want to assign the task UserID:");
        int toUserId = sc.nextInt();
        String request = "userId = " + user.getUserId() + " and username = " + user.getUsername() + " want to assign taskId = " + taskId + " to. "+ toUserId;
       OManager.addAssignmentRequest(user.getUserId(), user.getUsername(), taskId, toUserId);
        System.out.println("Request sent to admin.");
    }
    private static void showAssignmentRequests(OperationManager OManager) {
            System.out.println("Pending assignment requests:");
            System.out.println(OManager.getPendingAssignmentRequests());
    }

    private static void showHistoryAndRecovery(User user, OperationManager OManager) {
        List<TaskLog> logs = OManager.getTaskLogsByUserId(user.getUserId());

        if (logs.isEmpty()) {
            System.out.println("\n🚫 No history available.");
            return;
        }

        System.out.println("\n📜 Your Task History:");
        for (TaskLog log : logs) {
            System.out.println(
                    " - [Log ID: " + log.getLogId() + "] "
                            + log.getAction() + " on Task ID: " + log.getTaskId()
                            + " at " + log.getActionTimestamp()
                            + "\n   Details: " + log.getDetails()
            );
        }


    }


}
