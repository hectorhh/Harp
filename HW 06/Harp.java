/**
 * Name : Hector Herrera
 * PennKey : Hectorh
 * Recitation : 216
 * 
 * Execution: Java Harp
 * 
 * Will use HarpString to simulate a 37-string harp with notes ranging from 
 * 110 Hz to 880 Hz.
 */
public class Harp {
    private static double concertA = 440.0; //tuning frequency
    
    //represents the 37 keys that will make up the strings of the harp
    private static String NOTE_MAPPING = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    /**
     * Description: Creates a 37-string harp with notes ranging from 
     * 110 Hz to 880 Hz 
     * 
     * Input: String[] args
     * 
     * Output: void
     */
    public static void main(String[] args) {
        // create an array of harp strings
        HarpString[] string = new HarpString [NOTE_MAPPING.length()];
        
        //will fill in the array of harp strings with values
        for (int i = 0; i < NOTE_MAPPING.length(); i++) {
            double concert = concertA * Math.pow(2.0, (i - 24.0) / 12.0);
            string[i] = new HarpString(concert); 
        }
        
        // infinite loop to check if a key is pressed and play the 
        // associated note
        while (true) {
            // check if the user has typed a key; if so, process it 
            if (PennDraw.hasNextKeyTyped()) {
                char key = PennDraw.nextKeyTyped();
                int index = NOTE_MAPPING.indexOf(key);
                // checks if the key played is in NOTE_MAPPING
                if (index != -1) {
                    string[index].pluck();     
                }
            }
            
            // compute the combined sound of all harp strings
            // sample will store the value
            double sample = 0;
            for (int i = 0; i < string.length; i++) {            
                sample += string[i].sample();
            }    
            
            // play the sample on standard audio
            StdAudio.play(sample);
            
            //for loop to continue advancing to the next sample 
            for (int i = 0; i < string.length; i++) {
                string[i].tic();
            }
        }
    }
}


