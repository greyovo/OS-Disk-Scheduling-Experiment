package grey.disk.ui;

import grey.disk.test.FCFS;
import grey.disk.test.SSTF;
import grey.disk.util.RequestMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public GridPane gridPane;
    public TextField firstLocField;
    public TextField requestNumField;
    public TextField maxTrackNumField;
    public HBox chartBox;
    public TextArea infoText;

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

    private RequestMaker maker = new RequestMaker();
    private ArrayList<Integer> requestList = null;
    private int firstLoc = -1;
    private int trackNum = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void generateRequest(ActionEvent event) {
        int requestNum = 0;
        try {
            requestNum = Integer.parseInt(requestNumField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "序列长度有误，请输入数字");
            alert.show();
            return;
        }
        if (requestNum < 400) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "序列长度过小");
            alert.show();
            return;
        }
        maker.setRequestNum(requestNum);
        updateRequestList(maker.getRandomRequestList());
    }

    @FXML
    public boolean checkCorrect(ActionEvent event) {
        try {
            firstLoc = Integer.parseInt(firstLocField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "磁道号有误，请输入数字");
            alert.show();
            return false;
        }
        trackNum = Integer.parseInt(maxTrackNumField.getText());
        if (firstLoc < 0 || firstLoc > trackNum) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "磁道号小于0或大于最大磁道数");
            alert.show();
            return false;
        }
        if (requestList == null || requestList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "请求序列为空，未生成请求序列");
            alert.show();
            return false;
        }
        return true;
    }

    @FXML
    public void clear(ActionEvent event) {
        infoText.setText("");
        requestList.clear();
        firstLocField.setText("");
        requestNumField.setText("");
//        maxTrackNumField.setText("");
    }

    private void updateRequestList(ArrayList<Integer> list) {
        infoText.setText(list.toString());
        this.requestList = list;
    }

    private void initLineChart() {
        xAxis.setLabel("访问时间");
        yAxis.setLabel("磁道号");
        lineChart.setTitle("各算法调度结果");
        lineChart.setPrefWidth(1600);
        lineChart.setPrefHeight(1000);
        lineChart.setCreateSymbols(false);
    }

    @FXML
    private void drawFCFS() {
        lineChart.getData().clear();
        
        //FCFS
        FCFS fcfs = new FCFS(firstLoc, trackNum, requestList);
        ArrayList<Integer> fcfsResult = fcfs.run();
        XYChart.Series<Number, Number> fcfsSeries = new XYChart.Series<>();
        
        for (int i = 0; i < fcfsResult.size(); i++) {
            fcfsSeries.getData().add(new XYChart.Data<>(i + 1, fcfsResult.get(i)));
        }
        
        lineChart.getData().add(fcfsSeries);
        try {
            new ChartView("FCFS", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawSSTF() {
        lineChart.getData().clear();
        //SSTF
        SSTF sstf = new SSTF(firstLoc, trackNum, requestList);
        ArrayList<Integer> sstfResult = sstf.run();
        XYChart.Series<Number, Number> sstfSeries = new XYChart.Series<>();
        sstfSeries.setName("SSTF");

        for (int i = 0; i < sstfResult.size(); i++) {
            sstfSeries.getData().add(new XYChart.Data<>(i + 1, sstfResult.get(i)));
        }

        lineChart.getData().add(sstfSeries);

        try {
            new ChartView("SSTF", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 2020/5/28 余下两种情况及其按钮 

    @FXML
    public void generateAndStart(ActionEvent event) {
        generateRequest(event);
    }
}
