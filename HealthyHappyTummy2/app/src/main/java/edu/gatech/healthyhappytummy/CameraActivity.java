package edu.gatech.healthyhappytummy;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

/**
 * Created by Matt on 4/13/2018.
 */


public class CameraActivity extends Activity {
    private Camera mCamera;
    private CameraView mPreview;

    public void

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = getCameraInstance();

        mPreview = new CameraView(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_view);
        preview.addView(mPreview);
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {

        }
        return c;
    }
}
