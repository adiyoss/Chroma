# Chroma 

Copyright (C) Adiyoss (2016)

A pitch and chroma implementation in Java
The implementation is based on http://resources.mpi-inf.mpg.de/MIR/chromatoolbox/

# Usage
After loading the .wav file using the `Wave` class, we can use the `Chroma` class to generate the chroma.

The `signal2Chroma` function gets as input a wave file and returns a 2D matrix which represents the chroma.

## Usage Example

```java
    String filename = "data/piano.wav"; // THE PATH TO THE WAV FILE
    Wave wave = new Wave(filename);
    Chroma chromaGenerator = new Chroma();
    double [][] chroma;
    chroma = chromaGenerator.signal2Chroma(wave.getNormalizedAmplitudes());
    DataAccess dal = new DataAccess();
    dal.writeMatrixToFile(chroma,"data/chroma.txt");
```

# Visualize
In order to visualize the chroma, you can use the Python script inside the python folder

```bash
    python plot_matrix.py --chroma 'data/chroma.txt'
```

