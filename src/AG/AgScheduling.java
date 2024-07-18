package AG;

import java.util.ArrayList;

public class AgScheduling
{
    protected ArrayList<GanttRecord> gantt =new ArrayList<>();;
    protected int currentTime =0;
    protected ReadyQueue readyQueue =new ReadyQueue();


    public ArrayList<GanttRecord> getGantt(ArrayList<Process> processes)
    {
        processes=this.orderProcessesByArrivingTime(processes);
        Process runningProcess=null;
        currentTime=this.getFirstArrivingTime(processes);
        int in=currentTime,out=currentTime;
        ArrayList<Process>processes1=this.getFirstArrivingProcesses(processes);
        for (Process process : processes1) {
            readyQueue.enqueue(process);
            processes.remove(process);
        }

        while (!readyQueue.isEmpty())
        {
            if(runningProcess==null){
                if(readyQueue.isEmpty()){
                    Process p =processes.get(0);
                    readyQueue.enqueue(p);
                    processes.remove(p);
                }
                runningProcess=readyQueue.dequeue();
                in=runningProcess.getArrivingTime();
            }
            int time= (int) Math.ceil(runningProcess.getQuantumTime()*0.25);
            if(runningProcess.getBurstTime()<=time){
                currentTime+=runningProcess.getBurstTime();
                out=currentTime;
                GanttRecord gR = new GanttRecord(in, out, runningProcess.getProcessID());
                gantt.add(gR);
                runningProcess=null;
                continue;
            }
            in =currentTime;
            currentTime+=time;
            out=currentTime;
            runningProcess.reduceBurstTime(time);
            processes1=this.getCurrentArrivals(processes, currentTime);
            //System.out.println("current arrivals number: "+ processes1.size());
            for (Process process : processes1) {
                readyQueue.enqueue(process);
                processes.remove(process);
            }

            Process highestPriorityProcess = this.getHighestPriorityProcess(readyQueue.queue, runningProcess.getPriority());

            if(highestPriorityProcess!=null) {
                GanttRecord gR = new GanttRecord(in, out, runningProcess.getProcessID());
                gantt.add(gR);
                int remainingTime=runningProcess.getQuantumTime()-time;
                int addedTime= (int) Math.ceil( remainingTime/2.0);
                runningProcess.increaseQuantumTime(addedTime);
                readyQueue.addToTheEnd(runningProcess);
                runningProcess=highestPriorityProcess;
                in=out;
                continue;
            }
            time= (int) Math.ceil(runningProcess.getQuantumTime()*0.5)-(int) Math.ceil(runningProcess.getQuantumTime()*0.25);
            if(runningProcess.getBurstTime()<=time){
                currentTime+=runningProcess.getBurstTime();
                out=currentTime;
                GanttRecord gR = new GanttRecord(in, out, runningProcess.getProcessID());
                gantt.add(gR);
                runningProcess=null;
                continue;
            }
            ////second 25%
            currentTime+=time;
            out=currentTime;
            runningProcess.reduceBurstTime(time);
            processes1=this.getCurrentArrivals(processes, currentTime);
            for (Process process : processes1) {
                readyQueue.enqueue(process);
                processes.remove(process);
            }


            Process shortestJobProcess = this.getShortestJobProcess(readyQueue.queue, runningProcess.getBurstTime());

            if(shortestJobProcess!=null) {
                GanttRecord gR = new GanttRecord(in, out, runningProcess.getProcessID());
                gantt.add(gR);
                int remainingTime=runningProcess.getQuantumTime()-time;
                runningProcess.increaseQuantumTime(remainingTime);
                readyQueue.addToTheEnd(runningProcess);
                runningProcess=highestPriorityProcess;
                in=out;
                continue;
            }
            int extraTime=0;
            while (shortestJobProcess==null) {
                currentTime += 1;
                extraTime++;
                runningProcess.reduceBurstTime(1);
                ///scenario iv handling
                if (runningProcess.getBurstTime() == 0) {
                    out = currentTime;
                    GanttRecord gR = new GanttRecord(in, out, runningProcess.getProcessID());
                    gantt.add(gR);
                    runningProcess = null;
                    break;
                }

                processes1 = this.getCurrentArrivals(processes, currentTime);
                for (Process p : processes1) {
                    readyQueue.enqueue(p);
                    processes.remove(p);
                }
                for (Process arrived : readyQueue.queue) {
                    if (arrived.getBurstTime() < runningProcess.getBurstTime()) {
                        //assigning the process with shorter job to shortestJobProcess
                        out = currentTime;
                        GanttRecord gR = new GanttRecord(in, out, runningProcess.getProcessID());
                        gantt.add(gR);
                        ///scenario iii handle
                        int remainingTime = runningProcess.getQuantumTime() - time - extraTime;
                        runningProcess.increaseQuantumTime(remainingTime);
                        readyQueue.addToTheEnd(runningProcess);
                        in = out;
                        runningProcess = arrived;
                        shortestJobProcess = arrived;
                        break;
                    }
                }
            }
        }
        return gantt;
    }


