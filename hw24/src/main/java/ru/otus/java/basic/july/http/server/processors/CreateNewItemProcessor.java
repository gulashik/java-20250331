package ru.otus.java.basic.july.http.server.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.july.http.server.HttpRequest;
import ru.otus.java.basic.july.http.server.application.ItemsRepository;
import ru.otus.java.basic.july.http.server.application.dtos.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class CreateNewItemProcessor implements RequestProcessor {
    private ItemsRepository itemsRepository;

    public CreateNewItemProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(request.getBody(), Item.class);
        itemsRepository.createNew(item);
        String jsonItem = gson.toJson(item);
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                jsonItem;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
