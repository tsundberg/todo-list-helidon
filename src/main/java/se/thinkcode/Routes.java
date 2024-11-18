package se.thinkcode;

import io.helidon.webserver.http.HttpRouting;
import se.thinkcode.todo.InMemoryTodoRepository;
import se.thinkcode.todo.TodoRepository;
import se.thinkcode.todo.TodoService;
import se.thinkcode.todo.v1.CreateTaskController;
import se.thinkcode.todo.v2.GetTasksController;

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

    private CreateTaskController getAddTaskControllerV1(TodoService service) {
        return new CreateTaskController(service);
    }

    private se.thinkcode.todo.v1.GetTasksController getGetTaskControllerV1(TodoService service) {
        return new se.thinkcode.todo.v1.GetTasksController(service);
    }

    private GetTasksController getGetTaskControllerV2(TodoService service) {
        return new GetTasksController(service);
    }

    private se.thinkcode.todo.v2.CreateTaskController getAddTaskControllerV2(TodoService service) {
        return new se.thinkcode.todo.v2.CreateTaskController(service);
    }
}
