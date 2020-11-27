package com.example;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class FilterStep<T> implements Iterable<T> {

	private final Iterable<T> prevIterable;
	private final Predicate<T> predicate;

	public FilterStep(Iterable<T> prevIterable, Predicate<T> predicate) {
		Objects.requireNonNull(prevIterable);
		Objects.requireNonNull(predicate);

		this.prevIterable = prevIterable;
		this.predicate = predicate;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			private Iterator<T> prevIterator = prevIterable.iterator();
			private T nextValue;

			@Override
			public boolean hasNext() {
				while (prevIterator.hasNext()) {
					T value = prevIterator.next();

					if (predicate.test(value)) {
						nextValue = value;
						return true;
					}
				}

				return false;
			}

			@Override
			public T next() {
				return nextValue;
			}
		};
	}
}
