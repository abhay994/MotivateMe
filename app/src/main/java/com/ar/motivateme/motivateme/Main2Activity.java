package com.ar.motivateme.motivateme;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artjimlop.altex.AltexImageDownloader;
import com.bumptech.glide.Glide;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gospelware.liquidbutton.LiquidButton;

import java.io.File;

public class Main2Activity extends AppCompatActivity {
    DatabaseReference mdatabase;
    ImageView imageView, imaged, images, imagew;
    LiquidButton liquidButton;
    int t;
    Uri uri;


    String post_image;
    Handler handler,handler2;
    TextView result;
    CollapsingToolbarLayout collapsingToolbarLayout;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*
        collapsingToolbarLayout.setTitle(itemTitle);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
*/

        liquidButton = (LiquidButton) findViewById(R.id.button);
        result=(TextView)findViewById(R.id.textView2);
        imageView = (ImageView) findViewById(R.id.imageView2);
        imaged = (ImageView) findViewById(R.id.download);
        imagew = (ImageView) findViewById(R.id.wallaper);
        images = (ImageView) findViewById(R.id.share);

        handler = new Handler();
        handler2=new Handler();
        String mpost_key = getIntent().getExtras().getString("key_post");
        /*Toast.makeText(Main2Activity.this,post_key,Toast.LENGTH_LONG).show();*/
        mdatabase = FirebaseDatabase.getInstance().getReference().child("motivate");
        mdatabase.keepSynced(true);

        mdatabase.child(mpost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_image = (String) dataSnapshot.child("image").getValue();
               /* Toast.makeText(getApplicationContext(),post_image,Toast.LENGTH_LONG).show();*/
                Glide.with(getApplicationContext()).load(post_image)


                        .into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



        imagew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getDrawable()!=null) {

                    Runnable runnable5=new Runnable() {
                        @Override
                        public void run() {
                            liquidButton.startPour();
                            DrawWallpaper wallpaper=new DrawWallpaper();
                            Context context=getApplicationContext();
                            wallpaper.Wallpaper(imageView,liquidButton,context);
                        }
                    };
                    handler.postDelayed(runnable5,2);






                }

            }
        });
        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
               /* handler2.removeCallbacks(runnable2);
                handler2.postDelayed(runnable2,100);*/

            }

            @Override
            public void onProgressUpdate(float progress) {

            }
        });
        imaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageView.getDrawable() != null) {
                    if ((ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                            (ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {


                        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);

                    }


                    if ((ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        File file = new File(Environment.getExternalStorageDirectory(), "Pictures/GOD");
                        if (!file.exists()) {
                            file.mkdir();

                        }
                        final AltexImageDownloader downloader = new AltexImageDownloader(new AltexImageDownloader.OnImageLoaderListener() {
                            @Override
                            public void onError(AltexImageDownloader.ImageError error) {
                                /*Toast.makeText(Main2Activity.this, "check connection", Toast.LENGTH_LONG).show();*/
                                imaged.setImageResource(R.drawable.ic_cancel_black_24dp);

                            }

                            @Override
                            public void onProgressChange(int percent) {
                                t = percent;
                                imaged.setImageResource(R.drawable.ic_sync_black_24dp);

                            }

                            @Override
                            public void onComplete(Bitmap result) {
                                handler.removeCallbacks(runnable1);
                                handler.postDelayed(runnable1, 500);
                        /*        handler2.removeCallbacks(runnable2);
                                handler2.postDelayed(runnable2,100);*/


                            }

                        });
                        AltexImageDownloader.writeToDisk(getApplicationContext(), post_image, "GOD/lk");
                        downloader.download(post_image, true);

                    }


                }
            }

        });


        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   /* handler2.removeCallbacks(runnable2);
                    handler2.removeCallbacks(runnable2, 100);*/

                if ((ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {


                    ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);

                }







                if(imageView.getDrawable()!=null) {
                    if ((ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                        ImageView ivImage = (ImageView) findViewById(R.id.imageView2);
                        // Get access to the URI for the bitmap

                        Drawable mDrawable = ivImage.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                                mBitmap, "Image Description", null);

                        uri = Uri.parse(path);
                        Downloaded downloaded = new Downloaded();

                        downloaded.execute();



                    }
                }

                // Get access to the URI for the bitmap
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }





    Runnable runnable1 =new Runnable() {
        @Override
        public void run() {
            imaged.setImageResource(R.drawable.ic_cloud_done_black_24dp);
        }










    };

    private class Downloaded extends AsyncTask<Void,String,String> {

        @Override
        protected String doInBackground(Void... uris) {

            Uri bmpUri =uri;
            if (bmpUri != null) {
                // Construct a ShareIntent with link to image

                Intent shareIntent = new Intent();


                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.ar.motivateme.motivateme");
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.setType("image/*");

                // Launch sharing dialog for image
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            } else {
                // ...sharing failed, handle error
            }
            return null;
        }

    }
    }


