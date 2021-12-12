package pl.polsl.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.DataSetAnalyzerApplication;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.basicmodel.BasicDataSet;
import pl.polsl.model.basicmodel.BasicDataSetManipulator;

import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("ClassEscapesDefinedScope")
public class DataSetController {
    protected DataSet dataSet;
    protected DataSetManipulator dataSetManipulator;
    protected Stage stage;


    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        this.dataSet = Objects.requireNonNullElseGet(dataSet, BasicDataSet::new);
        this.dataSetManipulator = Objects.requireNonNullElseGet(dataSetManipulator, BasicDataSetManipulator::new);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void switchToScene(String fxmlPath) {
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(DataSetAnalyzerApplication.class.getResource(fxmlPath));
        try {
            Parent root = null;

            root = fxmlLoader.load();
            DataSetController nextController = fxmlLoader.getController();
            nextController.setDataSet(dataSet, dataSetManipulator);
            nextController.setStage(stage);


            fxmlLoader.setController(nextController);

            Scene nextScene = new Scene(root);

            stage.setScene(nextScene);
        } catch (IOException e) {
            this.showPrompt("Critical app failure, please close app and report this to to creators.\n".concat(e.getMessage()), "OK", "main-view.fxml");
        }


        stage.show();
    }

    protected void showPrompt(String promptText, String buttonText, String nextSceneFxml){
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(DataSetAnalyzerApplication.class.getResource("prompt-view.fxml"));

        try {
            Parent root = fxmlLoader.load();

            PromptViewController nextController = fxmlLoader.getController();
            nextController.setDataSet(dataSet, dataSetManipulator);
            nextController.setStage(stage);
            nextController.setPromptLabel(promptText);
            nextController.setButtonLabel(buttonText);
            nextController.setNextSceneFxmlPath(nextSceneFxml);


            fxmlLoader.setController(nextController);

            Scene nextScene = new Scene(root);

            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }


        stage.show();
    }
}
