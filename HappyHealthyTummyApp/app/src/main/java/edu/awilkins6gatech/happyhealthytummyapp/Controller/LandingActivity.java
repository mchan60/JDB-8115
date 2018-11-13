package edu.awilkins6gatech.happyhealthytummyapp.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import edu.awilkins6gatech.happyhealthytummyapp.Model.DiaryEntry;
import edu.awilkins6gatech.happyhealthytummyapp.R;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class LandingActivity extends Activity implements SurfaceHolder.Callback {

    Camera mCamera;
    SurfaceView mPreview;

    private Uri fileUri; // file url to store image/video
    private String timeStamp;
    static File mediaStorageDir;
    Bitmap bmp;


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
        mediaStorageDir.mkdir();

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
                bmp = (Bitmap) data.getExtras().get("data");
                fileUri = getOutputMediaFileUri(1);
                //move onto next activity
                Intent intent = new Intent(
                        LandingActivity.this, AddEntryPageActivity.class);
                intent.putExtra("File Uri", String.valueOf(fileUri));
                intent.putExtra("Time Stamp", timeStamp);
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
        outState.putParcelable("File Uri", fileUri);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("File Uri");
    }



    //Helper Methods
    public Uri getOutputMediaFileUri(int type) {
        try {
            return FileProvider.getUriForFile(LandingActivity.this, LandingActivity.this.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(type));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("uri not returned");
            return null;
        }
    }





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File getOutputMediaFile(int type) throws IOException {
        File mediaStorageDir = getDirectory();

        // Create a media file name
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + getFileName());
            try (FileOutputStream out = new FileOutputStream(mediaFile)) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("file not created");

            return null;
        }
        return mediaFile;
    }


    //    swiping functionality
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //handle 'swipe left' action only

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {



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
            mCamera.stopPreview();
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }

    private File getDirectory() {
        if (!mediaStorageDir.exists()) {
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    IMAGE_DIRECTORY_NAME);
            mediaStorageDir.mkdir();
            System.out.println("directory supposedly made");
        }
        return mediaStorageDir;
    }

    private String getFileName() {
        return "IMG_" + getTimestamp() + ".jpg";
    }

    private String getTimestamp() {
        timeStamp = new SimpleDateFormat("yyyyMMMddEEEE_HHmmss",
                Locale.getDefault()).format(new Date());
        return timeStamp;
    }
}
