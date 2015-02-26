package jkeyfinder;

import java.util.Optional;

public final class Result {
	public final Optional<Key>	key;
	public final float[][]		chromagram;
	public final float[]		strengths;
	public final int			strongest;
	
	public Result(Optional<Key> key, float[][] chromagram, float[] strengths, int strongest) {
		this.key		= key;
		this.chromagram	= chromagram;
		this.strengths	= strengths;
		this.strongest	= strongest;
	}
}
