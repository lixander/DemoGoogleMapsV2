package com.example.hedeon.demogooglemapsv2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;


public class MainActivity extends ActionBarActivity implements LocationListener{
    private final LatLng LOCATION_BURNABY = new LatLng(6.267847, -75.333332);
    private final LatLng LOCATION_SURREY = new LatLng(6.267847, -75.568533);
    Cliente log;
    Usuario usuario;
    GoogleMap map1;
    LatLng latLng;
    EditText txtUser;
    EditText txtPassword;
    Marker m1;
    String message = "{\"estadoUsuario\":\"vivo\",\"idUsuario\":2,\"nickName\":\"lixandro\",\"nivelUsuario\":3,\"poderAtaque\":1,\"posUsuario\":\"{\\\"idUsuario\\\":2,\\\"latitud\\\":6.2447470000000003,\\\"longitud\\\":-75.574827999999997}\",\"token\":\"null\",\"vidaMaxUsuario\":100,\"vidaUsuario\":100}";
    String msgPosActual;
    String nu, pw, serverMessage;
    String loginJson;
    private String nombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.container, new FragmentA())
                        .commit();
            }

        }

        //setUpMapIfNeeded();






       //map.setMyLocationEnabled(true);
       //map.setMapType(map.MAP_TYPE_TERRAIN);




    public void setUpMapIfNeeded()
    {
        if(map1 == null)
        {
            map1 = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        if(map1 != null)
        {
            setUpMap();
        }
    }
    public LatLng getCurrentLocation(Context context)
    {
        try
        {
            LocationManager locMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String locProvider = locMgr.getBestProvider(criteria, false);
            Location location = locMgr.getLastKnownLocation(locProvider);

            // getting GPS status
            boolean isGPSEnabled = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNWEnabled = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNWEnabled)
            {
                // no network provider is enabled
                return null;
            }
            else
            {
                // First get location from Network Provider
                if (isNWEnabled)
                    if (locMgr != null)
                        location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                    if (location == null)
                        if (locMgr != null)
                            location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (NullPointerException ne)
        {
            Log.e("Current Location", "Current Lat Lng is Null");
            return new LatLng(0, 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new LatLng(0, 0);
        }
    }
    public void setUpMap()
    {

        map1.setMyLocationEnabled(true);
        //mLocMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location_network = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        Location myLocation = locationManager.getLastKnownLocation(provider);

        map1.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        double latitude;

        double longitude;
        if(location_network != null) {
            latitude = location_network.getLatitude();
            longitude = location_network.getLongitude();
        }else{
            Location   getLastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            longitude = getLastLocation.getLongitude();
            latitude = getLastLocation.getLatitude();
        }

        latLng =getCurrentLocation(getApplicationContext());
        map1.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 2));

        map1.animateCamera(CameraUpdateFactory.zoomTo(8));
        SystemClock.sleep(2000);
        map1.addMarker(((new MarkerOptions().position((latLng)).title("find me here").snippet("Esta es mi posición Actual"))));

    }



    public void onClick_User(View v)
    {

        txtUser   = (EditText)findViewById(R.id.UserName);
        txtUser.setText("");

    }

    public void onClick_City(View v)
    {
        setContentView(R.layout.fragment_b);
        MapFragment vv = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        if(vv == null)
        {
            Log.v("Soy un mango: ","Holi holi");
        }
        else {
            map1 = vv.getMap();
            map1.addMarker((new MarkerOptions().position((LOCATION_SURREY)).title("Ready to Fight!").snippet("Jugador 1")));
            //map1.addMarker((new MarkerOptions().position((LOCATION_BURNABY)).title("Ready to Fight!").snippet("Jugador 2")));
            m1 = map1.addMarker(new MarkerOptions()
                    .position(LOCATION_BURNABY)
                    .title("Melbourne")
                    .snippet("Population: 4,137,400")
                    );
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_SURREY, 8);
            map1.animateCamera(update);

            m1.setPosition(LOCATION_BURNABY);
            m1.setVisible(true);
            setUpMapIfNeeded();

            usuario = new Usuario(message);
            Log.v("Nick Usuario", usuario.getNickName());
            Log.v("getIdUsuario Usuario",usuario.getIdUsuario());
            Log.v("getLatitud Usuario",usuario.getPosUsuario());
            Log.v("getLatitud Usuario",usuario.getLatitud());
            Log.v("getLongitud Usuario",usuario.getLongitud());
            map1.addMarker((new MarkerOptions().position((new LatLng(Double.parseDouble(usuario.getLatitud()),Double.parseDouble(usuario.getLongitud())))).title(usuario.getEstadoUsuario()).snippet(usuario.getNickName())));
            msgPosActual = JSONPosicionToServer(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),usuario.getIdUsuario());
            Log.v("Mensaje Posición", msgPosActual);
            SendMessagePos sendMessageTask = new SendMessagePos();
            sendMessageTask.execute();
            //map1.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        }
    }
    public void onClick_Surrey(View v)
    {

        Log.d("MainActivity", "onClick");
        txtUser = (EditText)findViewById(R.id.UserName);
        txtPassword = (EditText)findViewById(R.id.password);
        // Create new fragment and transaction

            Fragment newFragment = new FragmentB();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack

            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();


            String nu = txtUser.getText().toString();
            String pw = txtPassword.getText().toString();

            log = new Cliente(nu,pw);
            loginJson = log.loginToJSON();
            Log.v("Holitas soy el JSON: ", loginJson);

            //messsage = textField.getText().toString(); // get the text message on the text field
            //textField.setText(""); // Reset the text field to blank
            SendMessage sendMessageTask = new SendMessage();
            sendMessageTask.execute();


    }

    private class SendMessage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ZMQ.Context cont = ZMQ.context(1);
                ZMQ.Socket socket = cont.socket(ZMQ.REQ); // Se crea el socker de tipo requeste , el servidor está seteado como reply
                socket.connect("tcp://192.168.0.134:5555"); // se conecta al server indicado en la ip [protocolo]://[dirección ip]:[puerto]

                Log.v("A Servidor", message);
                socket.send(log.loginToServer(loginJson));
                Log.v("Mensaje Login: ", loginJson);
                Log.v("Mensaje Login: ", log.loginToServer(loginJson));
                //{"estadoUsuario":"vivo","idUsuario":5,"nickName":"alexis","nivelUsuario":3,"poderAtaque":1,"posUsuario":"{\"idUsuario\":5,\"latitud\":6.2447470000000003,\"longitud\":-75.574827999999997}\n","token":"null","vidaMaxUsuario":100,"vidaUsuario":100}
                //{"estadoUsuario":"vivo","idUsuario":2,"nickName":"lixandro","nivelUsuario":3,"poderAtaque":1,"posUsuario":"{\"idUsuario\":2,\"latitud\":6.2447470000000003,\"longitud\":-75.574827999999997}\n","token":"null","vidaMaxUsuario":100,"vidaUsuario":100}
                //{"data":"{\"contrasena\":\"123434\",\"correo\":\"lixander\"}\n","idObjeto":"error"}
                //{"data":"{\"contrasena\":\"ghh\",\"correo\":\"vgvh\"}\\n","idObjeto":"login"}
                serverMessage = socket.recvStr().toString();
                Log.v("Mensaje Servidor",serverMessage);

                socket.close();
                cont.term();
            }catch (ZMQException e){
                e.printStackTrace();
            }
            return null;
        }

    }
    private class SendMessagePos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                ZMQ.Context cont = ZMQ.context(1);
                ZMQ.Socket socket = cont.socket(ZMQ.REQ); // Se crea el socker de tipo requeste , el servidor está seteado como reply
                socket.connect("tcp://192.168.0.134:5555"); // se conecta al server indicado en la ip [protocolo]://[dirección ip]:[puerto]

                //Log.v("A Servidor", message);

                socket.send(msgPosActual);
               // Log.v("Mensaje Login: ", loginJson);

                //{"estadoUsuario":"vivo","idUsuario":5,"nickName":"alexis","nivelUsuario":3,"poderAtaque":1,"posUsuario":"{\"idUsuario\":5,\"latitud\":6.2447470000000003,\"longitud\":-75.574827999999997}\n","token":"null","vidaMaxUsuario":100,"vidaUsuario":100}
                //{"estadoUsuario":"vivo","idUsuario":2,"nickName":"lixandro","nivelUsuario":3,"poderAtaque":1,"posUsuario":"{\"idUsuario\":2,\"latitud\":6.2447470000000003,\"longitud\":-75.574827999999997}\n","token":"null","vidaMaxUsuario":100,"vidaUsuario":100}
                //{"data":"{\"contrasena\":\"123434\",\"correo\":\"lixander\"}\n","idObjeto":"error"}
                //{"data":"{\"contrasena\":\"ghh\",\"correo\":\"vgvh\"}\\n","idObjeto":"login"}
                serverMessage = socket.recvStr().toString();
                Log.v("Mensaje Servidor",serverMessage);

                socket.close();
                cont.term();
            }catch (ZMQException e){
                e.printStackTrace();
            }
            return null;
        }

    }



    /*public boolean onMarkerClick(Marker marker) {
        // TODO Auto-generated method stub
        if(marker.equals(m1)){
            Log.w("Click", "test");
            return true;
        }
        return false;
    }
*/
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public String JSONPosicionToServer(String lat, String lon, String id)
    {

        try {


            JSONObject posJson = new JSONObject();
            posJson.put("latitud", lat);
            posJson.put("longitud", lon);
            posJson.put("idUsuario", id);


            return posJson.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
