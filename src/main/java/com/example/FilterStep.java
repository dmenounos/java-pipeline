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
			private Boolean foundNext;
			private T nextValue;

			private void tryAdvance() {
				if (foundNext != null) {
					return;
				}

				foundNext = Boolean.FALSE;
				nextValue = null;

				while (prevIterator.hasNext()) {
					T value = prevIterator.next();

					if (predicate.test(value)) {
						foundNext = Boolean.TRUE;
						nextValue = value;
						break;
					}
				}
			}

			@Override
			public boolean hasNext() {
				tryAdvance();
				return foundNext;
			}

			@Override
			public T next() {
				tryAdvance();
				foundNext = null;
				return nextValue;
			}
		};
	}
}
