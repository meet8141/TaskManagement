public class TaskQueue {
    private final int MAX = 100;
    private Task[] queue;
    private int front;
    private int rear;

    public TaskQueue() {
        queue = new Task[MAX];
        front = -1;
        rear = -1;
    }

    public boolean isEmpty() {
        return front == -1 || front > rear;
    }

    public boolean isFull() {
        return rear == MAX - 1;
    }

    public void enqueue(Task task) {
        if (isFull()) {
            System.out.println("Queue Overflow. Cannot enqueue task.");
            return;
        }
        if (front == -1) front = 0;
        queue[++rear] = task;
    }

    public Task dequeue() {
        if (isEmpty()) {
            System.out.println("Queue Underflow. No task to dequeue.");
            return null;
        }
        Task task = queue[front++];
        if (front > rear) front = rear = -1;
        return task;
    }


    private void showProgressBar(int percent) {
        StringBuilder bar = new StringBuilder("[");
        int steps = percent / 5;
        for (int i = 0; i < 20; i++) {
            if (i < steps) bar.append("#");
            else bar.append(" ");
        }
        bar.append("] ").append(percent).append("%");
        System.out.print("\r" + bar);
    }
    public void processSingleTaskByName(OperationManager OManager, String taskName) {
        if (isEmpty()) {
            System.out.println("⚠️ Queue is empty. No tasks to process.");
            return;
        }

        int matchCount = 0;

        for (int i = front; i <= rear; i++) {
            if (queue[i] != null && queue[i].getName().equalsIgnoreCase(taskName)) {
                matchCount++;
            }
        }

        if (matchCount == 0) {
            System.out.println("❌ No matching task found in queue.");
            return;
        }

        int completed = 0;

        System.out.println("\n🔄 Processing task: \"" + taskName + "\"...");

        while (!isEmpty()) {
            Task task = dequeue();

            if (task.getName().equalsIgnoreCase(taskName)) {
                OManager.markTaskAsCompleted(task.getTaskId());
                completed++;
                showProgressBar(100); // One task = 100%
                break;
            }
        }

        if (completed > 0) {
            System.out.println("\n✅ Task \"" + taskName + "\" marked as completed.");
        }
    }


}