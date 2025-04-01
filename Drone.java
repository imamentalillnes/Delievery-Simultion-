public class Drone {
    
    // variables 
    private int startTime = 0;
    private int endTime = 0;
    private boolean finished = false;

    // Constructor 
    public Drone(int time){
        startTime = time;
        endTime = startTime + 60;
    }

    // getter for end time of drones
    public int getDroneEnd(){
        return endTime;
    }

    //setter for finished variable
    public void setFinished(){
        finished = true;
    }

    // getter for finished variable
    public boolean getFinished(){
        return finished;
    }

    // gives the total time the truck took to delievery the package
    public int totalTime(){
        return endTime - startTime;
    }
}
