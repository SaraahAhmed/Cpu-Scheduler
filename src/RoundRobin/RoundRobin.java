package RoundRobin;

import java.util.Scanner;

public class RoundRobin {

    public  void RoundRobin() {
        Scanner in=new Scanner(System.in);

        System.out.println("Number of process: ");
        int num=in.nextInt();
        System.out.println ("context switching: ");
        int context = in.nextInt();

        //array to get bursts
        int[] b =new int[num];

        //get bursts
        for(int i = 0; i < num; i++){
            System.out.println("Burst for p" + (i + 1));
            b[i] = in.nextInt();
        }
        System.out.println("Enter quantum number: ");
        int q = in.nextInt();

        // Get waiting times
        int[] wait = new int[num];

        // Holds the total burst time after each loop
        int total;

        // Run the loop until each process burst time becomes 0
        do{
            for (int i = 0; i < num; i++) {
                if (b[i] > q){ //quantum is less than burst time
                    for(int j = 0; j < num; j++){
                        if(j != i && b[j] != 0)
                            wait[j] += q + context;
                    }
                    b[i] -= q;
                } else{
                    for (int j = 0; j < num; j++) {
                        if(j != i && b[j] != 0)
                            wait[j] += b[i];
                    }
                    b[i] = 0;
                }
            }
            // Reset the total burst time;
            total = 0;
            for (int i = 0; i < num; i++) {
                total += b[i];
            }
        } while(total != 0);// if the total burst time became 0 then stop

        System.out.println("Process\t\twaiting");

        //assign variable to get total waiting time
        float total_wait = 0;
        for(int i = 0; i < num; i++){
            System.out.println("p" + (i + 1) + "\t\t\t" + wait[i]);
            total_wait += wait[i];
        }
        System.out.println("Average waiting time is: " + (total_wait / num));
    }
}
