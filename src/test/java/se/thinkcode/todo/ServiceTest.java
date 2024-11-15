package se.thinkcode.todo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceTest {
    @Test
    void should_create_task() {
        TodoRepository repository = new InMemoryTodoRepository();
        TodoService service = new TodoService(repository);
        Owner owner = new Owner("David");
        Task expected = new Task("Smurfa");
        Task task = new Task("Smurfa");
        service.createTask(task, owner);

        List<Task> actual = service.getTasks(owner);

        assertThat(actual).containsExactly(expected);
    }
}
