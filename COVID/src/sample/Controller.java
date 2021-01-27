package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Controller {
    @FXML
    public Label labelConfirmed;
    @FXML
    public Label labelRecovered;
    @FXML
    public Label labelCritical;
    @FXML
    public Label labelDeaths;
    @FXML
    public Label labelLastChange;
    @FXML
    public Label labelLastUpdate;
    @FXML
    public Button button;
    @FXML
    public TextField textField;

    public static String responseBody;

    public static int confirmed;
    public static int recovered;
    public static int critical;
    public static int deaths;
    public static String lastChange;
    public static String lastUpdate;

    public void initialize() throws IOException, InterruptedException {
        textField.setText("All Countries");
        getResponseBody();
        getData(responseBody);
        setLabels();
    }

    public void show() throws IOException, InterruptedException {
        getResponseBody();
        getData(responseBody);
        setLabels();
    }


    public void getResponseBody() throws IOException, InterruptedException {

        String countryName = textField.getText().toLowerCase();

        if (countryName.equals("all countries")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://covid-19-data.p.rapidapi.com/totals"))
                    .header("x-rapidapi-key", "849b590626msha2d99165166d1f6p162ec1jsnc3306391c1f1")
                    .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
            System.out.println(responseBody);
        } else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://covid-19-data.p.rapidapi.com/country?name=" + countryName))
                    .header("x-rapidapi-key", "849b590626msha2d99165166d1f6p162ec1jsnc3306391c1f1")
                    .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
        }
    }

    public void getData(String response) {
        JSONArray json = new JSONArray(response);
        JSONObject obj = json.getJSONObject(0);
        confirmed = obj.getInt("confirmed");
        recovered = obj.getInt("recovered");
        critical = obj.getInt("critical");
        deaths = obj.getInt("deaths");
        lastChange = obj.getString("lastChange");
        lastUpdate = obj.getString("lastUpdate");
    }

    public void setLabels() {
        labelConfirmed.setText(String.valueOf(confirmed));
        labelRecovered.setText(String.valueOf(recovered));
        labelCritical.setText(String.valueOf(critical));
        labelDeaths.setText(String.valueOf(deaths));
        labelLastChange.setText(lastChange);
        labelLastUpdate.setText(lastUpdate);
    }
}
