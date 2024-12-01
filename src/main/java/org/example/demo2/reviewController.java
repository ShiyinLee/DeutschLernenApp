package org.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class reviewController {
    @FXML private ImageView choice1;
    @FXML private ImageView choice2;
    @FXML private ImageView choice3;
    @FXML private ImageView choice4;
    @FXML private Label resultLabel;
    @FXML private Button nextButton;
    @FXML private Label germanWord;

    private List<Word> words = new ArrayList<>();
    private int currentIndex = 0;
    private int correctAnswers = 0;

    @FXML
    private void initialize() {
        // Same words as in LearnController
        words.add(new Word("verreisen", "trip", "Ich möchte gern verreisen,und zwar nach München.", "/images/verreisen.png"));
        words.add(new Word("meditieren", "meditation", "Richtig tief meditieren lernen, einen Roman schreiben.", "/images/meditieren.png"));
        words.add(new Word("spazieren", "take a walk", "Trotz des schlechten Wetters gingen wir spazieren.", "/images/spazieren.png"));
        words.add(new Word("surfen", "surf", "Viele junge leute surfen, wenn sie am Meer sind.", "/images/surfen.png"));
        words.add(new Word("küssen", "kiss", "Sie küsst ihn.", "/images/küssen.png"));
        words.add(new Word("Der Hund", "dog", "Mein Sohn spielt gerne mit dem Hund", "/images/hund.png"));
        words.add(new Word("kochen", "cook", "Du kochst zu fett", "/images/kochen.png"));
        words.add(new Word("Die Wissenschaftlerin", "scientist", "Die Wissenschaftler dringt in die Geheimnisse der Natur ein.", "/images/wissenschaftlerin.png"));
        words.add(new Word("Die Katze", "cat", "Die Katze krallt sich am Baum an.", "/images/katze.png"));
        words.add(new Word("Der Regenschirm", "umbrella", "Nehmen Sie einen Regenschirm mit! Es wird bald regen!", "/images/regenschirm.png"));

        showQuestion();
    }

    private void showQuestion() {
        // Current word to review
        Word currentWord = words.get(currentIndex);

        // Create a list of options (include the correct word and some distractors)
        List<Word> distractors = new ArrayList<>(words);
        distractors.remove(currentWord);
        Collections.shuffle(distractors); // Shuffle to randomize choices

        List<Word> options =new ArrayList<>();
        options.add(currentWord);
        options.addAll(distractors.subList(0,3));
        Collections.shuffle(options);

        germanWord.setText(currentWord.getGerman());
        // Set images for choices
        choice1.setImage(new Image(getClass().getResourceAsStream(options.get(0).getImagePath())));
        choice2.setImage(new Image(getClass().getResourceAsStream(options.get(1).getImagePath())));
        choice3.setImage(new Image(getClass().getResourceAsStream(options.get(2).getImagePath())));
        choice4.setImage(new Image(getClass().getResourceAsStream(options.get(3).getImagePath())));

        // Assign click event handlers to check answers
        assignClickHandler(choice1, options.get(0), currentWord);
        assignClickHandler(choice2, options.get(1), currentWord);
        assignClickHandler(choice3, options.get(2), currentWord);
        assignClickHandler(choice4, options.get(3), currentWord);

    }

    private void assignClickHandler(ImageView imageView, Word option, Word correctWord) {
        imageView.setOnMouseClicked((MouseEvent event) -> {
            if (option.equals(correctWord)) {
                correctAnswers++;
                resultLabel.setText("Correct!");
                nextQuestion(); // Load next question
            } else {
                resultLabel.setText("Wrong!");
                highlightCorrectAnswer(correctWord);
            }
        });
    }

    private void highlightCorrectAnswer(Word correctWord) {
        if (correctWord.getImagePath().equals(choice1.getImage().getUrl())) {
            choice1.setStyle("-fx-border-color: green; -fx-border-width: 3;");
        } else if (correctWord.getImagePath().equals(choice2.getImage().getUrl())) {
            choice2.setStyle("-fx-border-color: green; -fx-border-width: 3;");
        } else if (correctWord.getImagePath().equals(choice3.getImage().getUrl())) {
            choice3.setStyle("-fx-border-color: green; -fx-border-width: 3;");
        } else if (correctWord.getImagePath().equals(choice4.getImage().getUrl())) {
            choice4.setStyle("-fx-border-color: green; -fx-border-width: 3;");
        }

        // Add a "Got it" button to continue
        nextButton.setText("Got it");
        nextButton.setOnAction(e -> nextQuestion());
    }



    private void nextQuestion() {
        choice1.setStyle(null);
        choice2.setStyle(null);
        choice3.setStyle(null);
        choice4.setStyle(null);
        if (currentIndex < words.size() - 1) {
            currentIndex++;
            showQuestion();
        } else {
            resultLabel.setText("You scored: " + correctAnswers + "/" + words.size());
            nextButton.setOnAction(e -> loadScene("welcome.fxml"));
        }
    }

    private void loadScene(String fxml) {
        try {
            Stage stage = (Stage) nextButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/" + fxml)));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void GoToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
