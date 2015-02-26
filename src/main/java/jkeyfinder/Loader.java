package jkeyfinder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;

public final class Loader {
	private static final float normFactor	= -1f / -(float)Short.MIN_VALUE;
	
	/** expects raw pcm data; 16 bit, signed, little endian, mono */
	public static Audio load(final File file, final double frameRate) throws IOException {
		try(final RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			final ByteBuffer	byteBuffer	= raf.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			final ShortBuffer	shortBuffer	= byteBuffer.asShortBuffer();
			return new Audio() {
				public double frameRate() {
					return frameRate;
				}
				public int frameCount() {
					return shortBuffer.limit();
				}
				public float get(int frame) {
					return shortBuffer.get(frame) * normFactor;
				}
			};
		}
	}
}
