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
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public GridPane gridPane;
    public TextField firstLocField;
    public TextField requestNumField;
    public TextField maxTrackNumField;
    public TextArea requestInfo;
    @FXML
    public Label statement;
    public RadioButton positiveRadio;
    public RadioButton negativeRadio;
    public ToggleGroup toggleGroup = new ToggleGroup();

    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();

    private RequestMaker maker;
    private ArrayList<Integer> request;
    private int firstLoc = -1;
    private int trackNum = -1;
    private int direction = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maker = new RequestMaker();
        request = new ArrayList<>();

        xAxis.setLabel("访问顺序");
        yAxis.setLabel("磁道号");

        statement.setText("条件说明\n" +
                "· 请输入初始磁头位置（0~1500，默认为300）和请求序列的长度（100~1000之间且需为4的倍数，默认为400）\n" +
                "· 最大磁道数已设定为1500\n" +
                "· 先点击生成请求序列，然后再点击所需测试的算法生成折线图\n" +
                "· [正向]、[反向]指定磁头是往磁道号增大或减小的方向扫描，用于SCAN和C-SCAN\n" +
                "\n" +
                "请求序列的特点\n" +
                "· 50%位于 0～499，25%分布在 500～999，25%分布在 1000～1499。\n" +
                "\n" +
                "算法介绍\n" +
                "· FCFS：先来先服务算法\n" +
                "· SSTF：最短服务时间算法\n" +
                "· SCAN：扫描算法，又称电梯LOOK算法\n" +
                "· C-SCAN：循环扫描算法\n");

        //设置控制方向的单选按钮组，及其监听器
        positiveRadio.setSelected(true);
        positiveRadio.setToggleGroup(toggleGroup);
        positiveRadio.setUserData(0);
        negativeRadio.setToggleGroup(toggleGroup);
        negativeRadio.setUserData(1);
        toggleGroup.selectedToggleProperty().addListener(observable -> {
            if (toggleGroup.getSelectedToggle() != null) {
                direction = (int) toggleGroup.getSelectedToggle().getUserData();
            }
        });

    }

    public Controller() {

    }

    @FXML
    public void generateRequest() {
        int requestNum;
        try {
            requestNum = Integer.parseInt(requestNumField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "序列长度有误，请输入整数");
            alert.show();
            return;
        }
        if (requestNum < 100 || requestNum > 1000) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "序列长度不在规定范围(100~1000)");
            alert.show();
            return;
        }
        if (requestNum % 4 != 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "序列长度不是4的倍数");
            alert.show();
            return;
        }
        maker.setRequestNum(requestNum);
        maker.remakeRandomList();

        updateRequestList(maker.requestList);
    }

    @FXML
    public boolean allPrepared() {
        trackNum = Integer.parseInt(maxTrackNumField.getText());
        try {
            firstLoc = Integer.parseInt(firstLocField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "磁道号有误，请输入整数");
            alert.show();
            return false;
        }
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
        requestInfo.setText("");
        firstLocField.setText("");
        requestNumField.setText("");
        request.clear();
//        maxTrackNumField.setText("");
    }

    private void updateRequestList(ArrayList<Integer> list) {
        requestInfo.setText(list.toString());
        request = list;
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
        String info = String.format("平均磁头移动道数:%.2f", fcfs.getDistance() / result.size());

        try {
            new ChartView("FCFS", lineChart, info).start(new Stage());
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
        String info = String.format("平均磁头移动道数:%.2f", sstf.getDistance() / result.size());

        try {
            new ChartView("SSTF", lineChart, info).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawSCAN() {
        if (!allPrepared())
            return;

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        SCAN scan = new SCAN(firstLoc, trackNum, maker.requestList, direction);

        ArrayList<Integer> result = scan.getResultList();
        lineChart.getData().add(getSeries("SCAN", result));
        String info = String.format("平均磁头移动道数:%.2f", scan.getDistance() / result.size());

        try {
            new ChartView("SCAN", lineChart, info).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawCSCAN() {
        if (!allPrepared())
            return;

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        CSCAN cscan = new CSCAN(firstLoc, trackNum, request, direction);

        ArrayList<Integer> result = cscan.getResultList();
        lineChart.getData().add(getSeries("CSCAN", result));
        String info = String.format("平均磁头移动道数:%.2f", cscan.getDistance() / result.size());

        try {
            new ChartView("C-SCAN", lineChart, info).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void drawAll() {
        if (!allPrepared())
            return;

        FCFS fcfs = new FCFS(firstLoc, trackNum, request);
        SSTF sstf = new SSTF(firstLoc, trackNum, request);
        SCAN scan = new SCAN(firstLoc, trackNum, maker.requestList, direction);
        CSCAN cscan = new CSCAN(firstLoc, trackNum, request, direction);

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.getData().addAll(
                getSeries("FCFS", fcfs.getResultList()),
                getSeries("SSTF", sstf.getResultList()),
                getSeries("SCAN", scan.getResultList()),
                getSeries("C-SCAN", cscan.getResultList())
        );

        String info = "平均磁头移动道数\n" +
                String.format("FCFS:%.2f    ", fcfs.getDistance() / request.size()) +
                String.format("SSTF:%.2f    ", sstf.getDistance() / request.size()) +
                String.format("SCAN:%.2f    ", scan.getDistance() / request.size()) +
                String.format("C-SCAN:%.2f    ", cscan.getDistance() / request.size());

        try {
            new ChartView("四种算法对比", lineChart, info).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
