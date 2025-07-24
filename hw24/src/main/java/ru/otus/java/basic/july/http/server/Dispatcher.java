package ru.otus.java.basic.july.http.server;

import com.google.gson.Gson;
import ru.otus.java.basic.july.http.server.application.ItemsRepository;
import ru.otus.java.basic.july.http.server.error_handling.BadRequestException;
import ru.otus.java.basic.july.http.server.error_handling.ErrorDto;
import ru.otus.java.basic.july.http.server.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundProcessor;
    private RequestProcessor defaultStaticResourcesProcessor;

    public Dispatcher() {
        ItemsRepository itemsRepository = new ItemsRepository();
        this.processors = new HashMap<>();
        this.processors.put("GET /", new HelloWorldProcessor());
        this.processors.put("GET /calculator", new CalculatorProcessor());
        this.processors.put("GET /items", new GetItemsInfoProcessor(itemsRepository));
        this.processors.put("POST /items", new CreateNewItemProcessor(itemsRepository));
        this.defaultNotFoundProcessor = new DefaultNotFoundProcessor();
        this.defaultStaticResourcesProcessor = new DefaultStaticResourcesProcessor();
    }

    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (Files.exists(Paths.get("static/", request.getUri().substring(1)))) {
            defaultStaticResourcesProcessor.execute(request, output);
            return;
        }
        if (!processors.containsKey(request.getRoutingKey())) {
            defaultNotFoundProcessor.execute(request, output);
            return;
        }
        try {
            processors.get(request.getRoutingKey()).execute(request, output);
        } catch (BadRequestException e) {
            ErrorDto errorDto = new ErrorDto(e.getCode(), e.getMessage());
            Gson gson = new Gson();
            String response = "" +
                    "HTTP/1.1 400 Bad Request\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    gson.toJson(errorDto);
            output.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
