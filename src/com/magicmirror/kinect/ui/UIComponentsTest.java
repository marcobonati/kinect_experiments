package com.magicmirror.kinect.ui;

import com.magicmirror.kinect.ui.components.UILabel;
import processing.core.PApplet;
import processing.core.PVector;

public class UIComponentsTest extends AbstractMainApplet {

    private UILabel _label;

    // The argument passed to main must match the class name
    public static void main(String[] args) {
        runApplet(UIComponentsTest.class);
    }


    @Override
    public void setup() {
        super.setup();

        _label = new UILabel(this)
                    .setColor(color(0,0,200))
                    .setBackground(color(36,255,138))
                    .setPosition(new PVector(50,50))
                    .setText("Hello World!");
    }

    @Override
    public void draw() {
        super.draw();
        _label.draw();
    }

}