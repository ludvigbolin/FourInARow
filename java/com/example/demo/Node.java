package com.example.demo;

import java.io.Serializable;

public class Node implements Serializable {
    int x;
    int y;


    public Node (int x, int y){
        this.x = x;
        this.y = y;

    }

    public int[] get() {
        int[] coord = new int[2];
        coord[0] = x;
        coord[1] = y;
        return coord;
    }

    public int hashCode(){
        return 10*x + y;
    }

    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass() && ((Node) obj).hashCode() == this.hashCode()){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new StringBuffer(" X : ").append(this.x).append(" Y : ").append(this.y).toString();
    }
}

