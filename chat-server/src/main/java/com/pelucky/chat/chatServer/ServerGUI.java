package com.pelucky.chat.chatServer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatServer");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        // gridPane.setGridLinesVisible(true);

        Label sceneTitle = new Label("Welcome To ChatServer");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        HBox hbSceneTitle = new HBox();
        hbSceneTitle.setAlignment(Pos.CENTER);
        hbSceneTitle.getChildren().add(sceneTitle);
        gridPane.add(hbSceneTitle, 0, 0, 2, 1);

        Label portLabel = new Label("Port:");
        gridPane.add(portLabel, 0, 1);
        TextField portTextFiled = new TextField();
        gridPane.add(portTextFiled, 1, 1);

        Label serverStatus = new Label("Status:");
        gridPane.add(serverStatus, 0, 2);
        final Text actiontarget = new Text("Chat server is not running...");
        gridPane.add(actiontarget, 1, 2);

        Button startBtn = new Button("Start Server");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(startBtn);
        gridPane.add(hbBtn, 0, 4, 2, 1);

        startBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Start the server now");
            }
        });

        final Separator separator = new Separator();
        separator.setValignment(VPos.CENTER);
        GridPane.setConstraints(separator, 0, 6);
        GridPane.setColumnSpan(separator, 2);
        gridPane.getChildren().add(separator);

        Label userListLabel = new Label("UserList:");
        userListLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        gridPane.add(userListLabel, 0, 8);

        TextArea userListTextArea = new TextArea();
        userListTextArea.setEditable(false);
        userListTextArea.setPrefHeight(240);
        gridPane.add(userListTextArea, 0, 10, 2, 1);

        Scene scene = new Scene(gridPane, 470, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}
