package se.thinkcode.todo.v2;

import io.helidon.http.media.ReadableEntity;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import se.thinkcode.todo.TodoService;

public class AddTaskController {
    private final TodoService service;

    public AddTaskController(TodoService service) {
        this.service = service;
    }

    public void handle(ServerRequest serverRequest, ServerResponse serverResponse) {
        ReadableEntity content = serverRequest.content();
        TaskRequest taskRequest = content.as(TaskRequest.class);

        service.createTask(taskRequest.toTask(), taskRequest.toOwner());

        serverResponse.status(200);
        serverResponse.send();
    }

}
