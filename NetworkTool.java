import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetworkTool {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping and Nmap Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 250);
        frame.setLayout(new FlowLayout());

        JLabel hostLabel = new JLabel("Enter Host:");
        JTextField hostField = new JTextField(20);
        frame.add(hostLabel);
        frame.add(hostField);

        JTextArea outputArea = new JTextArea(8, 35);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane);

        // Common method for executing the commands
        ActionListener runCommandAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = hostField.getText();
                String command = e.getActionCommand().equals("Ping Host") ? "ping -c 4 " : "nmap -A ";
                
                if (host.isEmpty()) {
                    outputArea.setText("Please enter a valid host.");
                    return;
                }

                JButton sourceButton = (JButton) e.getSource();
                sourceButton.setEnabled(false);
                
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        executeCommand(command + host, outputArea);
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Enable the button again
                        sourceButton.setEnabled(true);
                    }
                }.execute();
            }
        };

        JButton pingButton = new JButton("Ping Host");
        pingButton.addActionListener(runCommandAction);
        frame.add(pingButton);

        JButton nmapButton = new JButton("Run Nmap -A");
        nmapButton.addActionListener(runCommandAction);
        frame.add(nmapButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(closeButton);

        // Set frame visibility
        frame.setVisible(true);
    }

    private static void executeCommand(String command, JTextArea outputArea) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
            outputArea.setText(output.toString());
        } catch (Exception ex) {
            outputArea.setText("Error executing command: " + command);
            ex.printStackTrace();
        }
    }
}