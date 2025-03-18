package api_call.api_deeplink_fetch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SeleniumApiPostExample {
    public static void main(String[] args) {
        // Set up WebDriver (for example, Chrome)
        WebDriverManager.chromedriver().setup();
        
        WebDriver driver = new ChromeDriver();
        
        // Make a POST API request using HttpURLConnection
        String apiUrl = "https://routes.traveloes.com/v1/api/metaSearchResult";
        String jsonPayload = "{\r\n"
                + "	\"currencyCode\": \"USD\",\r\n"
                + "	\"languageCode\": \"en\",\r\n"
                + "	\"countryCode\": \"US\",\r\n"
                + "	\"adults\": 1,\r\n"
                + "	\"children\": 0,\r\n"
                + "	\"infants\": 0,\r\n"
                + "	\"intendedCabin\": \"ECONOMY\",\r\n"
                + "	\"tripSpec\": {\r\n"
                + "		\"departureAirports\": [\r\n"
                + "			\"JFK\"\r\n"
                + "		],\r\n"
                + "		\"arrivalAirports\": [\r\n"
                + "			\"DEL\"\r\n"
                + "		],\r\n"
                + "		\"departureDate\": \"2025-03-25\",\r\n"
                + "		\"returnDate\": \"\"\r\n"
                + "	},\r\n"
                + "	\"version\": 1\r\n"
                + "}";
        
        long startTime = System.currentTimeMillis();  // Start time for response time measurement
        
        try {
            // Create a URL object
            URL url = new URL(apiUrl);
            
            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set up connection properties
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            // connection.setRequestProperty("Authorization", "Bearer your_token"); // Replace with actual token if needed
            connection.setDoOutput(true);
            
            // Send the POST data
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the input stream
            if (responseCode == HttpURLConnection.HTTP_OK) { // Check if the request was successful
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Print the response
                    System.out.println("Response: " + response.toString());
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
            }

            // Calculate the response time
            long endTime = System.currentTimeMillis();  // End time for response time measurement
            long responseTimeMillis = endTime - startTime;   // Time taken for the response in milliseconds
            
            // Convert response time to seconds
            double responseTimeSeconds = responseTimeMillis / 1000.0; // divide by 1000 to convert milliseconds to seconds
            System.out.println("API Response Time: " + responseTimeSeconds + " seconds");

            // Close the connection
            connection.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use Selenium WebDriver for web automation if necessary
        driver.get("https://us.travomint.com/");

        // Example of interacting with a web element (make sure to change the element ID)
        // driver.findElement(By.id("some-element-id")).click();

        // Close the browser after automation
        // driver.quit();
    }
}
