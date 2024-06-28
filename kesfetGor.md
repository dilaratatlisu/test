package com.dilaratatlisu.javamaps.view;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.adapter.PostAdapter;
import com.dilaratatlisu.javamaps.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DiscoverActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private List<String> followingList;
    ImageView goHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        recyclerViewPosts = findViewById(R.id.recycler_view_posts_discover);
        recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewPosts.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerViewPosts.setAdapter(postAdapter);


        readPosts();

    }

    public void go_home(View view) {
        Intent intent= new Intent(DiscoverActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void readPosts() {

        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    postList.add(post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
