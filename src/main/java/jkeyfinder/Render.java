package jkeyfinder;

import java.util.Optional;

public final class Render {
	private Render() {}
	
	public static String result(Result result) {
		return strongest(result.strongest) + "\t" + keyOpt(result.key);
	}
	
	public static String strongest(int note) {
		return SHARP[note] + "|" + FLAT[note];
	}
	
	public static String keyOpt(final Optional<Key> keyOpt) {
		if (keyOpt.isPresent()) {
			final Key	key	= keyOpt.get();
			return Render.sharp(key) + "|" + Render.flat(key) + "|" + Render.openKey(key) + "|" + Render.camelot(key);
		}
		else {
			return "SILENCE";
		}
	}
	
	//-------------------------------------------------------------------------
	
	public static String camelot(Key key) {
		switch (key.mode) {
			case MAJOR:	return cof(key.root.ordinal(), 10) + "B";
			case MINOR:	return cof(key.root.ordinal(), 7)  + "A";
			default:	throw new IllegalArgumentException("unexpected mode: " + key.mode);
		}
	}
	
	public static String openKey(Key key) {
		switch (key.mode) {
			case MAJOR:	return cof(key.root.ordinal(), 3) + "d";
			case MINOR:	return cof(key.root.ordinal(), 0) + "m";
			default:	throw new IllegalArgumentException("unexpected mode: " + key.mode);
		}
	}
	
	private static int cof(int pitch, int offset) {
		return (pitch * 7 + offset) % 12 + 1;
	}
	
	//-------------------------------------------------------------------------
	
	private static final String[] SHARP	= {
		"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"
	};

	private static final String[] FLAT	= {
		"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"
	};
			
	public static String sharp(Key key) {
		return musician(key, SHARP);
	}
	
	public static String flat(Key key) {
		return musician(key, FLAT);
	}
	
	private static String musician(Key key, String[] names) {
		return names[key.root.ordinal()] + musicianMode(key.mode);
	}
	
	private static String musicianMode(Mode mode) {
		switch (mode) {
			case MAJOR:	return "";
			case MINOR:	return "m";
			default:	throw new IllegalArgumentException("unexpected mode: " + mode);
		}
	}
}
