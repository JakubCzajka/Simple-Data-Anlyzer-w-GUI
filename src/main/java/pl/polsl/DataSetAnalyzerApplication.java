package pl.polsl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.controller.DataSetController;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.basicmodel.BasicDataSet;
import pl.polsl.model.basicmodel.BasicDataSetManipulator;

import java.io.IOException;

public class DataSetAnalyzerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DataSetAnalyzerApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 356, 115);

        DataSetController controller = fxmlLoader.getController();
        DataSet dataSet = new BasicDataSet();
        DataSetManipulator dataSetManipulator = new BasicDataSetManipulator();

        controller.setStage(stage);
        controller.setDataSet(dataSet, dataSetManipulator);

        stage.setTitle("Data Analyzer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}