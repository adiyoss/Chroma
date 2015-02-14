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
