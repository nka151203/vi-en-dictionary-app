package DicAPI;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * This is where all begins .
 *
 * @author GOXR3PLUS
 *
 */
public class textToSpeechGoogleAPI {
    //Create a Synthesizer instance
    SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");

    /**
     * Constructor
     */
    public textToSpeechGoogleAPI() throws IOException {

        //Let's speak in English

        //Speak Chinese Fuckers
        //speak("我可以说你想要的任何语言！");

        //Let's Speak in Somalian
        //speak("Waxaan ku hadli karaa luqad aad rabto!");

        //Let's Speak in Hindi
        //speak("मैं चाहता हूं कि कोई भी भाषा बोल सकता हूँ!");

        //Let's Speak in Polish
        //speak("Mogę mówić dowolnym językiem, którego chcesz!");

        //Let's Speak in Persian       ----- This doens't work for some reason i have to figure out ... ;(
        //speak("من می توانم به هر زبان که می خواهید صحبت کنید!");

    }

    /**
     * Calls the MaryTTS to say the given text
     *
     * @param text
     */
    public void speak(String text) {
        try{
            Thread thread = new Thread(() -> {
                try {
                    //Create a JLayer instance
                    AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
                    player.play();

                    System.out.println("Successfull");

                } catch (IOException | JavaLayerException e) {

                    System.out.println("Không hỗ trợ nói từ này"); //Print the exception ( we want to know , not hide below our finger , like many developers do...)

                }
            });

            //We don't want the application to terminate before this Thread terminates
            thread.setDaemon(false);

            //Start the Thread
            thread.start();
        }catch (Exception e){

        }
        //Create a new Thread because JLayer is running on the current Thread and will make the application to lag
    }

}

