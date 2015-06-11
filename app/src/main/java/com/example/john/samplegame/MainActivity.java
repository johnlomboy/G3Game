package com.example.john.samplegame;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private CoordinatorLayout mCoordinator;
    private FloatingActionButton fab;
    private ViewTreeObserver vto;
    private Button mStargame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "ASDASDADAS");

        mCoordinator = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mStargame = (Button) findViewById(R.id.bStartgame);
        mStargame.setOnClickListener(this);

        fab = (FloatingActionButton) mCoordinator.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        vto = mStargame.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.e(TAG, "");
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up_animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.e(TAG, "Fab x " + mStargame.getX() + " Fab y " + mStargame.getY());
                        ObjectAnimator mover = ObjectAnimator.ofFloat(mStargame, "translationY", 0, 0);
                        mover.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mStargame.startAnimation(animation);
                mStargame.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
//        setupRecyclerView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent fabIntent = new Intent(this, DetailedActivity.class);
                fabIntent.putExtra(DetailedActivity.TAG, "About");
                startActivity(fabIntent);
                break;
            case R.id.bStartgame:
//                Snackbar.make(mCoordinator, "Startgame!", Snackbar.LENGTH_SHORT).setActionTextColor(Color.WHITE).show();
                Intent gameIntent = new Intent(this, GameActivity.class);
                startActivity(gameIntent);
                break;
            default:

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setupRecyclerView() {
//        ArrayList<String> stringList = new ArrayList<>();
//        stringList.add("John");
//        stringList.add("Doe");
//        stringList.add("Roger");
//        stringList.add("William");
//        stringList.add("Bart");
//
//        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(new SampleListAdapter(stringList));
//    }
}
