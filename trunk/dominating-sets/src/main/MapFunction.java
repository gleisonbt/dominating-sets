package main;
/**
 * map function as in declarative programming
 * @author Hussain Al-Mutawa
 * @version 1.0
 * @since 1.0
 * @param <T>
 */
public interface MapFunction<T> {
	/**
	 * maps a function to a given item in the collection
	 * @param index
	 * @param value
	 */
	void map(int index,T value);
}