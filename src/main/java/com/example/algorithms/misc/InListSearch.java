package com.example.algorithms.misc;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Поиск элемента односвязного списка. (Задача из собеседования Google)
 * <p>
 * Придумайте алгоритм поиска n-го с конца элемента односвязного списка, сложность которого будет равна O(N).
 * Алгоритм не должен использовать дополнительные структуры - массивы, списки и т.д., только примитивные типы.
 * Исходные данные: "голова" односвязного списка и индекс (с конца) искомого элемента.
 * <p>
 * Created by alexander on 12/26/15.
 */
public class InListSearch {

    public static void main(String... args) {

        UnidirectialList<Integer> list = new UnidirectialList<>(0);

        for (int i = 1; i < 10; i++) {
            list.add(i);
        }

        for (int item : list) {
            System.out.println(item);
        }

        Integer n = list.getElementFromEnd(3);
        System.out.println("N = " + n);

    }


    private static class UnidirectialList<T> implements Iterable<T> {

        private final Node<T> first;

        public UnidirectialList(T first) {
            this.first = new Node<>(first);
        }

        public void add(T item) {
//            Node<T> newNode = new Node<>(item, this.head);
            Node<T> next = first;
            while (next.getNext() != null) {
                next = next.getNext();
            }
            next.setNext(new Node<>(item));
        }

        public T getElementFromEnd(int index) {

            Iterator<T> firstIterator = iterator();
            Iterator<T> secondIterator = iterator();

            int i = 0;
            while (i < index) {
                if (firstIterator.hasNext()) {
                    firstIterator.next();
                    i++;
                } else {
                    throw new IllegalStateException("too short list");
                }
            }

            while (firstIterator.hasNext()) {
                firstIterator.next();
                T value = secondIterator.next();

                if (!firstIterator.hasNext()) {
                    return value;
                }
            }

            return null;

        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                private Node<T> current = first;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public T next() {
                    T value = current.getValue();
                    current = current.getNext();
                    return value;
                }
            };
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public Spliterator<T> spliterator() {
            throw new UnsupportedOperationException("not implemented yet");
        }
    }

    private static class Node<T> {

        final T value;
        Node next;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
