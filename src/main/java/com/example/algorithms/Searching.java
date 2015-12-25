package com.example.algorithms;

import java.util.List;

/**
 * Created by alexander on 12/25/15.
 */
public class Searching {

    public static <T> int sequentialSearch(List<T> list, T target) {
        for (int i = 0; i < list.size(); i++) {
            if (target.equals(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param list   input list should be sorted
     * @param target
     * @param <T>
     * @return
     */
    public static <T extends Comparable> int sortedSequentialSearch(List<T> list, T target) {
        for (int i = 0; i < list.size(); i++) {
            int compareResult = target.compareTo(list.get(i));

            if (compareResult == 0) {
                return i;
            } else if (compareResult > 0) {
                return -1;
            }
        }

        return -1;

    }

    public static <T extends Comparable> int binarySearch(List<T> list, T target) {

        int start = 0;
        int end = list.size() - 1;
        int iterCount = 0;

        try {
            while (start <= end) {
                iterCount++;
                int i = (start + end) / 2;
                int compareResult = target.compareTo(list.get(i));

                if (compareResult == 0) {
                    return i;
                } else if (compareResult > 0) { // target > list[i]
                    start = i + 1;
                } else {
                    end = i - 1;
                }
            }

            return -1;

        } finally {
            System.out.println("Binary search iter count: " + iterCount);
        }


    }


}
