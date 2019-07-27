import com.magicmirror.kinect.ui.UIConstants;
import processing.core.PApplet;
import SimpleOpenNI.*;


public class KinectPlayer extends PApplet  {

    SimpleOpenNI  context;

    // The argument passed to main must match the class name
    public static void main(String[] args) {
        PApplet.main("KinectPlayer");
    }

    // method used only for setting the size of the window
    public void settings() {

        //Set the window size
        size(UIConstants.MAIN_WINDOW_WIDTH, UIConstants.MAIN_WINDOW_HEIGHT);

    }

    // identical use to setup in Processing IDE except for size()
    public void setup() {

        println("1");
        context = new SimpleOpenNI(this, "test.oni");
        println("2");
        context.enableDepth();
        println("3");
        context.enableRGB();

        println("5");
        context.setRepeatPlayer(true);
        println("6");
        context.playbackPlay(true);
        context.setPlaybackSpeedPlayer(.5f);

        /*
        // playing, this works without the camera
        if( context.openFileRecording("test.oni") == false)
        {
            println("can't find recording !!!!");
            exit();
        } else {
            println("File opened!");
        }

    */

    }

    public void draw() {

        // update
        context.update();


        // draw the cam data
        if((context.nodes() & SimpleOpenNI.NODE_DEPTH) != 0)
        {
            if((context.nodes() & SimpleOpenNI.NODE_IMAGE) != 0)
            {
                image(context.depthImage(),0,0);
                image(context.rgbImage(),context.depthWidth() + 10,0);
            }
            else
                image(context.depthImage(),0,0);
        }



    }

    protected void drawBackground(){
        fill(UIConstants.MAIN_WINDOW_BACKGROUND);
        rect(0,0,UIConstants.MAIN_WINDOW_WIDTH,UIConstants.MAIN_WINDOW_HEIGHT);
    }


}
