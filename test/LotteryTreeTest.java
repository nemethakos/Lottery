import java.util.Random;

import org.junit.jupiter.api.Test;

class LotteryTreeTest {

	// @Test
	void testAllSubSets() {
		LotteryTree lt = new LotteryTree();

		System.out.println(lt.getAllCombinations(5, 1, 2, 3, 4, 5));
	}

	//@Test
	void testRandom() {
		LotteryTree lt = new LotteryTree();

		Random r = new Random();
		int[] numbers = new int[5];
		int i = 1;
		while (i <= 10_000_000) {
			if (i % 1_000_000 == 0) {
				System.out.println(i);
			}
			getRandomNumbers(r, numbers);
			try {
				lt.addNumbers(numbers);
				i++;
			} catch (IllegalArgumentException e) {
				// System.out.println("Wrong numbers:" + Arrays.toString(numbers));
			}

		}

		i = 0;
		while (i == 0) {
			getRandomNumbers(r, numbers);
			try {
				lt.printWinners(numbers);
				i += 1;
			} catch (Exception e) {
			}
		}
	}

	private void getRandomNumbers(Random r, int[] numbers) {
		for (int j = 0; j < 5; j++) {
			numbers[j] = r.nextInt(89) + 1;
		}
	}

	// @Test
	void testAddNumbers() {

		LotteryTree lt = new LotteryTree();
		/*
		 * lt.addNumbers(1,2,3,4,5); lt.addNumbers(1,3,4,5,6);
		 * System.out.println(lt.queryNumbers(1,2));
		 */

		for (int n1 = 1; n1 < 90 - 4; n1++) {

			for (int n2 = n1 + 1; n2 < 90 - 3; n2++) {

				for (int n3 = n2 + 1; n3 < 90 - 2; n3++) {

					for (int n4 = n3 + 1; n4 < 90 - 1; n4++) {

						int n5 = n4 + 1;

						// System.out.format("%2d, %2d, %2d, %2d, %2d\n", n1, n2, n3, n4, n5);
						lt.addNumbers(n1, n2, n3, n4, n5);
						// lt.addNumbers(n1, n2, n3, n4, n5);
					}
				}
			}
		}

		lt.printWinners(86, 87, 88, 89, 90);

		/*
		 * for (int i = 5; i <= 90; i++) { System.out.println("first number: " + i +
		 * ", count: " + lt.queryNumbers(1, 2, 3, 4, i)); }
		 */

	}

	@Test
	void testPrintWinners() {

		LotteryTree lt = new LotteryTree();

		lt.addNumbers(1, 2, 3, 4, 5);
		lt.addNumbers(1, 2, 3, 4, 10);
		lt.addNumbers(1, 2, 3, 9, 10);
		lt.addNumbers(1, 2, 8, 9, 10);
		lt.addNumbers(1, 9, 10, 11, 12);

		lt.printWinners(1, 2, 3, 4, 5);

		/*
		 * for (int i = 5; i <= 90; i++) { System.out.println("first number: " + i +
		 * ", count: " + lt.queryNumbers(1, 2, 3, 4, i)); }
		 */

	}

}
