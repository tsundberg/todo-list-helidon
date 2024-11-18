package se.thinkcode;

import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import se.thinkcode.todo.InMemoryTodoRepository;
import se.thinkcode.todo.TodoRepository;
import se.thinkcode.todo.TodoService;
import se.thinkcode.todo.v1.AddTaskController;
import se.thinkcode.todo.v1.GetTaskController;

public class TodoList {
    public static void main(String[] args) {
        WebServer.builder()
                .port(8080)
                .routing(TodoList::routing)
                .build()
                .start();
    }

    public static void routing(HttpRouting.Builder routing) {
        TodoRepository repository = new InMemoryTodoRepository();
        TodoService service = new TodoService(repository);

        routing.post("/addTask", (req, resp) -> new AddTaskController(service).handle(req, resp));
        routing.get("/getTasks/{owner}", (req, resp) -> new GetTaskController(service).handle(req, resp));
    }
}
