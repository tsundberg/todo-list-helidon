package se.thinkcode.todo;

import java.util.List;

public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public void createTask(Owner owner, Task task) {
        repository.add(owner, task);
    }

    public List<Task> getTasks(Owner owner) {
        return repository.getTasks(owner);
    }
}
