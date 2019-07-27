package com.magicmirror.kinect.ui;

import SimpleOpenNI.SimpleOpenNI;
import com.magicmirror.kinect.ui.components.UILabel;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class UIComponentsTest extends AbstractMainApplet {

    private UILabel _label;
    private SimpleOpenNI _kinect;

    // The argument passed to main must match the class name
    public static void main(String[] args) {
        runApplet(UIComponentsTest.class);
    }

    protected int getWindowWidth(){
        return (int)(UIConstants.MAIN_WINDOW_WIDTH *1.3);
    }

    protected int getWindowHeight(){
        return (int)(UIConstants.MAIN_WINDOW_HEIGHT *1.3);
    }

    @Override
    public void setup() {
        super.setup();

        _kinect = new SimpleOpenNI(this, SimpleOpenNI.RUN_MODE_MULTI_THREADED);
        _kinect.enableDepth();


        _label = new UILabel(this)
                    .setColor(color(10,10,10))
                    .setBackground(color(92,172,29, 150))
                    .setPosition(new PVector(50,50))
                    .setText("Right Shoulder. Angle: 245°");
    }

    @Override
    public void draw() {
        super.draw();

        _kinect.update();
        PImage image = _kinect.depthImage();
        if (image!=null) {
            pushMatrix();
            scale(1.6f);
            fill(UIConstants.MAIN_WINDOW_BACKGROUND);
            stroke(UIConstants.MAIN_UI_COLOR);
            rect(UIConstants.BORDER_SIZE, UIConstants.BORDER_SIZE, UIConstants.KINECT_IMAGE_WIDTH + UIConstants.BORDER_SIZE, UIConstants.KINECT_IMAGE_HEIGHT + UIConstants.BORDER_SIZE);
            image(image, UIConstants.BORDER_SIZE + 4, UIConstants.BORDER_SIZE + 4);
            popMatrix();
        }


        _label.setPosition(new PVector(mouseX +20 ,mouseY +20))
                .draw();

    }

}