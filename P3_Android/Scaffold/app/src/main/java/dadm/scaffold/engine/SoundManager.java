package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashMap;

public class SoundManager {

    private Context mContext;
    private SoundPool mSoundPool;
    private final int MAX_STREAMS = 50;
    private final int DEFAULT_MUSIC_VOLUME = 5;
    private final String MUSIC_PREF_KEY = "A";
    private final String SOUNDS_PREF_KEY = "B";

    private HashMap<GameEvent, Integer> mSoundsMap;
    private MediaPlayer mBgPlayer;

    private boolean mSoundEnabled;
    private boolean mMusicEnabled;

    public SoundManager(Context context) {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        mSoundEnabled = prefs.getBoolean(SOUNDS_PREF_KEY, true);
        mMusicEnabled = prefs.getBoolean(MUSIC_PREF_KEY, true);
        mContext = context;
        loadIfNeeded();
    }

    private void loadIfNeeded() {
        if (mSoundEnabled) {
            loadSounds();
        }
        if (mMusicEnabled) {
            loadMusic();
        }
    }

    public boolean getSoundStatus() {
        return mSoundEnabled;
    }

    public boolean getMusicStatus() {
        return mMusicEnabled;
    }

    public void toggleSoundStatus() {
        mSoundEnabled = !mSoundEnabled;
        if (mSoundEnabled) {
            loadSounds();
        } else {
            unloadSounds();
        }
// Save it to preferences
        PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                .putBoolean(SOUNDS_PREF_KEY, mSoundEnabled)
                .apply();
        //.commit();
    }

    public void toggleMusicStatus() {
        mMusicEnabled = !mMusicEnabled;
        if (mMusicEnabled) {
            loadMusic();
            resumeBgMusic();
        } else {
            unloadMusic();
        }
// Save it to preferences
        PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                .putBoolean(MUSIC_PREF_KEY, mMusicEnabled)
                .apply();
        //.commit();

    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSoundPool = new SoundPool(MAX_STREAMS,
                    AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes audioAttributes = new
                    AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
    }

    private void loadEventSound(Context context, GameEvent event, String
            filename) {
        try {
            AssetFileDescriptor descriptor =
                    context.getAssets().openFd("sfx/" + filename);
            int soundId = mSoundPool.load(descriptor, 1);
            mSoundsMap.put(event, soundId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSounds() {
        createSoundPool();
        mSoundsMap = new HashMap<GameEvent, Integer>();
        loadEventSound(mContext, GameEvent.AsteroidHit,
                "Asteroid_destroy.ogg");
        loadEventSound(mContext, GameEvent.SpaceshipHit,
                "Player_destroy.ogg");
        loadEventSound(mContext, GameEvent.LaserFired,
                "Laser_shoot.ogg");
        loadEventSound(mContext, GameEvent.LaserEnemy,
                "Laser_enemy.ogg");
    }

    private void unloadSounds() {
        mSoundPool.release();
        mSoundPool = null;
        mSoundsMap.clear();
    }


    public void playSoundForGameEvent(GameEvent event) {
        if (!mSoundEnabled) {
            return;
        }
        Integer soundId = mSoundsMap.get(event);
        if (soundId != null) {
            mSoundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    public void pauseBgMusic() {
        if (mMusicEnabled) {
            mBgPlayer.pause();
        }
    }

    public void resumeBgMusic() {
        if (mMusicEnabled) {
            mBgPlayer.start();
        }
    }

    private void loadMusic() {
        try {
            mBgPlayer = new MediaPlayer();
            AssetFileDescriptor afd = mContext.getAssets()
                    .openFd("sfx/Alumo_-_Under_the_Moon.mp3");
            mBgPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength());
            mBgPlayer.setLooping(true);
            mBgPlayer.setVolume(DEFAULT_MUSIC_VOLUME,
                    DEFAULT_MUSIC_VOLUME);
            mBgPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unloadMusic() {
        mBgPlayer.stop();
        mBgPlayer.release();
    }


}