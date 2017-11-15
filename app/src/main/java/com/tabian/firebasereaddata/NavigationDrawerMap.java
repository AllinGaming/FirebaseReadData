package com.tabian.firebasereaddata;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ir.basalam.rtlnavigationview.RtlNavigationView;

public class NavigationDrawerMap extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationChangeListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    public GoogleMap map;
    LatLng save_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer_right = (DrawerLayout) findViewById(R.id.drawer_layout_right);           // RIGHT DRAWER
        ActionBarDrawerToggle toggle_right = new ActionBarDrawerToggle(
                this, drawer_right, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_right.setDrawerListener(toggle_right);
        toggle_right.syncState();


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new BottomSheet.Builder(NavigationDrawerMap.this).sheet(R.menu.navigation_drawer_map).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case R.id.action_settings:
                                map.getMyLocation().getLatitude();
                                map.getMyLocation().getLongitude();
                                map.clear();

                                map.addMarker(new MarkerOptions().position(new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude())));
                                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude())));
if (save_point == null){
    save_point =new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
}
                                Snackbar.make(view, "Is this the location you want to choose?", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                map.clear();

                                                map.addMarker(new MarkerOptions().position(save_point));
                                            }
                                        })
                                        .setAction("Yes", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Snackbar.make(view, "Location saved", Snackbar.LENGTH_SHORT).show();
                                            }
                                        }).show();

                                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {  // GASIM MAPU
                                    @Override
                                    public void onMapClick(LatLng latLng) {

                                    }
                                });
                                break;
                        }
                    }
                }).show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RtlNavigationView rtlNavigationView = (RtlNavigationView) findViewById(R.id.nav_view_right);
        rtlNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerLayout drawe2r = (DrawerLayout) findViewById(R.id.drawer_layout_right);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (drawe2r.isDrawerOpen(GravityCompat.END)) {
            drawe2r.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            DrawerLayout drawer_right = (DrawerLayout) findViewById(R.id.drawer_layout_right);
            if (drawer_right.isDrawerOpen(Gravity.RIGHT)) {
                drawer_right.closeDrawer(Gravity.RIGHT);
            } else {
                drawer_right.openDrawer(Gravity.RIGHT);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout_right);
        drawer2.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(this);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {     // PALIM ON CLICK MAPU

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                // lstLatLngs.add(point);
                save_point = point;
                map.clear();
                map.addMarker(new MarkerOptions().position(point));
                map.moveCamera(CameraUpdateFactory.newLatLng(point));
            }
        });

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {

/*    map.addMarker(new MarkerOptions()
            .position(new LatLng(location.getLatitude(), location.getLongitude()))
            .title("My location"));*/

    }
}
