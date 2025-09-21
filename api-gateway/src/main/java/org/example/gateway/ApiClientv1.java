package org.example.gateway;

public class ApiClientv1 {
//    private static final String BASE_URL = "http://localhost:8080"; // catalog service port
//    private static final ObjectMapper mapper = new ObjectMapper();
//    private static final HttpClient client = HttpClient.newHttpClient();
//
//    public static List<Product> fetchProducts() throws IOException, InterruptedException {
//        HttpRequest req = null;
//        try {
//            req = HttpRequest.newBuilder()
//                    .uri(URI.create(BASE_URL + "/products"))
//                    .build();
//        } catch (Exception e) {
//            System.out.println("Exception caught");
//        }
//
//        if (req != null) {
//            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
//
//            if (resp.statusCode() != 200) {
//                throw new RuntimeException("Failed to fetch products:  " + resp.statusCode());
//            }
//            return mapper.readValue(resp.body(), new TypeReference<List<Product>>() {
//            });
//        }
//
//        return new ArrayList<>();
//    }

}
