package com.example.demo;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CalcBoard {
    private HashMap<Node, VBox> map;

    public CalcBoard(Board board) {
        map = board.getBoard();
    }

    public Set<Node> getNodeAssociatedWith(Node node, Background bg) {
        Set<Node> returnNodes = new HashSet<>();

        returnNodes.addAll(getNodesHorizontal(node, bg));
        returnNodes.addAll(getNodesVertical(node, bg));
        returnNodes.addAll(getNodesDiagonalDown(node, bg));
        returnNodes.addAll(getNodesDiagonalUp(node, bg));

        return returnNodes;
    }

    public int getValueInNode(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        if (map.get(node).getBackground().equals(bg) || !map.get(node).getBackground().equals(whiteBG)) {
            return -1;
        }
/*
        int hor = 0; //weight(getHorizontalValue(node, bg));
        int ver = weight(getVerticalValue(node, bg));
        int dia1 = 0; //weight(getDiagonalDownValue(node, bg));
        int dia2 = 0; //weight(getDiagonalUpValue(node, bg));
*/
        int hor = weight(getHorizontalValue(node, bg));
        int ver = weight(getVerticalValue(node, bg));
        int dia1 = weight(getDiagonalDownValue(node, bg));
        int dia2 = weight(getDiagonalUpValue(node, bg));

        return hor + ver + dia1 + dia2;
    }

    private int weight(int input) {
        if (input ==  4) //win if possible
            return 1000;
        //return (int) Math.pow(input - 1, 2); //may change
        return (int) Math.pow(2, input - 1);
    }

    public boolean isFourInARow(Node node, Background bg) {
        //TODO: check if correct

        int valueIfFIR = 5;
        boolean hor = (getHorizontalValue(node, bg)) >= valueIfFIR;
        boolean ver = (getVerticalValue(node, bg)) >= valueIfFIR;
        boolean dia1 = (getDiagonalDownValue(node, bg)) >= valueIfFIR;
        boolean dia2 = (getDiagonalUpValue(node, bg)) >= valueIfFIR;

        System.out.println("Is four in a row?");
        System.out.println(hor);
        System.out.println("score: " + getHorizontalValue(node, bg));
        System.out.println(hor || ver || dia1 || dia2);
        return hor || ver || dia1 || dia2;
    }

    public int maximumInARow(Node node, Background bg) {
        
        return -1;
    }

    private int getHorizontalValue(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        int xCoordStart = corrXCoord - 3;
        int yCoordStart = corrYCoord;

        int points = 1;

        int tempPoints = 1;
        int canMakeFourInARow = 4; // 0 = correct
        boolean isFirstCorrectNodeRed = false;
        for (int i = 0; i < 7; i++) {
            System.out.println("main node: " + xCoordStart + ":" + yCoordStart);
            System.out.println(xCoordStart + i + ":" + yCoordStart);
            System.out.println("tempPoints: " + tempPoints);
            System.out.println("points: " + points);
            System.out.println("canMake4: " + canMakeFourInARow);



            Node currNode = new Node(xCoordStart + i, yCoordStart);

            if (map.get(currNode) == null) {

                // Vägg
                // System.out.println("vägg");
                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

            } else if (map.get(currNode).getBackground().equals(bg)) {

                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = true;
                }

                // rätt char
                // System.out.println("rätt char");
                tempPoints++;
                canMakeFourInARow--;

                if (canMakeFourInARow < 0 && isFirstCorrectNodeRed) {
                    tempPoints--;
                }

            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {

                // fel char
                // System.out.println("fel char");
                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

                tempPoints = 1;
                canMakeFourInARow = 4;

            } else {
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = false;
                }

                // tomrum
                canMakeFourInARow--;
            }

            if (tempPoints > points && canMakeFourInARow <= 0) {
                points = tempPoints;
                //tempPoints = 1;
            }

        }

        return points;

        /*
         * Ta fram alla 7 platser
         * Loopa igenom alla
         * Ifall det är en vägg, lägg nuvarandra största värde som bästa värde
         * Ifall rätt char -> +1
         * Ifall fel char -> lägg nuvarandra största värde som bästa värde, nuvarande
         * största värde = 1,
         * Ifall tom plats, gå vidare utan att göra något
         */
    }

    private int getVerticalValue(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        int xCoordStart = corrXCoord;
        int yCoordStart = corrYCoord - 3;

        int points = 1;

        int tempPoints = 1;
        int canMakeFourInARow = 4; // 0 = correct
        boolean isFirstCorrectNodeRed = false;
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);
            // System.out.println("tempPoints: " + tempPoints);
            // System.out.println("points: " + points);
            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart, yCoordStart + i);
            // System.out.println("currNode is " + currNode.x + ":" + currNode.y);

            if (map.get(currNode) == null) {
                // Vägg

                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

            } else if (map.get(currNode).getBackground().equals(bg)) {
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = true;
                }

                // rätt char
                // System.out.println("inne i rätt");

                tempPoints++;
                canMakeFourInARow--;

                if (canMakeFourInARow < 0 && isFirstCorrectNodeRed) {
                    tempPoints--;
                }

            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

                tempPoints = 1;
                canMakeFourInARow = 4;

            } else {
                // tomrum
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = false;
                }

                canMakeFourInARow--;
            }

            if (tempPoints > points && canMakeFourInARow <= 0) {
                points = tempPoints;
                //tempPoints = 1;
                // System.out.println("Inne i justera points");
            }

        }

        return points;
    }

    private int getDiagonalUpValue(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        int xCoordStart = corrXCoord - 3;
        int yCoordStart = corrYCoord - 3;

        int points = 1;

        int tempPoints = 1;
        int canMakeFourInARow = 4; // 0 = correct
        boolean isFirstCorrectNodeRed = false;
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);
            // System.out.println("tempPoints: " + tempPoints);
            // System.out.println("points: " + points);
            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart + i, yCoordStart + i);

            if (map.get(currNode) == null) {
                // Vägg

                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

            } else if (map.get(currNode).getBackground().equals(bg)) {
                // rätt char
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = true;
                }

                tempPoints++;
                canMakeFourInARow--;
                if (canMakeFourInARow < 0 && isFirstCorrectNodeRed) {
                    tempPoints--;
                }
            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

                tempPoints = 1;
                canMakeFourInARow = 4;

            } else {
                // tomrum
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = false;
                }

                canMakeFourInARow--;
            }

            if (tempPoints > points && canMakeFourInARow <= 0) {
                points = tempPoints;
                //tempPoints = 1;
            }

        }

        return points;
    }

    private int getDiagonalDownValue(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        int xCoordStart = corrXCoord - 3;
        int yCoordStart = corrYCoord + 3;

        int points = 1;

        int tempPoints = 1;
        int canMakeFourInARow = 4; // 0 = correct
        boolean isFirstCorrectNodeRed = false;
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);
            // System.out.println("tempPoints: " + tempPoints);
            // System.out.println("points: " + points);
            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart + i, yCoordStart - i);

            if (map.get(currNode) == null) {
                // Vägg

                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

            } else if (map.get(currNode).getBackground().equals(bg)) {
                // rätt char
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = true;
                }

                tempPoints++;
                canMakeFourInARow--;

                if (canMakeFourInARow < 0 && isFirstCorrectNodeRed) {
                    tempPoints--;
                }
            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (tempPoints > points && canMakeFourInARow <= 0)
                    points = tempPoints;

                tempPoints = 1;
                canMakeFourInARow = 4;

            } else {
                // tomrum
                if (canMakeFourInARow == 4) {
                    isFirstCorrectNodeRed = false;
                }

                canMakeFourInARow--;
            }

            if (tempPoints > points && canMakeFourInARow <= 0) {
                points = tempPoints;
                //tempPoints = 1;
            }

        }

        return points;
    }

    private Set<Node> getNodesHorizontal(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        System.out.println("Coord is " + corrXCoord + ":" + corrYCoord);

        int xCoordStart = corrXCoord - 3;
        int yCoordStart = corrYCoord;

        Set<Node> nodes = new HashSet<>();

        Set<Node> tempNodes = new HashSet<>();

        int canMakeFourInARow = 4; // 0 = correct
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);

            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart + i, yCoordStart);
            /*
            System.out.println("currNode is " + currNode.x + ":" + currNode.y);

            System.out.println("tempNodes are ");
            for (Node n : tempNodes) {
                System.out.println(n.x + ":" + n.y);
            }

            System.out.println("nodes are ");
            for (Node n : nodes) {
                System.out.println(n.x + ":" + n.y);
            }
            */
            if (map.get(currNode) == null) {
                // Vägg

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();

            } else if (map.get(currNode).getBackground().equals(bg)) {
                // rätt char
                System.out.println("inne i rätt");

                tempNodes.add(currNode);

                canMakeFourInARow--;
            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();
                canMakeFourInARow = 4;

            } else {
                // tomrum

                tempNodes.add(currNode);
                canMakeFourInARow--;
            }

            if (/*tempPoints > points && */canMakeFourInARow <= 0) {
                nodes.addAll(tempNodes);
                //tempPoints = 1;
                System.out.println("Inne i justera points");
            }

        }

        return nodes;
    }

    private Set<Node> getNodesVertical(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        System.out.println("Coord is " + corrXCoord + ":" + corrYCoord);

        int xCoordStart = corrXCoord;
        int yCoordStart = corrYCoord - 3;

        Set<Node> nodes = new HashSet<>();

        Set<Node> tempNodes = new HashSet<>();

        int canMakeFourInARow = 4; // 0 = correct
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);

            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart, yCoordStart + i);
            /*
            System.out.println("currNode is " + currNode.x + ":" + currNode.y);

            System.out.println("tempNodes are ");
            for (Node n : tempNodes) {
                System.out.println(n.x + ":" + n.y);
            }

            System.out.println("nodes are ");
            for (Node n : nodes) {
                System.out.println(n.x + ":" + n.y);
            }
            */
            if (map.get(currNode) == null) {
                // Vägg

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();

            } else if (map.get(currNode).getBackground().equals(bg)) {
                // rätt char
                System.out.println("inne i rätt");

                tempNodes.add(currNode);

                canMakeFourInARow--;
            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();
                canMakeFourInARow = 4;

            } else {
                // tomrum

                tempNodes.add(currNode);
                canMakeFourInARow--;
            }

            if (/*tempPoints > points && */canMakeFourInARow <= 0) {
                nodes.addAll(tempNodes);
                //tempPoints = 1;
                System.out.println("Inne i justera points");
            }

        }

        return nodes;
    }

    private Set<Node> getNodesDiagonalDown(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        System.out.println("Coord is " + corrXCoord + ":" + corrYCoord);

        int xCoordStart = corrXCoord - 3;
        int yCoordStart = corrYCoord - 3;

        Set<Node> nodes = new HashSet<>();

        Set<Node> tempNodes = new HashSet<>();

        int canMakeFourInARow = 4; // 0 = correct
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);

            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart + i, yCoordStart + i);
            /*
            System.out.println("currNode is " + currNode.x + ":" + currNode.y);

            System.out.println("tempNodes are ");
            for (Node n : tempNodes) {
                System.out.println(n.x + ":" + n.y);
            }

            System.out.println("nodes are ");
            for (Node n : nodes) {
                System.out.println(n.x + ":" + n.y);
            }
            */
            if (map.get(currNode) == null) {
                // Vägg

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();

            } else if (map.get(currNode).getBackground().equals(bg)) {
                // rätt char
                System.out.println("inne i rätt");

                tempNodes.add(currNode);

                canMakeFourInARow--;
            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();
                canMakeFourInARow = 4;

            } else {
                // tomrum

                tempNodes.add(currNode);
                canMakeFourInARow--;
            }

            if (/*tempPoints > points && */canMakeFourInARow <= 0) {
                nodes.addAll(tempNodes);
                //tempPoints = 1;
                System.out.println("Inne i justera points");
            }

        }

        return nodes;
    }

    private Set<Node> getNodesDiagonalUp(Node node, Background bg) {
        Background whiteBG = new Background(new BackgroundFill(Color.WHITE, null, null));

        int corrXCoord = node.get()[0];
        int corrYCoord = node.get()[1];

        System.out.println("Coord is " + corrXCoord + ":" + corrYCoord);

        int xCoordStart = corrXCoord - 3;
        int yCoordStart = corrYCoord + 3;

        Set<Node> nodes = new HashSet<>();

        Set<Node> tempNodes = new HashSet<>();

        int canMakeFourInARow = 4; // 0 = correct
        for (int i = 0; i < 7; i++) {
            // System.out.println(xCoordStart + i + ":" + yCoordStart);

            // System.out.println("canMake4: " + canMakeFourInARow);

            Node currNode = new Node(xCoordStart + i, yCoordStart - i);
            /*
            System.out.println("currNode is " + currNode.x + ":" + currNode.y);

            System.out.println("tempNodes are ");
            for (Node n : tempNodes) {
                System.out.println(n.x + ":" + n.y);
            }

            System.out.println("nodes are ");
            for (Node n : nodes) {
                System.out.println(n.x + ":" + n.y);
            }
            */
            if (map.get(currNode) == null) {
                // Vägg

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();

            } else if (map.get(currNode).getBackground().equals(bg)) {
                // rätt char
                System.out.println("inne i rätt");

                tempNodes.add(currNode);

                canMakeFourInARow--;
            } else if (!map.get(currNode).getBackground().equals(whiteBG)) {
                // fel char

                if (/*tempPoints > points && */canMakeFourInARow <= 0)
                    nodes.addAll(tempNodes);

                tempNodes.clear();
                canMakeFourInARow = 4;

            } else {
                // tomrum

                tempNodes.add(currNode);
                canMakeFourInARow--;
            }

            if (/*tempPoints > points && */canMakeFourInARow <= 0) {
                nodes.addAll(tempNodes);
                //tempPoints = 1;
                System.out.println("Inne i justera points");
            }

        }

        return nodes;
    }

    public void heatBoard(Background bg) {
        HashMap<Node, Integer> heatmap = new HashMap<>();

        for (Node n : map.keySet()) {

            heatmap.put(n, getValueInNode(n, bg));
        }

        for (int y = 1; y <= 7; y++) {
            System.out.print("|");
            for (int x = 1; x <= 7; x++) {

                String printC = heatmap.get(new Node(x, y)) + "";

                /*
                 * if (c == '0'){
                 * c = ' ';
                 * }
                */
                System.out.printf("%2s", printC);
            }
            System.out.println(" |");

        }
    }
}

