package com.example.parker.facemaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Parker on 2/9/2016.
 */
public class Face extends SurfaceView{

    protected Random randNum = new Random();

    protected int skinColor = randNum.nextInt(3);
    protected int eyeColor = randNum.nextInt(3);
    protected int hairColor = randNum.nextInt(3);

    protected Path[] hairStyles;
    protected Path[] eyesStyles;
    protected Path[] noseStyles;
    protected Path eye;

    protected int hairStyleIndex = 2;
    protected int eyeStyleIndex = 0;
    protected int noseStyleIndex;

    protected int[] redValues = new int[3];
    protected int[] greenValues = new int[3];
    protected int[] blueValues = new int[3];

    public Face(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotCacheDrawing(false);
        setWillNotDraw(false);

//http://stackoverflow.com/questions/5978517/how-to-draw-path-into-the-view-in-android
        hairStyles = new Path[3];
        eyesStyles = new Path[3];
        noseStyles = new Path[4];

        hairStyles[0] = new Path();
        hairStyles[0].addRect(75.0f, 700.0f, 825.0f, 1200.0f, Path.Direction.CW);
        hairStyles[0].addArc(75.0f, 315.0f, 825.0f, 1075.0f, 180.0f, 180.0f);
        hairStyles[0].close();

        hairStyles[1] = new Path();
        hairStyles[1].addRect(100.0f, 200.0f, 800.0f, 700.0f, Path.Direction.CW);
        hairStyles[1].close();

        hairStyles[2] = new Path();
        hairStyles[2].addRect(350.0f, 250.0f, 550.0f, 490.0f, Path.Direction.CW);
        hairStyles[2].close();

        eye = new Path();
        eye.addCircle(275.0f, 600.0f, 75.0f, Path.Direction.CW);
        eye.addCircle(625.0f, 600.0f, 75.0f, Path.Direction.CW);
        eye.close();

        eyesStyles[0] = new Path();
        eyesStyles[0].addRect(200.0f, 500.0f, 350.0f, 550.0f, Path.Direction.CW);
        eyesStyles[0].addRect(550.0f, 500.0f, 700.0f, 550.0f, Path.Direction.CW);
        eyesStyles[0].close();

        eyesStyles[1] = new Path();
        eyesStyles[1].lineTo(200.0f, 460.0f);
        eyesStyles[1].lineTo(350.0f, 520.0f);
        eyesStyles[1].lineTo(350.0f, 580.0f);
        eyesStyles[1].lineTo(200.0f, 540.0f);
        eyesStyles[1].lineTo(200.0f, 460.0f);
        eyesStyles[1].setLastPoint(200.0f, 460.0f);

        eyesStyles[2] = new Path();
        eyesStyles[2].lineTo(700.0f, 460.0f);
        eyesStyles[2].lineTo(550.0f, 520.0f);
        eyesStyles[2].lineTo(550.0f, 580.0f);
        eyesStyles[2].lineTo(700.0f, 540.0f);
        eyesStyles[2].lineTo(700.0f, 460.0f);
        eyesStyles[2].setLastPoint(700.0f, 460.0f);


        //http://stackoverflow.com/questions/15597411/can-someone-explain-me-android-graphics-path-and-its-use-to-me
        noseStyles[0] = new Path();
        noseStyles[0].moveTo(420.0f, 600.0f);
        noseStyles[0].lineTo(480.0f, 600.0f);
        noseStyles[0].lineTo(580.0f, 720.0f);
        noseStyles[0].lineTo(320.0f, 720.0f);
        noseStyles[0].lineTo(420.0f, 600.0f);
        noseStyles[0].setLastPoint(420.0f, 600.0f);
        noseStyles[0].close();

        noseStyles[1] = new Path();
        noseStyles[1].moveTo(435.0f, 600.0f);
        noseStyles[1].lineTo(465.0f, 600.0f);
        noseStyles[1].lineTo(520.0f, 720.0f);
        noseStyles[1].lineTo(380.0f, 720.0f);
        noseStyles[1].lineTo(435.0f, 600.0f);
        noseStyles[1].setLastPoint(435.0f, 600.0f);
        noseStyles[1].close();

        noseStyles[2] = new Path();
        noseStyles[2].lineTo(420.0f, 680.0f);
        noseStyles[2].lineTo(435.0f, 720.0f);
        noseStyles[2].lineTo(420.0f, 760.0f);
        noseStyles[2].lineTo(405.0f, 720.0f);
        noseStyles[2].lineTo(420.0f, 680.0f);
        noseStyles[2].setLastPoint(420.0f, 680.0f);

        noseStyles[3] = new Path();
        noseStyles[3].lineTo(480.0f, 680.0f);
        noseStyles[3].lineTo(495.0f, 720.0f);
        noseStyles[3].lineTo(480.0f, 760.0f);
        noseStyles[3].lineTo(465.0f, 720.0f);
        noseStyles[3].lineTo(480.0f, 680.0f);
        noseStyles[3].setLastPoint(480.0f, 680.0f);

        randomize();
    }
    public void randomize(){
        int i;
        int randomNum = randNum.nextInt(100);
        int randomNum2 = randNum.nextInt(100);
        int randomNum3 = randNum.nextInt(100);

        if(randomNum < 33) {
            hairStyleIndex = 0;
        }
        else if(randomNum < 66){
            hairStyleIndex = 1;
        }
        else if(randomNum <= 100){
            hairStyleIndex = 2;
        }
        if(randomNum2 < 33) {
            eyeStyleIndex = 0;
        }
        else if(randomNum2 < 66){
            eyeStyleIndex = 1;
        }
        else if(randomNum2 <= 100){
            eyeStyleIndex = 2;
        }
        if(randomNum3 < 33) {
            noseStyleIndex = 0;
        }
        else if(randomNum3 < 66){
            noseStyleIndex = 1;
        }
        else if(randomNum3 <= 100){
            noseStyleIndex = 2;
        }
        for(i=0; i<3; i++){
            redValues[i] = randNum.nextInt(255);
            greenValues[i] = randNum.nextInt(255);
            blueValues[i] = randNum.nextInt(255);
        }
        hairColor = Color.argb(0xFF, redValues[0], greenValues[0], blueValues[0]);
        skinColor = Color.argb(0xFF, redValues[1], greenValues[1], blueValues[1]);
        eyeColor = Color.argb(0xFF, redValues[2], greenValues[2], blueValues[2]);

        invalidate();

    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mouth = new Paint();
        mouth.setColor(0xFF000000);
        mouth.setStyle(Paint.Style.FILL);
        drawHair(canvas, hairStyleIndex);
        drawEyes(canvas, eyeStyleIndex);
        drawNose(canvas, noseStyleIndex);
        canvas.drawRect(200.0f, 850.0f, 700.0f, 870.0f, mouth);
    }

