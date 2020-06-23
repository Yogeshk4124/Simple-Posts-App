package com.example.dots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dactivity_main);
        Toast.makeText(MainActivity.this,"Email:admin@gmail.com\nPassword:admin123",Toast.LENGTH_LONG).show();
        final FirebaseAuth auth= FirebaseAuth.getInstance();
        Button SignIn=findViewById(R.id.SignIn);

        final EditText Username=findViewById(R.id.username);
        final EditText Password=findViewById(R.id.password);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd=new ProgressDialog(MainActivity.this);
                pd.setMessage("Please Wait...");
                pd.show();
                String username=Username.getText().toString();
                String password=Password.getText().toString();
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this,"All fields are required",Toast.LENGTH_SHORT).show();}
                else if(password.length()<6)Toast.makeText(MainActivity.this,"Password length is less than 6",Toast.LENGTH_SHORT).show();
                else {
                    auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent =new Intent(MainActivity.this,Post.class);
                                pd.dismiss();
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener(){

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this,"Failed Login.\nEmail:admin@gmail.com\nPassword:admin123",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }
}
