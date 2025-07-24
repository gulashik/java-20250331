package ru.otus.java.basic.july.http.server.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.july.http.server.request.HttpRequest;
import ru.otus.java.basic.july.http.server.application.ItemsRepository;
import ru.otus.java.basic.july.http.server.application.dtos.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetItemsInfoProcessor implements RequestProcessor {
    private ItemsRepository itemsRepository;

    public GetItemsInfoProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String result;
        Gson gson = new Gson();
        if (request.containsParameter("id")) {
            long id = Long.parseLong(request.getParameter("id"));
            Item item = itemsRepository.getById(id);
            result = gson.toJson(item);
        } else {
            List<Item> items = itemsRepository.getAll();
            result = gson.toJson(items);
        }
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                result;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
