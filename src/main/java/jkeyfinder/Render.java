package jkeyfinder;

import java.util.Optional;

public final class Render {
	private Render() {}
	
	public static String output(final Optional<Key> keyOpt) {
		if (keyOpt.isPresent()) {
			final Key	key	= keyOpt.get(); 
			return Render.ibsh(key) + "\t" + Render.camelot(key) + "\t" + Render.openKey(key) + "\t" + Render.musician(key, false)+ "\t" + Render.musician(key, true);
		}
		else {
			return "SILENCE";
		}
	}
	
	public static String ibsh(Key key) {
		return key.name();
	}
	
	public static String camelot(Key key) {
		switch (key.mode) {
			case MAJOR:	return cof(key.note, 10) + "B";
			case MINOR:	return cof(key.note, 7)  + "A";
			default:	throw new IllegalArgumentException("unexpected mode: " + key.mode);
		}
	}
	
	public static String openKey(Key key) {
		switch (key.mode) {
			case MAJOR:	return cof(key.note, 3) + "d";
			case MINOR:	return cof(key.note, 0) + "m";
			default:	throw new IllegalArgumentException("unexpected mode: " + key.mode);
		}
	}
	
	private static int cof(int note, int offset) {
		return (note * 7 + offset) % 12 + 1;
	}
	
	private static final String[] SHARP	= {
		"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"
	};

	private static final String[] FLAT	= {
		"A", "Bb", "B", "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab"
	};
			
	public static String musician(Key key, boolean sharp) {
		switch (key.mode) {
			case MAJOR:	return (sharp ? SHARP : FLAT)[key.note] + "M";
			case MINOR:	return (sharp ? SHARP : FLAT)[key.note] + "m";
			default:	throw new IllegalArgumentException("unexpected mode: " + key.mode);
		}
	}
}
