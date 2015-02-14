package com.chroma.Consts;

/**
 * Created by adiyoss on 2/5/15.
 */
// This class stores all the constants in the code
public class ChromaConsts {

    public static final int winLenSTMSP = 4410;
    public static final int winOvSTMSP = winLenSTMSP/2;
    public static final int fs = 22050;
    public static final int featureRate =  fs/(winLenSTMSP-winOvSTMSP);
    public static final int sizeOfPitchFs = 120;
    public static final int midiMin = 20;
    public static final int midiMax = 107;
    public static final int pitch_size = 128;
    public static final int size_of_chroma = 12;
    public static final double normalize_threshold = 1.0000e-03;
    public static final int addTermLogCompr = 1;
    public static final int factorLogCompr = 100;

}
