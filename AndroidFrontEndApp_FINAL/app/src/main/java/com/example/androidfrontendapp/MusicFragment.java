package com.example.androidfrontendapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class MusicFragment extends Fragment {

    private Button Play;
    private MediaPlayer mp;
    private boolean prepared = false;
    private boolean started = false;
    private SeekBar volumeBar;
    private static final String radioURL = "http://stream.radioreklama.bg/radio1rock128";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_music, container, false);
        Play = rootView.findViewById(R.id.play_button);
        volumeBar = rootView.findViewById(R.id.seekbar_volume);
        Play.setEnabled(false);
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setScreenOnWhilePlaying(true);
        mp.setVolume(0.5f, 0.5f);
        new RadioTask().execute(radioURL);
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(started)
                {
                    started = false;
                    Play.setBackgroundResource(R.drawable.ic_play);
                    mp.pause();
                }
                else{
                    started = true;
                    Play.setBackgroundResource(R.drawable.ic_pause);
                    mp.start();
                }
            }
        });
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNum = progress / 100f;
                mp.setVolume(volumeNum, volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }

    public class RadioTask extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mp.setDataSource(strings[0]);
                mp.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Play.setEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(started)
        {
            mp.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(started)
        {
            mp.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(prepared)
        {
            mp.release();
        }
    }
}
