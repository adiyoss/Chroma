package com.chroma.chroma;

import java.util.ArrayList;

/**
 * Created by adiyoss on 2/7/15.
 */

public interface iSignal2Chroma {
    /**
     * create chroma representation from the pitch energy
     * @param f_pitch_energy
     * @return chroma
     */
    public double[][] signal2Chroma(ArrayList<double[]> f_pitch_energy, boolean applyLogNormalize);
}
