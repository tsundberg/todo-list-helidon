package se.thinkcode.todo.v1;

import se.thinkcode.todo.Task;

import java.util.ArrayList;
import java.util.List;

public record GetTasksResponse(String task) {

    public static List<GetTasksResponse> fromModel(List<Task> taskList) {
        List<GetTasksResponse> res = new ArrayList<>();
        for (Task task : taskList) {
            String t = task.task();
            GetTasksResponse getTasksResponse = new GetTasksResponse(t);
            res.add(getTasksResponse);
        }
        return res;
    }
}
