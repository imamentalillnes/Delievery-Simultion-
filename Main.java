import java.io.*;
import java.util.*;

public class Main{

    static ArrayList<Truck> compTrucks = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException{
        
        //variables 
        File schedule = new File("train_schedule.txt");
        Scanner scnr = new Scanner(schedule);
        int truckNums = 0;
        int truckStopped =0;
        int timer = 0;
        int packages = 0;
        int prePackages = 0;
        int trainsCrossing = 0;
        boolean trainpassing = false;
        boolean trucksDone = false;
        ArrayList<Truck> stoppedTrucks = new ArrayList<>();
        // Creates train schedule
        ArrayList<Integer> trainSch = TrainSchedule(scnr);
        scnr.close();

        //main loop
        while(packages < 1500){

            // checks if the timer is divisible by 15
            if(timer % 15 == 0 && prePackages < 1500){
                // makes new truck
                compTrucks.add(new Truck(truckNums, timer));
                truckNums++;
                prePackages += 10;
            }

            // checks each truck to see when they pass the crossing
            for(Truck a : compTrucks){
                if(timer == a.getPassing()){
                    //checks if the train is passing
                    if(trainpassing || !stoppedTrucks.isEmpty()){
                        a.setStopped(timer);
                        stoppedTrucks.add(a);
                    }
                    else{
                        a.Passed(timer);
                    }
                }
            }

            // starts the stopped trucks back up
            if(!trainpassing && stoppedTrucks.size() != 0){
                stoppedTrucks.remove(0).setUnStopped(timer);
            }

            for(Truck a : compTrucks){
                if(a.getTimeDone() == timer){
                    a.setFinished();
                    packages += 10;
                }
            }

            //checks timmer compaired to the next time on the trainSch
            if(trainsCrossing < 24){
                if(timer == trainSch.get(trainsCrossing)){
                    if(trainpassing){
                        trainpassing = false;
                        System.out.println(timer + ": TRAIN leaves crossing");
                    }
                    else {
                        trainpassing = true;
                        System.out.println(timer + ": TRAIN arrives at crossing");
                    }
                    trainsCrossing++;
                }
            }

            // checks if all trucks are done and if breaks out of the loop
            for(Truck a : compTrucks){
                if(!a.getFinished()){
                    break;
                }
                else{
                    trucksDone = true;
                }
            }


            //timer up one min
            timer++;
        }

        // prints the stats of each truck
        System.out.println();
        System.out.println("TRUCK STATS:");
        for(Truck a : compTrucks){
            System.out.println(a.toString());
        }
    }

    // Creates a Schedule of all the times the train comes and goes
    public static ArrayList<Integer> TrainSchedule(Scanner scnr){
        
        // Variables
        ArrayList<Integer> trainSch = new ArrayList<>();
        int pre = 0;
        int count = 0;

        //grabs every integer from the document
        while(scnr.hasNextInt()){
            if(count % 2 == 0){
                pre = scnr.nextInt();
                trainSch.add(pre);
                count++;
            }
            else {
                pre = pre + scnr.nextInt();
                trainSch.add(pre);
                count++;
            }
        }

        return trainSch;
    }
}