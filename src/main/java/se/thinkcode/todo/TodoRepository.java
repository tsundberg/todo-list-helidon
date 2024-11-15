package se.thinkcode.todo;

import java.util.List;

public interface TodoRepository {
    void add(Owner owner, Task task);

    List<Task> getTasks(Owner owner);

}
