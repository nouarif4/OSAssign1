

import java.util.List;
import java.util.Queue;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import java.util.LinkedList;
import java.util.ArrayList;

public class scheduler {

    List<process> processList;
    static int timeQuantum=2;
    int cpuTime;

    public scheduler() {
        this.processList = new ArrayList<>();
        this.cpuTime = 0;
    }

    public void addProcess(process p) {
        processList.add(p);
    }

    public void removeProcess(process p) {
        processList.remove(p);
    }


    public List<process> roundRobinScheduling() {
        List<process> completedProcesses = new ArrayList<>();
        Queue<process> readyQueue = new LinkedList<>();
    
        int currentTime = 0; // Track the current CPU time
    
        // Add processes to the ready queue as they arrive
        while (!processList.isEmpty() || !readyQueue.isEmpty()) {
            
            // Add processes that have arrived by currentTime
            for (process process : processList) {
                if (process.arrivalTime <= currentTime && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }
    
            if (readyQueue.isEmpty()) {
                currentTime++; // If there are no processes to run, advance time
                continue;
            }
    
            process currentProcess = readyQueue.poll();
    
            if (currentProcess.remainingTime > timeQuantum) {
                currentTime += timeQuantum;
                currentProcess.remainingTime -= timeQuantum;
            } else {
                currentTime += currentProcess.remainingTime;
                currentProcess.remainingTime = 0;
                currentProcess.completionTime = currentTime;
                currentProcess.calculateTurnaroundTime();
                currentProcess.calculateWaitingTime();
                completedProcesses.add(currentProcess);
            }
    
            // Add any new processes that have arrived by currentTime
            for (process process : processList) {
                if (process.arrivalTime <= currentTime && !readyQueue.contains(process)) {
                    readyQueue.add(process);
                }
            }
    
            // Remove completed processes from the processList
            processList.removeIf(process -> process.remainingTime == 0);
        }
    
        return completedProcesses;
    }
    

     public void calculateMetrics(List<process> completedProcesses, JTable table, JTextArea resultArea) {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int totalBurstTime = 0;

        for (process process : completedProcesses) {
            totalTurnaroundTime += process.turnAroundTime;
            totalWaitingTime += process.waitingTime;
            totalBurstTime += process.burstTime;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear previous table data

        for (process process : completedProcesses) {
            model.addRow(new Object[]{process.unigueID, process.arrivalTime, process.burstTime, process.turnAroundTime, process.waitingTime});
        }

        double avgTurnaroundTime = (double) totalTurnaroundTime / completedProcesses.size();
        double avgWaitingTime = (double) totalWaitingTime / completedProcesses.size();
        double cpuUtilization = ((double) totalBurstTime / cpuTime) * 100;

        resultArea.setText("Average Turnaround Time: " + avgTurnaroundTime + "\n" +
                "Average Waiting Time: " + avgWaitingTime + "\n" +
                "CPU Utilization: " + cpuUtilization + "%\n");
    }

    public void runSimulation(JTable table, JTextArea resultArea) {
        List<process> completedProcesses = roundRobinScheduling();
        calculateMetrics(completedProcesses, table, resultArea);
    }
}

