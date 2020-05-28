package grey.disk.ui;

import grey.disk.test.CSCAN;
import grey.disk.test.FCFS;
import grey.disk.test.SCAN;
import grey.disk.test.SSTF;
import grey.disk.util.RequestMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
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
    XYChart.Series<Number, Number> fcfsSeries = new XYChart.Series<>();
    XYChart.Series<Number, Number> sstfSeries = new XYChart.Series<>();
    XYChart.Series<Number, Number> scanSeries = new XYChart.Series<>();
    XYChart.Series<Number, Number> cscanSeries = new XYChart.Series<>();

    private RequestMaker maker = new RequestMaker();
    private ArrayList<Integer> requestList = null;
    private int firstLoc = -1;
    private int trackNum = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLineChart();
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
    public boolean checkCorrect() {
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
    public void clear() {
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
        lineChart.setPrefWidth(1600);
        lineChart.setPrefHeight(1000);
        lineChart.setCreateSymbols(false);
    }

    @FXML
    private void drawFCFS() {
        if (!checkCorrect())
            return;
        makeFcfsSeries();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        initLineChart();
        lineChart.getData().clear();
        if (!lineChart.getData().contains(fcfsSeries))
            lineChart.getData().add(fcfsSeries);
        try {
            new ChartView("FCFS", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeFcfsSeries() {
        //FCFS
        FCFS fcfs = new FCFS(firstLoc, trackNum, requestList);
        ArrayList<Integer> result = fcfs.run();
        fcfsSeries.setName("FCFS");
        for (int i = 0; i < result.size(); i++) {
            fcfsSeries.getData().add(new XYChart.Data<>(i + 1, result.get(i)));
        }
    }

    @FXML
    private void drawSSTF() {
        if (!checkCorrect())
            return;
        makeSstfSeries();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        initLineChart();
        lineChart.getData().clear();
        lineChart.getData().add(sstfSeries);
        try {
            new ChartView("SSTF", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeSstfSeries() {
        //SSTF
        SSTF sstf = new SSTF(firstLoc, trackNum, requestList);
        ArrayList<Integer> result = sstf.run();
        sstfSeries.setName("SSTF");
        for (int i = 0; i < result.size(); i++) {
            sstfSeries.getData().add(new XYChart.Data<>(i + 1, result.get(i)));
        }
    }

    // FIXME: 2020/5/28 SCAN CSCAN 都绘不出图
    @FXML
    public void drawSCAN() {
        if (!checkCorrect())
            return;
        makeScanSeries();
        lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        initLineChart();
        lineChart.getData().clear();
        lineChart.getData().add(scanSeries);
        try {
            new ChartView("SCAN", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeScanSeries() {
        //SCAN
        SCAN scan = new SCAN(firstLoc, trackNum, requestList, SCAN.POSITIVE_DIRECTION);
        ArrayList<Integer> result = scan.run();
        scanSeries = new XYChart.Series<>();
        scanSeries.setName("SCAN");
        for (int i = 0; i < result.size(); i++) {
            sstfSeries.getData().add(new XYChart.Data<>(i + 1, result.get(i)));
        }
    }

    @FXML
    public void drawCSCAN() {
        if (!checkCorrect())
            return;
        lineChart.getData().clear();
        lineChart.getData().add(getCscanSeries());
        try {
            new ChartView("CSCAN", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XYChart.Series<Number, Number> getCscanSeries() {
        //SCAN
        CSCAN cscan = new CSCAN(firstLoc, trackNum, requestList, SCAN.POSITIVE_DIRECTION);
        ArrayList<Integer> result = cscan.run();
        System.out.println(result);
        sstfSeries.setName("CSCAN");
        for (int i = 0; i < result.size(); i++) {
            cscanSeries.getData().add(new XYChart.Data<>(i + 1, result.get(i)));
        }
        return cscanSeries;
    }

    @FXML
    public void drawAll() {

    }


}
