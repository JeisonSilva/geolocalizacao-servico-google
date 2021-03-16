package com.example.geolocalizacaoservicegoogle;

public class Localizacao {
    Result[] results;

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }

    public String obterLatitude() {
        float latitude = results[0].geometry.location.lat;
        return String.valueOf(latitude);
    }

    public String obterLongitude() {
        float longitude = results[0].geometry.location.lng;
        return String.valueOf(longitude);
    }
}
