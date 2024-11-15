package se.thinkcode.todo;

import java.util.List;

public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public void createTask(Task task, Owner owner) {
        repository.add(owner, task);
    }

    public List<Task> getTasks(Owner owner) {
        return repository.getTasks(owner);
    }
}
