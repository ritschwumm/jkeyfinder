package jkeyfinder;

public enum Key {
	A_MAJOR		(Mode.MAJOR, 0),
	A_MINOR		(Mode.MINOR, 0),
	B_FLAT_MAJOR(Mode.MAJOR, 1),
	B_FLAT_MINOR(Mode.MINOR, 1),
	B_MAJOR		(Mode.MAJOR, 2),
	B_MINOR		(Mode.MINOR, 2),
	C_MAJOR		(Mode.MAJOR, 3),
	C_MINOR		(Mode.MINOR, 3),
	D_FLAT_MAJOR(Mode.MAJOR, 4),
	D_FLAT_MINOR(Mode.MINOR, 4),
	D_MAJOR		(Mode.MAJOR, 5),
	D_MINOR		(Mode.MINOR, 5),
	E_FLAT_MAJOR(Mode.MAJOR, 6),
	E_FLAT_MINOR(Mode.MINOR, 6),
	E_MAJOR		(Mode.MAJOR, 7),
	E_MINOR		(Mode.MINOR, 7),
	F_MAJOR		(Mode.MAJOR, 8),
	F_MINOR		(Mode.MINOR, 8),
	G_FLAT_MAJOR(Mode.MAJOR, 9),
	G_FLAT_MINOR(Mode.MINOR, 9),
	G_MAJOR		(Mode.MAJOR, 10),
	G_MINOR		(Mode.MINOR, 10),
	A_FLAT_MAJOR(Mode.MAJOR, 11),
	A_FLAT_MINOR(Mode.MINOR, 11);
	
	public final Mode	mode;
	public final int	note;

	private Key(Mode mode, int note) {
		this.mode	= mode;
		this.note	= note;
	}
}
