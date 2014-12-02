/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;
import java.util.*;
import java.io.*;
/**
 * Created by iiitd on 27/11/14.
 */
public class State {
    int n, m, score1, score2, chance;
    HashSet<String> markedEdges;
    HashSet<String> unmarkedEdges;

    public State(int _n, int _m) {
        n = _n;
        m = _m;
        score1 = score2 = chance = 0;
        markedEdges = new HashSet<String>();
        unmarkedEdges = new HashSet<String>();
    }

    public void addAllEdges() {
        for(int i=0;i<n*m;i++) {
            if(isValidEdge(i+" "+(i+1))) unmarkedEdges.add(i+" "+(i+1));
            if(isValidEdge(i+" "+(i+m))) unmarkedEdges.add(i+" "+(i+m));
        }
        for(String s:unmarkedEdges) {
            //System.out.println(s);
        }
        //System.out.println(unmarkedEdges.size());
    }

    public boolean isValidEdge(String s) {
        String[] nodes = s.split(" ");
        int source=Integer.parseInt(nodes[0]);
        int dest=Integer.parseInt(nodes[1]);

        return  source>=0 && dest>=0 && source<n*m && dest<n*m && ((dest-source==1 && dest/m==source/m)
                || (dest-source==m && dest%m==source%m));
    }

    public State clone() {
        State newS = new State(n,m);
        for(String s:this.unmarkedEdges) newS.unmarkedEdges.add(s);
        for(String s:this.markedEdges) newS.markedEdges.add(s);
        newS.score1 = score1;
        newS.score2 = score2;
        newS.chance = chance;

        return newS;
    }

    public boolean isEqual(State s) {
        if(n!=s.n || m!=s.m || score1!=s.score1 || score2!=s.score2 || chance!=s.chance
                || unmarkedEdges.size()!=s.unmarkedEdges.size() || markedEdges.size()!=s.markedEdges.size())
            return false;

        for(String edge : unmarkedEdges) if(!s.unmarkedEdges.contains(edge)) return false;

        return true;
    }
    
    public void print() {
        for(int i=0;i<n*m;) {
            for(int j=0;j<m;j++) {
                System.out.print(i);
                if(this.markedEdges.contains(i+" "+(i+1))) System.out.print("-");
                else System.out.print(" ");
                ++i;
            }
            System.out.println();
            for(int n=0;n<1;n++) {
                for(int j=i-m;j<i;j++) {
                    if(this.markedEdges.contains(j+" "+(j+m))) System.out.print("|");
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
        System.out.println("Player 1 :"+this.score1+" Player 2 :"+this.score2);
        System.out.println("----------------");
    }
}