    private void drawNose(Canvas canvas, int noseStyleIndex) {
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFF000000);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(25.0f);

        Paint nostril = new Paint();
        nostril.setColor(0xFF000000);
        nostril.setStyle(Paint.Style.FILL);

        if(noseStyleIndex == 0){
            canvas.drawPath(noseStyles[noseStyleIndex], borderPaint);
            canvas.drawCircle(515.0f, 700.0f, 20.0f, nostril);
            canvas.drawCircle(385.0f, 700.0f, 20.0f, nostril);
        }
        else if(noseStyleIndex == 1){
            canvas.drawPath(noseStyles[noseStyleIndex], borderPaint);
            canvas.drawCircle(485.0f, 700.0f, 20.0f, nostril);
            canvas.drawCircle(415.0f, 700.0f, 20.0f, nostril);
        }
        else if(noseStyleIndex == 2){
            canvas.drawPath(noseStyles[noseStyleIndex], nostril);
            canvas.drawPath(noseStyles[noseStyleIndex+1], nostril);
        }
    }

    private void drawEyes(Canvas canvas, int eyeStyleIndex) {
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFF000000);
        borderPaint.setStyle(Paint.Style.STROKE);

        Paint eyeWhites = new Paint();
        eyeWhites.setColor(0xFFFFFFFF);
        eyeWhites.setStyle(Paint.Style.FILL);

        Paint eyeBlack = new Paint();
        eyeBlack.setColor(0xFF000000);
        eyeBlack.setStyle(Paint.Style.FILL);

        Paint eyePaint = new Paint();
        eyePaint.setColor(eyeColor);
        eyePaint.setStyle(Paint.Style.FILL);

        Paint eyeBrowPaint = new Paint();
        eyeBrowPaint.setColor(0xFF333300);
        eyeBrowPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(eye, eyeWhites);

        if(eyeStyleIndex == 0 || eyeStyleIndex == 1) {
            canvas.drawCircle(275.0f, 600.0f, 50.0f, eyePaint);
            canvas.drawCircle(625.0f, 600.0f, 50.0f, eyePaint);

            canvas.drawCircle(275.0f, 600.0f, 15.0f, eyeBlack);
            canvas.drawCircle(625.0f, 600.0f, 15.0f, eyeBlack);

            canvas.drawPath(eyesStyles[eyeStyleIndex], eyeBrowPaint);
            if(eyeStyleIndex == 1) {
                canvas.drawPath(eyesStyles[eyeStyleIndex + 1], eyeBrowPaint);
            }
        }
        if(eyeStyleIndex == 2){
            canvas.drawCircle(275.0f, 600.0f, 70.0f, eyePaint);
            canvas.drawCircle(625.0f, 600.0f, 70.0f, eyePaint);
        }
    }

    public void drawHair(Canvas canvas, int index){
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFF000000);
        borderPaint.setStyle(Paint.Style.STROKE);

        Paint hairPaint = new Paint();
        hairPaint.setColor(hairColor);
        hairPaint.setStyle(Paint.Style.FILL);

        Paint skinPaint = new Paint();
        skinPaint.setColor(skinColor);
        skinPaint.setStyle(Paint.Style.FILL);

        if(index == 0 || index ==1){
            canvas.drawPath(hairStyles[hairStyleIndex], hairPaint);
            canvas.drawPath(hairStyles[hairStyleIndex], borderPaint);

            canvas.drawCircle(450.0f, 700.0f, 350.0f, skinPaint);
            canvas.drawCircle(450.0f, 700.0f, 350.0f, borderPaint);
            if(index == 0){
                canvas.drawArc(140.0f, 350.0f, 760.0f, 700.0f, 180.0f, 180.0f, true, hairPaint);
            }
        } else {
            canvas.drawCircle(450.0f, 700.0f, 350.0f, skinPaint);
            canvas.drawCircle(450.0f, 700.0f, 350.0f, borderPaint);

            canvas.drawPath(hairStyles[hairStyleIndex], hairPaint);
            canvas.drawPath(hairStyles[hairStyleIndex], borderPaint);
        }
    }
    public void drawAgain(){
        invalidate();
    }
}
