/**
 * Name : Hector Herrera
 * PennKey : Hectorh
 * Recitation : 216
 * 
 * Execution: Java RingBuffer
 * 
 * The ring buffer models the medium (a string tied down at both ends) in which 
 * the energy travels back and forth.
 */
public class RingBuffer {
    private double[] bufferArray; // items in the buffer
    private int first;            // index for the next dequeue or peek
    private int last;             // index for the next enqueue
    private int currentSize;      // number of items in the buffer
    
    /**
     * Description: Will create an empty buffer, with given 
     * max capacity
     * 
     * Input: an int that will represent the length of the bufferArray
     * 
     * Output: an empty buffer of length capacity
     */
    public RingBuffer(int capacity) {
        //create a bufferArray of length capacity
        bufferArray = new double [capacity];
    }
    
    /**
     * Description: Will return the number of items currently in the buffer
     * 
     * Input: none
     * 
     * Output: an int that represents the number of items currently
     * in the buffer
     */
    public int currentSize() {
        return currentSize;
    }
    
    /**
     * Description: will check if buffer is empty or not
     * 
     * Input: none
     * 
     * Output: boolean that will return true if it's Empty or false if it is not
     */
    public boolean isEmpty() {
        //checks if we have put anything into it
        if (bufferArray.length != currentSize && currentSize == 0) return true;
        else return false;
    }
    
    /**
     * Description: Will check if the buffer size is equal to the array capacity
     * 
     * Input: none
     * 
     * Output: boolean that will return true if the array is full and false
     * if it is not full
     */
    public boolean isFull() {
        //checks to see if they array is at max capacity
        if (currentSize == bufferArray.length) return true;
        else return false;
    }
    
    /**
     * Description: add item x to the end of the array
     * 
     * Input: will take in a double x representing the number that you have 
     * to enqueue into the array
     * 
     * Output: void
     */
    public void enqueue(double x) { 
        if (isFull()) {
            throw new RuntimeException("ERROR: Attempting to enqueue " +
                                       "to a full buffer.");
        }
        else {
            //will update bufferArray of index last by x
            bufferArray[last] = x;
            
            //will be in charge of increasing last and will wrap the array if
            //last is at the very end of the buffer
            if (last == bufferArray.length - 1) last = 0;
            else last++;
            
            //will increase the currentSize every time this method is called
            currentSize++;
        }
    }
    
    /**
     * Description: Will delete and return item from the front
     * 
     * Input: none
     * 
     * Output: will return the item from the front that was deleted
     */
    public double dequeue() {
        //declare the variable to hold the value that will be deleted
        double temp = 0;
        
        if (isEmpty()) {
            throw new RuntimeException("ERROR: Attempting to dequeue " +
                                       "from an empty buffer.");
        }
        else {
            //temp will hold the value of bufferArray[first]
            temp = bufferArray[first];
            
            //will delete the bufferArray of index first by setting it equal 
            //to 0
            bufferArray[first] = 0;
            
            //will be in charge of increasing first and will wrap the array if
            //first is at the very end of the buffer
            if (first == bufferArray.length - 1) first = 0;
            else first++;
            
            //will decrease the currentSize every time this method is called
            currentSize--;
        }
        //return the value that was deleted
        return temp;
    }
    
    /**
     * Description: Will return the item from the front without deleting it
     * 
     * Input: none
     * 
     * Output: Will return a double representing the item from the front of the 
     * array
     */
    public double peek() {
        if (isEmpty()) {
            throw new RuntimeException("ERROR: Attempting to peek " +
                                       "at an empty buffer.");
        }
        // will return the first element of the buffer if it is not empty
        else return bufferArray[first]; 
    }
    
    /**
     * Description: Will print the contents of the RingBuffer object
     * 
     * Input: none
     * 
     * Output: void
     */
    private void printBufferContents() {
        // print out first, last, and currentSize
        System.out.println("first:        " + first);
        System.out.println("last:         " + last);
        System.out.println("currentSize:  " + currentSize);
        
        // print bufferArray's length and contents if it is not null
        // otherwise just print a message that it is null
        if (bufferArray != null) {
            System.out.println("array length: " + bufferArray.length);
            System.out.println("Buffer Contents:");
            for (int i = 0; i < bufferArray.length; i++) {
                System.out.println(bufferArray[i]);
            }
        } 
        else {
            System.out.println("bufferArray is null");
        }
    }
    
    /**
     * Description: Will test the constructor and methods in RingBuffer
     * 
     * Input: Sring[] args that that represents the bufferSize
     * 
     * Output: void
     */
    public static void main(String[] args) {
        // create a RingBuffer with bufferSize elements
        // where bufferSize is a command-line argument
        int bufferSize = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(bufferSize);
        
        /* TEST CODE
         buffer.enqueue(.2);
         buffer.enqueue(0);
         buffer.enqueue(.4);
         
         System.out.println("It is empty: " + buffer.isEmpty());
         System.out.println("It is full: " + buffer.isFull());
         System.out.println("The first item is: " + buffer.peek());
         */
        
        //will print all of the contents in the buffer
        buffer.printBufferContents();
    }
}