package sample;

import animatefx.animation.*;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;


import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public Button dollarButton1;
    public Button dollarButton2;

    public Button euroButton1;
    public Button euroButton2;

    public Button rubleButton1;
    public Button rubleButton2;

    public Button uahButton1;
    public Button uahButton2;

    public TextField mainText;
    public JFXTextField resultText;


    public ArrayList<Button> firstLine;
    public ArrayList<Button> secondLine;


    public double from;
    public double to;

    public Button refreshButton;


    private double USD_TO_EUR = 0.85;
    private double USD_TO_RUB = 75.01;
    private double USD_TO_UAH = 27.42;


    private double EUR_TO_USD = 1.18;
    private double EUR_TO_RUB = 88.70;
    private double EUR_TO_UAH = 32.42;


    private double RUB_TO_USD = 0.013;
    private double RUB_TO_EUR = 0.011;
    private double RUB_TO_UAH = 0.37;


    private double UAH_TO_USD = 0.036;
    private double UAH_TO_EUR = 0.031;
    private double UAH_TO_RUB = 2.74;
    public Button switchValues;
    public Button switchCurrenices;
    public Button closeButton;

    public Blend glowForButtons = new Blend();


    public void refreshCurrencies() throws IOException {

        String url = "https://www.alta.ru/currency/";
        Document doc = Jsoup.connect(url).maxBodySize(0).get();

        Elements elements = doc.getAllElements();


        double usd_to_rub = Double.parseDouble(elements.select("#default > div > table > tbody > tr:nth-child(1) > td:nth-child(3)").text());
        USD_TO_RUB = usd_to_rub;
        RUB_TO_USD = 1.0 / usd_to_rub;


        double eur_to_rub = Double.parseDouble(elements.select("#default > div > table > tbody > tr:nth-child(2) > td:nth-child(3)").text());
        EUR_TO_RUB = eur_to_rub;
        RUB_TO_EUR = 1 / eur_to_rub;

        double uah_to_rub = Double.parseDouble(elements.select("#default > div > table > tbody > tr:nth-child(31) > td:nth-child(3)").text()) / 10;
        System.out.println();
        UAH_TO_RUB = uah_to_rub;
        RUB_TO_UAH = 1 / uah_to_rub;

        // other calculations :

        USD_TO_EUR = USD_TO_RUB / EUR_TO_RUB;
        UAH_TO_USD = UAH_TO_RUB / USD_TO_RUB;
        USD_TO_UAH = 1 / UAH_TO_USD;
        EUR_TO_USD = 1 / USD_TO_EUR;

        EUR_TO_UAH = EUR_TO_RUB / UAH_TO_RUB;
        UAH_TO_EUR = 1 / EUR_TO_UAH;


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        firstLine = new ArrayList<>();
        secondLine = new ArrayList<>();

        try {
            refreshCurrencies();
        } catch (IOException e) {
            System.out.println("Error parsing currencies. Using default values.");
            e.printStackTrace(); // тут прописать ошибку/действие в случае ошибки обновления валют
        }

        firstLine.add(dollarButton1);
        firstLine.add(euroButton1);
        firstLine.add(rubleButton1);
        firstLine.add(uahButton1);

        secondLine.add(dollarButton2);
        secondLine.add(euroButton2);
        secondLine.add(rubleButton2);
        secondLine.add(uahButton2);


        glowForButtons.setMode(BlendMode.DIFFERENCE);
        glowForButtons.setOpacity(0.28);


        from = 1;
        to = USD_TO_EUR;


        dollarButton1.setEffect(glowForButtons);
        euroButton2.setEffect(glowForButtons);

        dollarButton1.setDefaultButton(true);
        euroButton2.setDefaultButton(true);


        mainText.setText(String.valueOf(from));
        resultText.setText(String.valueOf(from * to));

    }


    public void exchange() {

        String text = mainText.getText();


        try {
            from = Double.parseDouble(text);
        } catch (Exception ex) {
            resultText.setText("");
            return;
        }


        if (dollarButton1.isDefaultButton()) {


            if (dollarButton2.isDefaultButton()) {
                to = 1;

            } else if (euroButton2.isDefaultButton()) {
                to = USD_TO_EUR;

            } else if (rubleButton2.isDefaultButton()) {
                to = USD_TO_RUB;

            } else if (uahButton2.isDefaultButton()) {
                to = USD_TO_UAH;
            }


        } else if (euroButton1.isDefaultButton()) {

            if (dollarButton2.isDefaultButton()) {

                to = EUR_TO_USD;
            } else if (euroButton2.isDefaultButton()) {

                to = 1;
            } else if (rubleButton2.isDefaultButton()) {

                to = EUR_TO_RUB;
            } else if (uahButton2.isDefaultButton()) {

                to = EUR_TO_UAH;
            }


        } else if (rubleButton1.isDefaultButton()) {

            if (dollarButton2.isDefaultButton()) {

                to = RUB_TO_USD;
            } else if (euroButton2.isDefaultButton()) {

                to = RUB_TO_EUR;
            } else if (rubleButton2.isDefaultButton()) {

                to = 1;
            } else if (uahButton2.isDefaultButton()) {

                to = RUB_TO_UAH;
            }

        } else if (uahButton1.isDefaultButton()) {

            if (dollarButton2.isDefaultButton()) {

                to = UAH_TO_USD;
            } else if (euroButton2.isDefaultButton()) {

                to = UAH_TO_EUR;
            } else if (rubleButton2.isDefaultButton()) {

                to = UAH_TO_RUB;
            } else if (uahButton2.isDefaultButton()) {

                to = 1;
            }


        }


        String resultValue = String.valueOf(from * to);

        if (resultValue.split("\\.")[1].length() > 2) {
            String whatToDelete = resultValue.substring(resultValue.indexOf(".") + 2);
            resultValue = resultValue.replace(whatToDelete, "");
        }


        resultText.setText(resultValue);

    }

    public void enableButton(MouseEvent mouseEvent) {
        Button pressedButton = (Button) mouseEvent.getSource();


        if (firstLine.contains(pressedButton)) {
            for (Button a : firstLine) {
                a.setEffect(new DropShadow());
            }
        } else {
            for (Button a : secondLine) {
                a.setEffect(new DropShadow());
            }
        }

        pressedButton.setEffect(glowForButtons);
        String pressed = mouseEvent.getSource().toString();

        if (dollarButton1.toString().equals(pressed) || euroButton1.toString().equals(pressed) || rubleButton1.toString().equals(pressed) || uahButton1.toString().equals(pressed)) {

            if (dollarButton1.toString().equals(pressed)) {

                dollarButton1.setDefaultButton(true);
                euroButton1.setDefaultButton(false);
                rubleButton1.setDefaultButton(false);
                uahButton1.setDefaultButton(false);


            } else if (euroButton1.toString().equals(pressed)) {

                dollarButton1.setDefaultButton(false);
                euroButton1.setDefaultButton(true);
                rubleButton1.setDefaultButton(false);
                uahButton1.setDefaultButton(false);

            } else if (rubleButton1.toString().equals(pressed)) {

                dollarButton1.setDefaultButton(false);
                euroButton1.setDefaultButton(false);
                rubleButton1.setDefaultButton(true);
                uahButton1.setDefaultButton(false);

            } else {

                dollarButton1.setDefaultButton(false);
                euroButton1.setDefaultButton(false);
                rubleButton1.setDefaultButton(false);
                uahButton1.setDefaultButton(true);

            }


        } else {

            if (dollarButton2.toString().equals(pressed)) {


                dollarButton2.setDefaultButton(true);
                euroButton2.setDefaultButton(false);
                rubleButton2.setDefaultButton(false);
                uahButton2.setDefaultButton(false);


            } else if (euroButton2.toString().equals(pressed)) {


                dollarButton2.setDefaultButton(false);
                euroButton2.setDefaultButton(true);
                rubleButton2.setDefaultButton(false);
                uahButton2.setDefaultButton(false);
            } else if (rubleButton2.toString().equals(pressed)) {


                dollarButton2.setDefaultButton(false);
                euroButton2.setDefaultButton(false);
                rubleButton2.setDefaultButton(true);
                uahButton2.setDefaultButton(false);

            } else {


                dollarButton2.setDefaultButton(false);
                euroButton2.setDefaultButton(false);
                rubleButton2.setDefaultButton(false);
                uahButton2.setDefaultButton(true);

            }


        }

        exchange();


    }


    public void bounce(MouseEvent mouseEvent) {
        //Pulse
        new Pulse((Node) mouseEvent.getSource()).play();
    }

    public void switchVal(ActionEvent actionEvent) {

        mainText.setText(resultText.getText());
        exchange();

    }

    public void switchCur(ActionEvent actionEvent) {

        int button1 = 0;
        int button2 = 0;


        for (int i = 0; i < firstLine.size(); i++) {
            if (firstLine.get(i).isDefaultButton()) {
                button1 = i;
                break;
            }
        }

        for (int i = 0; i < secondLine.size(); i++) {
            if (secondLine
                    .get(i).isDefaultButton()) {
                button2 = i;
                break;
            }
        }

        enableButton(firstLine.get(button2));
        enableButton(secondLine.get(button1));

    }

    public void exitApp(ActionEvent actionEvent) {
        Stage a = (Stage) closeButton.getScene().getWindow();
        a.close();
    }

    public void enableButton(Button button) {

        if (firstLine.contains(button)) {
            for (Button a : firstLine) {
                a.setEffect(new DropShadow());
            }
        } else {
            for (Button a : secondLine) {
                a.setEffect(new DropShadow());
            }
        }


        if (!(button.equals(switchCurrenices) || button.equals(switchValues))) {
            button.setEffect(glowForButtons);
        }


        String pressed = button.toString();

        if (dollarButton1.toString().equals(pressed) || euroButton1.toString().equals(pressed) || rubleButton1.toString().equals(pressed) || uahButton1.toString().equals(pressed)) {

            if (dollarButton1.toString().equals(pressed)) {

                dollarButton1.setDefaultButton(true);
                euroButton1.setDefaultButton(false);
                rubleButton1.setDefaultButton(false);
                uahButton1.setDefaultButton(false);


            } else if (euroButton1.toString().equals(pressed)) {

                dollarButton1.setDefaultButton(false);
                euroButton1.setDefaultButton(true);
                rubleButton1.setDefaultButton(false);
                uahButton1.setDefaultButton(false);

            } else if (rubleButton1.toString().equals(pressed)) {

                dollarButton1.setDefaultButton(false);
                euroButton1.setDefaultButton(false);
                rubleButton1.setDefaultButton(true);
                uahButton1.setDefaultButton(false);

            } else {

                dollarButton1.setDefaultButton(false);
                euroButton1.setDefaultButton(false);
                rubleButton1.setDefaultButton(false);
                uahButton1.setDefaultButton(true);

            }


        } else {

            if (dollarButton2.toString().equals(pressed)) {


                dollarButton2.setDefaultButton(true);
                euroButton2.setDefaultButton(false);
                rubleButton2.setDefaultButton(false);
                uahButton2.setDefaultButton(false);


            } else if (euroButton2.toString().equals(pressed)) {


                dollarButton2.setDefaultButton(false);
                euroButton2.setDefaultButton(true);
                rubleButton2.setDefaultButton(false);
                uahButton2.setDefaultButton(false);
            } else if (rubleButton2.toString().equals(pressed)) {


                dollarButton2.setDefaultButton(false);
                euroButton2.setDefaultButton(false);
                rubleButton2.setDefaultButton(true);
                uahButton2.setDefaultButton(false);

            } else {


                dollarButton2.setDefaultButton(false);
                euroButton2.setDefaultButton(false);
                rubleButton2.setDefaultButton(false);
                uahButton2.setDefaultButton(true);

            }


        }

        exchange();


    }

    public void clearButton(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setText("");
    }

    public void refreshAction(MouseEvent actionEvent) {

        new Jello((Node) actionEvent.getSource()).play();

        try {

            refreshCurrencies();


        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }
}
