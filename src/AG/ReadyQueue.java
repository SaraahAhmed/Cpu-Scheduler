package AG;

import java.util.ArrayList;

public class ReadyQueue {
    protected ArrayList<Process> queue = new ArrayList<>();

    /// popping process from ready queue
    public Process dequeue()
    {
        Process p = null;
        if (!isEmpty())
        {
            p = queue.get(0);
            queue.remove(p);
        }
        return p;
    }

    ///// inserting process in ready queue to based on priority value
    /*public void enqueue(Process process)
    {
        if (queue.isEmpty()) { queue.add(process);}
        else if(!this.contain(process)){
            int i;
            for (i = 0; i < queue.size(); i++) {
                if (queue.get(i).getPriority() > process.getPriority()) {
                    queue.add(i,process);
                    break;
                }
            }
            if(i == queue.size() ){queue.add(process);}
        }
    }*/

    public void addToTheEnd(Process process)
    {
         if(!this.contain(process)){
             queue.add(process);
            }
    }

    public void enqueue(Process process)
    {
        if (queue.isEmpty()) { queue.add(process);}
        else if(!this.contain(process)){
            int i;
            for (i = 0; i < queue.size(); i++) {
                if (queue.get(i).getArrivingTime() > process.getArrivingTime()) {
                    queue.add(i,process);
                    break;
                }
            }
            if(i == queue.size() ){queue.add(process);}
        }
    }




    private boolean contain(Process process){
        for(Process p : queue){ return p.getProcessID() == process.getProcessID(); }
        return false;
    }

    public int size() { return queue.size(); }
    public Boolean isEmpty() {return (queue.size() == 0);}
    public Process peek()
    {
        if(queue.isEmpty()){return null;}
        else{return queue.get(0);}
    }
}