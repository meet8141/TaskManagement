import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class OperationManager {
   public static List<String> requests = new ArrayList<>();
    public List<Task> getTasksByUserId(int userId) {
        List<Task> taskList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT t.*, u.user_id, u.username, u.email FROM Tasks t INNER JOIN Users u ON t.assigned_user_id = u.user_id WHERE u.user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("email"));
                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getDate("due_date"),
                        user
                );
                taskList.add(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public void addTask(String name, String description, String priority, String status, String dueDate, int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            try {
                LocalDate inputDate = LocalDate.parse(dueDate);
                LocalDate currentDate = LocalDate.now();

                if (inputDate.isBefore(currentDate)) {
                    System.out.println("❌ Cannot add task: The due date is in the past!");
                    return;
                }
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format! Please use YYYY-MM-DD format.");
                return;
            }

            String query = "INSERT INTO Tasks (name, description, priority, status, due_date, assigned_user_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, priority);
            stmt.setString(4, status);
            stmt.setString(5, dueDate);
            stmt.setInt(6, userId);
            stmt.executeUpdate();
            System.out.println("Task Added Successfully.");
        } catch (Exception e) {
            System.out.println("Try again");
        }
    }
    public void updateTask(String name, String description, String priority, int userId){
        try (Connection conn =DBConnection.getConnection()) {
            String query = "UPDATE Tasks SET description = ?, priority = ? WHERE name = ? and assigned_user_id = ?";
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(1,description);
            stmt.setString(2,priority);
            stmt.setString(3,name);
            stmt.setInt(4,userId);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Try again");
        }
    }
    public boolean removeTask(int taskId, int userId) {
        try (Connection conn = DBConnection.getConnection()) {
            String check = "SELECT * FROM Tasks WHERE task_id = ? AND assigned_user_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, taskId);
            checkStmt.setInt(2, userId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String delete = "DELETE FROM Tasks WHERE task_id = ?";
                PreparedStatement delStmt = conn.prepareStatement(delete);
                conn.setAutoCommit(false);
                delStmt.setInt(1, taskId);
                System.out.println("You want really to delete\nEnter (yes/No):");
                Scanner sc=new Scanner(System.in);
                String want=sc.nextLine();
                if(want.toLowerCase()=="yes"){
                delStmt.executeUpdate();
                conn.setAutoCommit(true);
                return true;
                }
                else{
                    conn.rollback();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void markTaskAsCompleted(int taskId) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE Tasks SET status = 'Completed' WHERE task_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Task> getPendingTasksByUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT t.task_id, t.name, t.description, t.priority, t.status, t.due_date, "
                + "u.user_id, u.username, u.email "
                + "FROM tasks t JOIN users u ON t.assigned_user_id = u.user_id "
                + "WHERE t.assigned_user_id = ? AND t.status <> 'Completed'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User assignedUser = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email")
                );

                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getDate("due_date"),
                        assignedUser
                );

                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
    public List<Task> DisplayAllTask(int userId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT t.task_id, t.name, t.description, t.priority, t.status, t.due_date, "
                + "u.user_id, u.username, u.email "
                + "FROM tasks t JOIN users u ON t.assigned_user_id = u.user_id "
                + "WHERE t.assigned_user_id = ? ";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User assignedUser = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email")
                );

                Task task = new Task(
                        rs.getInt("task_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getDate("due_date"),
                        assignedUser
                );

                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
    public List<TaskLog> getTaskLogsByUserId(int userId) {
        List<TaskLog> logs = new ArrayList<>();
        String query = "SELECT * FROM TaskLogs WHERE action_by_user_id = ? ORDER BY action_timestamp DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TaskLog log = new TaskLog(
                        rs.getInt("log_id"),
                        rs.getInt("task_id"),
                        rs.getString("action"),
                        rs.getInt("action_by_user_id"),
                        rs.getTimestamp("action_timestamp"),
                        rs.getString("details")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public void addAssignmentRequest(int userId, String username, int taskId,int UserIdToAssign) {
        String query = "INSERT INTO AssignmentRequests (request_id,user_id, username, task_id) VALUES (?,?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()){
             PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, UserIdToAssign);
            stmt.setString(3, username);
            stmt.setInt(4, taskId);
            stmt.executeUpdate();
            System.out.println("Request sent to admin.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<String> getPendingAssignmentRequests() {

        String query = "SELECT * FROM AssignmentRequests WHERE status = 'Pending'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String req = "userId = " + rs.getInt("request_id") +
                        " and username = " + rs.getString("username") +
                        " want to assign taskId = " + rs.getInt("task_id") + " to userId = " + rs.getInt("user_id");
                requests.add(req);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching pending assignment requests: " + e.getMessage());
        }
        return requests;
    }


    public void clearAssignmentRequests(int taskId) {
        String query = "UPDATE AssignmentRequests SET status = 'Processed' WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection()){
             PreparedStatement stmt = conn.prepareStatement(query) ;
            stmt.setInt(1, taskId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error clearing assignment requests: " + e.getMessage());
        }
    }
    public boolean assignTaskToUser(int taskId, int userId) {
    try (Connection conn = DBConnection.getConnection()) {
      String updateQuery = "UPDATE Tasks SET assigned_user_id = ? WHERE task_id = ?";
        PreparedStatement stmt = conn.prepareStatement(updateQuery);
        stmt.setInt(1, userId);
        stmt.setInt(2, taskId);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Task assigned successfully to user with ID: " + userId);
        } else {
            System.out.println("No task found with ID: " + taskId);
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Error connecting to the database: " + e.getMessage());
    }
        return true;
    }


}