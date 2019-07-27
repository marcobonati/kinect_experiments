import com.magicmirror.kinect.ui.UIConstants;
import processing.core.PApplet;
import SimpleOpenNI.*;
import processing.core.PImage;


public class KinectRecorder extends PApplet  {

    SimpleOpenNI  context;

    // The argument passed to main must match the class name
    public static void main(String[] args) {
        PApplet.main("KinectRecorder");
    }

    // method used only for setting the size of the window
    public void settings() {

        //Set the window size
        size(UIConstants.MAIN_WINDOW_WIDTH, UIConstants.MAIN_WINDOW_HEIGHT);

    }

    // identical use to setup in Processing IDE except for size()
    public void setup() {

        context = new SimpleOpenNI(this);

        // recording
        //context.enableIR();
        context.enableDepth();
        context.enableUser();
        context.enableRGB();
        //context.enableHand();

        // setup the recording
        context.enableRecorder("test.oni");


        // select the recording channels
        context.addNodeToRecording(SimpleOpenNI.NODE_DEPTH,true);
        context.addNodeToRecording(SimpleOpenNI.NODE_IMAGE,true);
//        context.addNodeToRecording(SimpleOpenNI.NODE_USER, true);

        /*
        context.addNodeToRecording(SimpleOpenNI.NODE_IMAGE,true);
        context.addNodeToRecording(SimpleOpenNI.NODE_PLAYER, true);
        context.addNodeToRecording(SimpleOpenNI.NODE_SCENE, true);
        context.addNodeToRecording(SimpleOpenNI.NODE_IR, true);
    */

    }

    public void draw() {

        // update
        context.update();

        PImage image = context.rgbImage();
        if (image!=null) {
            fill(UIConstants.MAIN_WINDOW_BACKGROUND);
            stroke(UIConstants.MAIN_UI_COLOR);
            rect(UIConstants.BORDER_SIZE, UIConstants.BORDER_SIZE, UIConstants.KINECT_IMAGE_WIDTH + UIConstants.BORDER_SIZE, UIConstants.KINECT_IMAGE_HEIGHT + UIConstants.BORDER_SIZE);
            image(image, UIConstants.BORDER_SIZE + 4, UIConstants.BORDER_SIZE + 4);
        }

    }

    protected void drawBackground(){
        fill(UIConstants.MAIN_WINDOW_BACKGROUND);
        rect(0,0,UIConstants.MAIN_WINDOW_WIDTH,UIConstants.MAIN_WINDOW_HEIGHT);
    }


}
