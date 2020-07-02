package com.preibisch.pinna2d;

import com.preibisch.pinna2d.app.MyApp;
import javafx.application.Application;
import javafx.stage.Stage;

public class PinnaFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyApp app = new MyApp();
        app.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
