package grey.disk.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class ChartView extends Application {
    private LineChart<Number, Number> lineChart;
    private String title;
    private String otherInfo;

    ChartView(String title, LineChart<Number, Number> lineChart, String otherInfo) {
        this.title = title;
        this.lineChart = lineChart;
        this.otherInfo = otherInfo;
    }

    @Override
    public void start(Stage stage) throws Exception {

        lineChart.setPrefWidth(1600);
        lineChart.setPrefHeight(800);
        lineChart.setCreateSymbols(false);

        Label infoLabel = new Label(otherInfo);
        infoLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setStyle("-fx-font-size:16");

        stage.setTitle(title);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding:10,10,10,20");
        vbox.getChildren().addAll(lineChart, infoLabel);
        Scene scene = new Scene(vbox, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }


}
