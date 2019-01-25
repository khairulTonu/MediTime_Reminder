package com.blanyal.remindme;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.system.ErrnoException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Tonu on 10/20/2017.
 */

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    public static Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ImageButton nextPage,manageSchedule,gellary,camera,btnMorning,btnNoon,btnNight,btnMenual;
    public String str;
    public byte[] byteArray;
    private Uri mCropImageUri;
    DrawerLayout drawer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        camera = (ImageButton) findViewById(R.id.btnCamera);
        //nextPage = (Button) findViewById(R.id.nxtPage) ;

        //manageSchedule = (Button) findViewById(R.id.btn_spotify2);
        manageSchedule = (ImageButton) findViewById(R.id.btnEdit);
        btnMenual = (ImageButton) findViewById(R.id.btnMenually);

        btnMorning = (ImageButton) findViewById(R.id.btnMorning);
        btnNoon = (ImageButton) findViewById(R.id.btnNoon);
        btnNight = (ImageButton) findViewById(R.id.btnNight);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        btnNoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noonPage = new Intent(HomePage.this,NoonActivity.class);
                startActivity(noonPage);
            }
        });

        btnNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nightPage = new Intent(HomePage.this,NightActivity.class);
                startActivity(nightPage);
            }
        });

        btnMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent morningPage = new Intent(HomePage.this,MorningActivity.class);
                startActivity(morningPage);
            }
        });

        manageSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takepicture = new Intent(HomePage.this,MainActivity.class);
                startActivity(takepicture);
            }
        });

        btnMenual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takepicture = new Intent(HomePage.this,ManageReminderAddActivity.class);
                startActivity(takepicture);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takepicture = new Intent(HomePage.this,TakePicture.class);
                startActivity(takepicture);
            }
        });

        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
//


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





    }



    /*@Override
    public void onClick(View view) {
        if (view == gellary) {
            showFileChooser();
        }
    }

    private void showFileChooser() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();


            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                startActivity(new Intent(getApplicationContext(), Image_Recognition.class));
            }
            catch (IOException e) {
                e.printStackTrace();


            }
        }

    }
*/





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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            //Set the fragment initially
//            MainFragment fragment = new MainFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction =
//                    getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.commit();
            Intent licensesIntent = new Intent(this,Licences.class);
            startActivity(licensesIntent);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawers();
            return true;
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            //Set the fragment initially
//            GalleryFragment fragment = new GalleryFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction =
//                    getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.commit();
            Intent developerIntent = new Intent(this,Developer1.class);
            startActivity(developerIntent);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawers();
            return true;

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
