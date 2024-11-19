package se.thinkcode;

import io.helidon.webserver.http.Handler;
import io.helidon.webserver.http.HttpRouting;
import se.thinkcode.todo.InMemoryTodoRepository;
import se.thinkcode.todo.TodoRepository;
import se.thinkcode.todo.TodoService;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    private final Map<String, Handler> overriden = new HashMap<>();

    public void routes(HttpRouting.Builder routing) {
        TodoService service = getTodoService();

        routing.post("/v1/addTask", (req, resp) -> getAddTaskControllerV1(service).handle(req, resp));
        routing.get("/v1/getTasks/{owner}", (req, resp) -> getGetTaskControllerV1(service).handle(req, resp));

        routing.post("/v2/addTask", (req, resp) -> getAddTaskControllerV2(service).handle(req, resp));
        routing.get("/v2/getTasks/{owner}", (req, resp) -> getGetTaskControllerV2(service).handle(req, resp));
    }

    /**
     * Add any controller you want to override in a test here,
     * and it will be used instead of the default one.
     */
    public void overrideController(Handler controller, Class<? extends Handler> controllerClass) {
        String canonicalName = controllerClass.getCanonicalName();

        overriden.put(canonicalName, controller);
    }

    private TodoService getTodoService() {
        TodoRepository repository = new InMemoryTodoRepository();

        return new TodoService(repository);
    }

    private Handler getAddTaskControllerV1(TodoService service) {
        String key = se.thinkcode.todo.v1.CreateTaskController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v1.CreateTaskController(service));
    }

    private Handler getGetTaskControllerV1(TodoService service) {
        String key = se.thinkcode.todo.v1.GetTasksController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v1.GetTasksController(service));
    }

    private Handler getGetTaskControllerV2(TodoService service) {
        String key = se.thinkcode.todo.v2.GetTasksController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v2.GetTasksController(service));
    }

    private Handler getAddTaskControllerV2(TodoService service) {
        String key = se.thinkcode.todo.v2.CreateTaskController.class.getCanonicalName();
        return overriden.getOrDefault(key, new se.thinkcode.todo.v2.CreateTaskController(service));
    }
}
