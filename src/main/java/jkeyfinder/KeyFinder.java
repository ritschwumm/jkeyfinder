package jkeyfinder;

import static java.lang.Math.*;
import static jkeyfinder.Util.*;

import java.util.Optional;

import org.jtransforms.fft.DoubleFFT_1D;

public final class KeyFinder {
	private KeyFinder() {}
	
	/** absent means "silence" */
	public static Result examine(Audio audio) {
		final float[][]		chromagram	= chromagramTA(audio);
		final float[]		strengths	= collapseTO(chromagram);
		final int			strongest	= strongest(strengths);
		final Optional<Key>	key			= classify(strengths);
		return new Result(key, chromagram, strengths, strongest);
	}

	//-------------------------------------------------------------------------
	//## chromagram generation
	
	private static float[][] chromagramTA(Audio audio) {
		final int sampleCount	= audio.frameCount();
		final int hops			= divUp(sampleCount, Config.hopSize);
		
		final Kernel[]		kernels	= skKernels(audio.frameRate());
		final DoubleFFT_1D	fft		= new DoubleFFT_1D(Config.fftSize);
		final double[]		buffer	= new double[Config.fftSize * 2];
		
		final float[]	window	= new float[Config.fftSize];
		for (int i=0; i<Config.fftSize; i++) {
			window[i]	= blackmanWindow(i, Config.fftSize);
		}

		final float[][] chromagram	= new float[hops][];
		int hopStart	= 0;
		for (int hop=0; hop<hops; hop++) {
			for (int i=0; i<Config.fftSize; i++) {
				final int frame	= hopStart+i;
				buffer[i]	= frame < sampleCount ? audio.get(frame) * window[i] : 0;
			}
			
			fft.realForwardFull(buffer);
			
			final float[]	chromaVector	= chromagramVA(kernels, buffer);
			chromagram[hop]	= chromaVector;
			
			hopStart	+= Config.hopSize;
		}
		return chromagram;
	}
	
	private static Kernel[] skKernels(double frameRate) {
		final Kernel[]	kernels		= new Kernel[Config.chromaSize];
		final double	binFraction	= 1.0 / Config.semitones;
		final float		qFactor		= (float)(Config.skStretch * (pow2(binFraction) - 1));
		
		for (int bin=0; bin<Config.chromaSize; bin++) {
			final float centerFrequency		= (float)(Config.startFreq * pow2(bin * binFraction));
			
			final float windowCenter		= (float)(centerFrequency * Config.fftSize / frameRate);
			final float windowSize			= windowCenter	* qFactor;
			final float windowStart			= windowCenter	- windowSize/2;
			final float windowEnd			= windowCenter	+ windowSize/2;

			final int integralStart	= (int)round(ceil(windowStart));
			final int integralEnd	= (int)round(floor(windowEnd)) + 1;
			final int integralSize	= integralEnd - integralStart;
			if (integralStart	<= 0)					throw new IllegalArgumentException("window start too low");
			if (integralEnd		>= Config.fftSize/2)	throw new IllegalArgumentException("window end too high");
			if (integralEnd		<= 0)					throw new IllegalArgumentException("window too small");
			
			final float[] coefficients	= new float[integralSize];
			float coefficientSum	= 0f;
			for (int i=0; i<integralSize; i++) {
				final float coefficient	= kernelWindow(integralStart - windowStart + i, windowSize);
				coefficients[i]		= coefficient;
				coefficientSum		+= coefficient;
			}
			
			final float normalFactor	= centerFrequency / coefficientSum;
			for (int i=0; i<integralSize; i++) {
				coefficients[i]	*= normalFactor;
			}
			kernels[bin] = new Kernel(integralStart, coefficients);
		}
		
		return kernels;
	}
	
	private static float kernelWindow(float position, float size) {
		return (float)(1.0 - cos((2 * PI * position) / size));
	}
	
	private static float[] chromagramVA(Kernel[] kernels, double[] fftResult) {
		final float[]	result = new float[kernels.length];
		for (int bin=0; bin<kernels.length; bin++) {
			final Kernel	kernel			= kernels[bin];
			final int		binOffset		= kernel.binOffset;
			final float[]	coefficients	= kernel.coefficients;
			
			float sum = 0f;
			for (int i=0; i<coefficients.length; i++) {
				final int		fftOffset	= (binOffset + i) * 2;
				final double	real		= fftResult[fftOffset + 0];
				final double	imag		= fftResult[fftOffset + 1];
				final float		magnitude	= (float)complexLength(real, imag);
				final float		value		= magnitude * coefficients[i];
				sum += value;
			}
			
			result[bin]	= sum;
		}
		return result;
	}
	
	//-------------------------------------------------------------------------
	//## classification
	
	/** absent means "silence" */
	private static int strongest(float[] inputVO) {
		int		bestNote	= -1;
		float	bestScore	= Float.MIN_NORMAL;
		for (int note=0; note<inputVO.length; note++) {
			final float score	= inputVO[note];
			if (score > bestScore) {
				bestScore	= score;
				bestNote	= note;
			}
		}
		return bestNote;
	}

	private static float[] collapseTO(float[][] inputTA) {
		final float[] outputVO	= new float[Config.semitones];
		for (int hop=0; hop<inputTA.length; hop++) {
			final float[] inputVA	= inputTA[hop];
			int targetBin	= 0;
			for (int bin=0; bin<inputVA.length; bin++) {
				outputVO[targetBin]	+= inputVA[bin];
				targetBin	+= 1;
				// start over at the beginning of an octave again
				if (targetBin == Config.semitones)	targetBin	= 0;
			}
		}
		return outputVO;
	}

	/** absent means "silence" */
	private static Optional<Key> classify(float[] inputVO) {
		Optional<Key>	bestKey		= Optional.empty();
		float			bestScore	= score(Config.profile.scale(Optional.<Mode>empty()), 0, inputVO);
		for (Mode mode : Mode.values()) {
			for (Pitch pitch : Pitch.values()) {
				final Scale	scale	= Config.profile.scale(Optional.of(mode));
				final float score	= score(scale, pitch.ordinal(), inputVO);
				if (score > bestScore) {
					bestScore	= score;
					bestKey		= Optional.of(new Key(pitch, mode));
				}
			}
		}
		return bestKey;
	}

	/** cosine similarity */
	private static float score(Scale scale, int offset, float[] inputVO) {
		float intersectionSum	= 0f;
		float profileSum		= 0f;
		float inputSum			= 0f;
		for (int bin=0; bin<inputVO.length; bin++) {
			final float	scaleValue	= scale.get(bin - offset);
			final float	inputValue	= inputVO[bin];
			intersectionSum	+= inputValue * scaleValue;
			profileSum		+= scaleValue * scaleValue;
			inputSum		+= inputValue * inputValue;
		}
		return profileSum > 0 && inputSum > 0
				? (float)(intersectionSum / (sqrt(profileSum) * sqrt(inputSum)))
				: 0;
	}
}
