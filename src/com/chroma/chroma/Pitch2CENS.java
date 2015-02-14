package com.chroma.chroma;

import com.chroma.Consts.ChromaConsts;

import java.util.ArrayList;

/**
 * Created by adiyoss on 2/7/15.
 */
public class Pitch2CENS implements iSignal2Chroma{

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
        double[] quantSteps = {0.4,0.2,0.1,0.05};
        double[] quantWeights = {0.25,0.25,0.25,0.25};
        double[][] f_chroma_energy = new double[seg_num][ChromaConsts.size_of_chroma];
        double[][] f_chroma_energy_distr = new double[seg_num][ChromaConsts.size_of_chroma];
        double[][] f_CENS;

        // apply log-normalize
        if(applyLogNormalize) {
            for (int i = 0; i < f_pitch_energy.size(); i++) {
                for (int j = 0; j < seg_num; j++)
                    f_pitch_energy.get(i)[j] = Math.log10(ChromaConsts.addTermLogCompr + f_pitch_energy.get(i)[j] * ChromaConsts.factorLogCompr);
            }
        }

        // calculate energy for each chroma band
        for(int p = 0; p<f_pitch_energy.size(); p++){
            int chroma = (p+1)%ChromaConsts.size_of_chroma;
            for(int j = 0; j<f_pitch_energy.get(p).length ; j++){
                f_chroma_energy[j][chroma] += f_pitch_energy.get(p)[j];
            }
        }

        // normalize the chroma vectors
        for(int k = 0; k<seg_num ; k++){
            if(sumOverThreshold(f_chroma_energy[k],ChromaConsts.normalize_threshold) > 0){
                double seg_energy_square = sum(f_chroma_energy[k]);
                for(int i=0 ; i<f_chroma_energy[k].length ; i++)
                    f_chroma_energy_distr[k][i] = f_chroma_energy[k][i] / seg_energy_square;
            }
        }

        // ====== calculate a CENS feature ====== //
        // component-wise quantisation of the normalized chroma vectors
        double[][] f_start_help = new double[seg_num][ChromaConsts.size_of_chroma];
        for (int n = 0; n< quantSteps.length; n++){
            for(int i=0 ; i<f_start_help.length ; i++){
                for(int j=0 ; j<f_start_help[i].length ; j++)
                    if(f_chroma_energy_distr[i][j] > quantSteps[n])
                        f_start_help[i][j] += quantWeights[n];
            }
        }

        // last step: normalize each vector with its l^2 norm
        f_CENS = helpers.normalizeChroma(f_start_help);
        return f_CENS;
    }


    /**
     * Calculate the sum of the vector x
     * @param x
     * @return the sum
     */
    private double sum(double[] x){
        double res = 0.0;
        for(int i=0 ; i<x.length ; i++)
            res+=x[i];
        return res;
    }

    /**
     * Counts how many items in x where above threshold
     * @param x
     * @param threshold
     * @return
     */
    private int sumOverThreshold(double[] x, double threshold){
        int res = 0;
        for(int i=0 ; i<x.length ; i++){
            if(x[i] > threshold)
                res++;
        }

        return res;
    }
}
