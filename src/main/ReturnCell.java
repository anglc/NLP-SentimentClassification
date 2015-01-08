package main;
public class ReturnCell<T> {
	T value;

	public ReturnCell(T t) {
		value = t;
	}

	public void set(T t) {
		value = t;
	}

	public T get() {
		return value;
	}
}
