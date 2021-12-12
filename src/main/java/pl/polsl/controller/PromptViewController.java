package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class PromptViewController extends DataSetController {
    @FXML
    private TextArea promptTextArea;

    @FXML
    private Button promptButton;

    private String nextSceneFxmlPath;

    public void setNextSceneFxmlPath(String nextSceneFxmlPath) {
        this.nextSceneFxmlPath = nextSceneFxmlPath;
    }

    public void setButtonLabel(String buttonLabel) {
        this.promptButton.setText(buttonLabel);
    }

    public void setPromptLabel(String promptLabel) {
        this.promptTextArea.setText(promptLabel);
    }

    public void buttonPressed() {
        super.switchToScene(nextSceneFxmlPath);
    }
}
