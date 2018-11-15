package starhide.sort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Write a description of class GUI here.
 * 
 * @author Blake Bauer
 */
public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7298410999933094381L;

	private ImagePanel image;
	public JSlider speed;

	public final int SPEED_MIN = 0, SPEED_MAX = 1000;
	public final int WIDTH = 512, HEIGHT = 256;
	public final int SIZE = 128;
	private Sort sort;

	private Thread current;

	public GUI() {
		super("Sort Visualizer");
		sort = new Sort(this, SIZE);
		sort.fillArray();
		makeFrame();
		drawImage(sort.numbers, sort.colors);
	}

	private void redraw() {
		image.repaint();
	}

	public void drawImage(ArrayList<Integer> numbers, ArrayList<Integer> colors) {
		int w = WIDTH / SIZE;
		int h = HEIGHT / 128;
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (x % w > 0 && y > HEIGHT - (h * numbers.get((int) (x / w)))) {
					Color c;
					switch (colors.get((int) (x / w))) {
					case 1:
						c = new Color(255, 0, 0);
						break;
					case 2:
						c = new Color(0, 255, 0);
						break;
					case 3:
						c = new Color(0, 0, 255);
						break;
					default:
						c = new Color(255, 255, 255);
					}

					image.getImage().setPixel(x, y, c);
				} else {
					image.getImage().setPixel(x, y, new Color(0, 0, 0));
				}
			}
		}

		redraw();
	}

	private void makeFrame() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 3));

		JButton selectionSort = new JButton("Selection");
		selectionSort.addActionListener(e -> runSort(sort::SelectionSort));
		buttonPanel.add(selectionSort);

		JButton insertionSort = new JButton("Insertion");
		insertionSort.addActionListener(e -> runSort(sort::Insertion));
		buttonPanel.add(insertionSort);
		
		JButton bubbleSort = new JButton("Bubble");
		bubbleSort.addActionListener(e -> runSort(sort::Bubble));
		buttonPanel.add(bubbleSort);

		JButton quickSort = new JButton("Quick Sort");
		quickSort.addActionListener(e -> runSort(sort::Quick));
		buttonPanel.add(quickSort);
		
		JButton mergeSort = new JButton("Merge");
		mergeSort.addActionListener(e -> runSort(sort::Merge));
		buttonPanel.add(mergeSort);

		JButton stop = new JButton("Stop");
		stop.addActionListener(ev -> stopThread());
		buttonPanel.add(stop);

		JButton reset = new JButton("Reset Array");
		reset.addActionListener(ev -> {
			stopThread();
			sort.fillArray();
			drawImage(sort.numbers, sort.colors);
		});
		buttonPanel.add(reset);
		contentPane.add(buttonPanel, BorderLayout.NORTH);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		bottomPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(0, 0, 0)),
				new EmptyBorder(10, 10, 10, 10)));

		
		speed = new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_MAX);
		speed.setPreferredSize(new Dimension(WIDTH/4, 25));
		
		speed.setMajorTickSpacing(100);
		speed.setMinorTickSpacing(10);
		bottomPanel.add(speed);

		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		image = new ImagePanel();
		image.setImage(new OFImage(WIDTH, HEIGHT));
		//image.setBorder(new LineBorder(new Color(128, 128, 128), 10));
		contentPane.add(image, BorderLayout.CENTER);

		makeMenuBar();
		pack();
		setVisible(true);
	}

	private void makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		fileMenu.add(quitItem);

		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("Image Viewer Program");
			}
		});
		helpMenu.add(aboutItem);
	}

	private void runSort(Runnable sortFunc) {
		sort.shouldStop = false;
		current = new Thread(sortFunc);
		current.start();
	}

	private void stopThread() {
		try {
			if (current != null) {
				sort.shouldStop = true;
				System.out.println("Waiting for thread to stop...");
				current.join();
				current = null;
				System.out.println("Thread has stopped.");
				sort.shouldStop = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new GUI();
	}
}
