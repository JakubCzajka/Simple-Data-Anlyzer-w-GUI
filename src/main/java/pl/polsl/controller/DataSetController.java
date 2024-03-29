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

/**
 * Base class for all controllers in this project.
 *
 * @version 1.3
 */
@SuppressWarnings("ClassEscapesDefinedScope")
public class DataSetController {
    /**
     * User DataSet
     * @see DataSet
     */
    protected DataSet dataSet;
    /**
     * DataSetManipulator used to perform operations on dataSet
     */
    protected DataSetManipulator dataSetManipulator;
    /**
     * Program view
     */
    protected Stage stage;


    /**
     * Basic setter for dataSet and dataSetManipulator.
     *
     * @param dataSet User DataSet. If null, an empty BasicDataSet is created.
     * @param dataSetManipulator Chosen DataSetManipulator. If null, BasicDataSetManipulator.
     */
    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        this.dataSet = Objects.requireNonNullElseGet(dataSet, BasicDataSet::new);
        this.dataSetManipulator = Objects.requireNonNullElseGet(dataSetManipulator, BasicDataSetManipulator::new);
    }

    /**
     * Stage setter.
     *
     * @param stage Program view.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method that switches active views in program.
     *
     * @param fxmlPath Path to fxml that contains next active view.
     */
    protected void switchToScene(String fxmlPath) {
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

    /**
     * Method that shows a basic prompt and redirects user to next view.
     *
     * @param promptText Prompt text.
     * @param buttonText Button text.
     * @param nextSceneFxml Path to next view fxml file.
     */
    protected void showPrompt(String promptText, String buttonText, String nextSceneFxml){
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
