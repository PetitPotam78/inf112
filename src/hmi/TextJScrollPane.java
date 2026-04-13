package hmi;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


/**
 * 
 * @author prou
 *
 */
public class TextJScrollPane  extends  JScrollPane {

	private JTextArea jTextArea;

	public  TextJScrollPane(String title, String initialText, boolean writable, int width) {
		jTextArea = new JTextArea(10, 40);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setText(initialText);
		jTextArea.setVisible(true);
		if (!writable)
			jTextArea.setEditable(false);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setBounds(4, 4, width-20, 250);
		setPreferredSize(new Dimension(width+50, 250));
		setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createCompoundBorder(
								BorderFactory.createTitledBorder(title),
								BorderFactory.createEmptyBorder(5,5,5,5)),
								getBorder()));
		add(jTextArea);
		setViewportView(jTextArea);
		setVisible(true);
	}


	public String getText() {
		return jTextArea.getText();
	}

	public void setText(String s) {
		 jTextArea.setText(s);
	}

}
