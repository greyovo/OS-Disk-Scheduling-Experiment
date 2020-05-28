package grey.disk.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("/grey/main.fxml"));
        initChart();
        primaryStage.setTitle("磁盘调度算法实验");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    private void initChart(){
        // TODO: 2020/5/28 设置折线图
    }


    public static void main(String[] args) {
        launch(args);
    }
}
