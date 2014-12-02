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
public class DotsAndBoxes {
    State currentState;

    public DotsAndBoxes(int n, int m) throws Exception {
        currentState = new State(n, m);
        currentState.addAllEdges();
    }

    public State move(String m, State s) {
        State newS = s.clone();
        String[] nodes = m.split(" ");
        int source = Integer.parseInt(nodes[0]);
        int dest = Integer.parseInt(nodes[1]);

        newS.unmarkedEdges.remove(m);
        newS.markedEdges.add(m);

        if(dest-source==1) {
            String e1 = (source-newS.m)+" "+source, e2 =(source-newS.m)+" "+(source-newS.m+1), e3=(dest-newS.m)+" "+dest;
            if(newS.isValidEdge(e1) && newS.markedEdges.contains(e1) &&
                    newS.isValidEdge(e2) && newS.markedEdges.contains(e2) &&
                    newS.isValidEdge(e3) && newS.markedEdges.contains(e3)) {
                if(newS.chance==0) newS.score1+=1;
                else newS.score2+=1;
            }

            e1 = source+" "+(source+newS.m); e2 =(source+newS.m)+" "+(source+newS.m+1); e3=dest+" "+(dest+newS.m);
            if(newS.isValidEdge(e1) && newS.markedEdges.contains(e1) &&
                    newS.isValidEdge(e2) && newS.markedEdges.contains(e2) &&
                    newS.isValidEdge(e3) && newS.markedEdges.contains(e3)) {
                if(newS.chance==0) newS.score1+=1;
                else newS.score2+=1;
            }

            if(newS.score1==s.score1 && newS.score2==s.score2) newS.chance = (newS.chance + 1)%2;
        }
        else {
            String e1 = source+" "+(source+1), e2 =dest+" "+(dest+1), e3=(source+1)+" "+(dest+1);
            if(newS.isValidEdge(e1) && newS.markedEdges.contains(e1) &&
                    newS.isValidEdge(e2) && newS.markedEdges.contains(e2) &&
                    newS.isValidEdge(e3) && newS.markedEdges.contains(e3)) {
                if(newS.chance==0) newS.score1+=1;
                else newS.score2+=1;
            }

            e1 = (source-1)+" "+source; e2 =(dest-1)+" "+dest; e3=(source-1)+" "+(dest-1);
            if(newS.isValidEdge(e1) && newS.markedEdges.contains(e1) &&
                    newS.isValidEdge(e2) && newS.markedEdges.contains(e2) &&
                    newS.isValidEdge(e3) && newS.markedEdges.contains(e3)) {
                if(newS.chance==0) newS.score1+=1;
                else newS.score2+=1;
            }

            if(newS.score1==s.score1 && newS.score2==s.score2) newS.chance = (newS.chance + 1)%2;
        }

        return newS;
    }

    public boolean isGoal(State s) {
        return s.unmarkedEdges.isEmpty();
    }

    public void play() throws Exception {
        while(!isGoal(currentState)) {
            //System.out.println(currentState.score1+" "+currentState.score2);
            currentState.print();
            if(currentState.chance==0) {
                /*for (String s : currentState.unmarkedEdges) {
                    currentState = move(s, currentState);
                    System.out.println(s+" "+currentState.score1+" "+currentState.score2);
                    break;
                }*/
                Object[] res = beamSearch(currentState);
                currentState = move((String)res[1], currentState);
                //System.out.println(res[1]);
            }
            else {
            /*while(true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String mo = br.readLine().trim();
                if(currentState.isValidEdge(mo)) { currentState = move(mo, currentState); break; }
                System.out.println("Again");
            }*/
                int num = currentState.unmarkedEdges.size(); Random r = new Random(); num = r.nextInt(num);
                Iterator it = currentState.unmarkedEdges.iterator(); String s1 = "";
                for(int i=0;i<=num;i++) {
                    s1 = (String)it.next();
                } currentState = move(s1, currentState);
                /*for (String s : currentState.unmarkedEdges) {
                    currentState = move(s, currentState);
                    //System.out.println(s+" "+currentState.score1+" "+currentState.score2);
                    break;
                }*/
            }
        }
        System.out.println(currentState.score1 + " " + currentState.score2);
    }

