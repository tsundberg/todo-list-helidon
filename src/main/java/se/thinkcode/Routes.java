package se.thinkcode;

import io.helidon.webserver.http.HttpRouting;
import se.thinkcode.todo.InMemoryTodoRepository;
import se.thinkcode.todo.TodoRepository;
import se.thinkcode.todo.TodoService;

public class Routes {
    public void routing(HttpRouting.Builder routing) {
        TodoService service = getTodoService();

        routing.post("/v1/addTask", (req, resp) -> getAddTaskControllerV1(service).handle(req, resp));
        routing.get("/v1/getTasks/{owner}", (req, resp) -> getGetTaskControllerV1(service).handle(req, resp));

        routing.post("/v2/addTask", (req, resp) -> getAddTaskControllerV2(service).handle(req, resp));
        routing.get("/v2/getTasks/{owner}", (req, resp) -> getGetTaskControllerV2(service).handle(req, resp));
    }

    private TodoService getTodoService() {
        TodoRepository repository = new InMemoryTodoRepository();

        return new TodoService(repository);
    }

    private se.thinkcode.todo.v1.AddTaskController getAddTaskControllerV1(TodoService service) {
        return new se.thinkcode.todo.v1.AddTaskController(service);
    }

    private se.thinkcode.todo.v1.GetTaskController getGetTaskControllerV1(TodoService service) {
        return new se.thinkcode.todo.v1.GetTaskController(service);
    }

    private se.thinkcode.todo.v2.GetTaskController getGetTaskControllerV2(TodoService service) {
        return new se.thinkcode.todo.v2.GetTaskController(service);
    }

    private se.thinkcode.todo.v2.AddTaskController getAddTaskControllerV2(TodoService service) {
        return new se.thinkcode.todo.v2.AddTaskController(service);
    }
}
