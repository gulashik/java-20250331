package ru.otus.java.basic.july.http.server.processors;

import ru.otus.java.basic.july.http.server.request.HttpRequest;
import ru.otus.java.basic.july.http.server.error_handling.BadRequestException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (!request.containsParameter("a")) {
            throw new BadRequestException("Отсутствует обязательный параметр запроса 'a'", "INCORRECT_INPUT_DATA");
        }
        if (!request.containsParameter("b")) {
            throw new BadRequestException("Отсутствует обязательный параметр запроса 'b'", "INCORRECT_INPUT_DATA");
        }
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));
        String result = a + " + " + b + " = " + (a + b);

        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>" + result + "</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
