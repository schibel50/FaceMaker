package com.example.parker.facemaker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.Random;

/*
@author Parker Schibel

@version 10 February 2016

This Class creates a subclass of a SurfaceView that serves as the basis for drawing
a face in the MainActivity
 */
public class Face extends SurfaceView{

    //random number generator used throughout the class
    protected Random randNum = new Random();

    //initialize colors
    protected int skinColor;
    protected int eyeColor;
    protected int hairColor;

    //initialize the arrays of styles
    protected Path[] hairStyles;
    protected Path[] eyesStyles;
    protected Path[] noseStyles;

    //a path that is used to start every eyeStyle
    protected Path eye;

    //placeholder for the index of each style chosen
    protected int hairStyleIndex;
    protected int eyeStyleIndex;
    protected int noseStyleIndex;

    //arrays that are used to randomize rgb values
    protected int[] redValues = new int[3];
    protected int[] greenValues = new int[3];
    protected int[] blueValues = new int[3];

    //auto-generated
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    /**
     * Constructor method for the Face object. Based on the SurfaceView constructor
     *
     * @param context necessary to run the SurfaceView
     * @param attrs   necessary to run the SurfaceView
     */
    public Face(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotCacheDrawing(false);
        setWillNotDraw(false);

        /**
         External Citation
         Date: 10 February 2016
         Problem: Didn't know how to use or initialize a path
         Resource:
         http://developer.android.com/reference/android/graphics/Path.html
         Solution: Looked through the API to determine how set it up and create paths
         */
        //paths for all the different options to draw.
        hairStyles = new Path[3];
        eyesStyles = new Path[3];
        noseStyles = new Path[4];

        //path for the long hair style option
        hairStyles[0] = new Path();
        hairStyles[0].addRect(75.0f, 700.0f, 825.0f, 1200.0f, Path.Direction.CW);
        hairStyles[0].addArc(75.0f, 315.0f, 825.0f, 1075.0f, 180.0f, 180.0f);
        hairStyles[0].close();

        //path for the flat top hair style option
        hairStyles[1] = new Path();
        hairStyles[1].addRect(100.0f, 200.0f, 800.0f, 700.0f, Path.Direction.CW);
        hairStyles[1].close();

        //path for the mohawk hair style option
        hairStyles[2] = new Path();
        hairStyles[2].addRect(350.0f, 250.0f, 550.0f, 490.0f, Path.Direction.CW);
        hairStyles[2].close();

        //path for the base of every eye style
        eye = new Path();
        eye.addCircle(275.0f, 600.0f, 75.0f, Path.Direction.CW);
        eye.addCircle(625.0f, 600.0f, 75.0f, Path.Direction.CW);
        eye.close();

        //path for the straight eyebrows of the normal eyes style
        eyesStyles[0] = new Path();
        eyesStyles[0].addRect(200.0f, 500.0f, 350.0f, 550.0f, Path.Direction.CW);
        eyesStyles[0].addRect(550.0f, 500.0f, 700.0f, 550.0f, Path.Direction.CW);
        eyesStyles[0].close();

        //path for the first angry eyebrow of the angry eyes style
        eyesStyles[1] = new Path();
        eyesStyles[1].moveTo(200.0f, 460.0f);
        eyesStyles[1].lineTo(350.0f, 520.0f);
        eyesStyles[1].lineTo(350.0f, 580.0f);
        eyesStyles[1].lineTo(200.0f, 540.0f);
        eyesStyles[1].lineTo(200.0f, 460.0f);
        eyesStyles[1].setLastPoint(200.0f, 460.0f);

        //path for the second angry eyebrow of the angry eyes style
        eyesStyles[2] = new Path();
        eyesStyles[2].moveTo(700.0f, 460.0f);
        eyesStyles[2].lineTo(550.0f, 520.0f);
        eyesStyles[2].lineTo(550.0f, 580.0f);
        eyesStyles[2].lineTo(700.0f, 540.0f);
        eyesStyles[2].lineTo(700.0f, 460.0f);
        eyesStyles[2].setLastPoint(700.0f, 460.0f);

        //path for the wide nose style
        noseStyles[0] = new Path();
        noseStyles[0].moveTo(420.0f, 600.0f);
        noseStyles[0].lineTo(480.0f, 600.0f);
        noseStyles[0].lineTo(580.0f, 720.0f);
        noseStyles[0].lineTo(320.0f, 720.0f);
        noseStyles[0].lineTo(420.0f, 600.0f);
        noseStyles[0].setLastPoint(420.0f, 600.0f);
        noseStyles[0].close();

        //path for the thin nose style
        noseStyles[1] = new Path();
        noseStyles[1].moveTo(435.0f, 600.0f);
        noseStyles[1].lineTo(465.0f, 600.0f);
        noseStyles[1].lineTo(520.0f, 720.0f);
        noseStyles[1].lineTo(380.0f, 720.0f);
        noseStyles[1].lineTo(435.0f, 600.0f);
        noseStyles[1].setLastPoint(435.0f, 600.0f);
        noseStyles[1].close();

        //path for the first nostril of the snake nose style
        noseStyles[2] = new Path();
        noseStyles[2].lineTo(420.0f, 680.0f);
        noseStyles[2].lineTo(435.0f, 720.0f);
        noseStyles[2].lineTo(420.0f, 760.0f);
        noseStyles[2].lineTo(405.0f, 720.0f);
        noseStyles[2].lineTo(420.0f, 680.0f);
        noseStyles[2].setLastPoint(420.0f, 680.0f);

        //path for the second nostril of the snake nose style
        noseStyles[3] = new Path();
        noseStyles[3].lineTo(480.0f, 680.0f);
        noseStyles[3].lineTo(495.0f, 720.0f);
        noseStyles[3].lineTo(480.0f, 760.0f);
        noseStyles[3].lineTo(465.0f, 720.0f);
        noseStyles[3].lineTo(480.0f, 680.0f);
        noseStyles[3].setLastPoint(480.0f, 680.0f);

        //randomize the first face that shows up
        randomize();
    }

