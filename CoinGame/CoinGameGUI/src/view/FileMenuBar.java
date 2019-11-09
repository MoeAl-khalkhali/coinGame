package view;

import javax.swing.JFrame;

import controller.MenuExitController;
import controller.MenuOpenController;
import controller.MenuSaveController;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FileMenuBar
{
	JMenu menu;
	JMenuItem open, save, recents, exit;

	public FileMenuBar(JFrame frame)
	{
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu("File");

		open = new JMenuItem("Open");
		MenuOpenController menuOpenController = new MenuOpenController(frame);
		open.addActionListener(menuOpenController);

		save = new JMenuItem("Save");
		MenuSaveController menuSaveController = new MenuSaveController(frame);
		save.addActionListener(menuSaveController);

		exit = new JMenuItem("Exit");
		MenuExitController menuExitController = new MenuExitController(frame);
		exit.addActionListener(menuExitController);

		menu.add(open);
		menu.add(save);
		menu.add(exit);
		menuBar.add(menu);

		frame.setJMenuBar(menuBar);

	}

}
