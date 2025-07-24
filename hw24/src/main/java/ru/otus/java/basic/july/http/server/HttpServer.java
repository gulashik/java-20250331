package ru.otus.java.basic.july.http.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.*;
import java.util.*;

public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final int port;
    
    public HttpServer(int port) {
        this.port = port;
    }
    
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту {}", port);
            
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleRequest(clientSocket);
                } catch (Exception e) {
                    logger.error("Ошибка при обработке запроса", e);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка при запуске сервера", e);
        }
    }
    
    private void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            
            String requestLine = in.readLine();
            if (requestLine == null) {
                return;
            }
            
            logger.info("Получен запрос: {}", requestLine);
            
            Map<String, String> headers = parseHeaders(in);
            logger.debug("Заголовки запроса: {}", headers);
            
            processRequest(requestLine, headers, out);
            
        } catch (Exception e) {
            logger.error("Необработанное исключение при обработке запроса", e);
            sendErrorResponse(clientSocket, 500, "Internal Server Error");
        }
    }
    
    private Map<String, String> parseHeaders(BufferedReader in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String line;
        
        while ((line = in.readLine()) != null && !line.trim().isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex > 0) {
                String key = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();
                headers.put(key, value);
                logger.debug("Заголовок: {} = {}", key, value);
            }
        }
        
        return headers;
    }
    
    private void processRequest(String requestLine, Map<String, String> headers, PrintWriter out) {
        try {

            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path = parts[1];
            
            logger.info("Обработка {} запроса к {}", method, path);

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=utf-8");
            out.println();
            out.println("<html><body><h1>Сервер работает!</h1></body></html>");
            
        } catch (Exception e) {
            logger.error("Ошибка при обработке запроса", e);
            throw e; // Перебрасываем исключение для обработки на верхнем уровне
        }
    }
    
    private void sendErrorResponse(Socket clientSocket, int statusCode, String statusText) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            out.println("HTTP/1.1 " + statusCode + " " + statusText);
            out.println("Content-Type: text/html; charset=utf-8");
            out.println();
            out.println("<html><body><h1>" + statusCode + " " + statusText + "</h1></body></html>");
            logger.info("Отправлен ответ с ошибкой: {} {}", statusCode, statusText);
        } catch (IOException e) {
            logger.error("Ошибка при отправке ответа об ошибке", e);
        }
    }
}