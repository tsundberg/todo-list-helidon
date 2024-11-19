package se.thinkcode.todo.v1;

import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;

public record CreateTaskRequest(String owner, String todo) {
    public Owner toOwner() {
        return new Owner(owner());
    }

    public Task toTask() {
        return new Task(todo());
    }
}
