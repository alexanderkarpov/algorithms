package com.example.algorithms;

import java.util.*;

/**
 * Created by alexander on 12/26/15.
 */
public class Sorting {

    public static <T extends Comparable> void mergeSort(List<T> list, int first, int last) {
        if (first < last) {
            int middle = (first + last) >> 1;
            mergeSort(list, first, middle);
            mergeSort(list, middle + 1, last);
            mergeSubLists(list, first, middle, middle + 1, last);
        }
    }

    static <T extends Comparable> void mergeSubLists(List<T> list, int first1, int last1, int first2, int last2) {
        LinkedList<T> list1 = new LinkedList<>(list.subList(first1, last1 + 1));
        LinkedList<T> list2 = new LinkedList<>(list.subList(first2, last2 + 1));

        List<T> result = mergeLists(list1, list2);

        int i = first1 < first2 ? first1 : first2;

        for (T item : result) {
            list.set(i++, item);
        }
    }

    static <T extends Comparable> List<T> mergeLists(LinkedList<T> list1, LinkedList<T> list2) {
        List<T> result = new ArrayList<>();
        T a = list1.peekFirst();
        T b = list2.peekFirst();

        while (a != null || b != null) {
            if (a == null) {
                result.addAll(list2);
                list2.clear();
            } else if (b == null) {
                result.addAll(list1);
                list1.clear();
            } else if (a.compareTo(b) < 0) {
                result.add(a);
                list1.removeFirst();
            } else {
                result.add(b);
                list2.removeFirst();
            }

            a = list1.peekFirst();
            b = list2.peekFirst();

        }

        return result;


    }

    // O(N^2)
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

    public static <T extends Comparable> void bubbleSort(List<T> list) {

        boolean swap = true;

        while (swap) {
            swap = false;
            T item = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (item.compareTo(list.get(i)) > 0) {

                    list.set(i - 1, list.get(i));
                    list.set(i, item);
                    swap = true;
                }
            }
        }

    }


}
