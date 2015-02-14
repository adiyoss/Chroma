package com.chroma.chroma;

import com.chroma.Consts.ChromaConsts;
import com.chroma.Consts.IirFilterCoeffA;
import com.chroma.Consts.IirFilterCoeffB;

import java.util.ArrayList;

/**
 * Created by adiyoss on 2/5/15.
 */
public class Chroma {

    // data members
    private iSignal2Chroma signal2Chroma = new Pitch2Chroma();

    /**
     *
     * @param x - audio signal, should be normalized
     * @return the chroma
     */
    public double[][] signal2Chroma(double[] x) {

        int[] fs_pitch = new int[ChromaConsts.pitch_size];
        int[] fs_index = new int[ChromaConsts.pitch_size];
        int wav_size = x.length;

        //====================================//
        //          RESAMPLE THE WAVE		  //
        //====================================//
        initPitchAndIndex(fs_pitch,fs_index);
        double[][] pcm_ds = new double[3][];
        pcm_ds[0] = x;
        pcm_ds[1] = resample(pcm_ds[0],1,5);
        pcm_ds[2] = resample(pcm_ds[1],1,5);
        //====================================//

        //====================================//
        //  COMPUTE FEATURES FOR ALL PITCHES  //
        //====================================//
        ArrayList<double[]> f_pitch_energy = new ArrayList<double[]>();
        ArrayList<Integer> seg_pcm_num = new ArrayList<Integer>();
        ArrayList<Integer> seg_pcm_start = new ArrayList<Integer>();
        ArrayList<Integer> seg_pcm_stop = new ArrayList<Integer>();
        ArrayList<FilterCoefficientsEntity> coefficients;

        // initialize vectors
        int step_size = ChromaConsts.winLenSTMSP - ChromaConsts.winOvSTMSP;
        int group_delay = Math.round(ChromaConsts.winLenSTMSP / 2);
        seg_pcm_start.add(1);
        for(int i = 0; i<wav_size/step_size+1 ; i++)
            seg_pcm_start.add(1 + step_size*i);
        for (int i = 0; i<seg_pcm_start.size() ; i++)
            seg_pcm_stop.add(Math.min(seg_pcm_start.get(i) + ChromaConsts.winLenSTMSP, wav_size));
        seg_pcm_stop.set(0, Math.min(group_delay, wav_size));
        seg_pcm_num.add(0,seg_pcm_start.size());

        for(int i=0 ; i<ChromaConsts.sizeOfPitchFs ; i++) {
            double[] row = new double[seg_pcm_num.get(0)];
            for (int j = 0; j < row.length; j++)
                row[j] = 0.0;
            f_pitch_energy.add(row);
        }
        //====================================//
        coefficients = getIIRCoeff(); // Load the IIR Filter Parameters

        //====================================//
        //   	  COMPUTE F_PITCH_ENERGY 	  //
        //====================================//
        for(int p=ChromaConsts.midiMin ; p<ChromaConsts.midiMax ; p++){
            // IIR Filter
            int index = fs_index[p];
            double[] f_filtfilt = filter(coefficients.get(p).b, coefficients.get(p).a, pcm_ds[index]);
            double[] f_square = square_arr(f_filtfilt);

            int factor = ChromaConsts.fs/fs_pitch[p];
            for (int k = 0; k<seg_pcm_num.get(0); k++){
                int start = (int)Math.ceil(((double)seg_pcm_start.get(k) / ChromaConsts.fs) * fs_pitch[p]);
                int stop = (int)Math.floor(((double)seg_pcm_stop.get(k) / ChromaConsts.fs) * fs_pitch[p]);
                double tmp = 0;
                for(int t = start-1 ; t< stop-1 ; t++)
                    tmp += f_square[t]*factor;
                f_pitch_energy.get(p)[k]=tmp;
            }
        }

        // creating the chroma by the desired algorithm
        double[][] chroma = signal2Chroma.signal2Chroma(f_pitch_energy,false);
        return chroma;
    }

    /**
     * This function gets as input a wave signal and its a and b coefficients for the iir filter and returns the filtered signal using iir filter
     * @param b
     * @param a
     * @param x
     * @return filtered signal
     */
    private double[] filter(double[] b, double[] a, double[] x)
    {
        double[] y = new double[x.length];
        int na = a.length;
        int nb = b.length;

        for(int n = 0; n<x.length ; n++){
            y[n] = b[0]*x[n];
            for(int i=0 ; i<Math.min(n,nb-1); i++)
                y[n] +=  b[i + 1] * x[n - i - 1];
            for(int i=0 ; i<Math.min(n,na-1); i++)
                y[n] -= a[i + 1] * y[n - i - 1];
        }

        return y;
    }

    /**
     * This function gets as input an array list of doubles and returns the square array
     * @param f_filtfilt
     * @return filter square
     */
    private double[] square_arr(double[] f_filtfilt){
        double[] result = new double[f_filtfilt.length];
        for(int i=0 ; i<f_filtfilt.length ; i++)
            result[i] = Math.pow(f_filtfilt[i],2);
        return result;
    }

    /**
     * resample the signal x to be from (x.length*p)/q
     * @param x
     * @param p
     * @param q
     * @return the new signal
     */
    private double[] resample(double[] x, int p, int q){
        double[] y = new double[(x.length*p)/q];
        for(int k=0 ; k<y.length ; k++)
            y[k] = x[Math.round((k)*q/p)];
        return y;
    }



    /**
     * initialize the pitch values and index values
     * @param pitch
     * @param index
     */
    private void initPitchAndIndex(int[] pitch, int[] index){
        int start_882 = 20;
        int start_4410 = 59;
        int start_22050 = 95;
        int end_22050 = 120;
        int i = 0;

        for(i=0 ; i<start_882 ; i++){
            pitch[i] = 0;
            index[i] = -1;
        }
        for(i=start_882 ; i<start_4410 ; i++){
            pitch[i] = 882;
            index[i] = 2;
        }
        for(i=start_4410 ; i<start_22050 ; i++){
            pitch[i] = 4410;
            index[i] = 1;
        }
        for(i=start_22050 ; i<end_22050 ; i++){
            pitch[i] = 22050;
            index[i] = 0;
        }
        for(i=end_22050 ; i<pitch.length ; i++){
            pitch[i] = 0;
            index[i] = -1;
        }
    }

    private ArrayList<FilterCoefficientsEntity> getIIRCoeff(){
        ArrayList<FilterCoefficientsEntity> coefficients = new ArrayList<FilterCoefficientsEntity>();

        // populate the coefficients array
        for(int i=0 ; i< IirFilterCoeffA.a.length ; i++){
            FilterCoefficientsEntity coefficient = new FilterCoefficientsEntity();
            coefficient.a = IirFilterCoeffA.a[i];
            coefficient.b = IirFilterCoeffB.b[i];
            coefficients.add(coefficient);
        }
        return coefficients;
    }
}
