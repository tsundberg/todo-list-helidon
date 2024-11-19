package se.thinkcode;

import io.helidon.webserver.WebServer;

public class TodoList {
    public static void main(String[] args) {
        WebServer.builder()
                .port(8080)
                .routing(it -> new Routes().routes(it))
                .build()
                .start();
    }
}
