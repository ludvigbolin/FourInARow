package com.example.demo;

import java.util.Scanner;

public class Human {

    Scanner scan;

    public Human() {
        scan = new Scanner(System.in);
    }

    public Node makeMove() {
        System.out.println("x coordinate: \n");
        // System.out.println();
        int x = scan.nextInt();
        System.out.println("y coordinate: \n");
        // System.out.println();
        int y = scan.nextInt();

        return new Node(x, y);
    }
}

