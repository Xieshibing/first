package ±àÒë¿ÎÉè;

import java.util.ArrayList;
import java.util.List;

public class WF {
    public String left="";
    public List<String> right=new ArrayList<String>();

    public WF(String string){
        this.left=string;
    }
    public void insert(String string){
        right.add(string);
    }

    public void print(){
        System.out.printf("%s->%s",left,right.get(0).toString());
        for(int i=1;i<right.size();i++){
            System.out.printf("|%s",right.get(i).toString());
            System.out.println();
        }
        System.out.println();
    }


}