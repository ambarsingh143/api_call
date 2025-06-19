package api_call.api_deeplink_fetch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiTest {
    public static void main(String[] args) {
        String apiUrl = "https://googleroutes.travomint.com/v1/api/metaSearchResult";
        String jsonPayload = "{\n" +
                "    \"currencyCode\": \"USD\",\n" +
                "    \"languageCode\": \"en\",\n" +
                "    \"countryCode\": \"US\",\n" +
                "    \"adults\": 1,\n" +
                "    \"children\": 0,\n" +
                "    \"infants\": 0,\n" +
                "    \"intendedCabin\": \"ECONOMY\",\n" +
                "    \"tripSpec\": {\n" +
                "        \"departureAirports\": [\"JFK\"],\n" +
                "        \"arrivalAirports\": [\"DXB\"],\n" +
                "        \"departureDate\": \"2025-06-25\",\n" +
                "        \"returnDate\": \"\"\n" +
                "    },\n" +
                "    \"version\": 1\n" +
                "}";

        try {
            URL url = new URL(apiUrl);
            System.out.println("API Domain: " + url.getHost());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            long startTime = System.currentTimeMillis();

            // Send JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes("utf-8"));
            }

            int responseCode = connection.getResponseCode();

            long endTime = System.currentTimeMillis();

            System.out.println("Response Code: " + responseCode);
            System.out.println("API Response Time: " + (endTime - startTime) / 1000.0 + " seconds");

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                // Parse JSON response (no full response printing)
                JSONObject jsonResponse = new JSONObject(response.toString());

                if (jsonResponse.has("itineraries")) {
                    JSONArray itineraries = jsonResponse.getJSONArray("itineraries");
                    if (itineraries.length() > 0) {
                        JSONObject firstItinerary = itineraries.getJSONObject(0);
                        if (firstItinerary.has("booking_url")) {
                            String bookingUrl = firstItinerary.getString("booking_url");
                            System.out.println("First booking_url: " + bookingUrl);
                        } else {
                            System.out.println("No booking_url found in the first itinerary.");
                        }
                    } else {
                        System.out.println("Itineraries array is empty.");
                    }
                } else {
                    System.out.println("No 'itineraries' field found in the response JSON.");
                }

            } else {
                System.out.println("Request failed with HTTP code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
