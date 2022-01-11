package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        this.state = 0;
    }

    @Override
    public double next() {
        state++;
        int weirdState = state | (state >>> 1) | (state >>> 5) % period;
        return normalize(weirdState);
    }

    private double normalize(int weirdState) {
        return (double) 2 / period * (weirdState % period) - 1;
    }
}
