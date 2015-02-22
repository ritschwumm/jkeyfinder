package jkeyfinder;

import java.util.Optional;

public final class Profile {
	public static final Profile SIMPLE		= new Profile(Scale.SIMPLE_MAJOR,		Scale.SIMPLE_MINOR);
	public static final Profile TEMPERLEY	= new Profile(Scale.TEMPERLEY_MAJOR,	Scale.TEMPERLEY_MINOR);
	public static final Profile GOMEZ		= new Profile(Scale.GOMEZ_MAJOR,		Scale.GOMEZ_MINOR);
	public static final Profile SHAATH		= new Profile(Scale.SHAATH_MAJOR,		Scale.SHAATH_MINOR);
	public static final Profile KRUMHANSL	= new Profile(Scale.KRUMHANSL_MAJOR,	Scale.KRUMHANSL_MINOR);
	
	public final Scale major;
	public final Scale minor;

	public Profile(Scale major, Scale minor) {
		this.major	= major;
		this.minor	= minor;
	}
	
	public Scale scale(Optional<Mode> modeOpt) {
		if (modeOpt.isPresent()) {
			final Mode mode	= modeOpt.get();
			switch (mode) {
				case MAJOR:	return major;
				case MINOR:	return minor;
				default:	throw new IllegalArgumentException("unexpected mode: " + mode);
			}
		}
		else return Scale.SILENT;
	}
}