    public static int getCompletionTime(Process p, ArrayList<GanttRecord> gantt) {
        int completionTime = 0;
        for(GanttRecord gR : gantt){
            if(gR.getProcessId() == p.getProcessID())
                completionTime = gR.getOutTime();
        }
        return completionTime;
    }

    public static int getTurnAroundTime(Process p, ArrayList<GanttRecord> gantt) {
        int completionTime = AgScheduling.getCompletionTime(p,gantt);
        return completionTime-p.getArrivingTime();
    }

    public static int getWaitingTime(Process p, ArrayList<GanttRecord> gantt) {
        int turnAroundTime = AgScheduling.getTurnAroundTime(p,gantt);
        return turnAroundTime-p.getBurstTime();
    }

    private ArrayList<Process> orderProcessesByArrivingTime(ArrayList<Process> processes){
        ArrayList<Process> newProcesses = new ArrayList<>();
        while(processes.size() != 0) {
            Process p = this.getFirstArrivingProcess(processes);
            processes.remove(p);
            newProcesses.add(p);
        }
        return newProcesses;
    }

    private Process getFirstArrivingProcess(ArrayList<Process> processes){
        int min = Integer.MAX_VALUE;
        Process process = null;
        for(Process p : processes){
            if(p.getArrivingTime() < min){
                min = p.getArrivingTime();
                process = p;
            }
        }
        return process;
    }

    private ArrayList<Process> getFirstArrivingProcesses(ArrayList<Process> processes){
        int min = this.getFirstArrivingTime(processes);
        ArrayList<Process> processes1 = new ArrayList<>();
        for(Process p : processes){
            if(p.getArrivingTime() == min){
                processes1.add(p);
            }
        }
        return processes1;
    }

    private ArrayList<Process> getCurrentArrivals(ArrayList<Process> processes, int time){
        ArrayList<Process> processes1 = new ArrayList<>();
        for(Process p : processes){
            if(p.getArrivingTime() <= time){
                processes1.add(p);
            }
        }
        return processes1;
    }

    private Process getHighestPriorityProcess(ArrayList<Process> processes, int priority){
        Process highestPriorityProcess=null;
        for(Process p : processes){
            if(p.getPriority() < priority){
                priority=p.getPriority();
                highestPriorityProcess=p;
            }
        }
        return highestPriorityProcess;
    }
    private Process getShortestJobProcess(ArrayList<Process> processes, int shortestJob){
        Process shortestJobProcess=null;
        for(Process p : processes){
            if(p.getBurstTime() < shortestJob){
                shortestJob=p.getBurstTime();
                shortestJobProcess=p;
            }
        }
        return shortestJobProcess;
    }

    private int getFirstArrivingTime(ArrayList<Process> processes){
        int min = Integer.MAX_VALUE;
        for(Process p : processes){
            if(p.getArrivingTime() < min){
                min = p.getArrivingTime();
            }
        }
        return min;
    }

}


