package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    int period;
    double factor;
    int state;
    int counter;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;
        this.counter = 0;
    }

    @Override
    public double next() {
        state++;
        counter++;
        if (counter % period == 0) {
            period = (int) (period * factor);
            counter = 0;
        }
        return (double) 2 / period * (counter % period) - 1;
    }
}
