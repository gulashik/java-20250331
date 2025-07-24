package ru.otus.java.basic.july.http.server.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private String rawRequest;
    private String method;
    private String uri;
    private String body;
    private Map<String, String> parameters;

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getRoutingKey() {
        return method + " " + uri;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getBody() {
        return body;
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parameters = new HashMap<>();
        this.parse();
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        method = rawRequest.substring(0, startIndex);
        uri = rawRequest.substring(startIndex + 1, endIndex);
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("&");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
        body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
    }

    public void info(boolean debug) {
        if (debug) {
            logger.info(rawRequest);
        }
        logger.info("METHOD: " + method);
        logger.info("URI: " + uri);
        logger.info("PARAMETERS: " + parameters);
        logger.info("BODY: " + body);
    }
}
