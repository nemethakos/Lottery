package org.lottery;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lottery.LotteryTree;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LotteryTreeTest {

    public static final int[] NUMBERS = new int[]{1, 2, 3, 4, 5};

    @Test
    void testAllSubSets() {
        LotteryTree lotteryTree = new LotteryTree();

        var expectedResult = new ArrayList<Set<Integer>>(
                Collections.singleton(
                        new HashSet<>(
                                Arrays.stream(NUMBERS)
                                        .boxed()
                                        .collect(Collectors.toList())
                        )));

        assertEquals(
                lotteryTree.getAllCombinations(5, NUMBERS),
                expectedResult);
    }

    @Disabled
    @Test
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
            lt.getWinnersText(numbers);
            i += 1;

        }
    }

    private void getRandomNumbers(Random r, int[] numbers) {
        for (int j = 0; j < 5; j++) {
            numbers[j] = r.nextInt(89) + 1;
        }
    }

    @Disabled
    @Test
    void testAddNumbers() {

        LotteryTree lt = new LotteryTree();

        for (int n1 = 1; n1 < 90 - 4; n1++) {

            for (int n2 = n1 + 1; n2 < 90 - 3; n2++) {

                for (int n3 = n2 + 1; n3 < 90 - 2; n3++) {

                    for (int n4 = n3 + 1; n4 < 90 - 1; n4++) {

                        int n5 = n4 + 1;

                        lt.addNumbers(n1, n2, n3, n4, n5);

                    }
                }
            }
        }

        lt.getWinnersText(86, 87, 88, 89, 90);
    }

    @Test
    void testWinnersText() {

        LotteryTree lt = new LotteryTree();

        lt.addNumbers(1, 2, 3, 4, 5);
        lt.addNumbers(1, 2, 3, 4, 10);
        lt.addNumbers(1, 2, 3, 9, 10);
        lt.addNumbers(1, 2, 8, 9, 10);
        lt.addNumbers(1, 9, 10, 11, 12);

        var s = lt.getWinnersText(1, 2, 3, 4, 5);
        assertEquals("""
                             	[1, 2, 3, 4, 5]: 1
                             #5: 1
                             	[1, 2, 3, 4]: 1
                             #4: 1
                             	[1, 2, 3]: 1
                             #3: 1
                             	[1, 2]: 1
                             #2: 1
                             """, s);
    }

}
