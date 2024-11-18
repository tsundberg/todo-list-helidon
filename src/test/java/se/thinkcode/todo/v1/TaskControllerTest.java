package se.thinkcode.todo.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.http.Status;
import io.helidon.webclient.api.ClientResponseTyped;
import io.helidon.webclient.http1.Http1Client;
import io.helidon.webclient.http1.Http1ClientRequest;
import io.helidon.webclient.http1.Http1ClientResponse;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.testing.junit5.DirectClient;
import io.helidon.webserver.testing.junit5.RoutingTest;
import io.helidon.webserver.testing.junit5.SetUpRoute;
import org.junit.jupiter.api.Test;
import se.thinkcode.Routes;
import se.thinkcode.TodoList;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RoutingTest
public class TaskControllerTest {
    final Http1Client client;

    TaskControllerTest(DirectClient client) {
        this.client = client;
    }

    @SetUpRoute
    static void routing(HttpRouting.Builder builder) {
        Routes routes = new Routes();
        routes.routing(builder);
    }

    @Test
    void should_create_task() {
        TaskResponse expected = new TaskResponse("Öva");
        createTask();
        List<TaskResponse> actualTasks = getTasks();

        assertThat(actualTasks).containsExactly(expected);
    }

    private void createTask() {
        Http1ClientRequest request = client.post("/v1/addTask");
        TaskRequest entity = new TaskRequest("Max", "Öva");
        ClientResponseTyped<TaskRequest> response = request.submit(entity, TaskRequest.class);

        assertThat(response.status()).isEqualTo(Status.OK_200);
        response.close();
    }

    private List<TaskResponse> getTasks() {
        ObjectMapper mapper = new ObjectMapper();
        Http1ClientRequest request = client.get("/v1/getTasks/Max");
        try (Http1ClientResponse response = request.request()) {
            String json = response.as(String.class);

            return mapper.readerForListOf(TaskResponse.class)
                    .readValue(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
