package me.rohanbansal.ricochet.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public class MusicStreamer implements Disposable {

    private final ArrayList<Music> music = new ArrayList<>();
    private int currentIndex = 0;
    private float volume;
    private boolean looping;

    /**
     * Initialize a music streamer instance
     * @param songPath path to song file
     * @param volume volume of song
     * @param looping loop the song
     */
    public MusicStreamer(String songPath, float volume, boolean looping) {
        music.add(Gdx.audio.newMusic(Gdx.files.internal(songPath)));
        this.volume = volume;
        this.looping = looping;
    }

    /**
     * Initialize a music streamer with default values
     * @param songPath path to song
     */
    public MusicStreamer(String songPath) {
        this(songPath, 1, false);
    }

    /**
     * Queue a song for playback
     * @param songPath song to be played
     */
    public void queue(final String songPath) {
        music.get(currentIndex).setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music musicInst) {
                currentIndex++;
                music.add(Gdx.audio.newMusic(Gdx.files.internal(songPath)));
                music.get(currentIndex).play();
            }
        });
    }

    /**
     * play the song
     */
    public void play() {
        music.get(currentIndex).play();
    }

    /**
     * pause the song
     */
    public void pause() {
        music.get(currentIndex).pause();
    }

    /**
     * stop the song
     */
    public void stop() {
        music.get(currentIndex).stop();
    }

    /**
     * get the inner GDX music instance
     * @return return the music instance
     */
    public Music getCurrentMusicInstance() {
        return music.get(currentIndex);
    }

    /**
     * Dispose the music instance
     */
    @Override
    public void dispose() {
        for(int x = 0; x < music.size(); x++) {
            music.get(x).dispose();
        }
        music.clear();
    }
}
