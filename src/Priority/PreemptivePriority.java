package Priority;

import java.util.Scanner;

public class PreemptivePriority {
    public void display()
    {
        //To know the number of the processes
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of the process ");
        int num = in.nextInt();
        System.out.print("\n");

        //To know the name of each process
        String name[] = new String[num];
        for (int i = 0; i < num; i++)
        {
            System.out.println("Enter the name of process  "
                    + (i + 1) + " ");
            name[i] = in.next();
        }
        System.out.print("\n");


        //To know the arrival time of each process
        int arrival[] = new int[num];
        for (int i = 0; i < num; i++)
        {
            System.out.println("Enter the arrival time of the process "
                    + (i + 1) + " ");
            arrival[i] = in.nextInt();
        }
        System.out.print("\n");


        //To know the burst time of each process
        int Brust[] = new int[num];
        for (int i = 0; i < num; i++)
        {
            System.out.println("Enter the Burst time of the process "
                    + (i + 1) + " ");
            Brust[i] = in.nextInt();
        }
        System.out.print("\n");


        //To know the priority time of each process
        int priority[] = new int[num + 1];
        for (int i = 0; i < num; i++)
        {
            System.out.println("Enter the priority of the process "
                    + (i + 1) + " ");
            priority[i] = in.nextInt();
        }
        System.out.print("\n");


        int Burst1[]=new int[num]; //assign the burst values here because it will change later
        for (int i = 0; i < num; i++)
        {
            Burst1[i] = Brust[i];
        }



        /* STARVATION SOLUTION

        int maxpriority = priority[0]; //get maximum priority
        for (int i = 1; i < (priority.length-1) ; i++)
        {
                if (priority[i] > maxpriority)
                {
                    maxpriority = priority[i];
                }

        }


        int maxarrival = arrival[0]; //get max arrival time
        for (int i = 1; i < arrival.length; i++)
        {
                if (arrival[i] > maxarrival)
                {
                    maxarrival = arrival[i];
                }
        }

        for (int i = maxarrival; i > 0 ; i--)
        {
            --maxpriority;
            if(maxpriority==0)
            {
                break;
            }
        }

        int maxpriority2 = priority[0];
        for (int i = 1; i < (priority.length-1) ; i++)
        {
            if (priority[i] > maxpriority2)
            {
                maxpriority2 = priority[i];
            }
        }

        for (int i = 0; i < (priority.length-1) ; i++)
        {
            if (maxpriority2 == priority[i])
            {
                priority[i] = maxpriority;
            }
        }

        */


        int wait_time[] = new int[num]; //to store the waiting time of the process
        int count = 0; //to know the number of the process that finish
        priority[num] = 1000; //store max priority in the last process
        for (int time = 0; count != num; time++)
        {
            int process = num; //to know the process that will enter
            for (int i = 0; i < num; i++)
            {
                if (arrival[i] <= time && priority[i] < priority[process] && Brust[i] > 0)
                    process = i;
            }
            Brust[process]--; //decreament the brust of the process during the time
            if (Brust[process] == 0) //it means that the process is finished
            {
                count++;
                int end = time + 1;  //store the end time of the process
                wait_time[process] = end - arrival[process] - Burst1[process];

            }
        }

        double total = 0; //to assign in it the summation of waiting time of each process
        System.out.println("Process name\t\tBurst time\t\tArrival time\t\tWaiting time\t\tPriority");
        for (int i = 0; i < num; i++)
        {
            {
                System.out.println(name[i] + "\t\t\t\t\t" + Burst1[i] + "\t\t\t\t\t" + arrival[i] + "\t\t\t\t\t" + wait_time[i] + "\t\t\t\t\t" + priority[i]);
                total = total + wait_time[i];
            }
        }
        //waiting time of each process --> (start - arrival) , average time = sum of total / no of processes
        System.out.print("\n");
        System.out.println("The average waiting time is "
                + (total / num)+"ms");
    }
}

