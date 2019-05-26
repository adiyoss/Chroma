/*
 * Chroma - Pitch and Chroma implementation in Java
 * Copyright (C) 2015 Yossi Adi, E-Mail: yossiadidrum@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
