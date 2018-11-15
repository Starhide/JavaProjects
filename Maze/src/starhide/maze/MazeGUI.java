package starhide.maze;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.border.*;
import java.io.*;

/**
 * Main Gui / Maze Generation class
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class MazeGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4263415208229351532L;

	private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

	private ImagePanel imagePanel;
	private Image image;
	private JSpinner width;
	private JSpinner height;
	private JTextField seed;
	private ButtonGroup group;
	private JScrollPane scroll;
	private MazeAlgorithm currentMaze;
	public JCheckBoxMenuItem realTime;

	/**
	 * Constructor for objects of class MazeGUI
	 */
	public MazeGUI() {
		super("Maze Algorithms");
		initFrame();
	}

	/**
	 * Action Method for genration button. Diplays and generates a maze
	 */
	public void generateMaze() {
		try {
			currentMaze = getMaze();
		} catch (LargeSizeException e) {
			JOptionPane.showMessageDialog(this, e, "Size Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (SmallSizeException e) {
			JOptionPane.showMessageDialog(this, e, "Size Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		currentMaze.generate();

		renderMaze();
	}

	/**
	 * Renders the maze and calls paint immeditaly allowing it to be used in a loop
	 */
	public void renderMaze() {
		image = new Image(currentMaze.getWidth() * 2 + 1, currentMaze.getHeight() * 2 + 1);
		for (int x = 0; x < currentMaze.getWidth() * 2 + 1; x++) {
			for (int y = 0; y < currentMaze.getHeight() * 2 + 1; y++) {
				if (currentMaze.getGrid()[x][y] == 1) {
					image.setPixel(x, y, Color.BLACK);
				} else {
					image.setPixel(x, y, Color.WHITE);
				}
			}
		}
		imagePanel.setImage(image);
		imagePanel.paintImmediately(0, 0, image.getWidth() * 4, image.getHeight() * 4);
	}

	/**
	 * Returns a maze object of the currently selected maze
	 * 
	 * @return A MazeAlgorithm
	 */
	private MazeAlgorithm getMaze() throws LargeSizeException, SmallSizeException {
		MazeAlgorithm maze = null;
		if (group.getSelection().getActionCommand().equals("Prims")) {
			maze = new Prims((int) (width.getValue()), (int) (height.getValue()), seed.getText(), this);
		} else if (group.getSelection().getActionCommand().equals("RB")) {
			maze = new RBackTracker((int) (width.getValue()), (int) (height.getValue()), seed.getText(), this);
		} else if (group.getSelection().getActionCommand().equals("Kruskal")) {
			maze = new Kruskals((int) (width.getValue()), (int) (height.getValue()), seed.getText(), this);
		} else if (group.getSelection().getActionCommand().equals("RD")) {
			maze = new RDivision((int) (width.getValue()), (int) (height.getValue()), seed.getText(), this);
		}
		return maze;
	}

	/**
	 * Exports the maze image as a png
	 */
	public void exportPNG() {
		if (image != null) {
			int val = fileChooser.showSaveDialog(this);

			if (val != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File file = fileChooser.getSelectedFile();

			if (!file.getAbsolutePath().endsWith(".png")) {
				file = new File(file.getAbsolutePath() + ".png");
			}

			try {
				ImageIO.write(image, "png", file);
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(this, "Something went wrong with saving the file.\n" + exc, "IOException",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No maze image to save.", "Operator Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Creates a binary representation of the current maze
	 */
	public void exportTXT() {
		int val = fileChooser.showSaveDialog(this);

		if (val != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File file = fileChooser.getSelectedFile();

		if (!file.getAbsolutePath().endsWith(".txt")) {
			file = new File(file.getAbsolutePath() + ".txt");
		}

		try {
			FileWriter writer = new FileWriter(file);
			writer.write(currentMaze.getWidth() + " x " + currentMaze.getHeight() + "\r\n");
			for (int x = 0; x < currentMaze.getWidth() * 2 + 1; x++) {
				for (int y = 0; y < currentMaze.getHeight() * 2 + 1; y++) {
					writer.write(currentMaze.getGrid()[x][y] + " ");
				}
				writer.write("\r\n");
			}

			writer.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Something went wrong with saving the file.\n" + e, "IOException",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * System exit
	 */
	private void exit() {
		System.exit(0);
	}

	private void about() {
		JOptionPane.showMessageDialog(this,
				"A simple program that generates mazes using different algorithms.\n"
						+ "Warning dont generate large mazes in realtime!\n" + "CS162 WOU\nBlake Bauer\nMay 6, 2016",
				"About", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Creates the frame
	 */
	private void initFrame() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints;

		// ImagePanel
		imagePanel = new ImagePanel();
		scroll = new JScrollPane(imagePanel);
		gridBagConstraints = getConstraints(1, 0, GridBagConstraints.BOTH, GridBagConstraints.NORTHEAST, 1.0, 1.0);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(700, 700));
		contentPane.add(scroll, gridBagConstraints);

		// Settings Panel
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridBagLayout());
		settingsPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		gridBagConstraints = getConstraints(1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTHEAST, 1.0,
				0.0);
		contentPane.add(settingsPanel, gridBagConstraints);

		// Size Label
		JLabel label = new JLabel("Width");
		gridBagConstraints = getConstraints(0, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.1, 0.0);
		settingsPanel.add(label, gridBagConstraints);

		// Size JSpinner
		width = new JSpinner();
		gridBagConstraints = getConstraints(1, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0.4,
				0.0);
		settingsPanel.add(width, gridBagConstraints);

		// Size Label
		label = new JLabel("Height");
		gridBagConstraints = getConstraints(2, 0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0.1, 0.0);
		settingsPanel.add(label, gridBagConstraints);

		height = new JSpinner();
		gridBagConstraints = getConstraints(3, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTH, 0.4, 0.0);
		settingsPanel.add(height, gridBagConstraints);

		// Seed Label
		label = new JLabel("Seed");
		gridBagConstraints = getConstraints(4, 0, GridBagConstraints.NONE, GridBagConstraints.CENTER, 0.1, 0.0);
		settingsPanel.add(label, gridBagConstraints);

		// Seed TextField
		seed = new JTextField();
		gridBagConstraints = getConstraints(5, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 0.4, 0.0);
		settingsPanel.add(seed, gridBagConstraints);

		// Generate Button
		JButton generate = new JButton("Generate Maze");
		generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateMaze();
			}
		});

		gridBagConstraints = getConstraints(0, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 0);
		gridBagConstraints.gridwidth = 6;
		settingsPanel.add(generate, gridBagConstraints);

		// RadioButtons
		gridBagConstraints = getConstraints(0, 0, GridBagConstraints.VERTICAL, GridBagConstraints.WEST, 0.0, 1.0);
		gridBagConstraints.gridheight = 2;
		contentPane.add(initRadioButtons(), gridBagConstraints);

		initMenuBar();

		setSize(new Dimension(800, 600));
		// pack();
		setVisible(true);
	}

	/**
	 * Creates the menu bar
	 */
	private void initMenuBar() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu file = new JMenu("File");
		menubar.add(file);

		JMenuItem item = new JMenuItem("Export as png");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportPNG();
			}
		});
		file.add(item);

		item = new JMenuItem("Export as txt");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportTXT();
			}
		});
		file.add(item);

		realTime = new JCheckBoxMenuItem("Render Realtime");
		realTime.setState(true);
		file.add(realTime);

		item = new JMenuItem("Exit");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
		file.add(item);

		item = new JMenuItem("About");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about();
			}
		});

		menubar.add(item);
	}

	/**
	 * Organiznational mehod
	 * 
	 * @return Panel with the radio buttons
	 */
	private JPanel initRadioButtons() {
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		group = new ButtonGroup();

		JRadioButton radioButton = new JRadioButton("Recursive Division");
		radioButton.setActionCommand("RD");
		radioPanel.add(radioButton);
		radioButton.setSelected(true);
		group.add(radioButton);

		radioButton = new JRadioButton("Recursive Backtracker");
		radioButton.setActionCommand("RB");
		radioPanel.add(radioButton);
		group.add(radioButton);

		radioButton = new JRadioButton("Kruskal's Algorithm");
		radioButton.setActionCommand("Kruskal");
		radioPanel.add(radioButton);
		group.add(radioButton);

		radioButton = new JRadioButton("Prim's Algorithm");
		radioButton.setActionCommand("Prims");
		radioPanel.add(radioButton);
		group.add(radioButton);

		radioPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		return radioPanel;
	}

	/**
	 * Creates a GridBagConstraints
	 * 
	 * @param The parameters are directly the values in GridBagConstraints
	 */
	private GridBagConstraints getConstraints(int gridx, int gridy, int fill, int anchor, double weightx,
			double weighty) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.fill = fill;
		gridBagConstraints.anchor = anchor;
		gridBagConstraints.weightx = weightx;
		gridBagConstraints.weighty = weighty;
		return gridBagConstraints;
	}

	public static void main(String args[]) {
		new MazeGUI();
	}
}
