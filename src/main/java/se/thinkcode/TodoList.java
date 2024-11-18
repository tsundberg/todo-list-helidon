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
                .routing(it ->  new Routes().routing(it))
                .build()
                .start();
    }
}
