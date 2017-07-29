package com.pelucky.chat.chatClient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ClientGUI extends Application{

    public void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Chat Client");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        // gridPane.setGridLinesVisible(true);

        Label scenetitle = new Label("Welcome To ChatClient");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        HBox hbSceneTitle = new HBox();
        hbSceneTitle.setAlignment(Pos.CENTER);
        hbSceneTitle.getChildren().add(scenetitle);
        gridPane.add(hbSceneTitle, 0, 0, 2, 1);

        Label host = new Label("Host:");
        gridPane.add(host, 0, 1);
        TextField hostTextFiled = new TextField();
        gridPane.add(hostTextFiled, 1, 1);

        Label port = new Label("Port:");
        gridPane.add(port, 0, 2);
        TextField portTextFiled = new TextField();
        gridPane.add(portTextFiled, 1, 2);

        Label username = new Label("Username:");
        gridPane.add(username, 0, 3);
        TextField usernameTextFiled = new TextField();
        gridPane.add(usernameTextFiled, 1, 3);

        Button button = new Button("Login in");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(button);
        gridPane.add(hBox, 0, 5, 2, 1);
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                // TODO Auto-generated method stub
            }
        });

        Scene scene = new Scene(gridPane, 260, 220);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
