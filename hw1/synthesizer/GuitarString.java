package synthesizer;

import java.util.ArrayList;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<>((int) (Math.round(SR / frequency)));

        int index = 0;
        while (index < buffer.capacity()) {
            buffer.enqueue(0.0);
            index += 1;
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        ArrayList<Double> numbers = new ArrayList<>(buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.dequeue();
            double r = Math.random() - 0.5;
            while (numbers.contains(r)) {
                r = Math.random() - 0.5;
            }
            buffer.enqueue(r);
            numbers.add(r);
        }
    }


    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double front = buffer.dequeue();
        double newSample = DECAY * 0.5 * (front + buffer.peek());
        buffer.enqueue(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}