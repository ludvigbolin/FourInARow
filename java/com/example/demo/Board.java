package com.example.demo;


import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;

public class Board{
    private HashMap<Node, VBox> table;

    public Board(){
        table  =  new HashMap<>();
        fillEmptyBoard();
    }

    public void fillEmptyBoard() {
        for (int y = 1; y <= 7; y++){
            for (int x = 1; x <= 7; x++){
                VBox box = new VBox();

                box.setMinHeight(50);
                box.setMinWidth(50);
                box.setMaxHeight(50);
                box.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                table.put(new Node(x, y),box);
            }
        }
    }

    public void setNode(int x, int y, Color c) {
        VBox box = getNode(x,y);
        box.setBackground(new Background(new BackgroundFill(c, null, null)));
    }

    public VBox getNode(int x, int y) {
        return table.get(new Node(x,y));
    }

    public Background getNodeBackground(int x, int y) {
        VBox box = getNode(x,y);
        return box.getBackground();
    }

    public void setNodeText(int x, int y, String text) {
        VBox box = getNode(x,y);
        Text t = new Text(text);
        t.setFill(Color.DARKGRAY);
        box.getChildren().add(t);

    }

    public void removeNodeText(int x, int y) {
        VBox box = getNode(x,y);
        box.getChildren().clear();
    }

    public void resetBoard() {
        for (int y = 1; y <= 7; y++){
            for (int x = 1; x <= 7; x++){
                setNode(x,y,Color.WHITE);
            }
        }
    }

    public HashMap<Node, VBox> getBoard() {
        return table;
    }

    public void writeOverBoard(Board inputBoard) {
        for (int y = 1; y <= 7; y++){
            for (int x = 1; x <= 7; x++){
                //VBox inputVBox = inputBoard.getNode(x,y);
                Background inputBG = inputBoard.getNodeBackground(x,y);
                VBox box = getNode(x,y);
                box.setBackground(inputBG);

            }
        }
    }

}

