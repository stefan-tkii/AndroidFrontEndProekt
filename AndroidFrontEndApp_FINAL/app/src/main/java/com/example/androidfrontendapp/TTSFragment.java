package com.example.androidfrontendapp;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class TTSFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private TextToSpeech myTTS;
    private EditText mEdit;
    private SeekBar seekPitch;
    private SeekBar seekSpeed;
    private Button button;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_tts, container, false);
        spinner = rootView.findViewById(R.id.spinner_lang);
        button = rootView.findViewById(R.id.enter_button);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.language_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        myTTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int result = myTTS.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Log.e("TTS", "Language not supported.");
                        Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        button.setEnabled(true);
                    }
                }
                else
                {
                    Log.e("NEWTTS", "Text to speech engine init not succsessful.");
                }
            }
        });
        spinner.setOnItemSelectedListener(TTSFragment.this);
        mEdit = rootView.findViewById(R.id.editfield);
        seekPitch = rootView.findViewById(R.id.seek_pitch);
        seekSpeed = rootView.findViewById(R.id.seek_speed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
        return rootView;
    }

    private void speak()
    {
        String text = mEdit.getText().toString();
        float pitch = (float)seekPitch.getProgress() / 50;
        if(pitch < 0.1)
        {
            pitch = 0.1f;
        }
        float speed = (float)seekSpeed.getProgress() / 50;
        if(speed < 0.1)
        {
            speed = 0.1f;
        }
        myTTS.setPitch(pitch);
        myTTS.setSpeechRate(speed);
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroyView() {
        if(myTTS != null)
        {
            myTTS.stop();
            myTTS.shutdown();
        }
        super.onDestroyView();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if(text.equals("English"))
        {
            int result = myTTS.setLanguage(Locale.ENGLISH);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "Language not supported.");
                Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            }
            else
            {
                button.setEnabled(true);
            }
        }
        else if(text.equals("Italian"))
        {
            int result = myTTS.setLanguage(Locale.ITALIAN);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "Language not supported.");
                Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            }
            else
            {
                button.setEnabled(true);
            }
        }
        else if(text.equals("German"))
        {
            int result = myTTS.setLanguage(Locale.GERMAN);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "Language not supported.");
                Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            }
            else
            {
                button.setEnabled(true);
            }
        }
        else if(text.equals("French"))
        {
            int result = myTTS.setLanguage(Locale.FRENCH);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "Language not supported.");
                Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            }
            else
            {
                button.setEnabled(true);
            }
        }
        else if(text.equals("Japanese"))
        {
            int result = myTTS.setLanguage(Locale.JAPANESE);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "Language not supported.");
                Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
            }
            else
            {
                button.setEnabled(true);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        int result = myTTS.setLanguage(Locale.ENGLISH);
        if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
        {
            Log.e("TTS", "Language not supported.");
            Toast.makeText(getActivity(), "Initializing...", Toast.LENGTH_SHORT).show();
            button.setEnabled(false);
        }
        else
        {
            button.setEnabled(true);
        }
    }
}
