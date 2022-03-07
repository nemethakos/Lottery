package org.lottery;

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

	private final LotteryTreeNode root = new LotteryTreeNode();

	/**
	 * Adds 5 numbers (a lottery ticket) to the list of lottery tickets
	 * 
	 * @param numbers a list of five lottery numbers (1-90)
	 * @throws IllegalArgumentException if the numbers are not valid lottery numbers
	 */
	public void addNumbers(int... numbers) {

		validateNumbersForCountAndNonRepetition(5, numbers);

		LotteryTreeNode node = root;
		for (var number: numbers) {
			node.increaseCount();
			node = node.getChild(number);
		}
		node.increaseCount();
	}

	/**
	 * Returns the number of winners for 5, 4, 3, 2 matches
	 * 
	 * @param numbers the drawn numbers
	 * @throws IllegalArgumentException if the numbers are not valid lottery numbers
	 */
	public String getWinnersText(int... numbers) {
		validateNumbersForCountAndNonRepetition(5, numbers);
		StringBuilder sb = new StringBuilder();

		for (var i = 5; i >= 2; i--) {
			List<Set<Integer>> combinations = getAllCombinations(i, numbers);
			var count = 0;
			for (Set<Integer> combination : combinations) {
				int[] combinationNumbers = new int[combination.size()];
				var combinationIterator = combination.iterator();
				var index = 0;
				while (combinationIterator.hasNext()) {
					combinationNumbers[index++] = combinationIterator.next();
				}
				int combinationCount = getCombinationCount(combinationNumbers);
				if (combinationCount > 0) {
					sb.append(String.format("\t%s: %d\n",
											Arrays.toString(combinationNumbers), combinationCount));
				}
				count += combinationCount;
			}
			sb.append(String.format("#%d: %d\n", i, count));
		}
		return sb.toString();
	}

	 List<Set<Integer>> getAllCombinations(int count, int... numbers) {
		List<Set<Integer>> accumulator = new ArrayList<>();
		boolean[] used = new boolean[numbers.length];

		generateAllSubsetsOfArray(numbers, count, 0, 0, used, accumulator);

		return accumulator;
	}

	int getCombinationCount(int... numbers) {
		if (numbers.length < 1 || numbers.length > 5) {
			throw new IllegalArgumentException("Illegal count of numbers: " + numbers.length);
		}
		validateNumbersForCountAndNonRepetition(numbers.length, numbers);

		LotteryTreeNode[] nodes = new LotteryTreeNode[numbers.length];
		LotteryTreeNode node = root;
		nodes[0] = root;
		int count = 0;
		for (int i = 0; i < numbers.length; i++) {
			node = node.getChild(numbers[i]);
			count = node.getCount();
			nodes[i] = node;
		}

		for (var nodeToDecrease: nodes) {
			nodeToDecrease.decreaseCount(count);
		}

		return count;
	}

	 private void generateAllSubsetsOfArray(int[] array, int numberOfElements, int start, int currLen, boolean[] used, List<Set<Integer>> accumulator) {

		Set<Integer> set = new HashSet<>();
		if (currLen == numberOfElements) {
			for (int i = 0; i < array.length; i++) {
				if (used[i]) {
					set.add(array[i]);
				}
			}
			accumulator.add(set);
			return;
		}
		if (start == array.length) {
			return;
		}
		used[start] = true;
		generateAllSubsetsOfArray(array, numberOfElements, start + 1, currLen + 1, used, accumulator);
		used[start] = false;
		generateAllSubsetsOfArray(array, numberOfElements, start + 1, currLen, used, accumulator);
	}

	private void validateNumbersForCountAndNonRepetition(int requiredCount, int... numbers) {
		if (numbers.length != requiredCount) {
			throw new IllegalArgumentException("Five numbers required" + Arrays.toString(numbers));
		}

		for (var number: numbers) {
			if (number < 1 || number > 90) {
				throw new IllegalArgumentException("Invalid number: " + number);
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
