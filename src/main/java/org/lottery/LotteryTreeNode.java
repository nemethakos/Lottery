package org.lottery;

public class LotteryTreeNode {

	private LotteryTreeNode[] children;
	private int count;

	public LotteryTreeNode getChild(int number) {
		if (number < 1 || number > 90) {
			throw new IllegalArgumentException();
		}
		if (children == null) {
			this.children = new LotteryTreeNode[90];
		}
		if (this.children[number - 1] == null) {
			this.children[number - 1] = new LotteryTreeNode();
		}
		return this.children[number - 1];
	}

	public int getCount() {
		return count;
	}

	public void increaseCount() {
		this.count++;
	}

	public void decreaseCount(int count) {
		this.count -= count;
	}
}
