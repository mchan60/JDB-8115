package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GestureDetectorCompat;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import edu.awilkins6gatech.happyhealthytummyapp.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class LandingActivity extends Activity implements SurfaceHolder.Callback {

    Camera mCamera;
    SurfaceView mPreview;

    Button captureButton;
    private Uri fileUri; // file url to store image/video
    static File mediaStorageDir;


    private GestureDetectorCompat gestureDetectorCompat;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Happy Healthy Tummy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //gestures
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());

        // External sdcard location
        mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

        mPreview = findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        try {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
        } catch (Exception e) {
            Log.d("CAMERA", e.toString());
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //camera functionality

    /**
     * Receiving activity result method will be called after taking a picture
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                fileUri = getOutputMediaFileUri(1);
                File source = new File(fileUri.getPath());
                FileChannel src = null;
                FileChannel dst = null;
                try {
                    src = new FileInputStream(source).getChannel();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    dst = new FileOutputStream(mediaStorageDir).getChannel();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (dst != null && src != null) {
                    try {
                        dst.transferFrom(src, 0, src.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("destination or source file was null");
                }
                //move onto next activity
                Intent intent = new Intent(
                        LandingActivity.this, AddEntryPageActivity.class);
                intent.putExtra("File Uri", String.valueOf(fileUri));
                startActivity(intent);
                //captureImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
                //move onto next activity
                Intent intent = new Intent(
                        LandingActivity.this, MainPageActivity.class);
                //intent.putExtra("File Uri", fileUri.toString());
                startActivity(intent);
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    //Helper Methods
    public Uri getOutputMediaFileUri(int type) {
//        return Uri.fromFile(getOutputMediaFile(type));
        try {
            return FileProvider.getUriForFile(LandingActivity.this, LandingActivity.this.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(type));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private File getOutputMediaFile(int type) throws IOException {
        System.out.println("got to output media file");

//        // External sdcard location
//        File mediaStorageDir = new File(
//                Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        //            if (!mediaStorageDir.mkdirs()) {
//                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
//                        + IMAGE_DIRECTORY_NAME + " directory");
//                return null;
//            }
        if (!mediaStorageDir.exists()) {
            try {
                mediaStorageDir.createNewFile();
                mediaStorageDir.mkdir();
            } catch (IOException e) {
                System.out.println("Caught IOException: " + e.getMessage());
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            System.out.println("finished output file");
            return null;
        }
        System.out.println("finished output file");
        return mediaFile;
    }


    //    swiping functionality
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

         /*
         Toast.makeText(getBaseContext(),
          event1.toString() + "\n\n" +event2.toString(),
          Toast.LENGTH_SHORT).show();
         */

            if(event2.getX() < event1.getX()){
                Toast.makeText(getBaseContext(),
                        "Swipe left - startActivity()",
                        Toast.LENGTH_SHORT).show();

                //switch another activity
                Intent intent = new Intent(
                        LandingActivity.this, MainPageActivity.class);
                startActivity(intent);
            }

            return true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        Camera.Parameters params = mCamera.getParameters();
//        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
//        Camera.Size selected = sizes.get(0);
//        params.setPreviewSize(selected.width, selected.height);
//        mCamera.setParameters(params);
//
//        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }

}
