package grey.disk.ui;

import grey.disk.test.CSCAN;
import grey.disk.test.FCFS;
import grey.disk.test.SCAN;
import grey.disk.test.SSTF;
import grey.disk.util.RequestMaker;
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
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller {

    public GridPane gridPane;
    public TextField firstLocField;
    public TextField requestNumField;
    public TextField maxTrackNumField;
    public HBox chartBox;
    public TextArea infoText;

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    private RequestMaker maker;
    private static ArrayList<Integer> request;
    private Integer[] requestArray = new Integer[444];
    private int firstLoc = -1;
    private int trackNum = -1;

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        maker = new RequestMaker();
//        request = new ArrayList<>();
//
//        xAxis.setLabel("访问时间");
//        yAxis.setLabel("磁道号");
//    }

    public Controller() {
        maker = new RequestMaker();
        request = new ArrayList<>();

        xAxis.setLabel("访问时间");
        yAxis.setLabel("磁道号");
    }

    @FXML
    public void generateRequest() {
        int requestNum;
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
        maker.remakeRandomList();

        updateRequestList(maker.requestList);
    }

    @FXML
    public boolean allPrepared() {
        System.out.println("\nRequestMaker.requestList = " + maker.requestList+"\n");

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
        if (request.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "请求序列为空，未生成请求序列");
            alert.show();
            return false;
        }
        return true;
    }

    @FXML
    public void clear() {
        infoText.setText("");
        request.clear();
        firstLocField.setText("");
        requestNumField.setText("");
//        maxTrackNumField.setText("");
    }

    private void updateRequestList(ArrayList<Integer> list) {
        infoText.setText(list.toString());
        request = list;
        request.trimToSize();
//        requestArray = (Integer[]) list.toArray();
    }

    /**
     * 根据列表及名称返回一个线图，用于插入到LineChart中
     */
    private XYChart.Series<Number, Number> getSeries(String name, ArrayList<Integer> list) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, list.get(i)));
        }
        return series;
    }

    @FXML
    private void drawFCFS() {

        if (!allPrepared())
            return;

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        FCFS fcfs = new FCFS(firstLoc, trackNum, request);

        ArrayList<Integer> result = fcfs.getResultList();
        lineChart.getData().add(getSeries("FCFS", result));
        try {
            new ChartView("FCFS", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void drawSSTF() {

        if (!allPrepared())
            return;

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        SSTF sstf = new SSTF(firstLoc, trackNum, request);

        ArrayList<Integer> result = sstf.getResultList();
        lineChart.getData().add(getSeries("SSTF", result));
        try {
            new ChartView("SSTF", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawSCAN() {

        if (!allPrepared())
            return;

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        SCAN scan = new SCAN(firstLoc, trackNum, maker.requestList, SCAN.POSITIVE_DIRECTION);

        ArrayList<Integer> result = scan.getResultList();
        lineChart.getData().add(getSeries("SCAN", result));
        try {
            new ChartView("SCAN", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawCSCAN() {

        if (!allPrepared())
            return;

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        CSCAN cscan = new CSCAN(firstLoc, trackNum, request, CSCAN.POSITIVE_DIRECTION);

        ArrayList<Integer> result = cscan.getResultList();
        lineChart.getData().add(getSeries("CSCAN", result));

        try {
            new ChartView("CSCAN", lineChart).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawAll() {
        if (!allPrepared())
            return;

    }


}
