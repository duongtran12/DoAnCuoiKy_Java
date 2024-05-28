package Java_CK;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ClientChaterJpanel extends JPanel {

	private static final long serialVersionUID = 1L;

	Socket mngSocket = null;
	String mngIP = "";
	int mngPort = 0;
	String staffName = "";
	BufferedReader bf = null;
	DataOutputStream os = null;
	OutputThread t = null;

	public ClientChaterJpanel() {
		setLayout(new BorderLayout(0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 7, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
				JButton btnConnect = new JButton("Chat Now");
				btnConnect.setForeground(new Color(204, 0, 0));
				btnConnect.setBackground(new Color(255, 255, 204));
				btnConnect.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mngPort = 12360;
						staffName = "you";
						try {
							mngSocket = new Socket("172.20.10.3", mngPort);
							if (mngSocket != null) {
								ChatPanel p = new ChatPanel(mngSocket, staffName, "Shop");
								add(p, BorderLayout.CENTER);
//						p.getTxtMessages().append("Manager is running");
								p.updateUI();

								bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream()));
								os = new DataOutputStream(mngSocket.getOutputStream());
								os.writeBytes("Staff:" + staffName);
								os.write(13);
								os.write(10);
								os.flush();
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				
				JPanel panel_3 = new JPanel();
				panel.add(panel_3);
				panel.add(btnConnect);
				
				JPanel panel_4 = new JPanel();
				panel.add(panel_4);
				
				JPanel panel_5 = new JPanel();
				panel.add(panel_5);
				
				JPanel panel_6 = new JPanel();
				panel.add(panel_6);
	}
}
