package jkeyfinder;

public final class Config {
	/*
	TODO fft size
	-	lowest semitone should have enough bins for a clear separation.
		=>	fft musty be large enough to provide the necessary resolution
		=>	depends on the sample rate
	-	highest semitone must not exceed half the sample rate
	-	sk spread must not lead to clipping the windows
		against lowest and highest usable fft bins
	*/
	
	private Config() {}
	
	public static final int		semitones	= 12;
	public static final int		octaves		= 6;
	public static final int		chromaSize	= octaves * semitones;
	
	public static final float	kammerton	= 440;
	public static final float	startFreq	= kammerton / (1<<4);
	
	public static final int		fftSize		= 16384;
	public static final int		hopSize		= fftSize/4;
	public static final float	skStretch	= 0.8f;
	
	public static final Profile	profile		= Profile.SHAATH;
}
