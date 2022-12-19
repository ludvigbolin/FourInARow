package com.example.demo;

import java.util.HashMap;

public class Graphics {
    HashMap<Node, Character> table;

    public Graphics(HashMap<Node, Character> table){
        this.table = table;
    }

    public void showBoard(){

        for (int y = 1; y <= 7; y++) {
            System.out.print("|");
            for (int x = 1; x <= 7; x++) {

                char c = table.get(new Node(x, y));

                /*if (c == '0'){
                    c = ' ';
                }*/
                System.out.print(" " + c);
            }
            System.out.println(" |");

        }

    }
}
