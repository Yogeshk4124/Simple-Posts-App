package com.example.dots;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Scroll extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);
        final ListView list=findViewById(R.id.listView);
        Query Q= FirebaseDatabase.getInstance().getReference("Posts").orderByChild("id");
        final ArrayList<String>array_name = new ArrayList<>(),array_category = new ArrayList<>(),array_description = new ArrayList<>(),array_date = new ArrayList<>(),array_location = new ArrayList<>();
        Q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String>name = new ArrayList<>(),category = new ArrayList<>(),description = new ArrayList<>(),date = new ArrayList<>(),location = new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    name.add(ds.child("Event Name").getValue(String.class));
                    category.add(ds.child("Event Category").getValue(String.class));
                    description.add(ds.child("Event Description").getValue(String.class));
                    date.add(ds.child("Event Date").getValue(String.class));
                    location.add(ds.child("Event Location").getValue(String.class));
//                    Toast.makeText(Scroll.this,""+ds.child("Event Name").getValue(String.class),Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(Scroll.this,""+description.size(),Toast.LENGTH_LONG).show();
                Log.d("Des:",""+description.size());
                Collections.reverse(name);
                Collections.reverse(category);
                Collections.reverse(description);
                Collections.reverse(date);
                Collections.reverse(location);
                MyAdapter myAdapter=new MyAdapter(getApplicationContext(),name,category,description,date,location);
                list.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Toast.makeText(Scroll.this,""+array_category.size(),Toast.LENGTH_LONG).show();
//        MyAdapter myAdapter=new MyAdapter(this,array_name,array_category,array_description,array_date,array_location);
//        list.setAdapter(myAdapter);

    }
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> e_name,e_category,e_description,e_date,e_location;

        MyAdapter (Context c, ArrayList<String> name,ArrayList<String> category, ArrayList<String> description, ArrayList<String> date,ArrayList<String> location) {
            super(c, R.layout.scroll_temp, R.id.Event_name,name);
            this.context = c;

            this.e_name= name;
            this.e_description= description;
            this.e_date=date;
            this.e_category=category;
            this.e_location=location;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.scroll_temp, parent, false);
//            Toast.makeText(Scroll.this,"pos:"+position,Toast.LENGTH_LONG).show();
            TextView event_name = row.findViewById(R.id.Event_name);
            TextView event_date=row.findViewById(R.id.Event_date);
            TextView event_category=row.findViewById(R.id.Event_category);
            TextView event_location=row.findViewById(R.id.Event_location);
            TextView event_description=row.findViewById(R.id.Event_description);
            SpannableString content = new SpannableString(e_name.get(position));
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            event_name.setText(content);
//            event_name.setText(e_name.get(position));
            event_category.setText("Event Category: "+e_category.get(position));
            event_location.setText("Event Location: "+e_location.get(position));
            event_date.setText("Event Date: "+e_date.get(position));
            event_description.setText("Event Description:\n"+e_description.get(position));
            return row;
        }
    }
}
