package com.blanyal.remindme;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;


public class ShowData extends Activity {

    DatabaseHelper myDb;
    TextView medicinName,txtHourlyDose,txtHourlyTime,txtDays,txtMorningDose,txtMorningTime,txtNoonDose,txtNoonTime,txtAfternoonDose,txtAfternoonTime,
            txtNightDose,txtNightTime,txtBeforeMeal,txtAfterMeal;
    FancyButton btnNext,btnEdit;

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        myDb = new DatabaseHelper(this);
        //LayoutInflater inflater = getLayoutInflater();
        //tl=(TableLayout) findViewById(R.id.tablelayout);
        //TableRow row = (TableRow)inflater(R.layout.tablerow, tl, false);

        btnEdit = (FancyButton) findViewById(R.id.btnEdit);
        btnNext = (FancyButton) findViewById(R.id.btnNext);
        medicinName = (TextView)findViewById(R.id.txtMedicine);
        //medicinName.setText("Hello");
        txtHourlyDose = (TextView) findViewById(R.id.txtHourlyDose);
        txtHourlyTime = (TextView) findViewById(R.id.txtHourlyTime);
        txtDays = (TextView) findViewById(R.id.txtDays);
        txtMorningDose = (TextView) findViewById(R.id.txtMorningDose);
        txtMorningTime = (TextView) findViewById(R.id.txtMorningTime);
        txtNoonDose = (TextView) findViewById(R.id.txtNoonDose);
        txtNoonTime = (TextView) findViewById(R.id.txtNoonTime);
        txtAfternoonDose = (TextView) findViewById(R.id.txtAfternoonDose);
        txtAfternoonTime = (TextView) findViewById(R.id.txtAfternoonTime);
        txtNightDose = (TextView) findViewById(R.id.txtNightDose);
        txtNightTime = (TextView) findViewById(R.id.txtNightTime);
        txtBeforeMeal = (TextView) findViewById(R.id.txtBeforeMeal);
        txtAfterMeal = (TextView) findViewById(R.id.txtAfterMeal);
        final Cursor res = myDb.getAllData();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteAll();
                Toast.makeText(ShowData.this, "Deletion Successful!!", Toast.LENGTH_LONG).show();
               // Toast.makeText(this, "Deletion Successful!!", Toast.LENGTH_LONG).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(res.getCount() == 0)
                {
                    Toast.makeText(ShowData.this, "Error!! Nothing Found!!", Toast.LENGTH_LONG).show();
                    //showMessage("Error!","Nothing Found!");
                    return;
                }

        StringBuffer buffer = new StringBuffer();
        if (res.moveToNext())
        {
            buffer = new StringBuffer();
            buffer.append(res.getString(0));
            medicinName.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(1));
            txtMorningDose.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(2));
            txtNoonDose.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(3));
            txtAfternoonDose.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(4));
            txtNightDose.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(5));
            txtBeforeMeal.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(6));
            txtAfterMeal.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(7));
            txtHourlyDose.setText(buffer.toString());
            buffer = new StringBuffer();
            buffer.append(res.getString(8));
            txtDays.setText(buffer.toString());

        }


        //showMessage("Data",buffer.toString());
        //textView.setText(buffer.toString());


        Log.e("Success","Read data");
        //txtMorningTime.setText("Hello");
        //viewAll();


            }
        });


    }

    public void viewAll()
    {
        /*btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0)
                {
                    //showMessage("Error!","Nothing Found!");
                    Toast.makeText(ShowData.this, "Database is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext())
                {
                    buffer.append("Medicine Name: "+ res.getString(0)+"\n");
                    //medicinName.setText("Hello");
                    buffer.append("Morning: "+ res.getString(1)+"\n");
                    //txtMorningDose.setText(res.getString(1));
                    buffer.append("Noon: "+ res.getString(2)+"\n");
                    //txtNoonDose.setText(res.getString(2));
                    buffer.append("After Noon: "+ res.getString(3)+"\n");
                    //txtAfternoonDose.setText(res.getString(3));
                    buffer.append("Night: "+ res.getString(4)+"\n");
                    //txtNightDose.setText(res.getString(4));
                    buffer.append("Before Meal: "+ res.getString(5)+"\n");
                    //txtBeforeMeal.setText(res.getString(5));
                    buffer.append("After Meal: "+ res.getString(6)+"\n");
                    //txtAfterMeal.setText(res.getString(6));
                    buffer.append("Hourly: "+ res.getString(7)+"\n");
                    //txtHourly.setText(res.getString(7));
                    buffer.append("Days: "+ res.getString(8)+"\n\n");
                    //txtDays.setText(res.getString(8));

                }

                //showMessage("Data",buffer.toString());
                //textView.setText(buffer.toString());


                Log.e("Success","Read data");
           // }
        //});
    }

    /*public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowData.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/
}
