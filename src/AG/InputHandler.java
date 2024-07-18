package AG;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
public class InputHandler {
    private ArrayList<Process> processes;
    private Scanner input;

    public InputHandler(){
        processes = new ArrayList<>();
        input = new Scanner(System.in);
    }
    public ArrayList<Process> getProcesses()
    {
        System.out.println("Enter processes properties. ");
        int id, priority, aTime, bTime,qTime;
        do{

            System.out.print("Enter process ID: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter process id as an integer: ");
                input.next();
            }
            id = input.nextInt();

            System.out.print("Enter process priority: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter process priority as an integer: ");
                input.next();
            }
            priority = input.nextInt();

            System.out.print("Enter process arriving time: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter process arriving time as an integer: ");
                input.next();
            }
            aTime = input.nextInt();

            System.out.print("Enter process burst time: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter process burst time as an integer: ");
                input.next();
            }
            bTime = input.nextInt();

            System.out.print("Enter process quantum time: ");
            while(!input.hasNextInt()){
                System.out.print("Error! Enter process quantum time as an integer: ");
                input.next();
            }
            qTime = input.nextInt();


            processes.add(new Process(id, priority, aTime, bTime,qTime));
            System.out.print("Do you want to continue? (y/n): ");
        }while(input.next().equals("y"));
        return processes;
    }
    public static ArrayList<Process> cloneInputProcesses(ArrayList<Process> processes){
        ArrayList<Process> processesCpy = new ArrayList<>();
        for(Process p : processes){
            processesCpy.add(new Process(p.getProcessID(),p.getPriority(),p.getArrivingTime(),p.getBurstTime(),p.getQuantumTime()));
        }
        return  processesCpy;
    }
}