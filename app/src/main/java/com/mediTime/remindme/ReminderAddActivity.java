


package com.blanyal.remindme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.vision.text.Text;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import static android.R.id.content;


public class ReminderAddActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    private Toolbar mToolbar;
    private EditText mTitleText;
    private CheckBox morningCheck,noonCheck,nightCheck,beforeMealCheck,afterMealCheck;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText,mediName;
    private FloatingActionButton mFAB1;
    private FloatingActionButton mFAB2;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mActive;
    private String tempTitle;
    String title,hour,send;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> medicineName = new ArrayList<String>();
    ArrayList<String> medicineDose = new ArrayList<String>();
    ArrayList<String> BAmeal = new ArrayList<String>();
    ArrayList<Integer> getHour  = new ArrayList<Integer>();

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    public void cutData()
    {
        int j=0;
        while (title.charAt(j)!='\n')
        {
            j++;
        }
        j++;
        StringBuilder stringBuilder = new StringBuilder();

        while (title.charAt(j)!='.')
        {

            stringBuilder.append(title.charAt(j));
            j++;
        }
        stringBuilder.append('.');
        send = stringBuilder.toString();


    }
    public void parseData(String message)
    {
        int j = 0;


        String string="";


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

    }

    public void analyzeData()
    {
        String medicineInfo = "";
        for(int i = 0; i < list.size(); i++)
        {
            medicineInfo = list.get(i);
            StringBuilder stringBuilderHour = new StringBuilder();
            StringBuilder stringBuildermediName = new StringBuilder();
            StringBuilder stringBuilderDose = new StringBuilder();
            for(int j = 0; j < medicineInfo.length(); j++)
            {
                if((medicineInfo.charAt(j)>='A' && medicineInfo.charAt(j)<='Z') || (medicineInfo.charAt(j) >= 'a' && medicineInfo.charAt(j) <= 'z'))
                {
                    stringBuildermediName.append(medicineInfo.charAt(j));
                }
                else
                {
                    break;
                }
            }

            medicineName.add(stringBuildermediName.toString());


            if(medicineInfo.toLowerCase().contains("hourly")) {
                int pos = medicineInfo.toLowerCase().indexOf("hourly");
                stringBuilderHour.append(medicineInfo.charAt(pos-1));
                if(medicineInfo.charAt(pos-2)<= '9' && medicineInfo.charAt(pos-2)>= '0')
                {
                        stringBuilderHour.append(medicineInfo.charAt(pos-2));
                }


                String hour = stringBuilderHour.toString();
                int value = Integer.parseInt(hour);
                getHour.add(value);
                BAmeal.add("");
                medicineDose.add("");
            }
            else {
                int pos;

                for(int j=0;j<medicineInfo.length();j++)
                {
                    if(stringBuilderDose.length()>=3)
                    {
                        break;
                    }
                    else if (medicineInfo.charAt(j)<= '9' && medicineInfo.charAt(j)>= '0')
                    {
                        stringBuilderDose.append(medicineInfo.charAt(j));
                    }
                }

                if(stringBuilderDose.toString().charAt(0)>='1'
                        && stringBuilderDose.toString().charAt(1)>='1'
                        && stringBuilderDose.toString().charAt(2)>= '1' )
                {
                    pos = 8;
                }
                else if(stringBuilderDose.toString().charAt(0)>='1'
                        && stringBuilderDose.toString().charAt(1)=='0'
                        && stringBuilderDose.toString().charAt(2)>= '1' )
                {
                    pos = 12;
                }

                else
                {
                    pos = 24;
                }


                getHour.add(pos);


                medicineDose.add(stringBuilderDose.toString());
                if(medicineInfo.toLowerCase().contains("after"))
                {
                    BAmeal.add("after");
                }
                else
                {
                    BAmeal.add("before");
                }


            }



        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            title = bundle.getString("translate");
            parseData(title);
            analyzeData();
            cutData();
        }
        //hour = bundle.getString("hour");

        tempTitle = "";

        // Initialize Views
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleText = (EditText) findViewById(R.id.reminder_title);
        mDateText = (TextView) findViewById(R.id.set_date);
        mTimeText = (TextView) findViewById(R.id.set_time);
        mRepeatText = (TextView) findViewById(R.id.set_repeat);
        mRepeatNoText = (TextView) findViewById(R.id.set_repeat_no);
        mRepeatTypeText = (TextView) findViewById(R.id.set_repeat_type);
        mediName = (TextView) findViewById(R.id.medi_name);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        morningCheck = (CheckBox) findViewById(R.id.checkMoring);
        noonCheck = (CheckBox) findViewById(R.id.noonCheck);
        nightCheck = (CheckBox) findViewById(R.id.nightCheck);
        beforeMealCheck = (CheckBox) findViewById(R.id.beforeMealCheck);
        afterMealCheck = (CheckBox) findViewById(R.id.afterMealCheck);



        String dose = medicineDose.get(0);
        int doseCnt = 0;
        if (dose=="")
        {
            if(Integer.toString(getHour.get(0)) == "8")
            {
                morningCheck.toggle();
                noonCheck.toggle();
                nightCheck.toggle();
            }
            else if(Integer.toString(getHour.get(0)) == "12")
            {
                morningCheck.toggle();
                nightCheck.toggle();
            }
            else
            {
                nightCheck.toggle();
            }
        }
        else {
            if (dose.charAt(0) >= '1') {
                morningCheck.toggle();
                tempTitle = tempTitle + "Morning Dose: " + dose.charAt(0) + '\n';
                doseCnt++;
            }
            if (dose.charAt(1) >= '1') {
                noonCheck.toggle();
                tempTitle = tempTitle + "Noon Dose: " + dose.charAt(1) + '\n';
                doseCnt++;
            }
            if (dose.charAt(2) >= '1') {
                nightCheck.toggle();
                tempTitle = tempTitle + "Night Dose: " + dose.charAt(2) + '\n';
                doseCnt++;
            }
            if (BAmeal.get(0).equalsIgnoreCase("after")) {
                afterMealCheck.toggle();
            }
            if (BAmeal.get(0).equalsIgnoreCase("before")) {
                beforeMealCheck.toggle();
            }
        }

        //mTitleText.setText(title);

        // Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_activity_add_reminder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Initialize default values
        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(getHour.get(0));
        mRepeatType = "Hour";
        mediName.setText(medicineName.get(0));
        tempTitle = medicineName.get(0) + '\n' + tempTitle;


        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        // Setup Reminder Title EditText
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Setup TextViews using reminder values
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mRepeatNoText.setText(mRepeatNo);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");

        // To save state on device rotation
        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeatText.setText(saveRepeat);
            mRepeat = saveRepeat;

            String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            mRepeatNoText.setText(savedRepeatNo);
            mRepeatNo = savedRepeatNo;

            String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatTypeText.setText(savedRepeatType);
            mRepeatType = savedRepeatType;

            mActive = savedInstanceState.getString(KEY_ACTIVE);
        }

        // Setup up active buttons
        if (mActive.equals("false")) {
            mFAB1.setVisibility(View.VISIBLE);
            mFAB2.setVisibility(View.GONE);

        } else if (mActive.equals("true")) {
            mFAB1.setVisibility(View.GONE);
            mFAB2.setVisibility(View.VISIBLE);
        }

        mTitleText.setText(tempTitle);
    }

    // To save state on device rotation
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE, mTitleText.getText());
        outState.putCharSequence(KEY_TIME, mTimeText.getText());
        outState.putCharSequence(KEY_DATE, mDateText.getText());
        outState.putCharSequence(KEY_REPEAT, mRepeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO, mRepeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatTypeText.getText());
        outState.putCharSequence(KEY_ACTIVE, mActive);
    }

    public void reloadPage()
    {
        //Log.e(send, " Get STRING");

        if (send.length() < 5)
        {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return;
        }
        Intent intent = new Intent(this,ReminderAddActivity.class);
        intent.putExtra("translate", send);
        startActivity(intent);
        this.finish();
    }

    // On clicking Time picker
    public void setTime(View v){
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    // On clicking Date picker
    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    // Obtain time from time picker
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = hourOfDay + ":" + "0" + minute;
        } else {
            mTime = hourOfDay + ":" + minute;
        }
        mTimeText.setText(mTime);
    }

    // Obtain date from date picker
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear ++;
        mDay = dayOfMonth;
        mMonth = monthOfYear;
        mYear = year;
        mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
        mDateText.setText(mDate);
    }

    // On clicking the active button
    public void selectFab1(View v) {
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB1.setVisibility(View.GONE);
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        mFAB2.setVisibility(View.VISIBLE);
        mActive = "true";
    }

    // On clicking the inactive button
    public void selectFab2(View v) {
        mFAB2 = (FloatingActionButton) findViewById(R.id.starred2);
        mFAB2.setVisibility(View.GONE);
        mFAB1 = (FloatingActionButton) findViewById(R.id.starred1);
        mFAB1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    // On clicking the repeat switch
    public void onSwitchRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            mRepeat = "true";
            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
        } else {
            mRepeat = "false";
            mRepeatText.setText(R.string.repeat_off);
        }
    }

    // On clicking repeat type button
    public void selectRepeatType(View v){
        final String[] items = new String[5];

        items[0] = "Minute";
        items[1] = "Hour";
        items[2] = "Day";
        items[3] = "Week";
        items[4] = "Month";

        // Create List Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                mRepeatType = items[item];
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //On clicking Medicine Name

    public void setMedicineName(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Medicine name");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {

                        }
                        else {
                            mediName.setText(input.getText().toString().trim());

                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    // On clicking repeat interval button
    public void setRepeatNo(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");

        // Create EditText box to input repeat number
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (input.getText().toString().length() == 0) {
                            mRepeatNo = Integer.toString(1);
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        }
                        else {
                            mRepeatNo = input.getText().toString().trim();
                            mRepeatNoText.setText(mRepeatNo);
                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                        }
                    }
                });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // do nothing
            }
        });
        alert.show();
    }

    // On clicking the save button
    public void saveReminder(){
        ReminderDatabase rb = new ReminderDatabase(this);

        // Creating Reminder
        int ID = rb.addReminder(new Reminder(mTitle, mDate, mTime, mRepeat, mRepeatNo, mRepeatType, mActive));

        // Set up calender for creating the notification
        mCalendar.set(Calendar.MONTH, --mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);

        // Check repeat type
        if (mRepeatType.equals("Minute")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMinute;
        } else if (mRepeatType.equals("Hour")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milHour;
        } else if (mRepeatType.equals("Day")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milDay;
        } else if (mRepeatType.equals("Week")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milWeek;
        } else if (mRepeatType.equals("Month")) {
            mRepeatTime = Integer.parseInt(mRepeatNo) * milMonth;
        }

        // Create a new notification
        if (mActive.equals("true")) {
            if (mRepeat.equals("true")) {
                new AlarmReceiver().setRepeatAlarm(getApplicationContext(), mCalendar, ID, mRepeatTime);
            } else if (mRepeat.equals("false")) {
                new AlarmReceiver().setAlarm(getApplicationContext(), mCalendar, ID);
            }
        }

        // Create toast to confirm new reminder
        Toast.makeText(getApplicationContext(), "Saved",
                Toast.LENGTH_SHORT).show();

        reloadPage();

        //onBackPressed();
    }

    // On pressing the back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Creating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
        return true;
    }

    // On clicking menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // On clicking the back arrow
            // Discard any changes
            case android.R.id.home:
                onBackPressed();
                return true;

            // On clicking save reminder button
            // Update reminder
            case R.id.save_reminder:
                //mTitle = title;
                mTitleText.setText(mTitle);

                if (mTitleText.getText().toString().length() == 0)
                    mTitleText.setError("Reminder Title cannot be blank!");

                else {
                    saveReminder();
                }
                return true;

            // On clicking discard reminder button
            // Discard any changes
            case R.id.discard_reminder:
                Toast.makeText(getApplicationContext(), "Discarded",
                        Toast.LENGTH_SHORT).show();

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}