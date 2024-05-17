package Java_CK;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ManagerChatterJpanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;
    private JTextField txtServerPort;
    private JTabbedPane tabbedPane;

    ServerSocket srvSocket = null;
    Thread t;

    public ManagerChatterJpanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 153, 255));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("Manager Port: ");
        lblNewLabel.setForeground(new Color(204, 0, 0));
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(lblNewLabel);

        txtServerPort = new JTextField();
        txtServerPort.setBackground(new Color(0, 153, 255));
        txtServerPort.setForeground(new Color(204, 0, 0));
        txtServerPort.setText("12345");
        panel.add(txtServerPort);
        txtServerPort.setColumns(10);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        int serverPort = Integer.parseInt(txtServerPort.getText());
        try {
            srvSocket = new ServerSocket(serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            try {
                // Accept a connection from a staff member
                Socket aStaffSocket = srvSocket.accept();
                if (aStaffSocket != null) {
                    BufferedReader bf = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
                    String message = bf.readLine();
                    if (message != null && !message.isEmpty()) {
                        // Extract the sender and content from the message
                        int pos = message.indexOf(":");
                        if (pos != -1) {
                            String sender = message.substring(0, pos);
                            String content = message.substring(pos + 1);

                            // Create a new ChatPanel and add it to the tabbedPane
                            ChatPanel panel = new ChatPanel(aStaffSocket, sender, "Manager");
                            tabbedPane.addTab(sender, panel);
                            panel.getTxtMessages().append(content);
                            panel.updateUI();
                        }
                    }
                }
                Thread.sleep(100);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
