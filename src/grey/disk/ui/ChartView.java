package grey.disk.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChartView extends Application {
    LineChart<Number, Number> lineChart;
    String title;

    ChartView(String title, LineChart<Number, Number> lineChart) {
        this.title = title;
        this.lineChart = lineChart;
    }

    @Override
    public void start(Stage stage) throws Exception {

        lineChart.setPrefWidth(1600);
        lineChart.setPrefHeight(1000);
        lineChart.setCreateSymbols(false);

        stage.setTitle(title);
        Pane pane = new Pane(lineChart);
        Scene scene = new Scene(pane, 1600, 1000);
        stage.setScene(scene);
        stage.show();
    }


}
