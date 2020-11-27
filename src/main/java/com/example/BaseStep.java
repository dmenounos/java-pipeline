package com.example;

import java.util.Iterator;
import java.util.Objects;

/**
 * Wraps a previous Iterable.
 * Provides a transformation Iterator.
 */
public abstract class BaseStep<I, O> implements Iterable<O> {

	protected final Iterable<I> prevIterable;

	protected BaseStep(Iterable<I> prevIterable) {
		Objects.requireNonNull(prevIterable);
		this.prevIterable = prevIterable;
	}

	/**
	 * Wraps a previous Iterator.
	 * Facilitates transformation over the iteration logic.
	 */
	protected static abstract class BaseIterator<I, O> implements Iterator<O> {

		protected final Iterator<I> prevIterator;
		protected Boolean foundNext;
		protected O nextValue;

		protected BaseIterator(Iterator<I> prevIterator) {
			Objects.requireNonNull(prevIterator);
			this.prevIterator = prevIterator;
		}

		/**
		 * Manages the {@code foundNext} and {@code nextValue} fields 
		 * on behalf of {@code hasNext()} and {@code next()} methods.
		 */
		protected abstract void tryAdvance();

		@Override
		public boolean hasNext() {
			tryAdvance();
			return foundNext;
		}

		@Override
		public O next() {
			tryAdvance();
			foundNext = null;
			return nextValue;
		}
	}
}
