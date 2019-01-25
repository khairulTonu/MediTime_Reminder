package com.blanyal.remindme;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.R.attr.button;
import static android.R.attr.lines;
import static com.blanyal.remindme.R.id.alarmMorning;

import static com.blanyal.remindme.R.id.list_item;


public class UserEditData extends Activity {

    DatabaseHelper myDb;
    int m=-1;
    TextView textView,textView2,textView3,textView4;
    FancyButton addData,skipBtn;
    EditText medicine_name,morning,noon,afternoon,night,before_meal,after_meal,hourly,days;
    public String medicineName,scheduleTime,message;
    ArrayList<String> list = new ArrayList<String>();
    StringBuilder stringBuilderChar = new StringBuilder();
    StringBuilder stringBuilderNum = new StringBuilder();
    CheckBox beforeMealCheck,afterMealCheck;
    MediaPlayer mPlayer;
    TextView txt;
    ImageButton alarmHourly, alarmMorning, alarmNoon, alarmAferNoon, alarmNight;


    public void AddData(final String medicineName, final String medicineTime, final String BAMeal, final String medicineDays) {
        //textView.setText(list.get(0));
        //textView2.setText(list.get(1));
        //textView3.setText(list.get(2));
        //textView4.setText(list.get(3));
        //setData(medicineName,medicineTime,BAMeal,medicineDays);
        parseData();

        addData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*boolean isInserted = myDb.insertData(medicine_name.getText().toString(), morning.getText().toString(), noon.getText().toString(),
                                afternoon.getText().toString(), night.getText().toString(),  hourly.getText().toString(), days.getText().toString());


                        if (isInserted == true) {
                            Toast.makeText(UserEditData.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserEditData.this, "Data  not inserted", Toast.LENGTH_SHORT).show();
                        }*/
                        //ParseData();
                        parseData();

                    }
                }

        );

    }
    public void setData(String medicineName, String medicineTime,String BAMeal,String medicineDays)
    {
        /*m++;
        if(m>=list.size())
        {
            Intent showData = new Intent(UserEditData.this,ShowData.class);
            startActivity(showData);
            return;
        }
        String temp = list.get(m);

        stringBuilderChar = new StringBuilder();
        int i = 0;
        while ((temp.charAt(i) >= 'A' && temp.charAt(i) <= 'Z') || (temp.charAt(i) >= 'a' && temp.charAt(i) <= 'z')) {
            //textResult.setText(message.charAt(i));
            stringBuilderChar.append(temp.charAt(i));
            i++;

        }
        medicineName = stringBuilderChar.toString();
        medicine_name.setText(medicineName);

        morning.setText(String.valueOf(temp.charAt(i)));

        noon.setText(String.valueOf(temp.charAt(i + 1)));

        afternoon.setText(String.valueOf(temp.charAt(i + 2)));

        night.setText(String.valueOf(temp.charAt(i + 3)));

        //before_meal.setText(String.valueOf(temp.charAt(i + 4)));

        //after_meal.setText(String.valueOf(temp.charAt(i + 5)));
        beforeMealCheck.setChecked(false);
        afterMealCheck.setChecked(false);

        if (temp.charAt(i + 4) == '1')
        {
            beforeMealCheck.toggle();
        }
        else
        {
            afterMealCheck.toggle();
        }

        hourly.setText(String.valueOf(temp.charAt(i + 6)));

        days.setText(String.valueOf(temp.charAt(i + 7)));*/
        //medicine_name.setText(medicineName);
        //morning.setText(String.valueOf(medicineTime.charAt(0)));
        //noon.setText(String.valueOf(medicineTime.charAt(1)));
        //night.setText(String.valueOf(medicineTime.charAt(2)));
        //txt.setText(medicineTime);

    }

    public void parseData()
    {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //double animationDuration = getDurationValue() * 1000;
        //myAnim.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.005, 21.50);

        myAnim.setInterpolator(interpolator);
        m++;
        if (m>=list.size())
        {
            Intent myIntent = new Intent(UserEditData.this,MainActivity.class);
            startActivity(myIntent);
            return;
        }

        String string="", medicineName = "", medicineTime = "", BAMeal = "", medicineDays = "";

        string = list.get(m);

        stringBuilderChar = new StringBuilder();
        stringBuilderNum = new StringBuilder();
        int k =0;
        while ((string.charAt(k)>='A' && string.charAt(k)<='Z') || (string.charAt(k) >= 'a' && string.charAt(k) <= 'z'))
        {
            stringBuilderChar.append(string.charAt(k));
            k++;
        }
        //k++;
        while ((string.charAt(k)<= '9') && (string.charAt(k)>='0') )
        {
            stringBuilderNum.append(string.charAt(k));
            k++;
        }
        medicineName = stringBuilderChar.toString();
        medicineTime = stringBuilderNum.toString();

        stringBuilderChar = new StringBuilder();
        stringBuilderNum = new StringBuilder();

        while ((string.charAt(k)>='A' && string.charAt(k)<='Z') || (string.charAt(k) >= 'a' && string.charAt(k) <= 'z'))
        {
            stringBuilderChar.append(string.charAt(k));
            k++;
        }
        while (k<string.length())
        {
            stringBuilderNum.append(string.charAt(k));
            k++;
        }

        alarmHourly.setEnabled(false);
        alarmMorning.setEnabled(false);
        alarmNoon.setEnabled(false);
        alarmNight.setEnabled(false);

        BAMeal = stringBuilderChar.toString();
        medicineDays = stringBuilderNum.toString();

        medicine_name.setText(medicineName);
        morning.setText(String.valueOf(medicineTime.charAt(0)));
        noon.setText(String.valueOf(medicineTime.charAt(1)));
        night.setText(String.valueOf(medicineTime.charAt(2)));
        hourly.setText("NO");

        beforeMealCheck.setChecked(false);
        afterMealCheck.setChecked(false);

        if (BAMeal.charAt(0) == 'B' || BAMeal.charAt(0) == 'b')
        {
            beforeMealCheck.toggle();
        }

        else if(BAMeal.charAt(0) == 'A' || BAMeal.charAt(0) == 'a')
        {
            afterMealCheck.toggle();

        }
        else {

            stringBuilderNum = new StringBuilder();
            for (int n=3;n<medicineTime.length();n++)
            {
                stringBuilderNum.append(medicineTime.charAt(n));
            }
            //String hour = stringBuilderNum.toString();
            hourly.setText(stringBuilderNum.toString());

        }
        days.setText(medicineDays);

        String hourCheck = hourly.getText().toString();
        String No = "NO";
        if(!hourCheck.trim().equals(No.trim()))
        {
            //Toast.makeText(getApplicationContext(), No + " " + hourCheck ,Toast.LENGTH_LONG).show();
            alarmHourly.setEnabled(true);
            alarmHourly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String str;
                    alarmHourly.startAnimation(myAnim);
                    playSound();

                    String hour = hourly.getText().toString();

                    Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                    str = "Medicine Name: " + medicine_name.getText().toString() + '\n' +
                            "Morning Dose: " + morning.getText().toString() + '\n' +
                            "Noon Dose: " + noon.getText().toString() + '\n' +
                            "Night Dose: " + night.getText().toString();
                    myIntent.putExtra("title", str);
                    myIntent.putExtra("hour", hour);
                    startActivity(myIntent);

                }
            });
        }

        else {

            if (!morning.getText().toString().equalsIgnoreCase("0")) {
                alarmMorning.setEnabled(true);
                alarmMorning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String str;
                        alarmMorning.startAnimation(myAnim);
                        playSound();

                        String hour = hourly.getText().toString();

                        Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                        str = "Medicine Name: " + medicine_name.getText().toString() + '\n' + "Morning Dose: " + morning.getText().toString();
                        myIntent.putExtra("title", str);
                        myIntent.putExtra("hour", hour);
                        startActivity(myIntent);

                    }
                });
            }
            if (!noon.getText().toString().equalsIgnoreCase("0")) {
                alarmNoon.setEnabled(true);
                alarmNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str;
                        alarmNoon.startAnimation(myAnim);
                        playSound();
                        Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                        str = "Medicine Name: " + medicine_name.getText().toString() + '\n' + "Noon Dose: " + noon.getText().toString();
                        myIntent.putExtra("title", str);
                        startActivity(myIntent);
                    }
                });
            }

            if (!night.getText().toString().equalsIgnoreCase("0")) {
                alarmNight.setEnabled(true);
                alarmNight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str;
                        alarmNight.startAnimation(myAnim);
                        playSound();
                        Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                        str = "Medicine Name: " + medicine_name.getText().toString() + '\n' + "Night Dose: " + night.getText().toString();
                        myIntent.putExtra("title", str);
                        startActivity(myIntent);
                    }
                });
            }
        }

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                startActivity(myIntent);
            }
        });


        //AddData(medicineName,medicineTime,BAMeal,medicineDays);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_data);

        myDb = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("translate");




        //textView = (TextView) findViewById(R.id.textView);
        //textView2 = (TextView) findViewById(R.id.textView2);
        //textView3 = (TextView) findViewById(R.id.textView3);
        //textView4 = (TextView) findViewById(R.id.textView4);


        skipBtn = (FancyButton) findViewById(R.id.btn_spotifySkip);
        addData = (FancyButton) findViewById(R.id.btn_spotifyNext);
        medicine_name = (EditText) findViewById(R.id.medi_name);
        morning = (EditText) findViewById(R.id.morning);
        noon = (EditText) findViewById(R.id.noon);
        //afternoon = (EditText) findViewById(R.id.afternoon);
        night = (EditText) findViewById(R.id.night);
        //before_meal = (EditText) findViewById(R.id.before_meal);
        //after_meal = (EditText) findViewById(R.id.after_meal);
        beforeMealCheck = (CheckBox) findViewById(R.id.beforeMealCheck);
        afterMealCheck = (CheckBox) findViewById(R.id.afterMealCheck);
        hourly = (EditText) findViewById(R.id.hourly);
        days = (EditText) findViewById(R.id.days);
        alarmMorning = (ImageButton) findViewById(R.id.alarmMorning);
        alarmMorning.setEnabled(false);
        alarmNoon = (ImageButton) findViewById(R.id.alarmNoon);
        alarmNoon.setEnabled(false);
        //alarmAferNoon = (ImageButton) findViewById(R.id.alarmAfternoon);
        alarmNight = (ImageButton) findViewById(R.id.alarmNight);
        alarmNight.setEnabled(false);
        alarmHourly = (ImageButton) findViewById(R.id.alarmHourly);
        alarmHourly.setEnabled(false);
        txt = (TextView) findViewById(R.id.txt);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //double animationDuration = getDurationValue() * 1000;
        //myAnim.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.005, 21.50);

        myAnim.setInterpolator(interpolator);

        int j = 0;


        String string="", medicineName = "", medicineTime = "", BAMeal = "", medicineDays = "";


        while (message.charAt(j) != '.') {
            StringBuilder stringBuilder = new StringBuilder();
            while (message.charAt(j) != '\n') {

                if (((message.charAt(j) >= 'A' && message.charAt(j) <= 'Z') || (message.charAt(j) >= 'a' && message.charAt(j) <= 'z'))
                        || ((message.charAt(j) <= '9') && (message.charAt(j) >= '0'))) {
                    stringBuilder.append(message.charAt(j));
                }
                j++;


            }
            j++;
            string = stringBuilder.toString();
            list.add(string);
        }
        AddData(medicineName,medicineTime,BAMeal,medicineDays);

        String hourCheck = hourly.getText().toString();
        String No = "NO";

        if(!hourCheck.trim().equals(No.trim()))
        {
            //Toast.makeText(getApplicationContext(), No + " " + hourCheck ,Toast.LENGTH_LONG).show();
            alarmHourly.setEnabled(true);
            alarmHourly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String str;
                    alarmHourly.startAnimation(myAnim);
                    playSound();

                    String hour = hourly.getText().toString();

                    Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                    str = "Medicine Name: " + medicine_name.getText().toString() + '\n' +
                            "Morning Dose: " + morning.getText().toString() + '\n' +
                            "Noon Dose: " + noon.getText().toString() + '\n' +
                            "Night Dose: " + night.getText().toString();
                    myIntent.putExtra("title", str);
                    myIntent.putExtra("hour", hour);
                    startActivity(myIntent);

                }
            });
        }

        else {

            if (!morning.getText().toString().equalsIgnoreCase("0")) {
                alarmMorning.setEnabled(true);
                alarmMorning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String str;
                        alarmMorning.startAnimation(myAnim);
                        playSound();

                        String hour = hourly.getText().toString();

                        Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                        str = "Medicine Name: " + medicine_name.getText().toString() + '\n' + "Morning Dose: " + morning.getText().toString();
                        myIntent.putExtra("title", str);
                        myIntent.putExtra("hour", hour);
                        startActivity(myIntent);

                    }
                });
            }
            if (!noon.getText().toString().equalsIgnoreCase("0")) {
                alarmNoon.setEnabled(true);
                alarmNoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str;
                        alarmNoon.startAnimation(myAnim);
                        playSound();
                        Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                        str = "Medicine Name: " + medicine_name.getText().toString() + '\n' + "Noon Dose: " + noon.getText().toString();
                        myIntent.putExtra("title", str);
                        startActivity(myIntent);
                    }
                });
            }

            if (!night.getText().toString().equalsIgnoreCase("0")) {
                alarmNight.setEnabled(true);
                alarmNight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str;
                        alarmNight.startAnimation(myAnim);
                        playSound();
                        Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                        str = "Medicine Name: " + medicine_name.getText().toString() + '\n' + "Night Dose: " + night.getText().toString();
                        myIntent.putExtra("title", str);
                        startActivity(myIntent);
                    }
                });
            }
        }

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UserEditData.this, MainActivity.class);
                startActivity(myIntent);
            }
        });



    }
    public void onDestroy() {
        // Stop the sound
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }

        super.onDestroy();
    }
    void playSound() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }

        mPlayer = MediaPlayer.create(UserEditData.this, R.raw.bubble);
        mPlayer.start();
    }


    public void viewAll()
    {
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0)
                {
                    //showMessage("Error!","Nothing Found!");

                    Toast.makeText(UserEditData.this, "Database is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext())
                {
                    buffer.append("Medicine Name: "+ res.getString(0)+"\n");
                    buffer.append("Morning: "+ res.getString(1)+"\n");
                    buffer.append("Noon: "+ res.getString(2)+"\n");
                    buffer.append("After Noon: "+ res.getString(3)+"\n");
                    buffer.append("Night: "+ res.getString(4)+"\n");
                    buffer.append("Before Meal: "+ res.getString(5)+"\n");
                    buffer.append("After Meal: "+ res.getString(6)+"\n");
                    buffer.append("Hourly: "+ res.getString(7)+"\n");
                    buffer.append("Days: "+ res.getString(8)+"\n\n");

                }

                //showMessage("Data",buffer.toString());
                textView.setText(buffer.toString());


                Log.e("Success","Read data");
            }
        });
    }





    /*public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserEditData.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/

}
