package com.magicmirror.kinect.ui;

import processing.core.PApplet;

public class AbstractMainApplet extends PApplet {

    private int _width;
    private int _height;

    public static void runApplet(Class clazz){
        PApplet.main(clazz.getCanonicalName());
    }

    public AbstractMainApplet(){
        super();
    }

    // method used only for setting the size of the window
    public void settings() {

        //Set the window size
        _width = this.getWindowWidth();
        _height = this.getWindowHeight();
        size(_width, _height);

    }

    protected int getWindowWidth(){
        return UIConstants.MAIN_WINDOW_WIDTH;
    }

    protected int getWindowHeight(){
        return UIConstants.MAIN_WINDOW_HEIGHT;
    }

    // identical use to setup in Processing IDE except for size()
    public void setup() {
        smooth();

        if (UIConstants.MAIN_FONT == null){
            UIConstants.MAIN_FONT = createFont("Helvetica", 14);
        }

    }


    public void draw() {
        this.drawBackground();
    }


    protected void drawBackground(){
        fill(UIConstants.MAIN_WINDOW_BACKGROUND);
        rect(0,0,this.width,this.height);
    }

}
