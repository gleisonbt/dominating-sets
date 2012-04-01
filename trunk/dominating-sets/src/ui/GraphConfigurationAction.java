package ui;

enum GraphConfigurationAction{
	Render_Graph ,
	Save,
	Open,
	Saved_layout,
	Reset;
	
	@Override
	public String toString() {
		return super.toString().replaceAll("[_]", " ");
	}
}
