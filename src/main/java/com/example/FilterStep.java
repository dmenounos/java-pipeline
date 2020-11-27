package com.example;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class FilterStep<T> extends BaseStep<T, T> {

	private final Predicate<T> predicate;

	public FilterStep(Iterable<T> prevIterable, Predicate<T> predicate) {
		super(prevIterable);

		Objects.requireNonNull(predicate);
		this.predicate = predicate;
	}

	@Override
	public Iterator<T> iterator() {
		return new BaseIterator<T, T>(prevIterable.iterator()) {

			protected void tryAdvance() {
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
		};
	}
}
