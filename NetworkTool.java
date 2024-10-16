import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkTool {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping and Nmap Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel hostLabel = new JLabel("Enter Host:");
        JTextField hostField = new JTextField(20);
        frame.add(hostLabel);
        frame.add(hostField);

        JTextArea outputArea = new JTextArea(8, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane);

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
}