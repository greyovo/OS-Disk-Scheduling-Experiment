package grey.disk.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChartView extends Application {
    private LineChart<Number, Number> lineChart;
    private String title;
    private String otherInfo ;

    ChartView(String title, LineChart<Number, Number> lineChart, String otherInfo) {
        this.title = title;
        this.lineChart = lineChart;
        this.otherInfo = otherInfo;
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
