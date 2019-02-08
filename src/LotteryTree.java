import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 90-tree implementation for storing lottery tickets, (5 numbers out of 90) and 
 * counting the winning tickets for 5, 4, 3, 2 matches in constant time. 
 */
public class LotteryTree {

	private LotteryTreeNode root = new LotteryTreeNode();

	/**
	 * Adds 5 numbers (a lottery ticket) to the list of lottery tickets
	 * 
	 * @param numbers a list of five lottery numbers (1-90)
	 * @throws IllegalArgumentException if the numbers are not valid lottery numbers
	 */
	public void addNumbers(int... numbers) {

		validateNumbers(5, numbers);

		LotteryTreeNode node = root;
		for (int i = 0; i < numbers.length; i++) {
			node.increaseCount();
			node = node.getChild(numbers[i]);
		}
		node.increaseCount();

	}

	/**
	 * Prints the number of winners for 5, 4, 3, 2 matches
	 * 
	 * @param numbers the drawn numbers
	 * @throws IllegalArgumentException if the numbers are not valid lottery numbers
	 */
	public void printWinners(int... numbers) {
		validateNumbers(5, numbers);
		for (var i = 5; i >= 2; i--) {
			List<Set<Integer>> combinations = getAllCombinations(i, numbers);
			var count = 0;
			for (Set<Integer> c : combinations) {
				int[] combinationNumbers = new int[c.size()];
				var ci = c.iterator();
				var index = 0;
				while (ci.hasNext()) {
					combinationNumbers[index++] = ci.next();
				}
				int combinationCount = queryNumbers(combinationNumbers);
				System.out.format("\t%s: %d\n", Arrays.toString(combinationNumbers), combinationCount);
				count += combinationCount;
			}
			System.out.format("#%d: %d\n", i, count);
		}

	}

	protected List<Set<Integer>> getAllCombinations(int count, int... numbers) {
		List<Set<Integer>> accumulator = new ArrayList<>();
		boolean[] used = new boolean[numbers.length];

		subset(numbers, count, 0, 0, used, accumulator);

		return accumulator;
	}

	protected int queryNumbers(int... numbers) {
		if (numbers.length < 1 || numbers.length > 5) {
			throw new IllegalArgumentException("Illegal count of numbers: " + numbers.length);
		}
		validateNumbers(numbers.length, numbers);

		LotteryTreeNode[] nodes = new LotteryTreeNode[numbers.length];
		LotteryTreeNode node = root;
		nodes[0] = root;
		int count = 0;
		for (int i = 0; i < numbers.length; i++) {
			node = node.getChild(numbers[i]);
			count = node.getCount();
			System.out.println("\t\t\t\t(" + (i + 1) + "): " + numbers[i] + ", c:" + count);
			nodes[i] = node;
		}

		for (int i = 0; i < nodes.length; i++) {
			nodes[i].decreaseCount(count);
		}

		return count;
	}

	protected void subset(int[] A, int k, int start, int currLen, boolean[] used, List<Set<Integer>> accumulator) {

		Set<Integer> set = new HashSet<>();
		if (currLen == k) {
			for (int i = 0; i < A.length; i++) {
				if (used[i] == true) {
					set.add(A[i]);
					// System.out.print(A[i] + " ");
				}
			}
			accumulator.add(set);
			// System.out.println();
			return;
		}
		if (start == A.length) {
			return;
		}
		// For every index we have two options,
		// 1.. Either we select it, means put true in used[] and make currLen+1
		used[start] = true;
		subset(A, k, start + 1, currLen + 1, used, accumulator);
		// 2.. OR we dont select it, means put false in used[] and dont increase
		// currLen
		used[start] = false;
		subset(A, k, start + 1, currLen, used, accumulator);
	}

	protected void validateNumbers(int requiredCount, int... numbers) {
		if (numbers.length != requiredCount) {
			throw new IllegalArgumentException("Five numbers required" + Arrays.toString(numbers));
		}

		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i] < 1 || numbers[i] > 90) {
				throw new IllegalArgumentException("Invalid number" + numbers[i]);
			}
		}

		Arrays.sort(numbers);

		for (int i = 0; i < numbers.length - 1; i++) {
			if (numbers[i] == numbers[i + 1]) {
				throw new IllegalArgumentException("No repetitions allowed: " + Arrays.toString(numbers));
			}
		}
	}
}
