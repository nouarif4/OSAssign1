public class process {
    String unigueID;
    int burstTime;
    int remainingTime;
    int arrivalTime;
    int completionTime;
    int turnAroundTime;
    int waitingTime;
    int responseTime;

    public process(String id, int arrivalTime, int burstTime) {
        this.unigueID = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    public void calculateTurnaroundTime() {
        this.turnAroundTime = this.completionTime - this.arrivalTime;
    }   

    public void calculateWaitingTime() {
        this.waitingTime = this.turnAroundTime - this.burstTime;
    }

    
}
