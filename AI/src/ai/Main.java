/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.*;
import java.util.*;
/**
 * Created by iiitd on 27/11/14.
 */
public class Main {
    public static void main(String[] args)  throws Exception {
        DotsAndBoxes game = new DotsAndBoxes(2, 3);
        game.play();
        /*State s = new State(3,3);
        s.markedEdges.add(0+" "+1);
        s.markedEdges.add(0+" "+3);
        s.markedEdges.add(1+" "+4);
        s.markedEdges.add(1+" "+2);
        s.markedEdges.add(2+" "+5);
        s.markedEdges.add(3+" "+6);
        s.markedEdges.add(3+" "+4);
        s.markedEdges.add(4+" "+7);
        s.markedEdges.add(5+" "+8);
        s.unmarkedEdges.add(4+" "+5);
        s.unmarkedEdges.add(7+" "+8);
        s.print();
        //System.out.println(game.getScore(s, 4+" "+5));
        //System.out.println(s.unmarkedEdges.size());
        Object[] res = game.beamSearch(s);
        //System.out.println(res[0]+" "+res[1]);*/
    }
}

