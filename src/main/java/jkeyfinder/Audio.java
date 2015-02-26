package jkeyfinder;

public interface Audio {
	double frameRate();
	int frameCount();
	float get(int frame);
}