    public boolean isThirdEdge(State s, String mo) {
        String[] nodes = mo.split(" ");
        int source = Integer.parseInt(nodes[0]);
        int dest = Integer.parseInt(nodes[1]);
        
        if(dest-source==1) {
            String e1 = (source-s.m)+" "+source, e2 =(source-s.m)+" "+(source-s.m+1), e3=(dest-s.m)+" "+dest;
            if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                int count=0;
                count+=s.markedEdges.contains(e3)?1:0;
                count+=s.markedEdges.contains(e2)?1:0;
                count+=s.markedEdges.contains(e1)?1:0;
                if(count==2) return true;
            }
            e1 = source+" "+(source+s.m); e2 =(source+s.m)+" "+(source+s.m+1); e3=dest+" "+(dest+s.m);
            if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                int count=0;
                count+=s.markedEdges.contains(e3)?1:0;
                count+=s.markedEdges.contains(e2)?1:0;
                count+=s.markedEdges.contains(e1)?1:0;
                if(count==2) return true;
            }
        }
        else {
            String e1 = source+" "+(source+1), e2 =dest+" "+(dest+1), e3=(source+1)+" "+(dest+1);
            if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                int count=0;
                count+=s.markedEdges.contains(e3)?1:0;
                count+=s.markedEdges.contains(e2)?1:0;
                count+=s.markedEdges.contains(e1)?1:0;
                if(count==2) return true;
            }
            e1 = (source-1)+" "+source; e2 =(dest-1)+" "+dest; e3=(source-1)+" "+(dest-1);
            if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                int count=0;
                count+=s.markedEdges.contains(e3)?1:0;
                count+=s.markedEdges.contains(e2)?1:0;
                count+=s.markedEdges.contains(e1)?1:0;
                if(count==2) return true;
            }
        }
        return false;
    }
    
    public int doubleCrosses(State s1, String mo) {
        int res=0;
        State s = s1.clone(); s = move(mo, s);
        for(String S : s.unmarkedEdges) {
            String[] nodes = S.split(" ");
            int source = Integer.parseInt(nodes[0]);
            int dest = Integer.parseInt(nodes[1]);
            if(dest-source==1) {
                int check=0;
                String e1 = (source-s.m)+" "+source, e2 =(source-s.m)+" "+(source-s.m+1), e3=(dest-s.m)+" "+dest;
                if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                    int count=0;
                    count+=s.markedEdges.contains(e3)?1:0;
                    count+=s.markedEdges.contains(e2)?1:0;
                    count+=s.markedEdges.contains(e1)?1:0;
                    if(count==3) check++;
                }
                e1 = source+" "+(source+s.m); e2 =(source+s.m)+" "+(source+s.m+1); e3=dest+" "+(dest+s.m);
                if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                    int count=0;
                    count+=s.markedEdges.contains(e3)?1:0;
                    count+=s.markedEdges.contains(e2)?1:0;
                    count+=s.markedEdges.contains(e1)?1:0;
                    if(count==3) check++;
                }
                if(check==2) res++;
                //System.out.println(check);
            }
            else {
                int check=0;
                String e1 = source+" "+(source+1), e2 =dest+" "+(dest+1), e3=(source+1)+" "+(dest+1);
                if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                    int count=0;
                    count+=s.markedEdges.contains(e3)?1:0;
                    count+=s.markedEdges.contains(e2)?1:0;
                    count+=s.markedEdges.contains(e1)?1:0;
                    if(count==3) check++;
                }
                e1 = (source-1)+" "+source; e2 =(dest-1)+" "+dest; e3=(source-1)+" "+(dest-1);
                if(s.isValidEdge(e1) && s.isValidEdge(e2) && s.isValidEdge(e3)) {
                    int count=0;
                    count+=s.markedEdges.contains(e3)?1:0;
                    count+=s.markedEdges.contains(e2)?1:0;
                    count+=s.markedEdges.contains(e1)?1:0;
                    if(count==3) check++;
                }
                if(check==2) res++;
            }
            
        } 
        return res;
    }
    
    public double getScore(State s, String mo) {
        if(s.unmarkedEdges.size()==0){
            return s.score1-s.score2;
        }
        
        boolean check=true; 
        for(String S : s.unmarkedEdges) if(!mo.equals(S)) check = check && isThirdEdge(s, S);
        if(!check && isThirdEdge(s, mo)) return -100;
        
        int dc=doubleCrosses(s, mo); int turns = s.n*s.m + dc; //System.out.println(turns+" "+mo);
        if(turns%2==1) {
            return 100;
        }
        else {
            return -100;
        }
    }


    public Object[] beamSearch(State s) {
        if(s.unmarkedEdges.isEmpty()) {
            return new Object[]{getScore(s,""),""};
        }
        ArrayList<node> children = new ArrayList<node>();
        for(String S : s.unmarkedEdges) {
            node n = new node(); n.score=getScore(s, S); n.move=S;
            children.add(n);
        }
        Collections.sort(children);
        ArrayList<Object[]> arr = new ArrayList<Object[]>();
        for(int i=0;i<Math.min(2,children.size());i++) {
            arr.add(beamSearch(move(children.get(i).move, s)));
        }
        double max = -1e10, sum=0; String move="";
        for(int i=0;i<Math.min(2, children.size());i++) {
            Object[] o = arr.get(i);
            double score = (double)o[0];
            String m = children.get(i).move;
            if(max<score) { max=score; move=m; }
            //System.out.println(children.size()+"a");
            sum+=score*1.0/children.size();
        }
        return new Object[]{sum, move};
    }
    
    class node implements Comparable<node>{
        double score;
        String move;
        
        public int compareTo(node n) {
            if(this.score<n.score) return -1;
            return 1;
        }
    }
}


