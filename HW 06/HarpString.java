/**
 * Name : Hector Herrera
 * PennKey : Hectorh
 * Recitation : 216
 * 
 * Execution: Java HarpString
 * 
 * Will use RingBuffer to represent a string when it is plucked and will
 * implement the Karplus-Strong algorithm
 */

public class HarpString {
    private RingBuffer buffer; // ring buffer
    private static double SAMPLING_RATE = 44100.0; // represents 44100 Hz
    private static double DECAY_FACTOR = -0.997; //helps implement KarplusStrong
    private int count; // created to store a value             
    
    /**
     * Description: create a harp string of the given frequency
     * 
     * Input: a double representing the frequency
     * 
     * Output: a HarpString with the length of the SAMPLING_RATE / frequency
     */
    public HarpString(double frequency) {
        //will round up SAMPLING_RATE / frequency and will be used to create 
        //the buffer of numSamples length
        int numSamples = (int) Math.ceil(SAMPLING_RATE / frequency);
        buffer = new RingBuffer(numSamples);
        
        //will fill in the buffer with values of 0
        for (int i = 0; i < numSamples; i++) {
            buffer.enqueue(0);
        }
        //buffer.printBufferContents();
    }
    
    /**
     * Description: pluck the harp string by replacing the buffer with white 
     * noise.
     * 
     * Input: none
     * 
     * Output: void
     */
    public void pluck() {
        //fill in the buffer with random samples 
        for (int i = 0; i < buffer.currentSize(); i++) {
            
            //randomSamples will be [-0.5, 0.5)
            double randomSamples = -0.5 * Math.random() + 0.5;
            
            //empties the buffer so that it can filled up with randomSamples
            buffer.dequeue();
            buffer.enqueue(randomSamples);
        }
        //buffer.printBufferContents();
    }
    
    /**
     * Description: advance the simulation one time step
     * 
     * Input: none
     * 
     * Output: void
     */
    public void tic() {
        //will store the value of the first sample
        double firstSample = buffer.peek();
        
        //deletes the first sample
        buffer.dequeue();
        
        //stores the value of the second sample
        double secondSample = buffer.peek();
        
        //implements math of the Karplus-Strong algorithm
        double karplus = DECAY_FACTOR * (firstSample + secondSample) / 2;
        buffer.enqueue(karplus);
        
        //increases the count each time tic() is called
        count++;
    }
    
    /**
     * Description: return the current sample
     * 
     * Input: none
     * 
     * Output: a double returning the value of buffer[first]
     */
    public double sample() {
        return buffer.peek();
    }
    
    /**
     * Description: return number of times tic was called
     * 
     * Input: none
     * 
     * Output: will return an int that represents the number of times tic was 
     * called
     */
    public int time() {
        return count; 
    }
    
    /**
     * Description: it is where we test our methods
     * 
     * Input: String[] args that will represent the number of samples to "play"
     * 
     * Output: void
     */
    public static void main(String[] args) {
        // how many samples should we "play"
        int numSamplesToPlay = Integer.parseInt(args[0]);
        
        // a starting set of samples; it's pretty easy to calculate
        // the new samples that will get generated with a calculator
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        
        // create a harp string to test with exactly samples.length,
        // this looks a little funny because the HarpString constructor
        // expects a frequency, not a number of elements
        HarpString testString = new HarpString(44100.0 / samples.length);
        
        // at this point the RingBuffer underlying testString should have
        // a capacity of samples.length and should be full
        System.out.println("testString.buffer.isEmpty(): " + 
                           testString.buffer.isEmpty());
        System.out.println("testString.buffer.isFull():  " + 
                           testString.buffer.isFull());
        
        // replace all the zeroes with the starting samples
        for (int i = 0; i < samples.length; i++) {
            testString.buffer.dequeue();
            testString.buffer.enqueue(samples[i]);
        }
        
        // "play" for numSamples samples; printing each one for inspection
        for (int i = 0; i < numSamplesToPlay; i++) {
            int t = testString.time();
            double sample = testString.sample();
            
            // this statement prints the time t, padded to 6 digits wide
            // and the value of sample padded to a total of 8 characters
            // including the decimal point and any - sign, and rounded
            // to four decimal places
            System.out.printf("%6d %8.4f\n", t, sample);
            
            testString.tic(); // advance to next sample
        }
        
        //WILL TEST PLUCK() METHOD
        //testString.pluck();
    }
}