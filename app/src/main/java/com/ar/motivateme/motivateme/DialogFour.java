package com.ar.motivateme.motivateme;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gospelware.liquidbutton.LiquidButton;


public class DialogFour extends DialogFragment {
    DatabaseReference mdatabase;
    String post_image;
    LiquidButton liquidButton;
    ImageView imageView, imagew, images;
    Handler handler4;
    Uri uri;
    RelativeLayout relativeLayout1,relativeLayout2;
    public static final int PERMISSION_REQUEST = 200;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler4 = new Handler();
        return inflater.inflate(R.layout.fragment_dialog_four, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        liquidButton = (LiquidButton) view.findViewById(R.id.button1);
        imageView = (ImageView) view.findViewById(R.id.imageView222);
        imagew = (ImageView) view.findViewById(R.id.wallaper4);
        images = (ImageView) view.findViewById(R.id.share4);
        relativeLayout2=(RelativeLayout)view.findViewById(R.id.layout2);
        relativeLayout1=(RelativeLayout)view.findViewById(R.id.layout1);
        relativeLayout1.post(revealAnimationRunnable);



        String mpost_key = getArguments().getString("edttext3");
        mdatabase = FirebaseDatabase.getInstance().getReference().child("love");
        mdatabase.keepSynced(true);

        mdatabase.child(mpost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                post_image = (String) dataSnapshot.child("image").getValue();
               /* Toast.makeText(getApplicationContext(),post_image,Toast.LENGTH_LONG).show();*/
                Glide.with(getContext()).load(post_image)


                        .into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        imagew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageView.getDrawable() != null) {

                    Runnable runnable4 = new Runnable() {
                        @Override
                        public void run() {
                           /* liquidButton.startPour();*/
                            DrawWallpaper wallpaper = new DrawWallpaper();
                            Context context = getContext();
                            wallpaper.Wallpaper(imageView, liquidButton, context);
                        }
                    };
                    handler4.postDelayed(runnable4,2);

                /*getDialog().cancel();*/
                }
            }
        });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {


                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);

                }


                if (imageView.getDrawable() != null) {
                    if ((ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            && (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

                        // Get access to the URI for the bitmap

                        Drawable mDrawable = imageView.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();

                        String path = MediaStore.Images.Media.insertImage(getActivity().getApplicationContext().getContentResolver(),
                                mBitmap, "Image Description", null);

                        uri = Uri.parse(path);
                       Downloaded downloaded = new Downloaded();

                        downloaded.execute();


                    }
                }
            }
        });

    }

    private class Downloaded extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... uris) {

            Uri bmpUri = uri;
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
    private final Runnable revealAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            int cx = relativeLayout1.getLeft()+relativeLayout1.getRight()/2;
            int cy = relativeLayout1.getTop();

            int finalRadius = Math.max(relativeLayout1.getWidth(), relativeLayout1.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(relativeLayout2, cx, cy, 0, finalRadius);
            animator.setDuration(500);
            relativeLayout2.setVisibility(View.VISIBLE);
            animator.start();
        }
    };

}
