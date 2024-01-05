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
                .baseUrl("http://localhost:9000/api_client/login")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestBody form = new FormBody.Builder()
                .add("email", ed1.getText().toString())
                .add("password", ed2.getText().toString())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);


        Call<List<AuthenticationTokenDTO>> call = jsonPlaceHolderApi.getToken();
        /*call.enqueue(new Callback<List<AuthenticationTokenDTO>>() {
            @Override
            public void onResponse(Call<List<AuthenticationTokenDTO>> call, Response<List<AuthenticationTokenDTO>> response) {
               if(!response.isSuccessful()){
                Toast.makeText(getApplicationContext(),
                        "KHDM MOK...",Toast.LENGTH_SHORT).show();
                return;
               }
               List<AuthenticationTokenDTO> posts = response.body();
               for(AuthenticationTokenDTO post:posts){
                   String content="";
                   content+="ID" +post.getId()+"\n";
                   content+="Token"+post.getToken();
                   Intent home = new Intent(MainActivity.this, Homes.class);
                   Log.i("ddddddddddddddddddddd1","mok");
                   startActivity(home);

                }
            }

            @Override
            public void onFailure(Call<List<AuthenticationTokenDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();

        });*/
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



               /* Log.d("MainActivity","this btn was clicked")
                val retrofit = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val appService = retrofit.create(AppService::class.java)
                appService.getAppData().enqueue(object :Callback<List<App>>{
                    override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                        val list = response.body()
                        if(list !=null){
                            for (app in list){
                                Log.d("MainActivity","id is ${app.id}")
                                Log.d("MainActivity","name is ${app.name}")
                                Log.d("MainActivity","version is ${app.version}")
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<App>>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
*/
                /*

                RequestBody form = new FormBody.Builder()
                        .add("email", ed1.getText().toString())
                        .add("password", ed2.getText().toString())
                        .build();
                RequestBody formBody = new RequestBody.create(form,JSON);
                Request request = new Request.Builder()
                        .url("http://192.168.236.239:9000/api_client/login")
                        .post(formBody)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build();


                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                        Log.i("ddddddddddddddddddddd","mok");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Redirecting...",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

*/
                //if(ed1.getText().toString().equals("admin") &&
                // ed2.getText().toString().equals("admin")) {
                // }else{
                //     Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

                //            tx1.setVisibility(View.VISIBLE);
                //    tx1.setBackgroundColor(Color.RED);
                //    counter--;
                //   tx1.setText(Integer.toString(counter));

                //if (counter == 0) {
                //b1.setEnabled(false);
                //}
            }
            //}
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