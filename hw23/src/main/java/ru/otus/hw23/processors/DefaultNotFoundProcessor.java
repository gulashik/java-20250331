package ru.otus.hw23.processors;



import ru.otus.hw23.request.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultNotFoundProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String response = "" +
                "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>Resource Not Found!!!</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
