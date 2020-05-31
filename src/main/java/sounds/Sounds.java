/*
 * The Sounds class manages the sounds for the game.
 */
package sounds;

import java.io.File;
import java.io.IOException;
import org.lwjgl.openal.AL;

/**
 *
 * @author brooks42
 */
// TODO: need to redo this entire class to work with how JME does sound
public class Sounds {

//    private static HashMap<String, Audio> audio;
    public static float volume = 0.0f;
    public static float music_volume = 1.0f;

    public static void cleanUp() {
        AL.destroy();
    }

    /**
     * Initialise resources
     */
    public static void init() {
//
//        audio = new HashMap<String, Audio>();
//        volume = 1.0f;
//
//        try {
//            // http://www.freesound.org/people/fins/sounds/146726/
//            audio.put("bounce", AudioLoader.getAudio("WAV", new File("resources/sounds/bounce.wav").toURI().toURL().openStream()));
//
//            // http://www.freesound.org/people/spazzo_1493/sounds/76376/
//            audio.put("gameover", AudioLoader.getAudio("WAV", new File("resources/sounds/gameover.wav").toURI().toURL().openStream()));
//
//            // http://www.freesound.org/people/cheesepuff/sounds/109239/
//            // (converted to an OGG file with http://www.online-convert.com/result/496e1057cbd6ddde6f7434ec7ccf6dc3
//            audio.put("song_one", AudioLoader.getAudio("OGG", new File("resources/sounds/song_one.ogg").toURI().toURL().openStream()));
//
//            // http://www.freesound.org/people/EdgardEdition/sounds/113636/
//            audio.put("click", AudioLoader.getAudio("WAV", new File("resources/sounds/click.wav").toURI().toURL().openStream()));
//
//            audio.put("lightning", AudioLoader.getAudio("OGG", new File("resources/sounds/lightning.ogg").toURI().toURL().openStream()));
//
//            /*
//             // you can play oggs by loading the complete thing into
//             // a sound
//             oggEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("testdata/restart.ogg"));
//
//             // or setting up a stream to read from. Note that the argument becomes
//             // a URL here so it can be reopened when the stream is complete. Probably
//             // should have reset the stream by thats not how the original stuff worked
//             oggStream = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("testdata/bongos.ogg"));
//
//             // can load mods (XM, MOD) using ibxm which is then played through OpenAL. MODs
//             // are always streamed based on the way IBXM works
//             modStream = AudioLoader.getStreamingAudio("MOD", ResourceLoader.getResource("testdata/SMB-X.XM"));
//
//             // playing as music uses that reserved source to play the sound. The first
//             // two arguments are pitch and gain, the boolean is whether to loop the content
//             modStream.playAsMusic(1.0f, 1.0f, true);
//
//             // you can play aifs by loading the complete thing into
//             // a sound
//             aifEffect = AudioLoader.getAudio("AIF", ResourceLoader.getResourceAsStream("testdata/burp.aif"));
//
//             // you can play wavs by loading the complete thing into
//             // a sound
//             wavEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("testdata/cbrown01.wav"));*/
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void playSoundEffect(String effect) {
//        if (volume == 0) {
//            return;
//        }
//        audio.get(effect).playAsSoundEffect(1.0f, volume, false);
    }

    public static void playMusic(String effect) {
//        if (volume == 0) {
//            return;
//        }
//        audio.get(effect).playAsMusic(1.0f, volume, true);
    }

//    public static void stopSound(String effect) {
//        audio.get(effect).stop();
//    }

    public static void stopAllSounds() {
//        for (Audio sound : audio.values()) {
//            sound.stop();
//        }
    }
}