    /**
     * This method randomizes all the different elements of the face
     * including the indices for the hairStyle, noseStyle, and eyeStyle.
     * It also randomizes the rgb values of the skin, eye, and hair colors
     */
    public void randomize(){
        int i;//counter

        //randomizes the styles
        hairStyleIndex = randNum.nextInt(3);
        noseStyleIndex = randNum.nextInt(3);
        eyeStyleIndex = randNum.nextInt(3);

        //randomize the colors
        for(i=0; i<3; i++){
            redValues[i] = randNum.nextInt(255);
            greenValues[i] = randNum.nextInt(255);
            blueValues[i] = randNum.nextInt(255);
        }

        //take the random rgb values arrays and apply them to each different color
        hairColor = Color.argb(0xFF, redValues[0], greenValues[0], blueValues[0]);
        skinColor = Color.argb(0xFF, redValues[1], greenValues[1], blueValues[1]);
        eyeColor = Color.argb(0xFF, redValues[2], greenValues[2], blueValues[2]);

        //redraw on the surfaceView
        invalidate();
    }

    /**
     * This method is responsibly for actually drawing onto the surfaceView
     *
     * @param canvas    the necessary Canvas for the program to draw on
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //sets the color of the mouth of the face
        Paint mouth = new Paint();
        mouth.setColor(0xFF000000);
        mouth.setStyle(Paint.Style.FILL);

        //draw all the different features
        drawHair(canvas, hairStyleIndex);
        drawEyes(canvas, eyeStyleIndex);
        drawNose(canvas, noseStyleIndex);
        canvas.drawRect(200.0f, 850.0f, 700.0f, 870.0f, mouth);
    }

    /**
     * Draws the nose of the face based on the nose spinner
     *
     * @param canvas necessary Canvas for drawing onto the surfaceView
     * @param noseStyleIndex  the index that corresponds to what has been
     *                        chosen in the nose style Spinner
     */
    private void drawNose(Canvas canvas, int noseStyleIndex) {
        //sets up the color for the border of the nose
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFF000000);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(25.0f);

        //sets up the color for the nostrils
        Paint nostril = new Paint();
        nostril.setColor(0xFF000000);
        nostril.setStyle(Paint.Style.FILL);

