private void isFollowed(final String id, final Button btnFollow) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                    .child("following");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (id != null && dataSnapshot.child(id).exists()) {
                            btnFollow.setText("following");
                        }
                        else{
                            btnFollow.setText("follow");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
