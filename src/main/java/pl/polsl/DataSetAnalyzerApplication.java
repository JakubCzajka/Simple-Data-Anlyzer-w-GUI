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

/**
 * Main class of program.
 *
 * @version 1.2
 */
public class DataSetAnalyzerApplication extends Application {
    /**
     * Method that starts the first view in app.
     *
     * @param stage Program window.
     * @throws IOException If it fails to load given fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DataSetAnalyzerApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        DataSetController controller = fxmlLoader.getController();
        DataSet dataSet = new BasicDataSet();
        DataSetManipulator dataSetManipulator = new BasicDataSetManipulator();

        controller.setStage(stage);
        controller.setDataSet(dataSet, dataSetManipulator);

        stage.setTitle("Data Analyzer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that launches the application.
     *
     * @param args Program arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}