        //this if-statement draws a wide nose if it is selected in the spinner
        //including nostrils
        if(noseStyleIndex == 0){
            canvas.drawPath(noseStyles[noseStyleIndex], borderPaint);
            canvas.drawCircle(515.0f, 700.0f, 20.0f, nostril);
            canvas.drawCircle(385.0f, 700.0f, 20.0f, nostril);
        }
        //draws the thin nose if it is selected in the spinner including nostrils
        else if(noseStyleIndex == 1){
            canvas.drawPath(noseStyles[noseStyleIndex], borderPaint);
            canvas.drawCircle(485.0f, 700.0f, 20.0f, nostril);
            canvas.drawCircle(415.0f, 700.0f, 20.0f, nostril);
        }
        //draws the nostrils of the snake nose if it is selected in the spinner
        else if(noseStyleIndex == 2){
            canvas.drawPath(noseStyles[noseStyleIndex], nostril);
            canvas.drawPath(noseStyles[noseStyleIndex+1], nostril);
        }
    }

    /**
     * Draws the eyes of the face based on the eye spinner and eyeColor
     *
     * @param canvas necessary Canvas for drawing onto the surfaceView
     * @param eyeStyleIndex  the index that corresponds to what has been
     *                        chosen in the eye style Spinner
     */
    private void drawEyes(Canvas canvas, int eyeStyleIndex) {
        //set up the border color for all objects drawn
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFF000000);
        borderPaint.setStyle(Paint.Style.STROKE);

        /*
         * sets up the various colors used for the different parts of the eyes including
         * the whites of the eyes, the pupil, the color of the eyes, and the color of the
         * eyebrows
         */
        Paint eyeWhites = new Paint();
        eyeWhites.setColor(0xFFFFFFFF);
        eyeWhites.setStyle(Paint.Style.FILL);

        Paint pupil = new Paint();
        pupil.setColor(0xFF000000);
        pupil.setStyle(Paint.Style.FILL);

        Paint eyePaint = new Paint();
        eyePaint.setColor(eyeColor);
        eyePaint.setStyle(Paint.Style.FILL);

        Paint eyeBrowPaint = new Paint();
        eyeBrowPaint.setColor(0xFF333300);
        eyeBrowPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(eye, eyeWhites);

        //if Normal or Angry style is chosen, start by drawing the eye, which is
        //the same for both
        if(eyeStyleIndex == 0 || eyeStyleIndex == 1) {
            canvas.drawCircle(275.0f, 600.0f, 50.0f, eyePaint);
            canvas.drawCircle(625.0f, 600.0f, 50.0f, eyePaint);

            canvas.drawCircle(275.0f, 600.0f, 15.0f, pupil);
            canvas.drawCircle(625.0f, 600.0f, 15.0f, pupil);

            //draw the eyebrows of whichever eye style was selected
            //if angry was selected, draw the second angry eyebrow
            canvas.drawPath(eyesStyles[eyeStyleIndex], eyeBrowPaint);

            if(eyeStyleIndex == 1) {
                canvas.drawPath(eyesStyles[eyeStyleIndex + 1], eyeBrowPaint);
            }
        }
        //if Alien style was chosen, don't draw eyebrows but make a very large
        //eyeColor circle
        if(eyeStyleIndex == 2){
            canvas.drawCircle(275.0f, 600.0f, 70.0f, eyePaint);
            canvas.drawCircle(625.0f, 600.0f, 70.0f, eyePaint);
        }
    }

    //auto-generated with drawArc
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    /**
     * Draws the hair of the face and indirectly also draws the head/skin
     *
     * @param canvas necessary Canvas for drawing onto the surfaceView
     * @param hairStyleIndex  the index that corresponds to what has been
     *                        chosen in the hair Spinner
     */
    public void drawHair(Canvas canvas, int hairStyleIndex){
        /*
         *Set up the colors for the border of the objects, the color of the hair and the
         * color of the skin
         */
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFF000000);
        borderPaint.setStyle(Paint.Style.STROKE);

        Paint hairPaint = new Paint();
        hairPaint.setColor(hairColor);
        hairPaint.setStyle(Paint.Style.FILL);

        Paint skinPaint = new Paint();
        skinPaint.setColor(skinColor);
        skinPaint.setStyle(Paint.Style.FILL);

        //if Long or Flat Top Hair was selected, enter this if statement to draw the hair
        //before drawing the head
        if(hairStyleIndex == 0 || hairStyleIndex ==1){
            canvas.drawPath(hairStyles[hairStyleIndex], hairPaint);
            canvas.drawPath(hairStyles[hairStyleIndex], borderPaint);

            canvas.drawCircle(450.0f, 700.0f, 350.0f, skinPaint);
            canvas.drawCircle(450.0f, 700.0f, 350.0f, borderPaint);

            //if Long hair was chosen, draw the bangs of the hair.
            if(hairStyleIndex == 0){
                canvas.drawArc(140.0f, 350.0f, 760.0f, 700.0f, 180.0f, 180.0f, true, hairPaint);
            }
        }
        //if Mohawk style was chosen, draw the head first and then draw the mohawk
        else {
            canvas.drawCircle(450.0f, 700.0f, 350.0f, skinPaint);
            canvas.drawCircle(450.0f, 700.0f, 350.0f, borderPaint);

            canvas.drawPath(hairStyles[hairStyleIndex], hairPaint);
            canvas.drawPath(hairStyles[hairStyleIndex], borderPaint);
        }
    }

    /**
     * Invalidates what has been drawn on the surfaceView so that it will
     * redraw it
     *
     * CAVEAT: There is probably a simpler way to implement the function of
     * this method without actually creating a new method
     */
    public void drawAgain(){
        invalidate();
    }
}
