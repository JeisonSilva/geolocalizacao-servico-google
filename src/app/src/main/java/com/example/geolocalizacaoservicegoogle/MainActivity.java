package com.example.geolocalizacaoservicegoogle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText input_edit_endereco;
    private RequestQueue requestQueue;
    private AppCompatButton btn_pesquisa;
    private AppCompatTextView tv_latitude;
    private AppCompatTextView tv_longitude;
    private ProgressBar pgb_carregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.input_edit_endereco = findViewById(R.id.input_edit_endereco);
        this.tv_latitude = findViewById(R.id.tv_latitude);
        this.tv_longitude = findViewById(R.id.tv_longitude);
        this.btn_pesquisa = findViewById(R.id.btn_pesquisa);
        this.pgb_carregar = findViewById(R.id.pgb_carregar);
        this.requestQueue = new Volley().newRequestQueue(getApplicationContext());

        this.btn_pesquisa.setOnClickListener(pesquisarListener);
    }

    View.OnClickListener pesquisarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String endereco = String.valueOf(input_edit_endereco.getText());
            if(endereco.isEmpty()){
                Toast.makeText(getApplicationContext(), "Digite um endereço", Toast.LENGTH_LONG).show();
                return;
            }

            pgb_carregar.setVisibility(View.VISIBLE);
            String url =String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=", endereco.replace(" ", "+"));
            StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   try {
                       Gson gson = new Gson();
                       Localizacao localizacao = gson.fromJson(response, Localizacao.class);

                       tv_latitude.setText(localizacao.obterLatitude());
                       tv_longitude.setText(localizacao.obterLongitude());
                       pgb_carregar.setVisibility(View.GONE);
                   }catch (Exception ex) {
                       ex.getStackTrace();
                       pgb_carregar.setVisibility(View.GONE);
                   }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Falha ao solicitar coordenadas", Toast.LENGTH_LONG).show();
                    pgb_carregar.setVisibility(View.GONE);
                }
            });

            requestQueue.add(jsonArrayRequest);
        }
    };
}