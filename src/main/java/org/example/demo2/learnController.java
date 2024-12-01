package org.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class learnController {
    @FXML
    private Label germanWordLabel;
    @FXML
    private Label englishTranslationLabel;
    @FXML
    private Label sentenceLabel;
    @FXML
    private ImageView wordImage;
    @FXML
    private Button nextButton;

    private List<Word> words = new ArrayList<>();
    private int currentIndex = 0;

    @FXML
    private void initialize() {
        // Add words to the list
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

        showWord();

        nextButton.setOnAction(e -> {
            if (currentIndex < words.size() - 1) {
                currentIndex++;
                showWord();
            } else {
                nextButton.setText("Completed all! Go to Review");
                nextButton.setOnAction(event -> loadScene("review.fxml"));
            }
        });
    }

    private void showWord() {
        Word word = words.get(currentIndex);
        germanWordLabel.setText(word.getGerman());
        englishTranslationLabel.setText(word.getEnglish());
        sentenceLabel.setText(word.getSentence());
        wordImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(word.getImagePath()))));
        //wordImage.setImage(new Image("/Users/shiyinli/java1/demo2/images/treten.png"));
    }

    private void loadScene(String fxml) {
        try {
            Stage stage = (Stage) nextButton.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml))));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    class Word {
        private String german;
        private String english;
        private String sentence;
        private String imagePath;

        public Word(String german, String english, String sentence, String imagePath) {
            this.german = german;
            this.english = english;
            this.sentence = sentence;
            this.imagePath = imagePath;
        }

        public String getGerman() {
            return german;
        }

        public String getEnglish() {
            return english;
        }

        public String getSentence() {
            return sentence;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

