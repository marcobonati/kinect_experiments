package com.magicmirror.kinect.ui.components;

import com.magicmirror.kinect.ui.UIConstants;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class UILabel {

    protected PApplet _parent;
    protected String _text;
    protected int _color = 255;
    protected int _background = -1;
    protected PVector _position;
    protected PFont _font;
    protected int _padding = 4;

    public UILabel(PApplet parent){
        _parent = parent;
        _position = new PVector(0,0,0);
        _font = UIConstants.MAIN_FONT;
    }

    public UILabel setText(String value){
        this._text = value;
        return this;
    }

    public String getText(){
        return this._text;
    }

    public int getColor() {
        return _color;
    }

    public UILabel setColor(int color) {
        this._color = color;
        return this;
    }

    public int getBackground() {
        return _background;
    }

    public UILabel setBackground(int background) {
        this._background = background;
        return this;
    }

    public PVector getPosition() {
        return _position;
    }

    public UILabel setPosition(PVector position) {
        this._position = position;
        return this;
    }

    public int getPadding() {
        return _padding;
    }

    public void setPadding(int padding) {
        this._padding = padding;
    }


    public void draw(){
        _parent.pushMatrix();
        _parent.translate(_position.x, _position.y);

        //calculate the text width and height
        float cw = _parent.textWidth(_text);

        //draw background
        if (_background != -1) {
            _parent.fill(_background);
            _parent.stroke(_background);
            _parent.rect(0, 0, cw + _padding * 2, _font.getSize() + _padding * 2);
        }

        //draw the text
        _parent.pushMatrix();
        _parent.textFont(_font);
        _parent.translate(0, _font.getSize());
        _parent.fill(_color);
        _parent.text(_text, _padding, _padding);
        _parent.popMatrix();


        _parent.popMatrix();
    }



}
