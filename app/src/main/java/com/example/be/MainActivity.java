/*
package com.example.be;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers la page d'accueil sans vérification
                Intent home = new Intent(MainActivity.this, Homes.class);
                startActivity(home);
            }
        });
    }
}



*/

package com.example.be;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.Datas.AuthenticationDTO;
import com.example.be.Datas.AuthenticationTokenDTO;
import com.example.be.Retrofit.JsonPlaceHolderApi;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Button b1,b2;
    EditText ed1,ed2;
    public MediaType JSON= MediaType.parse("application/json");
    TextView tx1;
    int counter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        b2 = (Button)findViewById(R.id.button2);
        Retrofit retrofit= new Retrofit.Builder()

                .baseUrl("http://100.89.160.207:9000/api_client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestBody form = new FormBody.Builder()
                .add("email", ed1.getText().toString())
                .add("password", ed2.getText().toString())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);


        Call<List<AuthenticationTokenDTO>> call = jsonPlaceHolderApi.getToken();

        //tx1 = (TextView)findViewById(R.id.textView3);
        // tx1.setVisibility(View.GONE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<AuthenticationTokenDTO> call1 = jsonPlaceHolderApi.postemail(new AuthenticationDTO(ed1.getText().toString(),ed2.getText().toString()));
                call1.enqueue(new Callback<AuthenticationTokenDTO>() {
                    @Override
                    public void onResponse(Call<AuthenticationTokenDTO> call, Response<AuthenticationTokenDTO> response) {

                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong password,Please Try Again", Toast.LENGTH_SHORT).show();


                            return;
                        }else{

                            Log.i("blabla",response.body().getToken());

                            Intent home = new Intent(MainActivity.this, Homes.class);
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("Token", response.body().getToken());
                            editor.putInt("Id", response.body().getId());
                            //editor.putInt("fname", response.body().getId());
                            // editor.putInt("", response.body().getId());
                            editor.apply();
                            //getIntent().putExtra("Token",response.body().getToken());
                            //getIntent().putExtra("Id",response.body().getId());
                            //Log.i("ddddddddddddddddddddd1","mok");
                            startActivity(home);
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticationTokenDTO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();

                    }
                });







            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent register = new Intent(MainActivity.this, Register.class);
                //startActivity(register);
                finish();
            }
        });


    }
}