package com.example;

import java.util.Arrays;

public class App {

	public static void main(String[] args) {

		Iterable<Integer> intList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		iterate(intList, "Default iteration");

		Iterable<Integer> filteredList = new FilterStep<Integer>(intList, i -> i % 2 == 0);
		iterate(filteredList, "Filtered iteration");

		Iterable<Double> mappedList = new MapStep<Integer, Double>(filteredList, i -> (double) i * i);
		iterate(mappedList, "Mapped iteration");
	}

	private static void iterate(Iterable<?> iterable, String message) {

		System.out.println();
		System.out.println(message);

		for (Object o : iterable) {
			System.out.println(o);
		}
	}
}
