package com.objectdetection;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class ObjectDetectionApp extends Application {
    private final ImageView imageView = new ImageView();

    @Override
    public void start(Stage stage) {
        Button uploadButton = new Button("Select Image");
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                detectObjects(selectedFile);
            }
        });

        VBox layout = new VBox(10, uploadButton, imageView);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Object Detection App");
        stage.show();
    }

    private void detectObjects(File imageFile) {
        try {
            URL url = new URL("http://localhost:5001/detect");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/octet-stream");

            try (OutputStream os = conn.getOutputStream()) {
                Files.copy(imageFile.toPath(), os);
            }

            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                Image image = new Image(is);
                imageView.setImage(image);
            } else {
                System.err.println("Failed: HTTP error code " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}