package com.example.be;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.be.Benef.BenefAdapter;
import com.example.be.Benef.BenefModel;
import com.example.be.Datas.MultiTransfer;
import com.example.be.Datas.Transfer;
import com.example.be.Retrofit.JsonPlaceHolderApi;
import com.example.be.Spinner.SpinnerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transfert extends AppCompatActivity {


    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static final String MY_PREFS_NAME1 = "my_pref";
    private static final String MY_PREFS_NAME2 = "my_pref2";
    private NotificationManagerCompat notificationManagerCompat;
    //String[] benef = {"Select Mobile OS", "Android", "IOS", "Windows",
    //        "Black Berry", "Symbian", "java"};
    SpinnerAdapter adapter;
    com.example.be.Spinner.SpinnerAdapter adapters;
    BenefAdapter adapter2;
    Button send;
    Spinner spinner;
    Button ajouterbenef;
    String F,D,G;
    Button addben;
    RecyclerView recycler_view;
    ArrayList<SpinnerModel> arrayList = new ArrayList<>();
    ArrayList<SpinnerModel> arrayList1 = new ArrayList<>();
    ArrayList<BenefModel> arrayList2 = new ArrayList<>();
    ArrayList<BenefModel> arrayList3 = new ArrayList<>();
    AlertDialog dialog;
    LinearLayout layout;
    TextView txt,iki1;
    EditText edtxt;
    Button bdel;
    EditText motif;
    int a;
    ListView simpleList = null;
    private ArrayAdapter mAdapter;
    private Object NotificationApp;


    //SharedPreferences prefs2 = getSharedPreferences(MY_PREFS_NAME2, MODE_PRIVATE);
    //String otp = prefs2.getString("kn", null);

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transfert);
        //SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME2, MODE_PRIVATE).edit();
        //editor.putString("kn", getRandomNumberString());
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel= new NotificationChannel("My notification","My notification",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString("Token", null);//"No name defined" is the default value.
        int idName = prefs.getInt("Id", 0);
        SharedPreferences prefs1 = getSharedPreferences(MY_PREFS_NAME1, MODE_PRIVATE);
        String fn = prefs1.getString("fn", null);//"No name defined" is the default value.
        String ln = prefs1.getString("ln", null);
        String pn = prefs1.getString("pn", null);

        motif=findViewById(R.id.motif4);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://client-microserv.herokuapp.com/api_client/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       /* RequestBody form = new FormBody.Builder()
                .add("email", ed1.getText().toString())
                .add("password", ed2.getText().toString())
                .build();*/


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<SpinnerModel>> call = jsonPlaceHolderApi.getlistbenef("Bearer " + name, idName);
        call.enqueue(new Callback<List<SpinnerModel>>() {
            @Override
            public void onResponse(Call<List<SpinnerModel>> call, Response<List<SpinnerModel>> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(),
                         //   "KHDM MOK...", Toast.LENGTH_SHORT).show();
                    return;
                }
                // List<BenefModel> benef = response.body();

               /* String content="";
                title.setText(client.getTitle());
                ln.setText(client.getLastName());
                idcard.setText(client.getIdCard());
                phone.setText(client.getPhoneNumber());
                adress.setText(client.getAddress());
                fn.setText(client.getFirstName());*/
                arrayList1 = new ArrayList<>();
/*
                arrayList.add(new SpinnerModel("1","Mickey"));
                arrayList.add(new SpinnerModel("2","Lamia"));
                arrayList.add(new SpinnerModel("3","Mona"));
                arrayList.add(new SpinnerModel("4","Mona"));
                arrayList.add(new SpinnerModel("5","Mona"));
*/
                // Log.i("pay", String.valueOf(arrayList));
                arrayList1 = new ArrayList<>();

                String content = " ";
                List<SpinnerModel> benef = response.body();
                for (SpinnerModel benefmodel : benef) {
                    Log.i("f", benefmodel.getAccount_number());
                    arrayList1.add(new SpinnerModel(benefmodel.getAccount_number(), benefmodel.getFirstName(), benefmodel.getLastName(),benefmodel.getPhoneNumber()));
                    Log.i("thissss", String.valueOf(arrayList1));

                    content += "FirstName" + benefmodel.getFirstName() + "\n";
                    content += "LastName" + benefmodel.getLastName() + "\n";
                    content += "Phone number" + benefmodel.getPhoneNumber() + "\n";
                    content += "PhoneNumber" + benefmodel.getAccount_number() + "\n";


                    //Intent home = new Intent(MainActivity.this, Homes.class);
                    Log.i("ddddddddddddddddddddd1", "mok");
                    //startActivity(home);

                }
                Log.i("thissss", String.valueOf(arrayList1));

                // spinner.setSelection(0);
                //spinner.text
                Log.i("thiss12", String.valueOf(arrayList1));
                adapters = new com.example.be.Spinner.SpinnerAdapter(getApplicationContext(), R.layout.row, arrayList1);
                spinner.setAdapter(adapters);

            }

            @Override
            public void onFailure(Call<List<SpinnerModel>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),
                    //    "Redirecting...", Toast.LENGTH_SHORT).show();
            }
        });


        // SpinnerAdapter adapter = new SpinnerAdapter(Transfert.this,R.layout.spinner)
        spinner = findViewById(R.id.spinner);
        txt = findViewById(R.id.ikik);
        send = findViewById(R.id.envoyertransfer);
        addben = (Button) findViewById(R.id.Ajouterbenefi);
        bdel = findViewById(R.id.deletbenef);
        iki1=findViewById(R.id.ikik1);
        //recycler_view =findViewById(R.id.recycler_view3);
        edtxt = findViewById(R.id.dkik);
        ajouterbenef = findViewById(R.id.ajouterbenef);
        bdel.setVisibility(View.INVISIBLE);
        iki1.setVisibility(View.INVISIBLE);
        //layout=findViewById(R.id.container1);
        //buildDialog();
        //setRecyclerView(a);
        //simpleList =findViewById(R.id.simpleListView);
        //setRecyclerView();
        bdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt.setText("");
                bdel.setVisibility(View.INVISIBLE);
            }
        });

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        SpinnerModel myModel = (SpinnerModel) parent.getSelectedItem();
                        Log.e("dddDATA", myModel.getAccount_number());
                        Log.e("dddDATA", myModel.getLastName());
                        Log.i("ddddDATA", String.valueOf(position));
                        a = position;
                        Log.e("dddDATA", String.valueOf(a));
                        ajouterbenef.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bdel.setVisibility(View.VISIBLE);

                                com.example.be.Spinner.SpinnerAdapter customAdapter = new com.example.be.Spinner.SpinnerAdapter(getApplicationContext(), R.id.kik, arrayList1);
                                //content+="ID" +client.get+"\n";
                                if (edtxt.getText().length() == 0) {
                                    Toast.makeText(getApplicationContext(),
                                            "The amount can't be null", Toast.LENGTH_SHORT).show();
                                } else {


                                    txt.setText(customAdapter.getItem(a).getLastName() + "-" +customAdapter.getItem(a).getFirstName() + "-" + customAdapter.getItem(a).getAccount_number() + "-"+ edtxt.getText() + "MAD\n");

                                    iki1.setText(customAdapter.getItem(a).getLastName() + "-" +customAdapter.getItem(a).getFirstName() + "-" + customAdapter.getItem(a).getAccount_number() + "-" + customAdapter.getItem(a).getPhoneNumber() + "-"+ edtxt.getText() + "Dhs\n");
                                }

                            }
                        });
                        //txt.setText(customAdapter.getItem(a).getLastName() + "-" +customAdapter.getItem(a).getFirstName() + "-" + customAdapter.getItem(a).getAccount_number() + "-" + customAdapter.getItem(a).getPhoneNumber() + "-"+ edtxt.getText() + "Dhs\n");


                        //arrayList2.add(myModel.getId(),myModel.getName());


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //Intent register = new Intent(MainActivity.this, Register.class);
                //startActivity(register);
                //dialog.show();
                // setRecyclerView();
                //ArrayAdapter<String> arr;
                //arr = new ArrayAdapter<String>(this, R.layout.mylayout,simpleList );
                //simpleList.setAdapter(arr);
                Log.i("dddd1DATA", String.valueOf(a));
                //Log.i("dddd2DATA", String.valueOf(arrayList2.get(a)));
                // Log.i("dddd3DATA", String.valueOf(getList3().get(a)));
                Log.i("thissss1", String.valueOf(arrayList1));
                Log.i("lol", String.valueOf(arrayList1));

                //SpinnerModel c=arrayList.get(a-1);
                //txt.setText(txt.getText()+"\n"+customAdapter.getItem(a).getId()+"-"+customAdapter.getItem(a).getName()+"-"+edtxt.getText()+"$ ");

                //simpleList.setFilterText(String.valueOf(a));
                //customAdapter.clear();
                //mAdapter = new ArrayAdapter(getApplicationContext(),  R.id.kik);
                //  simpleList.Items.Add(c);
                //for(int j=0;j<customAdapter.getCount();j++) {
                //    if (customAdapter.equals(c)) {
                //mAdapter.add(getList().get(a-1).getName());

                //adapter2 = new BenefAdapter((List<SpinnerModel>) getList().get(a));
                //recycler_view.setAdapter(adapter2);
                //Log.i("dddd5DATA", String.valueOf(recycler_view));
                //buildDialog();
                //    } else {
                //       customAdapter.remove(arrayList.get(j));
                //    }
                // }


                // recycler_view.setHasFixedSize(true);
                // recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                // adapter2 = new BenefAdapter(arrayList.get(a).getName());
                // recycler_view.setAdapter(adapter2);
                //recycler_view.setAdapter(customAdapter);
           // }

        //}//);
        addben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent(Transfert.this, Addbenef.class);
                startActivity(res);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List list = new ArrayList();

                //showForgotDialog(Transfert.this);

                 addNotification();
                final EditText taskEditText = new EditText(Transfert.this);
                AlertDialog dialog = new AlertDialog.Builder(Transfert.this)
                        .setTitle("OTP Confirmation")
                        .setMessage("Enter your otp number?")
                        .setView(taskEditText)
                        .setPositiveButton("Confirmation", new DialogInterface.OnClickListener() {



                                @Override
                                public void onClick (DialogInterface dialog,int which){
                                com.example.be.Spinner.SpinnerAdapter customAdapter1 = new com.example.be.Spinner.SpinnerAdapter(getApplicationContext(), R.id.kik, arrayList1);
                                List<Transfer> l1 = new ArrayList<>();

                                String[] arrSplit = iki1.getText().toString().split("-");

                                Log.i("bla11", arrSplit[0]);
                                Log.i("bla11", arrSplit[1]);
                                Log.i("bla11", arrSplit[2]);
                                Log.i("bla11", arrSplit[3]);


                                int l = Integer.parseInt(edtxt.getText().toString());

                                Transfer b = new Transfer(Float.parseFloat(edtxt.getText().toString()), 1, arrSplit[1], arrSplit[0], arrSplit[3]);
                                l1.add(b);
                                Log.i("bla", String.valueOf(l1));

                                Call<MultiTransfer> call2 = jsonPlaceHolderApi.postTransfer(name, idName, new MultiTransfer(idName, fn, ln, pn, Float.parseFloat(edtxt.getText().toString()), 30.5F, 2, motif.getText().toString(), true, l1));
                                call2.enqueue(new Callback<MultiTransfer>() {
                                    @Override
                                    public void onResponse(Call<MultiTransfer> call, Response<MultiTransfer> response) {

                                        if (!response.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Transfer has been done.", Toast.LENGTH_SHORT).show();


                                            return;
                                        }


                                        Toast.makeText(getApplicationContext(),
                                                "Transfer has been done.", Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onFailure(Call<MultiTransfer> call, Throwable t) {


                                    }
                                });
                                Intent home = new Intent(Transfert.this, Homes.class);
                                startActivity(home);
                            }
                            })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            }

        });
    }
    public static String getRandomNumberString() {

        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private void showForgotDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("OTP Confirmation")
                .setMessage("Enter your otp number?")
                .setView(taskEditText)
                .setPositiveButton("Confirmation", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent home = new Intent(Transfert.this,Homes.class);
                        startActivity(home);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    private void addNotification() {
        /*NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), "My notif")
                        .setSmallIcon(R.drawable.ic_baseline_email_24) //set icon for notification
                        .setContentTitle("Notifications Example") //set title of notification
                        .setContentText("This is a notification message")
                        .setOngoing(true)
                        //this is notification message
                        .setAutoCancel(true); // makes auto cancel of notification
        Log.e("plof","pepe");
        //.setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Transfert.this);
        managerCompat.notify(1, builder.build());*/
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "my_channel_01";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_baseline_email_24)
                        .setContentTitle("Transfer OTP")
                        .setContentText("Your transfer OTP="+getRandomNumberString())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    };






                //spinner.setBackgroundColor(000);
                //spinner.getBackground().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
                //spinner.setAdapter(adapter);
                // spinner.setOnItemSelectedListener(String:onOptionsItemSelected());


            /*

            private void buildDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.dialogue, null);
                EditText name = view.findViewById(R.id.Tname);
                builder.setView(view);
                builder.setTitle("Enter name").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(name.getText().toString());
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        }
    private void addCard(String name) {
        View view =getLayoutInflater().inflate(R.layout.card,null);
        TextView nameView=view.findViewById(R.id.Sname);
        Button delete =view.findViewById(R.id.Bname);
        nameView.setText(name);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layout.removeView(view);
            }
        });
        layout.addView(view);
    }*/
  /*  ArrayList<SpinnerModel> getList() {


        arrayList =  new ArrayList<>();

        arrayList.add(new SpinnerModel("1","Mickey"));
        arrayList.add(new SpinnerModel("2","Lamia"));
        arrayList.add(new SpinnerModel("3","Mona"));
        arrayList.add(new SpinnerModel("4","Mona"));
        arrayList.add(new SpinnerModel("5","Mona"));

        Log.i("pay", String.valueOf(arrayList));
        return arrayList;



    }*/

/*
    private List<BenefModel> getList3( ) {

        arrayList2 =  new ArrayList<>();

        arrayList2.add(new BenefModel("1","Mickey"));
        arrayList2.add(new BenefModel("2","Lamia"));
        arrayList2.add(new BenefModel("3","Mona"));
        arrayList2.add(new BenefModel("4","Mona"));
        arrayList2.add(new BenefModel("5","Mona"));

        Log.i("pay", String.valueOf(arrayList2));
        return arrayList2;



    }
    ArrayList<BenefModel> getList2() {

        arrayList3 =  new ArrayList<>();

        arrayList3.add(new BenefModel("id","Nom du beneficiaire"));


        Log.i("pay", String.valueOf(arrayList2));
        return arrayList3;



    }
    private void setRecyclerView() {

        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        /*if(arrayList2.isEmpty()){
            adapter2=new BenefAdapter(this.getList2());
        }else{*/
        //Log.i("dddDATA2", String.valueOf(arrayList));
     /*   Log.i("dddDATA2", String.valueOf(getList3().get(this.a)));
        Log.i("dddDATA2", String.valueOf(this.getList3()));
        adapter2 = new BenefAdapter(arrayList);
        recycler_view.setAdapter(adapter2);
        /* View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_historique, container, false);
         recycler_view = view.findViewById(R.id.recycler_view1);
         recycler_view.setLayoutManager(new LinearLayoutManager(this.getContext()));
         recycler_view.setAdapter(new PaymentAdapter(getList()));*/

 /*   }
*/




