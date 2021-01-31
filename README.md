# Currency-Converter-JavaFX
A first version of my Currency Converter mad from scratch, which logic was later used in MyCurrency project. 

### Installation

Project requires [Java FX](https://openjfx.io) to run.

To run my app run

```sh
$ git clone https://github.com/goldoner/Currency-Converter-JavaFX.git MyCurrency
$ cd MyCurrecy/out/artifacts/MyCurrency_jar
$ java -jar --module-path <<PATH TO JAVAFX SDK 14.0.2.1/lib>> --add-modules javafx.controls,,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED MyCurrency.jar
