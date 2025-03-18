package api_call.api_deeplink_fetch;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Keys;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SeleniumApiPostExample {
    
    public static void main(String[] args) {
        // Schedule the task every hour
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            executeApiCallAndAutomation();
        }, 0, 1, TimeUnit.HOURS);  // Initial delay is 0, and the interval is 1 hour
    }
    
    private static void executeApiCallAndAutomation() {
        // Set up WebDriver (for example, Chrome)
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        // Make a POST API request using HttpURLConnection
        String apiUrl = "https://routes.traveloes.com/v1/api/metaSearchResult";
        String jsonPayload = "{\r\n"
                + "	\"currencyCode\": \"USD\",\r\n"
                + "	\"languageCode\": \"en\",\r\n"
                + "	\"countryCode\": \"US\",\r\n"
                + "	\"adults\": 3,\r\n"
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
        long endTime;
        
        try {
            // Create a URL object
            URL url = new URL(apiUrl);
            
            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set up connection properties
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Send the POST data
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            String abc = "";
            long endTimee = System.currentTimeMillis();  // End time for response time measurement
            endTime = endTimee;
            
            // Read the response from the input stream
            if (responseCode == HttpURLConnection.HTTP_OK) { // Check if the request was successful
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Extract the "booking_url" from the response
                    String bookingUrl = extractBookingUrl(response.toString());
                    driver.manage().window().maximize();
                    if (bookingUrl != null && !bookingUrl.isEmpty()) {
                        // Use Selenium to navigate to the booking URL in the current tab
                        driver.get(bookingUrl);
                        Thread.sleep(10000);
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("window.scrollBy(0, 8000)");                        
                        driver.findElement(By.name("email")).sendKeys("ambar.singh@snva.com");
                        Thread.sleep(2000);
                        driver.findElement(By.name("phonenumber")).sendKeys("7987739916");
                        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
                        
                       
                        abc = bookingUrl;

                        // Open a new tab and switch to it
                        String openTab = Keys.chord(Keys.CONTROL, "t");
                        driver.findElement(By.tagName("body")).sendKeys(openTab); // Open a new tab (CTRL + t)

                        // Switch to the new tab and navigate to the booking URL
                        for (String windowHandle : driver.getWindowHandles()) {
                            driver.switchTo().window(windowHandle); // Switch to the new tab
                        }

                        // Navigate to the same booking URL in the new tab
                        //driver.get(bookingUrl);
                       // System.out.println("Navigated to the same booking URL in the new tab.");
                    }
                }
            } else {
                System.out.println("POST request failed with response code: " + responseCode);
            }

            // Calculate the response time
            long responseTimeMillis = endTime - startTime;   // Time taken for the response in milliseconds
            double responseTimeSeconds = responseTimeMillis / 1000.0; // divide by 1000 to convert milliseconds to seconds
            System.out.println("API Response Time: " + responseTimeSeconds + " seconds");
            System.out.println("Booking URL: " + abc);
            
            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Helper method to extract booking_url from the response
    private static String extractBookingUrl(String response) {
        // Example: Assuming the response is in JSON format and contains a "booking_url" key
        try {
            int bookingUrlIndex = response.indexOf("\"booking_url\":");
            if (bookingUrlIndex != -1) {
                int start = response.indexOf("\"", bookingUrlIndex + 14) + 1;
                int end = response.indexOf("\"", start);
                return response.substring(start, end);
            }
        } catch (Exception e) {
            System.out.println("Error extracting booking URL: " + e.getMessage());
        }
        return null;
    }
}
