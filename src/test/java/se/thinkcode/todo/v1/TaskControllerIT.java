package se.thinkcode.todo.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.http.Status;
import io.helidon.webclient.api.ClientResponseTyped;
import io.helidon.webclient.http1.Http1Client;
import io.helidon.webclient.http1.Http1ClientRequest;
import io.helidon.webclient.http1.Http1ClientResponse;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import io.helidon.webserver.testing.junit5.ServerTest;
import io.helidon.webserver.testing.junit5.SetUpRoute;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ServerTest
public class TaskControllerIT {
    private static Routes routes;
    private static HttpRouting.Builder builder;
    final Http1Client client;

    TaskControllerIT(Http1Client client) {
        this.client = client;
    }

    @SetUpRoute
    static void routing(HttpRouting.Builder builder) {
        TaskControllerIT.builder = builder;
        routes = new Routes();
        routes.routes(builder);
    }

    @Test
    void should_verify_route_using_a_mock() {
        CreateTaskController createTaskController = mock(CreateTaskController.class);
        routes.overrideController(createTaskController, CreateTaskController.class);
        routes.routes(builder);

        Http1ClientRequest request = client.post("/v1/addTask");
        CreateTaskRequest entity = new CreateTaskRequest("Max", "Öva");

        request.submit(entity, CreateTaskRequest.class);

        verify(createTaskController).handle(any(ServerRequest.class), any(ServerResponse.class));
    }

    @Test
    void should_create_task() {
        GetTasksResponse expected = new GetTasksResponse("Öva");
        createTask();
        List<GetTasksResponse> actualTasks = getTasks();

        assertThat(actualTasks).containsExactly(expected);
    }

    private void createTask() {
        Http1ClientRequest request = client.post("/v1/addTask");
        CreateTaskRequest entity = new CreateTaskRequest("Max", "Öva");
        ClientResponseTyped<CreateTaskRequest> response = request.submit(entity, CreateTaskRequest.class);

        assertThat(response.status()).isEqualTo(Status.OK_200);
        response.close();
    }

    private List<GetTasksResponse> getTasks() {
        ObjectMapper mapper = new ObjectMapper();
        Http1ClientRequest request = client.get("/v1/getTasks/Max");
        try (Http1ClientResponse response = request.request()) {
            String json = response.as(String.class);

            return mapper.readerForListOf(GetTasksResponse.class)
                    .readValue(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

