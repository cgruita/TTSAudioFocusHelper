package com.gruita.testaudiofocus;

/**
 * Created by cgruita on 8/12/16.
 */

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

public class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener {


    public static final String TAG = Const.TAG;

    AudioManager mAM;
//    private Context mCtx;

    public AudioFocusHelper(Context ctx) {
        mAM = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
//        mCtx = ctx;
    }

    /** Requests audio focus. Returns whether request was successful or not. */
    public boolean requestFocus(int focusType) {
        int granted = mAM.requestAudioFocus(this, AudioManager.STREAM_VOICE_CALL, focusType);

        boolean result = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == granted;
        Log.d(Const.TAG, "++.FH.request: " + focusType + ", result: " + result);
        return result;



    }

    /** Abandons audio focus. Returns whether request was successful or not. */
    public boolean abandonFocus() {
        int result = mAM.abandonAudioFocus(this);
        Log.d(Const.TAG, "++.FH.abandonFocus: " + result);
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;
    }

    /**
     * Called by AudioManager on audio focus changes. We implement this by calling our
     * MusicFocusable appropriately to relay the message.
     */
    public void onAudioFocusChange(int focusChange) {

        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                Log.d(Const.TAG, "++.FH.onAudioFocusChange: : GAIN");

                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.d(Const.TAG, "++.FH.onAudioFocusChange: : LOSS");

                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                Log.d(Const.TAG, "++.FH.onAudioFocusChange: TRANSIENT");
//                MusicService.lowerVolume();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.d(Const.TAG, "++.FH.onAudioFocusChange: : CAN DUCK");

                break;
            default:
                Log.d(Const.TAG, "++.FH.onAudioFocusChange: other");
        }
    }
}
