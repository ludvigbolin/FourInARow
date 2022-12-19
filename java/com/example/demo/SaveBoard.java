package com.example.demo;


import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.io.*;

public class SaveBoard{

    private static String filepath;
    private Background compBG = new Background(new BackgroundFill(Color.CRIMSON, null, null));
    private Background humanBG = new Background(new BackgroundFill(Color.BLACK, null, null));
    private Background winningBG = new Background(new BackgroundFill(Color.GREEN, null, null));

    public SaveBoard(String filepath_) {
        filepath = filepath_;
    }
    public void writeBoardToFile(Board b) {
        int[][] boardToInts = new int[7][7];

        for (int x = 0; x < boardToInts.length; x++) {
            for (int y = 0; y < boardToInts.length; y++) {
                int save;

                if (b.getNodeBackground(x+1, y+1).equals(compBG)) {
                    //computer == 1
                    save = 1;
                } else if (b.getNodeBackground(x+1, y+1).equals(humanBG)) {
                    //human == 2
                    save = 2;
                } else if (b.getNodeBackground(x+1, y+1).equals(winningBG)){
                    //green == 3
                    save = 3;
                } else {
                    //white == 0
                    save = 0;
                }

                boardToInts[x][y] = save;
            }
        }
        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(boardToInts);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Board readBoardToFile() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filepath);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        int[][] inputMatrix = (int[][]) objectIn.readObject();

        Board inputBoard = new Board();

        for (int x = 0; x < inputMatrix.length; x++) {
            for (int y = 0; y < inputMatrix.length; y++) {

                switch(inputMatrix[x][y]){
                    case (0): //is white
                        inputBoard.setNode(x+1,y+1, Color.WHITE);
                        break;
                    case (1): //is computer
                        inputBoard.setNode(x+1,y+1, Color.CRIMSON);
                        break;
                    case (2): //is human
                        inputBoard.setNode(x+1,y+1, Color.BLACK);
                        break;
                    case (3): //is green (has won)
                        inputBoard.setNode(x+1,y+1, Color.GREEN);
                        break;
                }

            }
        }
        System.out.println("The Object  was succesfully read from a file");
        return inputBoard;
    }

}
