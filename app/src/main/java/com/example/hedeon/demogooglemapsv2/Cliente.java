package com.example.hedeon.demogooglemapsv2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hedeon on 21/04/2015.
 */
public class Cliente {

        String password;
        String nombre;




        public Cliente(String nombre, String password) {

            this.password = password;
            this.nombre = nombre;


        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public String getNombre() {
            return nombre;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String loginToJSON(){

            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("contrasena", getPassword());
                jsonObject.put("correo",getNombre());



                return jsonObject.toString();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "";
            }

        }
    public String loginToServer(String loginJson)
    {
        JSONObject jsonObject= new JSONObject();
        try {

            jsonObject.put("data",loginJson);
            jsonObject.put("idObjeto", "login");


            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

}
