package SJF;

import java.util.Scanner;

public class SJF {

    public  void SJF()
    {

        Scanner sc = new Scanner(System.in);
        System.out.println ("Number of process: ");
        int n = sc.nextInt();
        System.out.println ("context switching: ");
        int context = sc.nextInt();
        int[] pid = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completeTime = new int[n];
        int[] turnAround = new int[n];
        int[] waitTime = new int[n];
        int[] f = new int[n];

        int startTime = 0, totalprocess = 0;
        float avgwt = 0, avgta = 0;

        for(int i = 0; i < n; i++) {
            System.out.println ("process " + (i + 1) + " arrival time:");
            arrivalTime[i] = sc.nextInt();
            System.out.println ("process " + (i + 1) + " burst time:");
            burstTime[i] = sc.nextInt();
            pid[i] = i + 1;
            f[i] = 0;
        }


        while(true) {
            int c = n, min = Integer.MAX_VALUE;

            if (totalprocess == n) break;

            for (int i = 0; i < n; i++) {
                if ((arrivalTime[i] <= startTime) && (f[i] == 0) && (burstTime[i] < min)) {
                    min = burstTime[i];
                    c = i;
                }
            }
            if (c == n) startTime++;
            else {
                completeTime[c] = startTime + burstTime[c] + context;
                startTime += burstTime[c];
                turnAround[c] = completeTime[c] - arrivalTime[c];
                waitTime[c] = turnAround[c] - burstTime[c];
                f[c] = 1;
                pid[totalprocess] = c + 1;
                totalprocess++;
            }
        }

        System.out.println("\nprocess,arrival,burst,complete,turn,waiting");

        for(int i = 0; i < n; i++) {
            avgwt += waitTime[i];
            avgta += turnAround[i];
            System.out.println(pid[i] +
                    "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] +
                    "\t\t" + completeTime[i] + "\t\t" + turnAround[i] +
                    "\t\t" + waitTime[i]);
        }
        System.out.println ("\naverage turn around time is " + (avgta / n));
        System.out.println ("average waiting time is "+ (avgwt / n));
        sc.close();

        for(int i = 0; i < n; i++) {
            System.out.print(pid[i] + " ");
        }
    }
}