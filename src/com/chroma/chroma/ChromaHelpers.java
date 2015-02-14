package com.chroma.chroma;

import com.chroma.Consts.ChromaConsts;

/**
 * Created by adiyoss on 2/7/15.
 */
public class ChromaHelpers {
    /**
     *
     * @param x - the chroma values
     * @return the normalized chroma
     */
    public double[][] normalizeChroma(double[][] x){
        double[][] normalizedChroma = new double[x.length][ChromaConsts.size_of_chroma];
        double[] unitVector = new double[ChromaConsts.size_of_chroma];

        // initializing and normalizing the unit vector
        for(int i=0 ; i<unitVector.length ; i++)
            unitVector[i] = 1;
        double norm = norm_2(unitVector);
        for(int i=0 ; i<unitVector.length ; i++)
            unitVector[i] /= norm;


        // normalise the vectors according to the l^2 norm
        for(int k=0 ; k<x.length ; k++){
            double n = norm_2(x[k]);
            if (n < ChromaConsts.normalize_threshold)
                for(int i=0 ; i<unitVector.length;  i++)
                    normalizedChroma[k][i] = unitVector[i];
            else {
                for(int i=0 ; i<x[k].length ; i++)
                    normalizedChroma[k][i] = x[k][i]/n;
            }
        }
        return normalizedChroma;
    }

    /**
     * compute norm 2 for x
     * @param x
     * @return
     */
    private double norm_2(double[] x){
        double res = 0;
        for(int i=0 ; i<x.length ; i++)
            res += x[i]*x[i];
        res = Math.sqrt(res);
        return res;
    }
}
