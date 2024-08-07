/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.exemplomapa;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jxmapviewer.viewer.GeoPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteService {
    private static final String API_KEY = "SUA_KEY";
    private static final String URL_TEMPLATE = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=" + API_KEY + "&start=%s,%s&end=%s,%s";

    public List<GeoPosition> getRoute(GeoPosition start, GeoPosition end) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = String.format(URL_TEMPLATE, 
            String.format("%.6f", start.getLongitude()).replace(",", "."),
            String.format("%.6f", start.getLatitude()).replace(",", "."),
            String.format("%.6f", end.getLongitude()).replace(",", "."),
            String.format("%.6f", end.getLatitude()).replace(",", ".")
        );
        
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();

        List<GeoPosition> route = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (jsonObject.has("features")) {
            JSONArray features = jsonObject.getJSONArray("features");
            if (features.length() > 0) {
                JSONObject feature = features.getJSONObject(0);
                if (feature.has("geometry")) {
                    JSONObject geometry = feature.getJSONObject("geometry");
                    if (geometry.has("coordinates")) {
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        for (int i = 0; i < coordinates.length(); i++) {
                            JSONArray coord = coordinates.getJSONArray(i);
                            double lon = coord.getDouble(0);
                            double lat = coord.getDouble(1);
                            route.add(new GeoPosition(lat, lon));
                            System.out.println("lat"+lat);
                        }
                    }
                }
            }
        } else {
            System.out.println("Erro na resposta da API: " + jsonResponse);
        }

        return route;
    }
}
