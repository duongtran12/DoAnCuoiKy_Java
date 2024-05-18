package Java_CK;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ManagerChatterJpanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

    ServerSocket srvSocket = null;
    BufferedReader bf = null;
    Thread t;

    /**
     * Create the panel.
     */
    public ManagerChatterJpanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 153, 255));
        add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        // Removed the JTextField for server port

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        // Hardcoded server port value
        int serverPort = 12360;
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
                Socket aStaffSocket = srvSocket.accept();
                if (aStaffSocket != null) {
                    bf = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
                    String S = bf.readLine();
                    int pos = S.indexOf(":");
                    String staffName = S.substring(pos + 1);
                    ChatPanel p = new ChatPanel(aStaffSocket, "Shop", staffName);
                    tabbedPane.add(staffName, p);
                    p.updateUI();
                }
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
