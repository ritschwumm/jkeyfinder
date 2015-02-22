package jkeyfinder;

public final class Kernel {
	public final int		binOffset;
	public final float[]	coefficients;

	public Kernel(int binOffset, float[] coefficients) {
		this.binOffset		= binOffset;
		this.coefficients	= coefficients;
	}
}
