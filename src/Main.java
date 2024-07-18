import AG.*;
import AG.Process;
import Priority.PreemptivePriority;
import RoundRobin.RoundRobin;
import SJF.SJF;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AgInterface agInterface=new AgInterface();
        agInterface.display();

        //RoundRobin roundRobin=new RoundRobin();
        //roundRobin.RoundRobin();

        //SJF sjf=new SJF();
        //sjf.SJF();

        //PreemptivePriority preemptivePriority=new PreemptivePriority();
        //preemptivePriority.display();

    }
}