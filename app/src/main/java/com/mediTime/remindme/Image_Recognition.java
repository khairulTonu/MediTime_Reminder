package com.blanyal.remindme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import mehdi.sakout.fancybuttons.FancyButton;

public class Image_Recognition extends Activity {

    Bitmap bitmap;
    public String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__recognition);

        Button btnNextPage = (Button) findViewById(R.id.btnNextPage);
        final TextView txtResult = (TextView) findViewById(R.id.txtResult);
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        /*if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("picture");

            final Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);*/
            bitmap= TakePicture.cropped;
            if(bitmap == null) {
                bitmap = HomePage.bitmap;
            }

            image.setImageBitmap(bitmap);

            btnNextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                    if (!textRecognizer.isOperational())
                        Log.e("ERROR", "Detector dependencies are not yet available");
                    else {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> items = textRecognizer.detect(frame);
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < items.size(); ++i) {
                            TextBlock item = items.valueAt(i);
                            stringBuilder.append(item.getValue());
                            stringBuilder.append("\n");
                        }
                        stringBuilder.append('.');
                        //txtResult.setText(stringBuilder.toString());


                        str = stringBuilder.toString();
                        Intent myintent = new Intent(Image_Recognition.this, ReminderAddActivity.class);
                        myintent.putExtra("translate", str);
                        startActivity(myintent);
                    }
                }
            });
        }
    //}
}
