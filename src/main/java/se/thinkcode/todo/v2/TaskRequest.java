package se.thinkcode.todo.v2;

import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;

public record TaskRequest(String owner, String todo) {
    public Task toTask() {
        return new Task(todo());
    }

    public Owner toOwner() {
        return new Owner(owner());
    }
}
