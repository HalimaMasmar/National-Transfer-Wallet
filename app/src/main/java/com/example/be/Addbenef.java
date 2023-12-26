package com.example.be;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.be.Datas.AuthenticationDTO;
import com.example.be.Datas.AuthenticationTokenDTO;
import com.example.be.Retrofit.JsonPlaceHolderApi;
import com.example.be.Spinner.SpinnerModel;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Addbenef extends AppCompatActivity {
    private static final String MY_PREFS_NAME = "MyPrefsFile" ;
    EditText ln,fn,pn,an;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbenef);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);//"No name defined" is the default value.
        int idName = prefs.getInt("Id", 0);
        btn=findViewById(R.id.button_submit);
        fn=findViewById(R.id.editText1);
        an=findViewById(R.id.editText2);
        pn=findViewById(R.id.editText3);
        ln=findViewById(R.id.editText5);
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://client-microserv.herokuapp.com/api_client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        /*RequestBody form = new FormBody.Builder()
                .add("email", ed1.getText().toString())
                .add("password", ed2.getText().toString())
                .build();*/


        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);


       //Call<SpinnerModel> call = jsonPlaceHolderApi.postbenef();
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SpinnerModel> call = jsonPlaceHolderApi.postbenef(name,idName,new SpinnerModel(an.getText().toString(),fn.getText().toString(),ln.getText().toString(),pn.getText().toString()));
                call.enqueue(new Callback<SpinnerModel>() {
                    @Override
                    public void onResponse(Call<SpinnerModel> call, Response<SpinnerModel> response) {

                        if(!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "KHDM MOK...", Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(Addbenef.this, Transfert.class);
                            //getIntent().putExtra("Token",response.body().getToken());
                            // getIntent().putExtra("Id",response.body().getId());
                            // Log.i("ddddddddddddddddddddd1","mok");
                            startActivity(home);


                            return;
                        }else{
                            //Log.i("blabla",response.body().getToken());

                            Intent home = new Intent(Addbenef.this, Transfert.class);
                            //getIntent().putExtra("Token",response.body().getToken());
                            // getIntent().putExtra("Id",response.body().getId());
                            // Log.i("ddddddddddddddddddddd1","mok");
                            startActivity(home);
                            //return;
                        }
                    }

                    @Override
                    public void onFailure(Call<SpinnerModel> call, Throwable t) {
                        Intent home = new Intent(Addbenef.this, Transfert.class);
                        //getIntent().putExtra("Token",response.body().getToken());
                        // getIntent().putExtra("Id",response.body().getId());
                        // Log.i("ddddddddddddddddddddd1","mok");
                        startActivity(home);

                    }
                });

            }
        });



            }
}