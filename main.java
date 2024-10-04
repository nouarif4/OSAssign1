import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;   
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[]args)
    { JFrame frame = new JFrame("Process Scheduling Simulator");
    frame.setSize(600, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container container = frame.getContentPane();
    container.setLayout(new BorderLayout());

    // Input Panel
    JPanel inputPanel = new JPanel(new GridLayout(3, 2));
    JTextField processIDField = new JTextField();
    JTextField arrivalTimeField = new JTextField();
    JTextField burstTimeField = new JTextField();

    inputPanel.add(new JLabel("Process ID:"));
    inputPanel.add(processIDField);
    inputPanel.add(new JLabel("Arrival Time:"));
    inputPanel.add(arrivalTimeField);
    inputPanel.add(new JLabel("Burst Time:"));
    inputPanel.add(burstTimeField);

    container.add(inputPanel, BorderLayout.NORTH);

    // Table for process display
    String[] columnNames = {"Process ID", "Arrival Time", "Burst Time", "Turnaround Time", "Waiting Time"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(tableModel);
    JScrollPane tableScrollPane = new JScrollPane(table);
    container.add(tableScrollPane, BorderLayout.CENTER);

    // Result area
    JTextArea resultArea = new JTextArea(5, 20);
    JScrollPane resultScrollPane = new JScrollPane(resultArea);
    container.add(resultScrollPane, BorderLayout.SOUTH);

    // Buttons
    JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("Add Process");
    JButton runButton = new JButton("Run Simulation");
    buttonPanel.add(addButton);
    buttonPanel.add(runButton);
    container.add(buttonPanel, BorderLayout.SOUTH);

    // Create Scheduler instance
    scheduler scheduler = new scheduler();  // Fixed time quantum of 2

    // Add button action
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = processIDField.getText();
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());

            scheduler.addProcess(new process(id, arrivalTime, burstTime));

            processIDField.setText("");
            arrivalTimeField.setText("");
            burstTimeField.setText("");
        }
    });

    // Run Simulation button action
    runButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            scheduler.runSimulation(table, resultArea);
        }
    });

    frame.setVisible(true);
}
}
    

