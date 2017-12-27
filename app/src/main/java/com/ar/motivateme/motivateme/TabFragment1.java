package com.ar.motivateme.motivateme;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.GlideContext;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.BlurTransformation;


public class TabFragment1 extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference mdatabase;
    public TabFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv1);



        GridLayoutManager horizontalLayoutManagaer=new GridLayoutManager(getActivity(),2);


        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("motivate");
        mdatabase.keepSynced(true);


        FirebaseRecyclerAdapter<Objectsy,BloggerView> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Objectsy, BloggerView>(
                Objectsy.class,
                R.layout.card_layout,
                BloggerView.class,
                mdatabase
        ) {
            @Override
            protected void populateViewHolder(BloggerView viewHolder, final Objectsy model, final int position) {
                viewHolder.setImg(getContext(),model.getImage());
                final String post_key = getRef(position).getKey();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         /*Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                         intent.putExtra("key_post", post_key);
                         startActivity(intent);*/
                        Bundle bundle = new Bundle();
                        final FragmentManager fm = getActivity().getSupportFragmentManager();

                        bundle.putString("edttext", post_key);
// set Fragmentclass Arguments
                        BlankFragment fragobj = new BlankFragment();
                        fragobj.setArguments(bundle);
                        fragobj.show(fm,"gg1");
                    }
                });





            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);










    }

    public static class BloggerView extends RecyclerView.ViewHolder{
        View mView;
        ImageView img;
        ImageView img2;

        public BloggerView(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setImg(Context cnt, String image) {
            img = (ImageView) mView.findViewById(R.id.image_v);
            img2 = (ImageView) mView.findViewById(R.id.imgback);
            Glide.with(cnt)

                    .load(image)


                    .into(img2)
            ;
            Glide.with(cnt).load(image)


                    .into(img);


        }


    }
}
