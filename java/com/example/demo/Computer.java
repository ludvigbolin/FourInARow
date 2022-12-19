package com.example.demo;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class Computer {
    private float offensiveness;
    private Background cpBG;
    private Background humanBG;
    private HashMap<Node, Integer> heatMap;
    private Board playingBoard;
    private Boolean hasGravity;

    public Computer(float offensiveness, Background cpBG, Background humanBG, Board playingBoard) {
        this.offensiveness = offensiveness; // 1 == equaly offensive as defensive
        this.cpBG = cpBG;
        this.humanBG = humanBG;
        heatMap = new HashMap<>();
        this.playingBoard = playingBoard;

        hasGravity = false;
    }

    public boolean changeGravity(){ //false as standard
        hasGravity = !hasGravity;
        return hasGravity;
    }

    private void calcOffensiveHeatMap() {
        CalcBoard calcB = new CalcBoard(playingBoard);

        for (Node n : playingBoard.getBoard().keySet()) {

            int value = 0;
            if (heatMap.get(n) != null)
                value = heatMap.get(n);

            value += calcB.getValueInNode(n, cpBG) * offensiveness; // may want floats instead
            System.out.print(value + ", ");
            heatMap.put(n, value);
        }
        System.out.println();
    }

    private void calcDefensiveHeatMap() {
        CalcBoard calcB = new CalcBoard(playingBoard);

        for (Node n : playingBoard.getBoard().keySet()) {

            int value = 0;
            if (heatMap.get(n) != null)
                value = heatMap.get(n);

            value += calcB.getValueInNode(n, humanBG); // may want floats instead
            //System.out.print(value + ", ");

            heatMap.put(n, value);
        }
        System.out.println();
    }

    private void addGravity(boolean hasGravity){
        if (!hasGravity)
            return;

        HashMap<Node, VBox> map = playingBoard.getBoard();
            //nodesToCalc.clear();


        HashSet<Node>  finalNodes = new HashSet<>();

        //System.out.println(map.keySet());
        for (int xVal = 1; xVal <= 7; xVal++) {
            for (int yVal = 7; yVal >= 1; yVal--) {
                //System.out.println(xVal + ":" + yVal);
                //System.out.println(map.get(new Node(xVal,yVal)));

                if (map.get(new Node(xVal,yVal)) == null)
                    continue;
                //check if null!
                Background bgOfNode = map.get(new Node(xVal, yVal)).getBackground();
                Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));
                if (bgOfNode.equals(whiteBG)) {
                    finalNodes.add(new Node(xVal,yVal));
                    break;
                }
            }
        }

        //for printing
       Iterator it = finalNodes.iterator();

        System.out.print("nodes to pick from: ");
        while (it.hasNext()) {
            System.out.print(it.next().hashCode() + ", ");
        }
        System.out.println("done");

        //TODO: 1 - kolla på detta
        HashMap<Node, Integer> extraHeatMap = new HashMap<>();

        for (Node n : finalNodes) {
            if (heatMap.containsKey(n)){
                extraHeatMap.put(n,heatMap.get(n));
            }
        }
        heatMap = extraHeatMap;
    }


    private void calcPlayAbove() {
        CalcBoard calcB = new CalcBoard(playingBoard);

        for (Node n : playingBoard.getBoard().keySet()) {

            Node nodeAbove = new Node(n.x,n.y-1);

            if (!playingBoard.getBoard().keySet().contains(nodeAbove)){
                continue;
            }
            int value = 0;
            if (heatMap.get(n) != null)
                value = heatMap.get(n);

            value -= calcB.getValueInNode(nodeAbove, humanBG);
            //System.out.print(value + ", ");
            heatMap.put(n, value);
        }
        System.out.println();
    }

    private void calcDoubleFIR(){

    }


    public Node makeTurn() {
        heatMap = new HashMap<>(); // reset heatMap
        calcDefensiveHeatMap();
        calcOffensiveHeatMap();
        calcPlayAbove();

        showHeatMap();

        //change to "fyraIRad" here
        addGravity(hasGravity); //removes all places which are invalid
        //CHECK IF 100% CORRECT

        int bestChoice = 0;
        ArrayList<Node> bestNodes = new ArrayList<>();

        int tempChoice = -1;
        for (Node n : heatMap.keySet()) {
            tempChoice = heatMap.get(n);

            if (tempChoice > bestChoice) {
                bestChoice = tempChoice;
                bestNodes.clear();
                bestNodes.add(n);
            } else if (tempChoice == bestChoice) {
                bestNodes.add(n);
            }
        }

        System.out.print("[ ");
        for (Node n : bestNodes) {
            System.out.print(n.x + ":" + n.y + " ");
        }
        System.out.println("]");

        int index = new Random().nextInt(bestNodes.size());

        return bestNodes.get(index);
    }

    public void showHeatMap() {

        for (int y = 1; y <= 7; y++) {
            System.out.print("|");
            for (int x = 1; x <= 7; x++) {

                int c = heatMap.get(new Node(x, y));

                System.out.printf("%3d", c);
            }
            System.out.println(" |");

        }
    }

    public HashMap<Node, Integer> getHeatMap() {
        return heatMap;
    }
}

