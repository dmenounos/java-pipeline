package com.example;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public class MapStep<I, O> extends BaseStep<I, O> {

	private final Function<I, O> function;

	public MapStep(Iterable<I> prevIterable, Function<I, O> function) {
		super(prevIterable);

		Objects.requireNonNull(function);
		this.function = function;
	}

	@Override
	public Iterator<O> iterator() {
		return new BaseIterator<I, O>(prevIterable.iterator()) {

			protected void tryAdvance() {
				if (foundNext != null) {
					return;
				}

				foundNext = Boolean.FALSE;
				nextValue = null;

				if (prevIterator.hasNext()) {
					foundNext = Boolean.TRUE;
					I value = prevIterator.next();
					nextValue = function.apply(value);
				}
			}
		};
	}
}
