package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MenuOpenController implements ActionListener
{
	private JFrame frame;

	public MenuOpenController(JFrame frame)
	{
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
			JOptionPane.showMessageDialog(frame, "Selected File " + fileChooser.getSelectedFile().getPath());
	}

}