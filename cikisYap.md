logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(OptionsActivity.this, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        });
