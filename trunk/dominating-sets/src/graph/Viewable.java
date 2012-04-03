package graph;

public interface Viewable<V extends Locateable> {
	V getViewableObject();
	void setViewableObject(V viewabl);
}
