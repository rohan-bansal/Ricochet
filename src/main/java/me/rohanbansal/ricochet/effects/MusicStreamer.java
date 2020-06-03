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

    public MusicStreamer(String songPath, float volume, boolean looping) {
        music.add(Gdx.audio.newMusic(Gdx.files.internal(songPath)));
        this.volume = volume;
        this.looping = looping;
    }

    public MusicStreamer(String songPath) {
        this(songPath, 1, false);
    }

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

    public void play() {
        music.get(currentIndex).play();
    }

    public void pause() {
        music.get(currentIndex).pause();
    }

    public void stop() {
        music.get(currentIndex).stop();
    }

    public Music getCurrentMusicInstance() {
        return music.get(currentIndex);
    }

    @Override
    public void dispose() {
        for(int x = 0; x < music.size(); x++) {
            music.get(x).dispose();
        }
        music.clear();
    }
}
