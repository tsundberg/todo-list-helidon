package se.thinkcode;

import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import se.thinkcode.todo.InMemoryTodoRepository;
import se.thinkcode.todo.TodoRepository;
import se.thinkcode.todo.TodoService;

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

        routing.post("/v1/addTask", (req, resp) -> new se.thinkcode.todo.v1.AddTaskController(service).handle(req, resp));
        routing.get("/v1/getTasks/{owner}", (req, resp) -> new se.thinkcode.todo.v1.GetTaskController(service).handle(req, resp));

        routing.post("/v2/addTask", (req, resp) -> new se.thinkcode.todo.v2.AddTaskController(service).handle(req, resp));
        routing.get("/v2/getTasks/{owner}", (req, resp) -> new se.thinkcode.todo.v2.GetTaskController(service).handle(req, resp));
    }
}
