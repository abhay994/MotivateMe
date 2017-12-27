package com.ar.motivateme.motivateme;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.hanks.htextview.HTextView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mdatabase;
    ImageView img2;
    TabLayout tabLayout;
     Toolbar toolbar;
    CardView cardView,cardView1,cardView2;
    ImageView img11,img22;
    AppBarLayout appBarLayout;
    AnimatorSet animationSet;
    ViewPager viewPager;
    ImageView image;
    TextView textView;
    RelativeLayout relativeLayout1,relativeLayout2,relativeLayout11,relativeLayout22;

    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        animationSet = new AnimatorSet();
/*        img22 = (ImageView) findViewById(R.id.coimg1);*/
         appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
          tabLayout = (TabLayout) findViewById(R.id.tab_layout);
           cardView=(CardView)findViewById(R.id.cardtab);
        cardView1=(CardView)findViewById(R.id.cconnect);
        cardView2=(CardView)findViewById(R.id.nocon);

        relativeLayout22=(RelativeLayout)findViewById(R.id.rev22);
        relativeLayout11=(RelativeLayout)findViewById(R.id.rev11);
        relativeLayout11.post(revealAnimationRunnable2);
        relativeLayout2=(RelativeLayout)findViewById(R.id.rev2);
        relativeLayout1=(RelativeLayout)findViewById(R.id.rev1);
        textView=(TextView)findViewById(R.id.text11);
       image = (ImageView) findViewById(R.id.iv_about);

      viewPager  = (ViewPager) findViewById(R.id.pager);
        viewPager.setVisibility(View.GONE);


        /*hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/font-name.ttf"));
// be sure to set custom typeface before setting the animate type, otherwise the font may not be updated.
        hTextView.setAnimateType(HTextViewType.LINE);
        hTextView.animateText("new simple string");*/



        if(isInternetOn()){
       cardView1.setVisibility(View.GONE);
       viewPager.setVisibility(View.VISIBLE);

   }
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetOn()){
                    cardView1.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);

                }else {

                }
            }
        });




        Glide.with(getApplicationContext()).load(R.drawable.jareckiwallpaper)


                .into(image)
        ;

        cardView.setCardBackgroundColor(Color.parseColor("#006158"));


        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("HeAlTh"));
        tabLayout.addTab(tabLayout.newTab().setText("StUdEnT"));
        tabLayout.addTab(tabLayout.newTab().setText("LOvE"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){

                   Glide.with(getApplicationContext()).load(R.drawable.jareckiwallpaper)


                            .into(image)
                    ;


                    textView.setText(R.string.q1_q);


                    //  cardView.setCardBackgroundColor(Color.parseColor("#006158"));


                }
                if(tab.getPosition()==1){


                    Glide.with(getApplicationContext()).load(R.drawable.health)


                            .into(image)
                    ;


                    //   cardView.setCardBackgroundColor(Color.parseColor("#FBD87D"));


                    textView.setText("");

                }
                if(tab.getPosition()==2){

                    Glide.with(getApplicationContext()).load(R.drawable.study2)


                            .into(image)
                    ;
                    textView.setText("");
                   // cardView.setCardBackgroundColor(Color.parseColor("#257ED8"));
                }
                if(tab.getPosition()==3){
                   Glide.with(getApplicationContext()).load(R.drawable.love)


                            .into(image)
                    ;

                    textView.setText(R.string.q2_q);
                  //  cardView.setCardBackgroundColor(Color.parseColor("#994695"));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection


        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }




    private final Runnable revealAnimationRunnable2 = new Runnable() {
        @Override
        public void run() {
            int cx = relativeLayout11.getLeft();
            int cy = relativeLayout11.getTop()+relativeLayout11.getRight();

            int finalRadius = Math.max(relativeLayout11.getWidth(), relativeLayout11.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(relativeLayout22, cx, cy, finalRadius, 0);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    relativeLayout22.setVisibility(View.GONE);
                    relativeLayout1.post(revealAnimationRunnable);

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator.setDuration(3450);

            animator.start();
        }
    };



    private final Runnable revealAnimationRunnable = new Runnable() {
        @Override
        public void run() {
            int cx = relativeLayout1.getLeft();
            int cy = relativeLayout1.getTop();

            int finalRadius = Math.max(relativeLayout1.getWidth(), relativeLayout1.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(relativeLayout2, cx, cy,0, finalRadius);


            animator.setDuration(400);
            relativeLayout2.setVisibility(View.VISIBLE);
            animator.start();
        }
    };
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
