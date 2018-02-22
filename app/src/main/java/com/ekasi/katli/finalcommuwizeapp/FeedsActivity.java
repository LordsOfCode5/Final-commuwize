package com.ekasi.katli.finalcommuwizeapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Katlego on 11/12/2017.
 */

public class FeedsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mCommunityList;
    private DatabaseReference mDatabase;
    private ImageButton comment_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FeedsActivity.this, PostActivity.class);
                startActivity(i);
            }
        });

        comment_btn = (ImageButton) findViewById(R.id.comment_btn);
        Intent comment = new Intent (FeedsActivity.this, PostActivity.class);
        startActivity(comment);

        mCommunityList = (RecyclerView) findViewById(R.id.community_list);
        mCommunityList.setHasFixedSize(true);
        mCommunityList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CommunityApp");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <community , CommunityViewHolder> FBRA = new FirebaseRecyclerAdapter<community, CommunityViewHolder>(

                community.class,
                R.layout.community_row,
                CommunityViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(CommunityViewHolder viewHolder, community model, int position) {

                System.out.println(model.getTitle());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(getApplicationContext(),(model.getImage()));

            }
        };

        mCommunityList.setAdapter(FBRA);
    }

    public static class CommunityViewHolder extends RecyclerView.ViewHolder {
private View mView;
        public CommunityViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.text_title);
            post_title.setText(title);
        }
        public void setImage(Context ctx, String image){
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
            post_image.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(FeedsActivity.this, MyProfile.class);
            startActivity(intent);


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
          else if (id == R.id.nav_profile_picture) {

            Intent intent = new Intent(FeedsActivity.this, MyProfile.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

