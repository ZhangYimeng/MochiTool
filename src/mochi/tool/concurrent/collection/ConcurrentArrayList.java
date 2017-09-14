package mochi.tool.concurrent.collection;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

public class ConcurrentArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
	
	private static final long serialVersionUID = 6587554356493348751L;
	@SuppressWarnings("unused")
	private static final int DEFAULT_CAPACITY = 10;

	@Override
	public E get(int index) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

}
