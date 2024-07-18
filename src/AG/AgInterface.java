package AG;

import java.util.ArrayList;

public class AgInterface {
    public void display(){
        InputHandler in = new InputHandler();
        float avgWait = 0;
        float avgTurnAround = 0;
        ArrayList<Process> processes = in.getProcesses();

        ArrayList<Process> processesCpy = InputHandler.cloneInputProcesses(processes);
        ArrayList<GanttRecord> gantt = new AgScheduling().getGantt(processesCpy);

        System.out.println("GANTT chart");
        for(int i = 0 ; i < gantt.size() ; i++){
            GanttRecord gR = gantt.get(i);
            if(i == 0)
                System.out.print(gR.toString());
            else
                System.out.print(" --P" + gR.getProcessId() + "-- |" + gR.getOutTime() +"|");
        }
        System.out.println();
        System.out.println();


        System.out.println("Completion Time");
        for(Process p : processes){
            int completionTime = AgScheduling.getCompletionTime(p, gantt);
            System.out.println("P" + p.getProcessID()+": t = " + completionTime);
        }

        System.out.println("Turn Around Time");
        for(Process p : processes){
            int turnAroundTime = AgScheduling.getTurnAroundTime(p, gantt);
            System.out.println("P" + p.getProcessID()+": t = " + turnAroundTime);
            avgTurnAround += turnAroundTime;
        }
        avgTurnAround = avgTurnAround / processesCpy.size();
        System.out.println("Waiting Time");
        for(Process p : processes){
            int waitingTime = AgScheduling.getWaitingTime(p, gantt);
            System.out.println("P" + p.getProcessID()+": t = " + waitingTime);
            avgWait += waitingTime;
        }
        if(avgWait!=0){avgWait = avgWait / processes.size();}


        //System.out.println("Average Turn Around Time : " + avgTurnAround);
        System.out.println("Average Waiting Time : " + avgWait);
    }
}
