package hmi;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


/**
 * @author  prou
 */
public class PasswordJPanel extends JPanel {

	/**
	 * @uml.property  name="password"
	 */
	private String password;
	private JPasswordField jPasswordField;

	public PasswordJPanel(String text, String initialPassword, int width) {
		password = initialPassword;
		JLabel jLabel;
		setLayout(new GridLayout(1, 2, 4, 4));
		setPreferredSize(new Dimension(width-20, 20));
		jLabel = new JLabel(text);
		jLabel.setVisible(true);
		add(jLabel);
		jPasswordField = new JPasswordField(initialPassword);
		jPasswordField.setVisible(true);
		jPasswordField.addCaretListener(new PasswordActionListener());
		add(jPasswordField);
		setVisible(true);

	}

	/**
	 * @return
	 * @uml.property  name="password"
	 */
	public String getPassword() {
		return password;
	}

	class PasswordActionListener implements CaretListener {
		public void caretUpdate(CaretEvent e) {
			password = new String(jPasswordField.getPassword());
		}
	}
}
