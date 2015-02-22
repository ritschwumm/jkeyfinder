package jkeyfinder;

import java.io.File;
import java.util.Optional;

import org.jtransforms.utils.ConcurrencyUtils;

public final class Main {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("usage: keyfinder framerate file");
			System.exit(1);
		}
		
		final int	frameRate	= Integer.parseInt(args[0]);
		final File	file		= new File(args[1]);

		// don't spawn any daemon threads
		ConcurrencyUtils.setNumberOfThreads(1);
	
		final Audio			audio	= Loader.load(file, frameRate);
		final Optional<Key>	keyOpt	= KeyFinder.findKey(audio);
		final String		str		= Render.output(keyOpt);
		System.out.println(str);
	}
}
