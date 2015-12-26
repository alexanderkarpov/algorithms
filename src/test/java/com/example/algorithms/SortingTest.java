package com.example.algorithms;

import junit.framework.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexander on 12/26/15.
 */
public class SortingTest {

    @Test
    public void testMergeSort() {

//        int[] data1 = {1, 3, 8, 10, 15, 18, 25, 68};
//        int[] data2 = {2, 4, 7, 9, 14, 20, 26, 63};

        int[] data1 = {1, 3};
        int[] data2 = {2, 4};

        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();

        for(int data : data1) {
            list1.add(data);
        }

        for(int data : data2) {
            list2.add(data);
        }

        List<Integer> mergedList = Sorting.mergeLists(list1, list2);

        System.out.println(mergedList);
        Assert.assertEquals(data1.length + data2.length, mergedList.size());



    }

}
