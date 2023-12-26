package com.example.be;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.Datas.Client;
import com.example.be.Datas.MultiTransfer;
import com.example.be.Datas.Transfer;
import com.example.be.Payment.PaymentAdapter;
import com.example.be.Payment.PaymentModel;
import com.example.be.Restituer.RestituAdapter;
import com.example.be.Restituer.RestituModel;
import com.example.be.Retrofit.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Resitution extends AppCompatActivity /*implements RestituAdapter.onNoteListener*/ {
    private static final String MY_PREFS_NAME ="MyPrefsFile";
    private static final String MY_PREFS_NAME1 ="MyPrefsFile1";
    RecyclerView recycler_view;
    RestituAdapter adapter;
    SearchView searchView;
    LinearLayout layout;
    List<RestituModel> restitu_list;
    Button b,r;
    EditText c,motif;
    TextView ln,fn,idcard,montant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resitution);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);//"No name defined" is the default value.
        int idName = prefs.getInt("Id", 0); //0 is the default value.
        b= findViewById(R.id.rechercheref);
        r= findViewById(R.id.restituerRR);
        c= findViewById(R.id.ref);
        layout=findViewById(R.id.restitutionid);
        motif=findViewById(R.id.motif3);
        ln=findViewById(R.id.lname);
        fn=findViewById(R.id.fname);
        idcard=findViewById(R.id.idcar);
        montant=findViewById(R.id.price);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://client-microserv.herokuapp.com/api_client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       /* RequestBody form = new FormBody.Builder()
                .add("email", ed1.getText().toString())
                .add("password", ed2.getText().toString())
                .build();*/
        layout.setVisibility(View.INVISIBLE);

        c.getText();
        JsonPlaceHolderApi jsonPlaceHolderApi =  retrofit.create(JsonPlaceHolderApi.class);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MultiTransfer> call = jsonPlaceHolderApi.getTransfer("Bearer "+name, idName,c.getText().toString());
                call.enqueue(new Callback<MultiTransfer>() {
                    @Override
                    public void onResponse(Call<MultiTransfer> call, Response<MultiTransfer> response) {
                        if(!response.isSuccessful()){
                            //Toast.makeText(getApplicationContext(),
                                //    "KHDM MOK...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Toast.makeText(getApplicationContext(),
                            //    "yes",Toast.LENGTH_SHORT).show();
                        MultiTransfer benef = response.body();
                         //Log.i("benef", String.valueOf(benef));
                        //Log.i("tttt", String.valueOf(benef));

                        Transfer trsmodel = benef.getTransfers().get(0);
                        Log.i(TAG, "onResponse1111: "+trsmodel.getReceiver_lname());
                        ln.setText(trsmodel.getReceiver_lname());
                        fn.setText(trsmodel.getReceiver_fname());
                        idcard.setText(trsmodel.getReceiver_phnumber());
                        String t=String.valueOf(trsmodel.getTransfer_amount());
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME1, MODE_PRIVATE).edit();
                        editor.putFloat("Solde", trsmodel.getTransfer_amount());

                        //editor.putInt("fname", response.body().getId());
                        // editor.putInt("", response.body().getId());
                        editor.apply();
                        montant.setText(t+"Dhs");
                        layout.setVisibility(View.VISIBLE);
                           // for (Transfer trsmodel : payment_list) {
                               // Log.i("mok",trsmodel.getReceiver_lname());

                                /*ln.setText(trsmodel.getReceiver_lname());
                                fn.setText(trsmodel.getReceiver_fname());
                                idcard.setText(trsmodel.getTransfer_status());
                                montant.setText((int) trsmodel.getTransfer_amount());*/



                        //return;
                        //Client client = response.body();
/*



                        //Intent home = new Intent(MainActivity.this, Homes.class);
                        //Log.i("ddddddddddddddddddddd1","mok");
                        //startActivity(home);*/

                    }


                    @Override
                    public void onFailure(Call<MultiTransfer> call, Throwable t) {
                        Log.d("TAG","keke = "+t.toString());
                        Toast.makeText(getApplicationContext(),
                                "Done",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MultiTransfer> call1 = jsonPlaceHolderApi.putTransfer("Bearer "+name, idName,c.getText().toString(),motif.getText().toString());
                call1.enqueue(new Callback<MultiTransfer>() {
                    @Override
                    public void onResponse(Call<MultiTransfer> call, Response<MultiTransfer> response) {
                        if(!response.isSuccessful()){
                            Log.d("TAG","keke = "+response.toString());
                            Toast.makeText(getApplicationContext(),
                                    "This payment has already been refunded.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(),
                                "You have asked for a refund.",Toast.LENGTH_SHORT).show();
                        Intent historique = new Intent(Resitution.this, Homes.class);
                        startActivity(historique);
                        //MultiTransfer benef = response.body();
                        //Log.i("benef", String.valueOf(benef));
                        //Log.i("tttt", String.valueOf(benef));

                        /*Transfer trsmodel = benef.getTransfers().get(0);
                        Log.i(TAG, "onResponse1111: "+trsmodel.getReceiver_lname());
                        ln.setText(trsmodel.getReceiver_lname());
                        fn.setText(trsmodel.getReceiver_fname());
                        idcard.setText(trsmodel.getReceiver_phnumber());
                        String t=String.valueOf(trsmodel.getTransfer_amount());
                        montant.setText(t+"Dhs");*/
                        // for (Transfer trsmodel : payment_list) {
                        // Log.i("mok",trsmodel.getReceiver_lname());

                                /*ln.setText(trsmodel.getReceiver_lname());
                                fn.setText(trsmodel.getReceiver_fname());
                                idcard.setText(trsmodel.getTransfer_status());
                                montant.setText((int) trsmodel.getTransfer_amount());*/



                        //return;
                        //Client client = response.body();
/*



                        //Intent home = new Intent(MainActivity.this, Homes.class);
                        //Log.i("ddddddddddddddddddddd1","mok");
                        //startActivity(home);*/

                    }


                    @Override
                    public void onFailure(Call<MultiTransfer> call, Throwable t) {
                        Log.d("TAG","keke = "+t.toString());
                        Toast.makeText(getApplicationContext(),
                                "Ce payment est deja restituer",Toast.LENGTH_SHORT).show();


                    }
                });


            }
        });
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String newText) {
                List<RestituModel> filteredList = new ArrayList<>();
                for(RestituModel item: restitu_list ){
                    if(item.getId().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
            }
        });
        //b = findViewById(R.id.restituer_tv);
        //searchView= findViewById(R.id.action_search);
        setRecyclerView();
        /*List<> filteredList = new ArrayList<>();
            for(PaymentModel item: payment_list ){*/

          //  }*/
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }

            private void filter(String newText) {
                List<PaymentModel> filteredList = new ArrayList<>();
                for(PaymentModel item: payment_list ){
                    if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
            }
        });*/
    }/*
    private void setRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestituAdapter(this.getList(),this);
        recycler_view.setAdapter(adapter);
        /* View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_historique, container, false);
         recycler_view = view.findViewById(R.id.recycler_view1);
         recycler_view.setLayoutManager(new LinearLayoutManager(this.getContext()));
         recycler_view.setAdapter(new PaymentAdapter(getList()));*/

    /*}
    private List<RestituModel> getList(){
        restitu_list =  new ArrayList<>();

        restitu_list.add(new RestituModel("1","Mickey","2000",""));
        restitu_list.add(new RestituModel("2","Lamia","3000",""));
        restitu_list.add(new RestituModel("3","Mona","5000",""));
        restitu_list.add(new RestituModel("4","mimi","20000",""));
        Log.i("pay", String.valueOf(restitu_list));
        return restitu_list;
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG,"noteclicked:clicked");
        RestituModel a = restitu_list.get(position);
        Intent intent = new Intent(this, Motif.class );
        Log.i("aaaaaaaaaaaaaa",this.getList().get(position).getName());
        startActivity(intent);
    }*/
}