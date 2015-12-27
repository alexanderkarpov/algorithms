package com.example.algorithms;

import java.util.*;

/**
 * Created by alexander on 12/25/15.
 */
public class App {

    public static void main(String... args) {

//        List<Integer> list = makeIntArrayList(16, 1000);
//        System.out.println(list);
//        System.out.println(list.subList(1, 2));

        testSort();
//        testFindKthLargest();
//        testBinarySearch();


    }

    private static void testSort() {
//        List<Integer> list = makeIntArrayList(16, 1000);
        List<Integer> list = Arrays.asList(5,8,17,25,10,15,2,7,6,18);


        System.out.println("source: " + list);
        Sorting.quickSort(list, 0, list.size()-1);
//        Sorting.mergeSort(list, 0, list.size()-1);
//        Sorting.insertionSort(list);
//        Sorting.bubbleSort(list);
        System.out.println("sorted: " + list);

    }


    private static void testFindKthLargest() {
        List<Integer> list = makeIntArrayList(10, 1000);

        int max6 = Searching.findKthLargest(list, 6);

        System.out.println("list: " + list);
        System.out.println("6th largest: " + max6);
        Collections.sort(list);
        System.out.println("sorted list: " + list);
        System.out.println("6th largest: " + list.get(list.size() - 6));

    }


    private static void testBinarySearch() {
        List<Integer> list = makeIntArrayList(33, 1000);

        Collections.sort(list);

//        int target = list.get(list.size() - 1);
        int target = list.get(list.size() / 2);
//        int target = list.get(0);
//        int target = 1001;

        int index = Searching.binarySearch(list, target);
        System.out.println("list: " + list);
        System.out.println("target: " + target);
        System.out.println("index of target: " + index);
    }

    private static List<Integer> makeIntArrayList(int size, int maxValue) {
        List<Integer> list = new ArrayList<>(size);
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            list.add(random.nextInt(maxValue));
        }

        return list;
    }


}
