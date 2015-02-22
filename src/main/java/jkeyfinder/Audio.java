package jkeyfinder;

public interface Audio {
	int frameRate();
	int frameCount();
	float get(int frame);
}
