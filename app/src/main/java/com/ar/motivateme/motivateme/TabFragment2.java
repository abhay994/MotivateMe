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
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class TabFragment2 extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference mdatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv12);



        GridLayoutManager horizontalLayoutManagaer=new GridLayoutManager(getActivity(),2);


        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("health");
        mdatabase.keepSynced(true);


        FirebaseRecyclerAdapter<Objectsy,BloggerView> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Objectsy, BloggerView>(
                Objectsy.class,
                R.layout.card_layourgride,
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
                        Bundle bundle1 = new Bundle();
                        final FragmentManager fm1 = getActivity().getSupportFragmentManager();

                        bundle1.putString("edttext1", post_key);
// set Fragmentclass Arguments
                         DialogTwo fragobj1 = new DialogTwo();
                        fragobj1.setArguments(bundle1);
                        fragobj1.show(fm1,"gg");
                    }
                });





            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);










    }

    public static class BloggerView extends RecyclerView.ViewHolder{
        View mView;
        ImageView img2;
        ImageView img22;

        public BloggerView(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setImg(Context cnt, String image) {
            img2 = (ImageView) mView.findViewById(R.id.image_v2);
            img22 = (ImageView) mView.findViewById(R.id.imgback2);
           Glide.with(cnt).load(image)


                    .into(img22)
            ;
           Glide.with(cnt).load(image)

                    .into(img2);
        }

    }

}
