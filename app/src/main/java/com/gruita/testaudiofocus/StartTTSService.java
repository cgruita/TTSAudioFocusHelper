package com.gruita.testaudiofocus;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by cgruita on 8/16/16.
 */
public class StartTTSService extends Service {

    public static final String TAG = Const.TAG;

    private TextToSpeech mTts;
    private CountDownTimer mCountDownTimer;
    private AudioFocusHelper mAudioFocusHelper = null;




    @Override
    public void onCreate() {

        Log.d(TAG, "++.Serv.onCreate: ");


        mAudioFocusHelper = new AudioFocusHelper(this);

        mTts =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
//                    Log.d(TAG, "init TTS successful");

                    mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onDone(String utteranceId) {
//                             Log.d(TAG, "TTS finished");
                            mAudioFocusHelper.abandonFocus();
                        }

                        @Override
                        public void onError(String utteranceId) {
                        }

                        @Override
                        public void onStart(String utteranceId) {
                        }
                    });

                } else {
                    Log.e(TAG, "init TTS failed");
                }
            }


        });

        mCountDownTimer = new CountDownTimer(60000L, 1000L) {

            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;

                if(secondsRemaining == 50 || secondsRemaining == 30 || secondsRemaining == 10){
                    makeAppSpeak("This is a short message in order to help us test the audio focus. ", (int) secondsRemaining);
                }
                if(secondsRemaining % 5 == 0){
                    Log.d(TAG, "remaining: " + secondsRemaining);
                }

            }

            public void onFinish() {
                stopSelf();
            }
        };
        mCountDownTimer.start();
    }


        @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "++.Serv.onBind: ");
        return null;
    }


    private void makeAppSpeak(final String textToBeSpoken, int secondsLeft){
        int focusType =  -1;
        switch (secondsLeft){
            case 50:
                focusType = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
                Log.d(TAG, " ### makeAppSpeak: DUCK: " + focusType);

                break;
            case 30:
                focusType = AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
                Log.d(TAG, " ### makeAppSpeak: TRANS: " + focusType);
                break;
            case 10:
                focusType = AudioManager.AUDIOFOCUS_GAIN;
                Log.d(TAG, " ### makeAppSpeak: GAIN: " + focusType);
                break;
            default:
                break;

        }

        boolean focusRequestGraned = mAudioFocusHelper.requestFocus(focusType);
        if(focusRequestGraned){

            HashMap<String, String> myHashAlarm = new HashMap<String, String>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "SOME MESSAGE");
            mTts.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
            Toast.makeText(getApplicationContext(), textToBeSpoken,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy(){
        if(mTts !=null){
            mTts.stop();
            mTts.shutdown();
        }
        Log.d(TAG, "++.Serv.onDestroy: ");
        super.onDestroy();
    }
}
