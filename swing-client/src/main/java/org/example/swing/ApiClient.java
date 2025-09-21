package org.example.swing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Product;
import org.example.swing.model.Cart;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ApiClient {


    // Base URL of your API Gateway
    private static final String GATEWAY_URL = "http://localhost:8080";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Fetch all products
    public List<Product> fetchProducts() throws IOException {
        String urlString = GATEWAY_URL + "/products";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        List<Product> products = objectMapper.readValue(
                conn.getInputStream(),
                new TypeReference<List<Product>>() {
                }
        );
        conn.disconnect();
        return products;
    }

    // Place an order
    public void placeOrder(Cart cart) throws IOException {
        String urlString = GATEWAY_URL + "/orders";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        String jsonCart = objectMapper.writeValueAsString(cart);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonCart.getBytes());
            os.flush();
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed to place order: HTTP error code " + conn.getResponseCode());
        }
        conn.disconnect();
    }

    // Login user
    public boolean loginUser(String username, String password) throws IOException {
        String urlString = GATEWAY_URL + "/users/login";
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();
        return responseCode == HttpURLConnection.HTTP_OK;
    }

    // Add more API calls here as needed, e.g., fetchCart, updateCartItem, etc.
}
