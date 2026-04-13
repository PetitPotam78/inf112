package hmi;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;



/**
 * @author  prou
 */
public class InputJPanel  extends JPanel {

	/**
	 * @uml.property  name="input"
	 */
	String input;
	JTextField jTextField;

	public InputJPanel(String text, String initialInput, int width) {
		JLabel jLabel;
		this.input = initialInput;
		setLayout(new GridLayout(1, 2, 4, 4));
		setPreferredSize(new Dimension(width-20, 20));
		jLabel = new JLabel(text);
		jLabel.setVisible(true);
		add(jLabel);
		jTextField = new JTextField(input);
		jTextField.setVisible(true);
		jTextField.addCaretListener(new InputListener());
		add(jTextField);
		setVisible(true);
	}
	
	/**
	 * @param input
	 * @uml.property  name="input"
	 */
	public void setEntree(String input) {
		this.input = input;
		jTextField.setText(input);
	}
	
	/**
	 * @return
	 * @uml.property  name="entree"
	 */
	public String getEntree() {
		return input;
	}

	class InputListener implements CaretListener {
		public void caretUpdate(CaretEvent e) {
			input = new String(jTextField.getText());
		}
	}
}
