package api_call.api_deeplink_fetch;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Keys;
import io.github.bonigarcia.wdm.WebDriverManager;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
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
                + "	\"adults\": 1,\r\n"
                + "	\"children\": 1,\r\n"
                + "	\"infants\": 1,\r\n"
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
        
        String apiDomain = extractApiDomain(apiUrl);  // Extract API domain
        
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

            // If response code is 200 (OK), proceed with the next steps
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String abc = "";
                long endTimee = System.currentTimeMillis();  // End time for response time measurement
                endTime = endTimee;

                // Read the response from the input stream
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
                        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

                        System.out.println("✅ Scrolled to the bottom!");

                        // Wait for a few seconds to see the effect
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Filling out form fields
                        driver.findElement(By.name("email")).sendKeys("ambar.singh@snva.com");
                        Thread.sleep(2000);
                        driver.findElement(By.name("phonenumber")).sendKeys("7987739916");
                        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
                        driver.findElement(By.xpath("//input[@id=\":r2:\"]")).sendKeys("Alok");
                        driver.findElement(By.xpath("//input[@id=\":r4:\"]")).sendKeys("Testing");
                        driver.findElement(By.xpath("//input[@id=\":r5:\"]")).click();
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[1]/div/div[2]/div/div/div[2]/div/div[3]/button[5]")).click();
                        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("//input[@id=\":rg:\"]")).sendKeys("Ambar");
                        driver.findElement(By.xpath("//input[@id=\":ri:\"]")).sendKeys("Testing");
                        driver.findElement(By.xpath("//input[@id=\":rj:\"]")).click();
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[1]/div/div[2]/div/div/div[2]/div/div[3]/button[5]")).click();
                        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("//input[@id=\":ru:\"]")).sendKeys("Keshav");
                        driver.findElement(By.xpath("//input[@id=\":r10:\"]")).sendKeys("Testing");
                        driver.findElement(By.xpath("//input[@id=\":r11:\"]")).click();
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div/div/div/div[1]/div/div[2]/div/div/div[2]/div/div[3]/button[5]")).click();
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
                        Thread.sleep(10000);
                        driver.findElement(By.xpath("//button[@class=\"btn btn-siteorange done-velres next-stpinf ml-auto\"]")).click();
                        Thread.sleep(5000);
                        driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div[1]/div[2]/div[4]/div[2]/div/div/div[2]/div/div[1]/div/div/label/span[1]")).click();
                        Thread.sleep(5000);
                        driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div/div/div[1]/div[2]/div[4]/div[2]/div/div/div[2]/div/div[2]/div/div/span/button")).click();
                        Thread.sleep(5000);
                        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/header/div/div/a/div/div/div[2]")).click();

                        abc = bookingUrl;

                        // Open a new tab and switch to it
                        String openTab = Keys.chord(Keys.CONTROL, "t");
                        driver.findElement(By.tagName("body")).sendKeys(openTab); // Open a new tab (CTRL + t)

                        // Switch to the new tab and navigate to the booking URL
                        for (String windowHandle : driver.getWindowHandles()) {
                            driver.switchTo().window(windowHandle); // Switch to the new tab
                        }

                    }
                }
                // Calculate the response time
                long responseTimeMillis = endTime - startTime;   // Time taken for the response in milliseconds
                double responseTimeSeconds = responseTimeMillis / 1000.0; // divide by 1000 to convert milliseconds to seconds
                System.out.println("API Response Time: " + responseTimeSeconds + " seconds");
                System.out.println("Booking URL: " + abc);

                // Send email report
                sendEmail("API is working fine! Domain: " + apiDomain + " Response Time: " + responseTimeSeconds + " seconds", 
                    "Booking URL: " + abc);
            } else {
                // If response code is not 200, show an error message
                System.out.println("Error: API request failed with response code " + responseCode + ". API is not working.");

                // Send email report about API failure
                sendEmail("API is not working", "Response Code: " + responseCode + "\nDomain: " + apiDomain);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Helper method to extract booking_url from the response
    private static String extractBookingUrl(String response) {
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

    // Helper method to extract API domain from URL
    private static String extractApiDomain(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return url.getHost();  // Get the domain from the URL
        } catch (Exception e) {
            System.out.println("Error extracting API domain: " + e.getMessage());
            return "";
        }
    }

    // Helper method to send email
    private static void sendEmail(String subject, String body) {
        String host = "smtp.gmail.com"; // SMTP server for Gmail
        String from = "ambar.singh@snva.com"; // Your email address
        String password = "lovq evli zniy iivy"; // Your email password
        String to = "ambar.singh@snva.com"; // Recipient email address

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
