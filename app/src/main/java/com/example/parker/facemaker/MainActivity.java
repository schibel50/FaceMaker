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

/*
@author Parker Schibel

@version 10 February 2016

This program runs an app that allows a user to draw a face using buttons and seekBars
to change the colors of the various body parts and change the styles of certain body
parts(hair, eyes, skin).
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, OnItemSelectedListener {

    //randomize button
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

    //Face object
    public Face mainFace = null;

    //boolean that is only used at the beginning in order to initially set
    //up the seekBars and RadioButtons
    protected boolean firstDraw = true;

    /**
     * This method creates the initial setup for the app. Initializes all of
     * the variables and sets up all of the listeners for the program
     *
     * @param savedInstanceState The last saved instance state of the program
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        /**
         External Citation
         Date: 10 February 2016
         Problem: Wasn't sure how to initialize my Face object as a surface view
         Resource: Code from our Ticket To Ride project that Jess Mann
         wrote. (https://github.com/mannj17/TicketToRide/blob/master/app/src/main
         /java/com/example/jess/myapplication/MainActivity.java)
         Solution: I looked at her code to initialize my surfaceView since she
         had a similar way of setting up our board for our surfaceView
         */
        mainFace = (Face) findViewById(R.id.FaceSurface);

        //initialize the randomize button
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

        //set up the array of strings to be used in the spinners
        String[] hairOptions;
        String[] eyesOptions;
        String[] noseOptions;

        hairOptions = getResources().getStringArray(R.array.hair_array);
        eyesOptions = getResources().getStringArray(R.array.eyes_array);
        noseOptions = getResources().getStringArray(R.array.nose_array);

        /**
         External Citation
         Date: 10 February 2016
         Problem: I could not figure out how to initialize and set up
         listeners for spinners. I could not find anything clear
         in API or on other sites that assist with writing code
         Resource:
         Code from Lab 4 of Object Oriented Design with Dr. Vegdahl
         Solution: There was a spinner set up with a listener in this lab,
         so I used that code as a basis for how I set up all of my different spinners
         */
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
        noseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noseSpin.setAdapter(noseAdapter);
        noseSpin.setOnItemSelectedListener(this);

        //set the initial values of the seekBars based on the randomized colors
        setValues();
        //informs the setValues method that the first draw has been done
        firstDraw = false;
    }

    /**
     * This method updates the seekBars to the current rgb values that are reflected
     * based on which radioButton has been clicked. It also updates the spinners to
     * the styles that are currently displayed on the face. It also sets up the initial
     * setup of the app by setting the seekBars to the initial hair color and checking
     * the Hair Color radioButton.
     */
    private void setValues() {
        /**
         External Citation
         Date: 10 February 2016
         Problem: Wasn't sure how to pull different rgb values from each of
         the color values that were saved as ints
         Resource:
         http://developer.android.com/reference/android/graphics/Color.html
         Solution: The red green blue methods of Color explained how they pull
         those rgb values from ints using bitwise operators
         */

        /*
        This if statement specifically sets up the initial state where the
        hair color button is the initial one that is pressed and the progress bars
        are updated to initially reflect the original random hair color
         */

        if(firstDraw){
            hairSelect.setChecked(true);
            blueBar.setProgress((mainFace.hairColor & 0xFF));
            greenBar.setProgress(((mainFace.hairColor >> 8) & 0xFF));
            redBar.setProgress(((mainFace.hairColor >> 16) & 0xFF));
        }
        /*
        Changes the progress of the seekBars depending on which radioButton has
        been selected. For example, if skin Color is chosen, it will update the
        progress of the bars to reflect the value of the color of that element
         */
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
        //sets the spinners to what has been chosen for each style
        hairSpin.setSelection(mainFace.hairStyleIndex);
        eyesSpin.setSelection(mainFace.eyeStyleIndex);
        noseSpin.setSelection(mainFace.noseStyleIndex);
    }

    /**
     * This method serves two purposes:
     * 1)Update the radioButtons based on which ones are already checked
     *  and which ones were just recently checked. (also update the values
     *  of the various elements on the board.
     * 2)If the randomize button is pressed, call the randomize method
     *  for the Face object
     *
     * @param v The current view that is listening
     */
    @Override
    public void onClick(View v) {
        /*
        If one of the radioButtons is clicked, it will automatically
        un-click the other buttons and update the seekBars to reflect
        the color of the clicked button
         */
        if (v.getId() == R.id.HairButton) {
            hairSelect.setChecked(true);
            eyesSelect.setChecked(false);
            skinSelect.setChecked(false);
            setValues();
        }
        else if (v.getId() == R.id.EyesButton) {
            hairSelect.setChecked(false);
            eyesSelect.setChecked(true);
            skinSelect.setChecked(false);
            setValues();
        }
        else if (v.getId() == R.id.SkinButton) {
            hairSelect.setChecked(false);
            eyesSelect.setChecked(false);
            skinSelect.setChecked(true);
            setValues();
        }
        //if the randomize button is pressed, randomize all the values
        //and set the new values on the elements of the UI
        else if(v.getId() == R.id.RandomButton){
            mainFace.randomize();
            setValues();
        }
    }

    /**
     * As a seekBar is changed, this method updates the values of the
     * textView that shows the progress and of the color values
     * for the Face object
     *
     * @param seekBar   the seekBar that is being changed
     * @param progress  the current progress of the seekBar. i.e.
     *                  the position that it is at at that moment
     * @param fromUser  boolean that says if the change of the seekBar
     *                  was from the user
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //update the progress value text underneath each seekBar
        if (seekBar == redBar) {
            redValue.setText("Red: " + progress);
        }
        else if (seekBar == blueBar) {
            blueValue.setText("Blue: " + progress);
        }
        else if (seekBar == greenBar) {
            greenValue.setText("Green: " + progress);
        }

        //Constantly update the color values of each element as a seekBar is moved by
        //the user
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
        //redraw with the new updated values
        mainFace.drawAgain();
    }


    /**
     * Listens for any changes to the spinners and adjusts them accordingly
     *
     * CAVEAT: This method has yet to complete successfully.
     *
     * @param parent the AdapterView of whichever Spinner was clicked
     * @param view   the current view that the listener is on
     * @param position The current chosen value on the spinner
     * @param id     the id of the spinner that was clicked on
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /**
         External Citation
         Date: 10 February 2016
         Problem: Could not get the background color of my button
         to change
         Resource: Wasn't sure how to get the different values from each spinner
         //http://stackoverflow.com/questions/10777977/android-multiple-spinners
         Solution: Showed me the use of the getSelectedItemPosition method
         */

        //spinner placeholder variable
        Spinner spinner = (Spinner) parent;

        //if a spinner is changed, get the new selected value and update the indices
        if(spinner.getId() == R.id.HairSpinner){
            mainFace.hairStyleIndex = parent.getSelectedItemPosition();
        }
        else if(spinner.getId() == R.id.EyesSpinner){
            mainFace.eyeStyleIndex = parent.getSelectedItemPosition();
        }
        else if(spinner.getId() == R.id.NoseSpinner){
            mainFace.noseStyleIndex = parent.getSelectedItemPosition();
        }
        //redraw with the new chosen style
        mainFace.drawAgain();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //not used
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //not used
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // not used
    }
}
