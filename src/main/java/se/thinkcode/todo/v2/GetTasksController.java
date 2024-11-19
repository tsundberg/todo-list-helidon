package se.thinkcode.todo.v2;

import io.helidon.common.parameters.Parameters;
import io.helidon.webserver.http.Handler;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import se.thinkcode.todo.Owner;
import se.thinkcode.todo.Task;
import se.thinkcode.todo.TodoService;

import java.util.List;

public class GetTasksController implements Handler {
    private final TodoService service;

    public GetTasksController(TodoService service) {
        this.service = service;
    }

    @Override
    public void handle(ServerRequest serverRequest, ServerResponse serverResponse) {
        Parameters parameters = serverRequest.path().pathParameters();
        String ownerName = parameters.get("owner");
        Owner owner = new Owner(ownerName);
        List<Task> tasks = service.getTasks(owner);

        List<GetTasksResponse> getTasksRespons = GetTasksResponse.fromModel(tasks);

        serverResponse.status(200);
        serverResponse.send(getTasksRespons);
    }
}
