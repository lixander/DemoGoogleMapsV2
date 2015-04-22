package com.example.hedeon.demogooglemapsv2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hedeon on 21/04/2015.
 */
public class Usuario {
    String estadoUsuario;
    String idUsuario;
    String nickName;
    String poderAtaque;
    String posUsuario;
    String token;
    String latitud;
    String longitud;
    String vidaMaxUsuario;
    String vidaUsuario;

    public Usuario(String Json)
    {
        try
        {
            JSONObject myJson = new JSONObject(Json);

            // use myJson as needed, for example
            estadoUsuario = myJson.optString("estadoUsuario");
            idUsuario = myJson.optString("idUsuario");
            nickName = myJson.optString("nickName");
            poderAtaque = myJson.optString("poderAtaque");
            posUsuario = myJson.optString("posUsuario");
            JSONObject posJson = new JSONObject(posUsuario);
            latitud = posJson.optString("latitud");
            longitud = posJson.optString("longitud");
            vidaMaxUsuario = myJson.optString("vidaMaxUsuario");
            vidaUsuario = myJson.optString("vidaUsuario");
            token = myJson.optString("token");


        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

    }



    public String getVidaUsuario() {
        return vidaUsuario;
    }

    public void setVidaUsuario(String vidaUsuario) {
        this.vidaUsuario = vidaUsuario;
    }

    public String getVidaMaxUsuario() {
        return vidaMaxUsuario;
    }

    public void setVidaMaxUsuario(String vidaMaxUsuario) {
        this.vidaMaxUsuario = vidaMaxUsuario;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPosUsuario() {
        return posUsuario;
    }

    public void setPosUsuario(String posUsuario) {
        this.posUsuario = posUsuario;
    }

    public String getPoderAtaque() {
        return poderAtaque;
    }

    public void setPoderAtaque(String poderAtaque) {
        this.poderAtaque = poderAtaque;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    //{"estadoUsuario":"vivo","idUsuario":5,"nickName":"alexis","nivelUsuario":3,"poderAtaque":1,"posUsuario":"{\"idUsuario\":5,\"latitud\":6.2447470000000003,\"longitud\":-75.574827999999997}\n","token":"null","vidaMaxUsuario":100,"vidaUsuario":100}


}
