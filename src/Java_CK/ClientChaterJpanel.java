package Java_CK;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ClientChaterJpanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtStaff;
	private JTextField txtServerIP;
	private JTextField txtServerPort;

	Socket mngSocket = null;
	String mngIP = "";
	int mngPort = 0;
	String staffName = "";
	BufferedReader bf = null;
	DataOutputStream os = null;
	OutputThread t = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame("Client Chatter");
					ClientChater panel = new ClientChater();
					frame.setContentPane(panel);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setSize(612, 300);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the panel.
	 */
	public ClientChaterJpanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 153, 255));
		panel.setBorder(new TitledBorder(null, "Staff and Server Info.", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 7, 0, 0));

		JLabel lblNewLabel = new JLabel("Staff: ");
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel);

		txtStaff = new JTextField();
		panel.add(txtStaff);
		txtStaff.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Mng IP: ");
		lblNewLabel_1.setForeground(new Color(204, 0, 0));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);

		txtServerIP = new JTextField();
		panel.add(txtServerIP);
		txtServerIP.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Port: ");
		lblNewLabel_2.setForeground(new Color(204, 0, 0));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_2);

		txtServerPort = new JTextField();
		panel.add(txtServerPort);
		txtServerPort.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setForeground(new Color(204, 0, 0));
		btnConnect.setBackground(new Color(255, 255, 204));
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mngIP = txtServerIP.getText();
				mngPort = Integer.parseInt(txtServerPort.getText());
				staffName = txtStaff.getText();
				try {
					mngSocket = new Socket(mngIP, mngPort);
					if (mngSocket != null) {
						ChatPanel p = new ChatPanel(mngSocket, staffName, "Manager");
						add(p, BorderLayout.CENTER);
						p.getTxtMessages().append("Manager is running\n");
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
		panel.add(btnConnect);
	}
}
