package graph;

public abstract class AbstractViewable<V extends Locateable> implements Viewable<V> {
	private V viewabl;
	@Override
	public V getViewableObject() {
		return viewabl;
	}

	@Override
	public void setViewableObject(V viewabl) {
		this.viewabl=viewabl;
	}

}
