package jkeyfinder;

public final class Key {
	public final Pitch	root;
	public final Mode	mode;

	public Key(Pitch root, Mode mode) {
		this.root	= root;
		this.mode	= mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Key other = (Key) obj;
		if (mode != other.mode) return false;
		if (root != other.root) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Key [root=" + root + ", mode=" + mode + "]";
	}
}
