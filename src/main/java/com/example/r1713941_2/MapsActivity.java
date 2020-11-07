package com.example.r1713941_2;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String[] posxx, posyy;
    Double posx,posy;
    String title;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12);

        // Intent를 통하여 지명, 경도, 위도를 전달받아서 지도상에 표시하고, textview에 내역을 표기한다.
        Intent intent=getIntent();
        title = intent.getStringExtra("title");
        posyy=intent.getStringArrayExtra("posyy");
        posxx=intent.getStringArrayExtra("posxx");
        i=intent.getIntExtra("item",0);

        Bitmap bm=((BitmapDrawable) getResources().getDrawable(R.drawable.bus)).getBitmap();
        for (int j=0;j<i;j++) {
            posx=Double.parseDouble(posxx[j]);
            posy=Double.parseDouble(posyy[j]);
            LatLng loc = new LatLng(posy, posx);
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(loc).title(title).icon(BitmapDescriptorFactory.fromBitmap(bm));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }
}
