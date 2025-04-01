import java.io.*;
import java.nio.channels.Pipe.SourceChannel;
import java.util.*;

public class Main{

    static ArrayList<Truck> compTrucks = new ArrayList<>();
    static ArrayList<Drone> compDrones = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException{
        
        //variables 
        File schedule = new File("train_schedule.txt");
        Scanner scnr = new Scanner(schedule);
        double PerbyTruck = 0.75;
        double PerbyDrone = 1 - PerbyTruck;
        int truckNums = 0;
        int truckStopped =0;
        int timer = 0;
        int packages = 0;
        int dronePackages = 0;
        int truckPackages = 0;
        int trainsCrossing = 0;
        boolean trainpassing = false;
        boolean trucksDone = false;
        ArrayList<Truck> stoppedTrucks = new ArrayList<>();
        // Creates train schedule
        ArrayList<Integer> trainSch = TrainSchedule(scnr);
        scnr.close();

        //main loop
        while(packages < 1500){

            //checks if the timer is divisible by 3
            if(timer % 3 == 0 && dronePackages < Math.ceil(1500 * PerbyDrone)){
                compDrones.add(new Drone(timer));
                dronePackages++;
            }

            // checks if the timer is divisible by 15
            if(timer % 15 == 0 && truckPackages < Math.ceil(1500 * PerbyTruck)){
                // makes new truck
                compTrucks.add(new Truck(truckNums, timer));
                truckNums++;
                truckPackages += 10;
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

            //searches through all the trucks to find the ones that are done
            for(Truck a : compTrucks){
                if(a.getTimeDone() == timer){
                    a.setFinished();
                    packages += 10;
                }
            }

            // Searches through all the drones for the ones that are done
            for(Drone a : compDrones){
                if(a.getDroneEnd() == timer){
                    a.setFinished();
                    packages += 1;
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

        //prints number of drones and Trucks
        int trucks = 0;
        for(Truck a : compTrucks){
            trucks++;
        }
        int drones = 0;
        for(Drone a : compDrones){
            drones++;
        }
        System.out.println("Trucks used: " + trucks);
        System.out.println("Drones used: " + drones);

        // Prints train schedule
        for(int i = 0; i < trainSch.size(); i++){
            if(i % 2 == 0){
                System.out.print("Train arrived at: " + trainSch.get(i));
            }
            else {
                System.out.println(" | Train departed at:" + trainSch.get(i));
            }
        }

        //Average truck Trip
        int aveTrip = 0;
        for(Truck a : compTrucks){
            aveTrip += a.totalTime();
        }
        System.out.println("Average Trip Time for Trucks: " + (aveTrip/compTrucks.size()));

        //Time all Trucks delievered Packages
        int truckFin = 0;
        for(Truck a : compTrucks){
            if(truckFin < a.getTimeDone()){
                truckFin = a.getTimeDone();
            }
        }
        System.out.println("Trucks Finished at: " + truckFin);

        //Drone trip Time Print
        System.out.println("Average Trip Time for Drone: " + compDrones.get(0).totalTime());

        //Time all Drones delievered Packages
        int droneFin = 0;
        for(Drone a : compDrones){
            if(droneFin < a.getDroneEnd()){
                droneFin = a.getDroneEnd();
            }
        }
        System.out.println("Drones Finished at: " + droneFin);

        //total time for all packages delievered
        if(truckFin > droneFin){
            System.out.println("All packages delievered by: " + truckFin);
        }
        else {
            System.out.println("All packages delievered by: " + droneFin);
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