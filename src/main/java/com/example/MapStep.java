package com.example;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public class MapStep<I, O> implements Iterable<O> {

	private final Iterable<I> prevIterable;
	private final Function<I, O> function;

	public MapStep(Iterable<I> prevIterable, Function<I, O> function) {
		Objects.requireNonNull(prevIterable);
		Objects.requireNonNull(function);

		this.prevIterable = prevIterable;
		this.function = function;
	}

	@Override
	public Iterator<O> iterator() {
		return new Iterator<O>() {

			private Iterator<I> prevIterator = prevIterable.iterator();
			private O nextValue;

			@Override
			public boolean hasNext() {
				if (prevIterator.hasNext()) {
					I value = prevIterator.next();
					nextValue = function.apply(value);
					return true;
				}

				return false;
			}

			@Override
			public O next() {
				return nextValue;
			}
		};
	}
}
