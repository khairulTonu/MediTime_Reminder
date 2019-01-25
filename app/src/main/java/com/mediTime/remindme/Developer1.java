package com.blanyal.remindme;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;

public class Developer1 extends AppCompatActivity {

    TextView txtEmail;
    Button nxtButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer1);
        txtEmail = (TextView) findViewById(R.id.textEmail);
        nxtButton = (Button) findViewById(R.id.nxtBtn);
        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(Developer1.this,Developer2.class);
                startActivity(newIntent);
            }
        });
        /*txtEmail.setText(Html.fromHtml("<a href=\"mailto:mdkislam27@gmail.com\">mdkislam27@gmail.com</a>"));
        txtEmail.setMovementMethod(LinkMovementMethod.getInstance());*/


    }
}
