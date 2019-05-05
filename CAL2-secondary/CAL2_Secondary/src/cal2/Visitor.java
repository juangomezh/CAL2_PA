package cal2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Visitor extends Thread {
    private int id;
    private Exhibition ex;
    public Visitor(int id, Exhibition ex){
        this.id=id;
        this.ex=ex;
        this.start();
    }
    public void run(){
        try {sleep((int)(3000* Math.random()));} catch (InterruptedException e){}
        try {
            ex.enter(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        ex.look(id);
        try {
            ex.leave(id);
        } catch (InterruptedException ex) {
            Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
