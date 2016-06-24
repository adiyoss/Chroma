import argparse
import numpy as np
from matplotlib import pyplot as plt


def main(path):
    # read the data
    data = list()
    with open(path) as f:
        for line in f:
            row = list()
            vals = line.split()
            for val in vals:
                row.append(val)
            data.append(row)
    chrom = np.asarray(data, dtype='float')

    # plot the chroma
    notes = ['C ', 'C#', 'D ', 'D#', 'E ', 'F ', 'F#', 'G ', 'G#', 'A ', 'A#', 'B ']
    idx = np.arange(len(notes))
    plt.imshow(chrom)
    plt.yticks(idx, notes)
    plt.show()


if __name__ == "__main__":
    # -------------MENU-------------- #
    # command line arguments
    parser = argparse.ArgumentParser()
    parser.add_argument("--chroma", help="path to the chroma txt file", default='../data/chroma.txt')

    args = parser.parse_args()

    # main function
    main(args.chroma)
