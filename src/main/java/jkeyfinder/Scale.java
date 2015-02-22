package jkeyfinder;

public final class Scale {
	public static final Scale SILENT			= new Scale(
			0, 0, 0, 0,
			0, 0, 0, 0,
			0, 0, 0, 0);
	
	public static final Scale SIMPLE_MAJOR		= new Scale(
			1, 0, 1, 0,
			1, 1, 0, 1,
			0, 1, 0, 1);
	public static final Scale SIMPLE_MINOR		= new Scale(
			1, 0, 1, 1,
			0, 1, 0, 1,
			1, 0, 1, 0); 
	
	public static final Scale KRUMHANSL_MAJOR	= new Scale(
			6.35f,	2.23f,	3.48f,	2.33f,
			4.38f,	4.09f,	2.52f,	5.19f,
			2.39f,	3.66f,	2.29f,	2.88f);
	public static final Scale KRUMHANSL_MINOR	= new Scale(
			6.33f,	2.68f,	3.52f,	5.38f,
			2.60f,	3.53f,	2.54f,	4.75f,
			3.98f,	2.69f,	3.34f,	3.17f);
	
	public static final Scale TEMPERLEY_MAJOR	= new Scale(
			5.0f,	2.0f,	23.5f,	22.0f,
			24.5f,	24.0f,	22.0f,	24.5f,
			22.0f,	23.5f,	21.5f,	24.0f);
	public static final Scale TEMPERLEY_MINOR	= new Scale(
			25.0f,	22.0f,	23.5f,	24.5f,
			22.0f,	24.0f,	22.0f,	24.5f,
			23.5f,	22.0f,	21.5f,	24.0f);
	
	public static final Scale GOMEZ_MAJOR		= new Scale(
			0.82f,	0.00f,	0.55f,	0.00f,
			0.53f,	0.30f,	0.08f,	1.00f,
			0.00f,	0.38f,	0.00f,	0.47f);
	public static final Scale GOMEZ_MINOR		= new Scale(
			0.81f,	0.00f,	0.53f,	0.54f,
			0.00f,	0.27f,	0.07f,	1.00f,
			0.27f,	0.07f,	0.10f,	0.36f);
	
	public static final Scale SHAATH_MAJOR		= new Scale(
			6.6f,	2.0f,	3.5f,	2.3f,
			4.6f,	4.0f,	2.5f,	5.2f,
			2.4f,	3.7f,	2.3f,	3.4f);
	public static final Scale SHAATH_MINOR		= new Scale(
			6.5f,	2.7f,	3.5f,	5.4f,
			2.6f,	3.5f,	2.5f,	5.2f,
			4.0f,	2.7f,	4.3f,	3.2f);
	
	private final float[]	values;
	
	public Scale(float... values) {
		if (values.length != Config.semitones)
				throw new IllegalArgumentException("expected " + Config.semitones + " coefficients, got " + values.length);
		this.values	= values;
	}

	/** wraps index around the edges */
	public float get(int index) {
		return values[Util.modPos(index, values.length)];
	}
}
