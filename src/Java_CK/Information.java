package Java_CK;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Information extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextArea textArea;

	/**
	 * Create the panel.
	 */
	public Information() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 240, 245));
		panel.setBounds(0, 0, 740, 128);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setForeground(new Color(30, 144, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 50, 54, 27);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(74, 50, 493, 33);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Xuất thông tin");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				String xmlInfo = getProductInfoAsXML(id);
				textArea.setText(xmlInfo); // Hiển thị thông tin XML trên textArea
			}
		});
		btnNewButton.setBounds(591, 50, 122, 33);
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 129, 740, 491);
		add(panel_1);
		panel_1.setLayout(null);

		// Thêm JTextArea để hiển thị thông tin XML
		textArea = new JTextArea();
		textArea.setBounds(10, 10, 720, 471);
		panel_1.add(textArea);
	}

	private String getProductInfoAsXML(String id) {
		String xmlInfo = "<product>\n";

		try {
			// Establish database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hoa", "root", "123456789");

			// Prepare SQL statement
			String sql = "SELECT * FROM hoa.hoa WHERE id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, id);

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			// Process the result set
			if (resultSet.next()) {
				xmlInfo += "<id>" + resultSet.getString("id") + "</id>\n";
				xmlInfo += "<name>" + resultSet.getString("name") + "</name>\n";
				xmlInfo += "<price>" + resultSet.getString("price") + "</price>\n";
				xmlInfo += "<salePrice>" + resultSet.getString("salePrice") + "</salePrice>\n";
				xmlInfo += "<saleStartDate>" + resultSet.getString("saleStartDate") + "</saleStartDate>\n";
				xmlInfo += "<saleEndDate>" + resultSet.getString("saleEndDate") + "</saleEndDate>\n";
				xmlInfo += "<stock>" + resultSet.getString("stock") + "</stock>\n";
				xmlInfo += "<availability>" + resultSet.getString("availability") + "</availability>\n";
				// Add other fields as needed
			}

			// Close connections
			resultSet.close();
			statement.close();
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		xmlInfo += "</product>";
		return xmlInfo;
	}

}
