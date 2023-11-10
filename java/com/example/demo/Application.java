package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application extends javafx.application.Application {
    private Board b;
    private String winningText;
    private boolean inTesting = false;
    private boolean isTextShowing = false;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Four In a Row");


        HBox root = new HBox();
        root.setMinWidth(20);
        root.setMinHeight(20);
        root.setSpacing(20);
        root.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, null, null))); // Look for a green box


        Scene scene = new Scene(root); // Create the scene with root element
        primaryStage.setScene(scene);

        GridPane gridPane = new GridPane();

        b = new Board();
        SaveBoard saveBoard = new SaveBoard("/Users/ludvigbolin/IdeaProjects/demo/src/main/java/com/example/demo/saveFile.txt");

        /*
        B.setNode(1,7, Color.CRIMSON);
        B.setNode(2,7,Color.CRIMSON);
        //B.setNode(3,7, Color.CRIMSON);
        B.setNode(3,7,Color.CRIMSON);
        B.setNode(5,7,Color.CRIMSON);
        B.setNode(6,7,Color.CRIMSON);
        //B.setNode(7,7,Color.CRIMSON);
        b.setNode(6,7,Color.CRIMSON);
        */

        CalcBoard cB = new CalcBoard(b);

        Background compBG = new Background(new BackgroundFill(Color.CRIMSON, null, null));
        Background humanBG = new Background(new BackgroundFill(Color.BLACK, null, null));
        Computer cp = new Computer(1.2F, compBG, humanBG, b); // 1 == offensive factor


        int test = cB.getValueInNode(new Node(4,7), humanBG);
        System.out.println("value in node " + test);

        cp.changeGravity(); //changed to gravity, always draw...
        boolean gravity = true;

        //TODO: implement winning message
        AtomicBoolean haveSomeoneWon = new AtomicBoolean(false);

        Text winning = new Text();
        winning.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        winning.setVisible(false);
        winning.setText(winningText);


        Node openingTurn = cp.makeTurn();
        b.setNode(openingTurn.x, openingTurn.y, Color.CRIMSON);

        Board lastTurnsBoard = new Board();
        for (Node n : b.getBoard().keySet()) {
            VBox box = b.getNode(n.x,n.y);

            box.setOnMouseClicked(event -> {

                if (inTesting) {
                    if (b.getNodeBackground(n.x,n.y).equals(new Background(new BackgroundFill(Color.WHITE, null, null)))){
                        b.setNode(n.x,n.y,Color.BLACK);
                    } else if (b.getNodeBackground(n.x,n.y).equals(new Background(new BackgroundFill(Color.BLACK, null, null)))) {
                        b.setNode(n.x,n.y,Color.CRIMSON);
                    } else {
                        b.setNode(n.x,n.y,Color.WHITE);
                    }
                    return;
                }
                // Update last turn board before new move is done
                lastTurnsBoard.writeOverBoard(b);

                if (b.getNodeBackground(n.x,n.y).equals(new Background(new BackgroundFill(Color.WHITE, null, null)))){

                    //gravity

                    int yCoord = n.y;
                    if (gravity) {
                        for (int y = 7; y >= 1; y--) {
                            if (b.getNodeBackground(n.x, y).equals(new Background(new BackgroundFill(Color.WHITE, null, null)))) {
                                yCoord = y;
                                break;
                            }
                        }
                    }

                    b.setNode(n.x,yCoord,Color.BLACK);

                    //DOESNT WORK
                    if (cB.isFourInARow(new Node(n.x,yCoord), humanBG)){
                        winningText = "YOU WIN";
                        winning.setVisible(true);

                        Set<Node> winningNodes = cB.getNodeAssociatedWith(new Node(n.x, yCoord), humanBG);
                        for (Node w: winningNodes) {
                            if (b.getNode(w.x, w.y).getBackground().equals(humanBG))
                                b.setNode(w.x, w.y, Color.GREEN);
                        }
                        haveSomeoneWon.set(true);

                    }

                    //B.setNode(n.x,n.y,Color.BLACK);

                    Node turn = cp.makeTurn();


                    //box.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
                    b.setNode(turn.x, turn.y, Color.CRIMSON);

                    //DOESNT WORK

                    if (cB.isFourInARow(turn, compBG)){
                        winningText = "COMPUTER WINS";
                        winning.setVisible(true);

                        Set<Node> winningNodes = cB.getNodeAssociatedWith(turn, compBG);
                        for (Node w: winningNodes) {
                            if (b.getNode(w.x, w.y).getBackground().equals(compBG))
                            b.setNode(w.x, w.y, Color.GREEN);
                        }
                        haveSomeoneWon.set(true);


                    }


                }

                System.out.println(n.x + ":" + n.y);
            });

            gridPane.add(box,n.x,n.y);

            if (haveSomeoneWon.get()){
                while(true){

                }
            }

        }

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Text restart = new Text(10, 50, "RESTART");
        restart.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        restart.setOnMouseClicked(event -> {
            b.resetBoard();
            Node openingTurn2 = cp.makeTurn();
            b.setNode(openingTurn2.x, openingTurn2.y, Color.CRIMSON);
            System.out.println("RESTART");
            winning.setVisible(false);
        });

        Text loadBoard = new Text(10, 50, "LOAD BOARD");
        loadBoard.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        loadBoard.setOnMouseClicked(event -> {
            try {
                b.writeOverBoard(saveBoard.readBoardToFile());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Text readBoard = new Text(10, 50, "SAVE BOARD");
        readBoard.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        readBoard.setOnMouseClicked(event -> {
            saveBoard.writeBoardToFile(b);
        });

        Text retryBoard = new Text(10, 50, "UNDO STEP");
        retryBoard.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        retryBoard.setOnMouseClicked(event -> {
            b.writeOverBoard(lastTurnsBoard);
        });

        Text statShower = new Text(10, 50, "SHOW STATS");
        statShower.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));


        statShower.setOnMouseClicked(event -> {
            for (Node n : b.getBoard().keySet()) {
                if (isTextShowing) {
                    b.removeNodeText(n.x, n.y);
                } else {
                    cp.makeTurn();
                    b.setNodeText(n.x, n.y, "" + cp.getHeatMap().get(n));
                }
            }
            if (isTextShowing) {
                isTextShowing = false;
            } else {
                isTextShowing = true;
            }
        });

        Text testMode = new Text(10, 50, "TESTING: OFF");
        testMode.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        testMode.setOnMouseClicked(event -> {
            if (inTesting) {
                testMode.setText("TESTING: OFF");
                inTesting = false;
            } else {
                testMode.setText("TESTING: ON");
                inTesting = true;
            }

        });

        root.getChildren().add(gridPane);

        VBox menuBox = new VBox();
        menuBox.setMinWidth(20);
        menuBox.setMinHeight(20);
        //menuBox.setSpacing(20);
        menuBox.getChildren().add(restart);
        menuBox.getChildren().add(loadBoard);
        menuBox.getChildren().add(readBoard);
        menuBox.getChildren().add(retryBoard);
        menuBox.getChildren().add(testMode);
        menuBox.getChildren().add(statShower);
        root.getChildren().add(menuBox);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}