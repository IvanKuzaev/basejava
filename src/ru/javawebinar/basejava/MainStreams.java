package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreams {
    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (acc, e) -> 10 * acc + e);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        AtomicInteger sum = new AtomicInteger();
        return integers
                .stream()
                .collect(Collectors.partitioningBy(v -> {
                    sum.addAndGet(v);
                    return v % 2 == 0;
                }))
                .get(sum.get() % 2 == 1);
    }

    public static void main(String[] args) {
        int[] a;
        a = new int[] {1, 2, 3, 3, 2, 3};
        System.out.println("minValue(" + Arrays.toString(a) + ") = " + minValue(a));
        a = new int[] {9, 8};
        System.out.println("minValue(" + Arrays.toString(a) + ") = " + minValue(a));
        a = new int[] {4, 8, 7, 8, 2, 8, 4, 7, 7, 7, 2, 3};
        System.out.println("minValue(" + Arrays.toString(a) + ") = " + minValue(a));

        System.out.println();

        List<Integer> li;
        li = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        System.out.println("oddOrEven(" + li + ") = " + oddOrEven(li));
        li = Arrays.asList(23, 16, 34, 2, 54, 11, 67);
        System.out.println("oddOrEven(" + li + ") = " + oddOrEven(li));
        li = Arrays.asList(123, 324, 32, 764, 445, 21, 12, 51, 25, 51, 32, 32, 1, 78, 43, 1);
        System.out.println("oddOrEven(" + li + ") = " + oddOrEven(li));
    }
}
