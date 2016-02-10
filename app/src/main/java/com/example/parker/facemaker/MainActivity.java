package com.example.parker.facemaker;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.parker.facemaker.R.id.FaceSurface;
import static com.example.parker.facemaker.R.id.HairSpinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, OnItemSelectedListener {

    protected Button randomButton;

    //RadioButton variables
    protected RadioButton hairSelect;
    protected RadioButton eyesSelect;
    protected RadioButton skinSelect;

    //SeekBar variables
    protected SeekBar redBar;
    protected SeekBar greenBar;
    protected SeekBar blueBar;

    //TextView variables
    protected TextView redValue;
    protected TextView greenValue;
    protected TextView blueValue;

    //Spinner variables
    protected Spinner hairSpin;
    protected Spinner eyesSpin;
    protected Spinner noseSpin;

    public Face mainFace = null;

    protected boolean firstDraw = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mainFace = (Face) findViewById(R.id.FaceSurface);

        randomButton = (Button) findViewById(R.id.RandomButton);
        randomButton.setOnClickListener(this);

        //Initialize all of the RadioButtons
        hairSelect = (RadioButton) findViewById(R.id.HairButton);
        hairSelect.setOnClickListener(this);

        eyesSelect = (RadioButton) findViewById(R.id.EyesButton);
        eyesSelect.setOnClickListener(this);

        skinSelect = (RadioButton) findViewById(R.id.SkinButton);
        skinSelect.setOnClickListener(this);

        //Initialize all of the SeekBars
        redBar = (SeekBar) findViewById(R.id.RedSeek);
        redBar.setOnSeekBarChangeListener(this);

        greenBar = (SeekBar) findViewById(R.id.GreenSeek);
        greenBar.setOnSeekBarChangeListener(this);

        blueBar = (SeekBar) findViewById(R.id.BlueSeek);
        blueBar.setOnSeekBarChangeListener(this);

        //Initialize rgb value TextViews
        redValue = (TextView) findViewById(R.id.RedText);
        greenValue = (TextView) findViewById(R.id.GreenText);
        blueValue = (TextView) findViewById(R.id.BlueText);

        //Initialize Spinners
        hairSpin = (Spinner) findViewById(R.id.HairSpinner);
        eyesSpin = (Spinner) findViewById(R.id.EyesSpinner);
        noseSpin = (Spinner) findViewById(R.id.NoseSpinner);

        String[] hairOptions;
        String[] eyesOptions;
        String[] noseOptions;

        hairOptions = getResources().getStringArray(R.array.hair_array);
        eyesOptions = getResources().getStringArray(R.array.eyes_array);
        noseOptions = getResources().getStringArray(R.array.nose_array);

        //Used code from Lab 3
        ArrayAdapter hairAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, hairOptions);
        hairAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hairSpin.setAdapter(hairAdapter);
        hairSpin.setOnItemSelectedListener(this);

        ArrayAdapter eyesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, eyesOptions);
        eyesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eyesSpin.setAdapter(eyesAdapter);
        eyesSpin.setOnItemSelectedListener(this);

        ArrayAdapter noseAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, noseOptions);
        eyesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noseSpin.setAdapter(noseAdapter);
        noseSpin.setOnItemSelectedListener(this);

        setValues();
        firstDraw = false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.HairButton) {
            hairSelect.setChecked(true);
            eyesSelect.setChecked(false);
            skinSelect.setChecked(false);
            setValues();
        } else if (v.getId() == R.id.EyesButton) {
            hairSelect.setChecked(false);
            eyesSelect.setChecked(true);
            skinSelect.setChecked(false);
            setValues();
        } else if (v.getId() == R.id.SkinButton) {
            hairSelect.setChecked(false);
            eyesSelect.setChecked(false);
            skinSelect.setChecked(true);
            setValues();
        } else if(v.getId() == R.id.RandomButton){
            mainFace.randomize();
            setValues();
        }
    }

    private void setValues() {
        if(firstDraw){
            hairSelect.setChecked(true);
            blueBar.setProgress((mainFace.hairColor & 0xFF));
            greenBar.setProgress(((mainFace.hairColor >> 8) & 0xFF));
            redBar.setProgress(((mainFace.hairColor >> 16) & 0xFF));
        }
        else if(skinSelect.isChecked()){
            blueBar.setProgress((mainFace.skinColor & 0xFF));
            greenBar.setProgress(((mainFace.skinColor >> 8) & 0xFF));
            redBar.setProgress(((mainFace.skinColor >> 16) & 0xFF));
        }
        else if(hairSelect.isChecked()){
            blueBar.setProgress((mainFace.hairColor & 0xFF));
            greenBar.setProgress(((mainFace.hairColor >> 8) & 0xFF));
            redBar.setProgress(((mainFace.hairColor >> 16) & 0xFF));
        }
        else if(eyesSelect.isChecked()){
            blueBar.setProgress((mainFace.eyeColor & 0xFF));
            greenBar.setProgress(((mainFace.eyeColor >> 8) & 0xFF));
            redBar.setProgress(((mainFace.eyeColor >> 16) & 0xFF));
        }
        hairSpin.setSelection(mainFace.hairStyleIndex);
        eyesSpin.setSelection(mainFace.eyeStyleIndex);
        noseSpin.setSelection(mainFace.noseStyleIndex);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == redBar) {
            redValue.setText("Red: " + progress);
        } else if (seekBar == blueBar) {
            blueValue.setText("Blue: " + progress);
        } else if (seekBar == greenBar) {
            greenValue.setText("Green: " + progress);
        }
        if(skinSelect.isChecked() && fromUser) {
            mainFace.skinColor = Color.argb(0xFF, redBar.getProgress(), greenBar.getProgress(),
                    blueBar.getProgress());
        }
        else if(eyesSelect.isChecked() && fromUser) {
            mainFace.eyeColor = Color.argb(0xFF, redBar.getProgress(), greenBar.getProgress(),
                    blueBar.getProgress());
        }
        else if(hairSelect.isChecked() && fromUser) {
            mainFace.hairColor = Color.argb(0xFF, redBar.getProgress(), greenBar.getProgress(),
                    blueBar.getProgress());
        }
        mainFace.drawAgain();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //http://stackoverflow.com/questions/10777977/android-multiple-spinners
        //int currentId = parent.getId();
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.HairSpinner){
            mainFace.hairStyleIndex = parent.getSelectedItemPosition();
        }
        else if(spinner.getId() == R.id.EyesSpinner){
            mainFace.eyeStyleIndex = parent.getSelectedItemPosition();
        }
        else if(spinner.getId() == R.id.NoseSpinner){
            mainFace.noseStyleIndex = parent.getSelectedItemPosition();
        }
        mainFace.drawAgain();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
