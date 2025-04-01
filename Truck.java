public class Truck {

    // Variables
    private boolean started = false;
    private boolean stopped = false;
    private boolean finished = false;
    private int truckNum;
    private int timeStart = 0;
    private int timeDone = 0;
    private int passCrossing = 0;
    private int trainStart = 0;

    public Truck(){
        //does nothing
    }

    // makes the truck
    public Truck(int num, int start){
        started = true;
        truckNum = num;
        timeStart = start;
        passCrossing = start + (3000/30);
        timeDone = start + (30000/30);
        System.out.println("TRUCK #" + truckNum + " begins journey");
    }
    
    // sets the stop
    public void setStopped(int timer){
        stopped = true;
        trainStart = timer;
        System.out.println(timer + ": TRUCK #" + truckNum + " waits at crossing");
            
    }

    // takes in when the truck is moving again
    public void setUnStopped(int timer){
        stopped = false;
        timeDone = timeDone + timer - trainStart;
        System.out.println(timer + ": TRUCK #" + truckNum + " crosses crossing");
    }

    //sets finished
    public void setFinished(){
        finished = true;
        started = false;
        System.out.println(timeDone + ": TRUCK #" + truckNum + " completes journey");
    }

    public boolean getFinished(){
        return finished;
    }

    public int getTimeDone(){
        return timeDone;
    }

    public int getPassing(){
        return passCrossing;
    }

    public boolean getStopped(){
        return stopped;
    }

    public void Passed(int timer){
        System.out.println(timer + ": TRUCK #" + truckNum + " crosses crossing");
    }

    // gives the total time the truck took to delievery the package
    public int totalTime(){
        return timeDone;
    }
}
