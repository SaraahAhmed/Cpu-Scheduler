package AG;

public class Process {
    private int processID;
    private int priority;
    private int arrivingTime;
    private int burstTime;
    private int quantumTime;

    public Process() {
        this.processID = 0;
        this.priority = 0;
        this.arrivingTime = 0;
        this.burstTime = 0;
        this.quantumTime = 0;
    }

    public Process(int processID, int priority, int arrivingTime, int burstTime,int quantumTime ) {
        this.processID = processID;
        this.priority = priority;
        this.arrivingTime = arrivingTime;
        this.burstTime = burstTime;
        this.quantumTime = quantumTime;
    }

    public int getProcessID() {return processID;}
    public void setProcessID(int processID) {this.processID = processID;}

    public int getPriority() {return priority;}
    public void setPriority(int priority) {this.priority = priority;}

    public int getArrivingTime() {return arrivingTime;}
    public void setArrivingTime(int arrivingTime) {this.arrivingTime = arrivingTime;}

    public int getBurstTime() {return burstTime;}
    public void setBurstTime(int burstTime) {this.burstTime = burstTime;}

    public int getQuantumTime() {return quantumTime;}
    public void setQuantumTime(int quantumTime){this.quantumTime=quantumTime;}

    @Override
    public String toString() {
        return "{" +
                "processID = " + processID +
                ", priority = " + priority +
                ", arrivingTime = " + arrivingTime +
                ", burstTime = " + burstTime +
                '}';
    }

    public boolean reduceBurstTime(int time) {
        if(burstTime >= time) {
            burstTime = burstTime - time;
            return false;
        }
        else{ burstTime=0; return true; }
    }

    public boolean reduceQuantumTime(int time) {
        if(quantumTime >= time) {
            quantumTime = quantumTime - time;
            return false;
        }
        else{ quantumTime=0; return true;}
    }
    public void increaseQuantumTime(int time) {
        quantumTime = quantumTime + time;
    }
}