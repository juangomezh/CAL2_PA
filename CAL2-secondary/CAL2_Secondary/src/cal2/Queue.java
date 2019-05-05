package cal2;

// The class Queue manages the waiting queues (actually lists, but it allow us 
// to represent the content of the process queues of the monitors with push and
// pop of integers. Every time that the queue is modified, it content is printed
// in the JTextField passed in the contructor as a parameter.

import javax.swing.JTextField;

public class Queue {
    int[] content;
    JTextField tf;
    private boolean closed=false, stop=false;
    int ptr;

    public boolean isClosed() {
        return closed;
    }

    public boolean isStop() {
        return stop;
    }
    
    public void setC(boolean c)
    {
        closed=c;
    }
    public void setS(boolean c)
    {
        stop=c;
    }
    public Queue(int capacity, JTextField tf){
        content = new int[capacity];
        ptr=0;
        this.tf=tf;
    }
    public synchronized void pushIn(int n) throws InterruptedException{
        if(stop || closed) wait();
        content[ptr]=n;
        ptr++;
        print();
    }
    public synchronized void push(int n) throws InterruptedException{
        if(stop) wait();
        content[ptr]=n;
        ptr++;
        print();
    }
    public synchronized void pop(int n) throws InterruptedException{
        if(stop) wait();
        boolean flag=false;
        for (int i=0;i<ptr-1;i++) {
            if (n==content[i]) flag=true;
            if (flag) content[i]=content[i+1];
        }
        ptr--;
        print();
    }
    public synchronized void popW(int n) throws InterruptedException{
        if(stop || closed) wait();
        boolean flag=false;
        for (int i=0;i<ptr-1;i++) {
            if (n==content[i]) flag=true;
            if (flag) content[i]=content[i+1];
        }
        ptr--;
        print();
    }
    public synchronized void emptyQueue(){ptr=0;}
    public synchronized int first(){return content[0];}
    public synchronized int noOfItems(){return ptr;}
    public synchronized String ptrString(){
        String str="";
        for (int i=0;i<ptr;i++) str=str+"-"+content[i];
        return str;
    }
    public synchronized void print(){ tf.setText(ptrString());}
    public synchronized void signal()
    {
        if(!closed || !stop)    notifyAll();
    }
}
