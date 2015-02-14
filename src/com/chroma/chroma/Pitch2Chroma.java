package com.chroma.chroma;

import com.chroma.Consts.ChromaConsts;

import java.util.ArrayList;

/**
 * Created by adiyoss on 2/7/15.
 */
public class Pitch2Chroma implements iSignal2Chroma {

    // data members
    ChromaHelpers helpers = new ChromaHelpers();

    /**
     * create chroma representation from the pitch energy
     * @param f_pitch_energy
     * @return chroma
     */
    public double[][] signal2Chroma(ArrayList<double[]> f_pitch_energy, boolean applyLogNormalize){
        // parameters
        int seg_num = f_pitch_energy.get(0).length;
        double[][] f_chroma = new double[seg_num][ChromaConsts.size_of_chroma];

        // apply log-normalize
        if(applyLogNormalize) {
            for (int i = 0; i < f_pitch_energy.size(); i++) {
                for (int j = 0; j < seg_num; j++)
                    f_pitch_energy.get(i)[j] = Math.log10(ChromaConsts.addTermLogCompr + f_pitch_energy.get(i)[j] * ChromaConsts.factorLogCompr);
            }
        }

        //calculate energy for each chroma band
        for(int p = 0; p<f_pitch_energy.size(); p++){
            int chroma = (p+1)%ChromaConsts.size_of_chroma;
            for(int j = 0; j<seg_num ; j++){
                f_chroma[j][chroma] += f_pitch_energy.get(p)[j];
            }
        }

        // normalize each vector with its l^2 norm
        f_chroma = helpers.normalizeChroma(f_chroma);

        return f_chroma;
    }
}
