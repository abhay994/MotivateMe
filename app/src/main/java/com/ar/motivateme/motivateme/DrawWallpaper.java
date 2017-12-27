package com.ar.motivateme.motivateme;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.gospelware.liquidbutton.LiquidButton;

import java.io.IOException;

/**
 * Created by abhay on 7/6/17.
 */

public class DrawWallpaper {



    public void Wallpaper(ImageView imageView,LiquidButton liquidButton,Context context){

       liquidButton.startPour();

        imageView.buildDrawingCache();

        WallpaperManager myWallpaperManager
                = WallpaperManager.getInstance(context);
        Bitmap bmap = imageView.getDrawingCache();
        liquidButton.setAutoPlay(true);

        try {


            myWallpaperManager.setBitmap(bmap);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            liquidButton.finishPour();
        }
    }
}
