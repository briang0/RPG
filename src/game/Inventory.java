package game;

import java.util.ArrayList;

public class Inventory {

	private ArrayList<Item> inv;
	private int maxSize;
	private int selectedIndex;

	public Inventory(ArrayList<Item> inv, int maxSize) {
		this.inv = inv;
		this.maxSize = maxSize;
	}

	public Inventory(int maxSize) {
		this.maxSize = maxSize;
		inv = new ArrayList<Item>();
	}

	public ArrayList<Item> getInv() {
		return inv;
	}

	public void setInv(ArrayList<Item> inv) {
		this.inv = inv;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public Item getItem(int index) {
		if (index >= inv.size()) {
			return null;
		}
		return inv.get(index);
	}

	public Item getSelectedItem() {
		if (selectedIndex < inv.size())
			return inv.get(selectedIndex);
		return null;
	}

	public boolean add(Item i) {
		if (inv.size() <= maxSize) {
			inv.add(i);
			return true;
		} else {
			return false;
		}
	}

	public void ejectItem(Item i) {
		inv.remove(i);
	}

	public void ejectItem(int index) {
		inv.remove(index);
	}

	public String toString() {
		String output = "";

		for (int x = 0; x < inv.size(); x++) {
			output += inv.get(x).toString();
		}

		return output;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

}
