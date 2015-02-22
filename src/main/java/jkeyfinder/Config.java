package jkeyfinder;

public final class Config {
	private Config() {}
	
	public static final int		semitones	= 12;
	public static final int		octaves		= 6;
	public static final int		chromaSize	= octaves * semitones;
	// must be an A
	public static final float	startFreq	= 440f / (1<<4);

	public static final int		fftSize		= 16384;
	public static final int		hopSize		= fftSize/4;
	public static final float	skStretch	= 0.8f;
	
	public static final Profile	profile		= Profile.SHAATH;
}
