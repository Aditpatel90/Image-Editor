import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

class WeatherForecast {

    public static void main(String[] args) {
        // TODO

        String latitude = "";
        String longitude = "";
        String hourly = "temperature_2m";
        String temperatureUnit = "";
        String timezone = "EST";
        String location = "";

        if (args.length != 3) {
            System.err.println("Usage: java WeatherForecast --latitude <latitude> --longitude <longitude> --unit <unit>");
            System.exit(1);
        }

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("--latitude")) {
                latitude = args[i + 1];
            } else if (args[i].equals("--longitude")) {
                longitude = args[i + 1];
            } else if (args[i].equals("--unit")) {
                temperatureUnit = args[i + 1];
            } else if (args[i].equals("--location")) {
                location = args[i + 1];
            }
        }


        try {
            String unit = null;
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude +
                    "&hourly=" + hourly + "&temperature_unit=" + temperatureUnit + "&timezone=" + timezone;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
            } else {
                System.err.println("Failed to fetch data. Response code: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            System.err.println("Error fetching data: " + e.getMessage());
        }
    }
}