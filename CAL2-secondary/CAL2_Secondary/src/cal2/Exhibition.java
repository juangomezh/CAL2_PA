package cal2;

import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

public class Exhibition {
    private int capacity;
    private Queue queueWaiting,queueInside;
    private Semaphore sem;

    public Queue getQueueWaiting() {
        return queueWaiting;
    }

    public void setQueueWaiting(Queue queueWaiting) {
        this.queueWaiting = queueWaiting;
    }

    public Queue getQueueInside() {
        return queueInside;
    }

    public void setQueueInside(Queue queueInside) {
        this.queueInside = queueInside;
    }
    
    public Exhibition(int capacity, JTextField waiting, JTextField inside){
        this.capacity=capacity;
        sem=new Semaphore(capacity,true);
        queueWaiting=new Queue(100,waiting);
        queueInside=new Queue(capacity,inside);
    }
    public void enter(int v) throws InterruptedException{
        queueWaiting.push(v);
        try{ sem.acquire();} catch(InterruptedException e){}
        queueWaiting.popW(v);
        queueInside.pushIn(v);
    }

    public void leave(int v) throws InterruptedException{
        queueInside.pop(v);
        sem.release();
    }
    public void look(int v){
        try {
            Thread.sleep(2000+(int) (3000*Math.random()));
        } catch (InterruptedException e){}
    }
}
