package starhide.sort;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the sorting for the application
 * Kind of a main class like GUI but separate for organization purposes
 * Sorting Methods must handle color assigning, stopping, and sleeping.
 *
 * @author Blake Bauer
 */
public class Sort {
	private GUI gui;
	public ArrayList<Integer> numbers;
	public ArrayList<Integer> colors;
	private int size;
	public boolean shouldStop = true;

	public Sort(GUI gui, int size) {
		this.gui = gui;
		this.size = size;

		numbers = new ArrayList<>();
		colors = new ArrayList<>();
	}

	public void fillArray() {
		Random r = new Random();
		numbers.clear();
		colors.clear();
		for (int x = 0; x < size; x++) {
			numbers.add(r.nextInt(128));
			colors.add(0);
		}
	}

	private void refreshGUI() {
		gui.drawImage(numbers, colors);
	}

	private void sleep() {
		int duration = gui.SPEED_MAX - gui.speed.getValue();
		if (duration > 10) {
			try {
				Thread.sleep(duration);
			} catch (Exception e) {

			}
		}
	}

	public void SelectionSort() {
		int min = 0;
		int temp = 0;
		for (int x = 0; x < size; x++) {
			min = x;
			colors.set(x, 1);
			for (int y = x + 1; y < size; y++) {
				colors.set(y, 2);
				if (numbers.get(y) < numbers.get(min)) {
					if (min != x)
						colors.set(min, 0);
					min = y;
					colors.set(min, 3);
				}
				refreshGUI();
				if (y != min)
					colors.set(y, 0);

				sleep();

				if (shouldStop)
					return;
			}
			colors.set(min, 0);
			temp = numbers.get(x);
			numbers.set(x, numbers.get(min));
			numbers.set(min, temp);
			refreshGUI();
			colors.set(x, 0);
		}
		refreshGUI();
	};

	public void Insertion() {
		for (int i = 1; i < size; i++) {
			colors.set(i, 1);
			int k = i - 1;
			while (k > -1 && numbers.get(i) < numbers.get(k)) {
				colors.set(k, 2);
				refreshGUI();
				colors.set(k, 0);
				k--;
				sleep();
				if (shouldStop)
					return;
			}

			int t = numbers.remove(i);
			numbers.add(k == -1 ? 0 : k + 1, t);
			refreshGUI();
			colors.set(i, 0);
			colors.set(k == -1 ? 0 : k, 0);
		}
		refreshGUI();
	}

	public void Bubble() {
		int swaps;
		int lastSwap = size - 1;
		int m;
		do {
			m = lastSwap;
			swaps = 0;
			colors.set(m, 1);
			for(int i = 0; i < m; i++) {
				if(numbers.get(i) > numbers.get(i + 1)) {
					int t = numbers.get(i);
					numbers.set(i, numbers.get(i + 1));
					numbers.set(i + 1, t);
					lastSwap = i + 1;
					swaps++;
				}
				colors.set(i, 2);
				colors.set(i+1, 3);
				refreshGUI();
				colors.set(i, 0);
				colors.set(i+1, 0);
				if (shouldStop)
					return;
				sleep();
			}
			colors.set(m, 0);
		} while(swaps > 0);
		refreshGUI();
	}
	
	public void Quick() {
		quickHelper(0, size - 1);
		refreshGUI();
	}

	private void quickHelper(int low, int high) {
		if (shouldStop)
			return;
		if (low < high) {
			int mid = numbers.get((low + high) / 2);
			colors.set((low + high) / 2, 1);

			int i = low - 1;
			int j = high + 1;

			while (true) {
				do {
					if (shouldStop)
						return;
					i++;
					colors.set(i, 2);
					refreshGUI();
					colors.set(i, 0);
					sleep();
				} while (numbers.get(i) < mid);
				colors.set(i, 2);
				do {
					if (shouldStop)
						return;
					j--;
					colors.set(j, 3);
					refreshGUI();
					colors.set(j, 0);
					sleep();
				} while (numbers.get(j) > mid);
				colors.set(j, 3);
				refreshGUI();
				colors.set(i, 0);
				colors.set(j, 0);

				if (i >= j)
					break;

				int t = numbers.get(i);
				numbers.set(i, numbers.get(j));
				numbers.set(j, t);
			}

			quickHelper(low, i - 1);
			quickHelper(j + 1, high);
		}
		refreshGUI();
	}
	
	public void Merge() {
		mergeHelper(0, size - 1);
		refreshGUI();
	}
	
	private void mergeHelper(int low, int high) {
		if (shouldStop)
			return;
		if(low < high) {
			int m = (low + high) / 2;
			mergeHelper(low, m);
			mergeHelper(m + 1, high);	
			if (shouldStop)
				return;
			Object[] left = numbers.subList(low, m + 1).toArray();
			
			int j = m + 1;
			int i = 0;
			int t = low;
			
			while(i < left.length && j <= high) {	
				if (shouldStop)
					return;
				if(numbers.get(j) <= (Integer)left[i]) {
					numbers.set(t, numbers.get(j));
					colors.set(t, 2);
					refreshGUI();
					colors.set(t, 0);
					t++;
					j++;
				} else {
					numbers.set(t, (Integer)left[i]);
					colors.set(t, 2);
					refreshGUI();
					colors.set(t, 0);
					t++;
					i++;
				}
			}
			
			while(i < left.length) {
				if (shouldStop)
					return;
				numbers.set(t, (Integer)left[i]);
				colors.set(t, 2);
				refreshGUI();
				colors.set(t, 0);
				t++;
				i++;
			}
			
			while(j <= high) {
				if (shouldStop)
					return;
				numbers.set(t, numbers.get(j));
				colors.set(t, 2);
				refreshGUI();
				colors.set(t, 0);
				t++;
				j++;
			}
			
		}
		refreshGUI();
	}
}
