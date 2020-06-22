package com.example.dots;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.powerspinner.OnSpinnerOutsideTouchListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Post extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText event_name,event_date,event_location,event_description;
    ProgressDialog pd;
    PowerSpinnerView event_category;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        Button Upload=findViewById(R.id.Upload);
        event_name=findViewById(R.id.Event_name);
        event_date=findViewById(R.id.Event_date);
        event_category=findViewById(R.id.Event_category);
        event_location=findViewById(R.id.Event_location);
        event_description=findViewById(R.id.Event_description);
        Button SeePost=findViewById(R.id.SeePost);
        Button SelectDate=findViewById(R.id.Select_Date);


        final DatePickerDialog datePickerDialog=new DatePickerDialog(this,this,Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            datePickerDialog.show();
            }
        });

        SeePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Post.this,Scroll.class);
                startActivity(intent);
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(Post.this);
                pd.setMessage("Uploading...");
                if(event_name.getText().toString().equals(""))
                    event_name.setError("Please enter Event Name");
                else if(event_category.getText().toString().equals(""))
                    event_category.setError("Please enter Event Category");
                else if(event_description.getText().toString().equals(""))
                    event_description.setError("Please enter Event description");
                else if(event_date.getText().toString().equals(""))
                    event_date.setError("Please enter Event Date");
                else if(event_location.getText().toString().equals(""))
                    event_location.setError("Please enter Event Location");
                else
                {
                    pd.show();
                    upload();
                }
            }
        });
        event_category.setSpinnerOutsideTouchListener(new OnSpinnerOutsideTouchListener() {
            @Override
            public void onSpinnerOutsideTouch(View view, MotionEvent motionEvent) {
                event_category.showOrDismiss();
            }
        });
    }
    public void upload(){
        HashMap<String,String> hm=new HashMap<>();
        final String timespan=String.valueOf(System.currentTimeMillis());
        hm.put("id",timespan);
        hm.put("Event Name",event_name.getText().toString());
        hm.put("Event Category",event_category.getText().toString());
        hm.put("Event Description",event_description.getText().toString());
        hm.put("Event Date",event_date.getText().toString());

        hm.put("Event Location",event_location.getText().toString());
//        Date currentTime = Calendar.getInstance().getTime();
        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference("Posts");

        String address="Posts/"+"post_"+timespan;
        ref1.child(timespan).setValue(hm).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(Post.this,"Post Published",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Post.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date=dayOfMonth+"/"+month+"/"+year;
        event_date.setText(date);
    }
}