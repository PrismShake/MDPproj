package com.example.mdpproj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class pickworks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_pickworks );
    }
    public void resistanceMach(View view) {
        Intent a = new Intent (this, profilePage.class );
        a.putExtra("resWork", R.id.res);
        startActivity(a);
    }

    public void benchPress(View view) {
        Intent b = new Intent (this, profilePage.class );
        b.putExtra("bench", R.id.bench);
        startActivity(b);
    }

    public void yoga(View view) {
        Intent c = new Intent (this, profilePage.class );
        c.putExtra("yoga", R.id.yog);
        startActivity(c);
    }

    public void freeWeights(View view) {
        Intent d = new Intent (this, profilePage.class );
        d.putExtra("free", R.id.bell);
        startActivity(d);
    }

    public void treadmill(View view) {
        Intent e = new Intent (this, profilePage.class );
        e.putExtra("mill", R.id.tread);
        startActivity(e);
    }

    public void cycling(View view) {
        Intent f = new Intent (this, profilePage.class );
        f.putExtra("cyc", R.id.bike);
        startActivity(f);
    }

}