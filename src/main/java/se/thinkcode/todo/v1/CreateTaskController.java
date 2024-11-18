package se.thinkcode.todo.v1;

import io.helidon.http.media.ReadableEntity;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

public class CreateTaskController {
    private final TodoService service;

    public CreateTaskController(TodoService service) {
        this.service = service;
    }

    public void handle(ServerRequest serverRequest, ServerResponse serverResponse) {
        ReadableEntity content = serverRequest.content();
        CreateTaskRequest createTaskRequest = content.as(CreateTaskRequest.class);

        Owner owner = createTaskRequest.toOwner();
        Task task = createTaskRequest.toTask();
        service.createTask(owner, task);

        serverResponse.status(200);
        serverResponse.send();
    }

}
