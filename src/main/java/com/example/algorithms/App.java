package com.example.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by alexander on 12/25/15.
 */
public class App {

    public static void main(String... args) {

//        testBinarySearch();
        testFindKthLargest();


    }

    private static void testBinarySearch() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 33; i++) {
            list.add(random.nextInt(1000));
        }

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

    private static void testFindKthLargest() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1000));
        }

        int max6 = Searching.findKthLargest(list, 6);

        System.out.println("list: " + list);
        System.out.println("6th largest: " + max6);
        Collections.sort(list);
        System.out.println("sorted list: " + list);
        System.out.println("6th largest: " + list.get(list.size() - 6));

    }
}
