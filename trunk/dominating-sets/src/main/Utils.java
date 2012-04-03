package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**
 * declarative utilties
 * @author Hussain Al-Mutawa
 * @version 1.0
 * @since 1.0
 */
public class Utils {

	public static <T> void map(final Collection<T> list, final MapFunction<T> mapFunction) {
		final Iterator<T>it=list.iterator();
		for (int index=0;it.hasNext();++index) {
			mapFunction.map(index,it.next());
		}
	}
	
	public static <T> void map(final T[] list, final MapFunction<T> mapFunction) {
		final Iterator<T>it=Arrays.asList(list).iterator();
		for (int index=0;it.hasNext();++index) {
			mapFunction.map(index,it.next());
		}
	}
	
	public static<T> List<T> asList(T[]t){
		return new ArrayList<T>(Arrays.asList(t));
	}

}
