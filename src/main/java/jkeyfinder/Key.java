package jkeyfinder;

public final class Key {
	public final Pitch	pitch;
	public final Mode	mode;

	public Key(Pitch pitch, Mode mode) {
		this.pitch	= pitch;
		this.mode	= mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((pitch == null) ? 0 : pitch.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Key other = (Key) obj;
		if (mode != other.mode) return false;
		if (pitch != other.pitch) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Key [pitch=" + pitch + ", mode=" + mode + "]";
	}
}
