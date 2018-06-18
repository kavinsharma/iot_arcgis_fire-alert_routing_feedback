package com.example.root.rroute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by root on 11/25/17.
 */


public class MainActivity extends AppCompatActivity{
    TextView address;
    TextView distance;
    TextView mobile;
    TextView Contact;
    Button button;
    Button button1;
    DatabaseReference Dref;
    String fire;
    String StatuS;
    Integer Stat;
    String lat ="null";
    String lon;
    String contact;
    String Address;

    public Double lo = null;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address= (TextView) findViewById(R.id.textView);
        distance=(TextView) findViewById(R.id.textView2);
        mobile=(TextView) findViewById(R.id.textView3);
        Contact = (TextView) findViewById(R.id.textView5);
        button = (Button) findViewById(R.id.button2);
        button1 = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MainActivity.this, RoutingSample.class);

                startActivity(login);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(MainActivity.this, Analytics.class);
                startActivity(login);
            }
        });

        Dref= FirebaseDatabase.getInstance().getReference();
        Dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

/*                Userdata uInfo = new Userdata();
                uInfo.setLat(dataSnapshot.getValue(Userdata.class).getLat()); //set the name
                uInfo.setLon(dataSnapshot.getValue(Userdata.class).getLon());
  */              StatuS=dataSnapshot.child("fire_sensor_status").getValue().toString();
                fire=dataSnapshot.child("firetype").getValue().toString();

                String someVariable = dataSnapshot.child("lat").getValue().toString();
                contact=dataSnapshot.child("contact").getValue().toString();


                Address = dataSnapshot.child("address").getValue().toString();
                Stat = Integer.parseInt(StatuS);
                if (Stat == 1) {

                  //  lon = Double.parseDouble(temp[1]);
                    address.setText("Incident Address: " + Address);
                    distance.setText("FireType: " +fire);
                    Contact.setText(contact);
                    mobile.setText("Mobile: ");
                    button.setEnabled(true);
                    button1.setEnabled(false);
                    lat = someVariable;
                }
                else {
                    address.setText("No Fire Incident Reported");
                    distance.setText(" ");
                    Contact.setText(" ");
                    mobile.setText(" ");
                    button.setEnabled(false);
                    button1.setEnabled(true);
                }

    /*           Log.d(TAG, "showData: name: " + uInfo.getLat());
                Log.d(TAG, "showData: email: " + uInfo.getLon());
*/
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }



        });


        //--------single value


        //------- single value ends here
        String tokenfordb = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message1");
        Log.d(TAG, "Refreshed token for Server: " + tokenfordb);
        myRef.setValue(tokenfordb);
    }
}






