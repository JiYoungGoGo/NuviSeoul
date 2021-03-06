package com.gangnam4bungate.nuviseoul.ui.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.model.AreaBaseModel;
import com.gangnam4bungate.nuviseoul.network.NetworkManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.mystory.commonlibrary.network.MashupCallback;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, MashupCallback {

    private GoogleMap mMap;
    //@BindView(R.id.cast_button_type_closed_caption) TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ButterKnife.bind(this);
        }catch (Exception e){

        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        NetworkManager.getInstance().requestAreaBaseListInfo(this, CODES.API_CONTENTTYPE.FESTIVAL);

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onMashupSuccess(JSONObject object, String requestCode) {

        if(requestCode != null && requestCode.equals(CODES.RequestCode.REQUEST_AREABASELIST)) {
            try {
                AreaBaseModel model = new Gson().fromJson(object.toString(), AreaBaseModel.class);
                if(model != null){

                }
            } catch (Exception e) {
                Log.e(CODES.TAG, "onMashupSucess Exception : " + e.getMessage());
            }

            Log.d(CODES.TAG, object.toString());
        }
    }

    @Override
    public void onMashupFail(VolleyError error, String requestCode) {
        if(requestCode != null && requestCode.equals(CODES.RequestCode.REQUEST_AREABASELIST)) {

            //Log.d(CODES.TAG, object.toString());
        }
    }
}
