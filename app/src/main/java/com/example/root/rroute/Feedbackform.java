package com.example.root.rroute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by root on 12/1/17.
 */

public class Feedbackform extends AppCompatActivity implements View.OnClickListener{
    String lat_log = "77.981931,30.331999";
    long time= System.currentTimeMillis();
    Button button;
    private String cause = "";
    DatabaseReference Dref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        button = (Button) findViewById(R.id.button2);
   //     RadioButton genderBtn=(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("feedback/"+cause+"/"+time+"/");
                myRef.setValue(lat_log);
                Toast.makeText(getApplicationContext(),"Feedback Submitted Successfully",Toast.LENGTH_SHORT).show();
                DatabaseReference myRef2 = database.getReference("fire_sensor_status");
                myRef2.setValue("0");
                Toast.makeText(getApplicationContext(),"Sensor Reset Done",Toast.LENGTH_SHORT).show();
                Intent login = new Intent(Feedbackform.this, MainActivity.class);
                startActivity(login);
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton2:
                if (checked)
                    cause = "struct_fire";
                    break;
            case R.id.radioButton3:
                if (checked)

                    cause = "hazmat";
                    break;
            case R.id.radioButton4:
                if (checked)
                    // Ninjas rule
                    cause = "grass_fire";
                    break;
            case R.id.radioButton5:
                if (checked)
                    // Ninjas rule
                    cause = "trash_fire";
                    break;
            case R.id.radioButton6:
                if (checked)
                    // Ninjas rule
                    cause = "false_alarm";
                    break;
            case R.id.radioButton7:
                if (checked)
                    // Ninjas rule
                    cause = "other_fire";
                    break;





        }
    }

    @Override
    public void onClick(View view) {

    }
}
