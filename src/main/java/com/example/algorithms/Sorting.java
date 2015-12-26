package com.example.algorithms;

import java.util.List;

/**
 * Created by alexander on 12/26/15.
 */
public class Sorting {

    public static <T extends Comparable> void insertionSort(List<T> list) {

        for (int i = 1; i < list.size(); i++) {

            T workingElement = list.get(i);
            int j = i - 1;
            while (j >= 0 && workingElement.compareTo(list.get(j)) < 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, workingElement);
        }

    }


}
