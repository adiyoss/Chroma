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
