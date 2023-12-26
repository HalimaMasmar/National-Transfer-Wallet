package com.example.be;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.Datas.AuthenticationTokenDTO;
import com.example.be.Datas.Client;
import com.example.be.Datas.Compte;
import com.example.be.Datas.Transfer;
import com.example.be.Retrofit.JsonPlaceHolderApi;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Homes extends AppCompatActivity {
    private static final String MY_PREFS_NAME ="MyPrefsFile";
    private static final String MY_PREFS_NAME1 = "my_pref";
    private static final String MY_PREFS_NAME2 ="MyPrefsFile1";
    Button historique;
    Button transfert,restitution,logout;
    TextView ln,fn,idcard,phone,adress,title;
    int id;
    String is, token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);
        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME2, MODE_PRIVATE);
        Float solde1 = prefs1.getFloat("Solde", 0);//"No name defined" is the default value.
        int idName = prefs.getInt("Id", 0); //0 is the default value.

        historique = (Button) findViewById(R.id.Historique);
        restitution = (Button) findViewById(R.id.restitution);
        transfert = (Button) findViewById(R.id.Transfert);
        logout=findViewById(R.id.logout);
        ln=findViewById(R.id.lastname);
        fn=findViewById(R.id.firstname);
        idcard=findViewById(R.id.cardid);
        phone=findViewById(R.id.phone);
        adress=findViewById(R.id.adress);
        title=findViewById(R.id.title);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                AlertDialog.Builder builder = new AlertDialog.Builder(Homes.this);
                builder.setTitle("Log out confirmation");
                builder.setMessage("Do you want to log out ?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "Session in", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Session is still on", Toast.LENGTH_SHORT).show();
                       // finish();
                    }
                });
                builder.show();
            }
        });




        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),
                        //"Redirecting...", Toast.LENGTH_SHORT).show();
                Intent historique = new Intent(Homes.this, Historique.class);
                startActivity(historique);

            }

        });
        restitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent res = new Intent(Homes.this, Resitution.class);
                startActivity(res);

            }

        });

        transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "Redirecting...", Toast.LENGTH_SHORT).show();
                Intent trans = new Intent(Homes.this, Transfert.class);
                startActivity(trans);

            }

        });
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://client-microserv.herokuapp.com/api_client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);


        Call<Client> call = jsonPlaceHolderApi.getclient("Bearer "+name, idName);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),
                            "KHDM MOK...",Toast.LENGTH_SHORT).show();
                    return;
                }
                Client client = response.body();

                String content="";
                title.setText(client.getTitle());
                    ln.setText(client.getLastName());
                idcard.setText(client.getIdCard());
                phone.setText(client.getPhoneNumber());
                //adress.setText(client.getAddress());
                fn.setText(client.getFirstName());

                for (Compte trsmodel : client.getCompte()) {
                    Log.i("yey", String.valueOf(trsmodel.getSolde()));
                    adress.setText( String.valueOf(trsmodel.getSolde()+solde1)+"MAD");


                }
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME1, MODE_PRIVATE).edit();
                editor.putString("fn", client.getFirstName());
                editor.putString("ln", client.getLastName());
                editor.putString("pn", client.getPhoneNumber());

                editor.apply();


                    content+="FirstName" +client.getFirstName()+"\n";
                    content+="LastName"+client.getLastName()+"\n";
                    content+="IDcard" +client.getIdCard()+"\n";
                 content+="PhoneNumber" +client.getPhoneNumber()+"\n";
                  content+="adress" +client.getAddress()+"\n";
                //content+="ID" +client.get+"\n";

                    //Intent home = new Intent(MainActivity.this, Homes.class);
                    Log.i("ddddddddddddddddddddd1","mok");
                    //startActivity(home);

                }


            @Override
            public void onFailure(Call<Client> call, Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Redirecting...",Toast.LENGTH_SHORT).show();

            }
    });
    }



    }