import com.jsoniter.output.JsonStream;
import processing.core.PApplet;
import controlP5.*;
import SimpleOpenNI.*;
import processing.core.PImage;
import processing.core.PVector;


public class Main extends PApplet {

    Slider slider;
    SimpleOpenNI kinect;
    ImageType currentImageType;

    // UI
    UIUtils uiUtils = new UIUtils();
    ControlP5 cp5;
    DropdownList drpImageType;
    CheckBox chkShowImage;

    //Network
    WebSocketServer socketServer = null;

    //Up
    float LeftshoulderAngle = 0;
    float LeftelbowAngle = 0;
    float RightshoulderAngle = 0;
    float RightelbowAngle = 0;

    //Legs
    float RightLegAngle = 0;
    float LeftLegAngle = 0;

    enum ImageType {
        None,
        Depth,
        RGB,
        IR,
        User
    }

    // The argument passed to main must match the class name
    public static void main(String[] args) {
        PApplet.main("Main");
    }

    // method used only for setting the size of the window
    public void settings() {

        //Set the window size
        size(UIConstants.MAIN_WINDOW_WIDTH, UIConstants.MAIN_WINDOW_HEIGHT);

    }

    // identical use to setup in Processing IDE except for size()
    public void setup() {

        try {
            socketServer = new WebSocketServer(8887);
            socketServer.start();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        //currentImageType = ImageType.User;

        //Initialize the UI
        this.initUI();

        //Initialize the Kinect
        this.setupKinect();

    }

    protected void initUI() {
        cp5 = new ControlP5(this);

        drpImageType = cp5.addDropdownList("ShowImageType")
                .setPosition(UIConstants.UI_LEFT_POSITION, UIConstants.BORDER_SIZE + 10);
        uiUtils.customize(drpImageType, "Show Image Type", new String[]{"No Image", "Depth Image", "RGB Image", "IR Image", "User Image"})
                .setWidth(150);


    }


    private void setupKinect() {
        // Setup the Kinect
        kinect = new SimpleOpenNI(this, SimpleOpenNI.RUN_MODE_MULTI_THREADED);

        kinect.enableDepth();
        //kinect.enableRGB();
        //kinect.enableIR();
        kinect.enableUser();// because of the version this change

        //fill(255, 0, 0);


        //size(kinect.depthWidth()+kinect.irWidth(), kinect.depthHeight());
        kinect.setMirror(false);
    }

    public void draw() {
        this.drawBackground();

        kinect.update();

        this.drawKinectImage();

        //image(kinect.irImage(),kinect.depthWidth(),0);
        //image(kinect.userImage(),0,0);

        IntVector userList = new IntVector();
        kinect.getUsers(userList);
        if (userList.size() > 0) {
            int userId = userList.get(0);
            //If we detect one user we have to draw it
            if (kinect.isTrackingSkeleton(userId)) {
                //DrawSkeleton
                drawSkeleton(userId);
                //drawUpAngles
                ArmsAngle(userId);
                //Draw the user Mass
                //MassUser(userId);
                //AngleLeg
                //LegsAngle(userId);
            }
        }

        cp5.draw();
    }

    /**
     * Draw Kinect camera image
     */
    void drawKinectImage() {
        PImage image = getCurrentImageToDisplay();
        if (image!=null) {
            fill(UIConstants.MAIN_WINDOW_BACKGROUND);
            stroke(UIConstants.MAIN_UI_COLOR);
            rect(UIConstants.BORDER_SIZE, UIConstants.BORDER_SIZE, UIConstants.KINECT_IMAGE_WIDTH + UIConstants.BORDER_SIZE, UIConstants.KINECT_IMAGE_HEIGHT + UIConstants.BORDER_SIZE);
            image(image, UIConstants.BORDER_SIZE + 4, UIConstants.BORDER_SIZE + 4);
        }
    }

    private PImage getCurrentImageToDisplay() {
        if (currentImageType == ImageType.User) {
            return kinect.userImage();
        } else if (currentImageType == ImageType.RGB) {
            return kinect.rgbImage();
        } else if(currentImageType ==ImageType.IR) {
            return kinect.irImage();
        } else if(currentImageType ==ImageType.Depth) {
            return kinect.depthImage();
        } else {
            return null;
        }
    }

    void drawSkeleton(int userId) {

        stroke(255 );
        strokeWeight(5);
        PVector var4 = new PVector();
        PVector var5 = new PVector();
        kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW, var4);
        kinect.getJointPositionSkeleton(userId, SimpleOpenNI.SKEL_RIGHT_HAND, var5);
        PVector var6 = new PVector();
        PVector var7 = new PVector();
        kinect.convertRealWorldToProjective(var4, var6);
        kinect.convertRealWorldToProjective(var5, var7);
        this.line(var6.x, var6.y, var7.x, var7.y);

        boradcastLine(var6, var7, "leg");


        stroke(255 );
        strokeWeight(3);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_HEAD, SimpleOpenNI.SKEL_NECK);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_LEFT_SHOULDER);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_LEFT_ELBOW);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_ELBOW, SimpleOpenNI.SKEL_LEFT_HAND);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_NECK, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_RIGHT_ELBOW);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW, SimpleOpenNI.SKEL_RIGHT_HAND);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER, SimpleOpenNI.SKEL_TORSO);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_LEFT_HIP);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_HIP, SimpleOpenNI.SKEL_LEFT_KNEE);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_LEFT_KNEE, SimpleOpenNI.SKEL_LEFT_FOOT);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_TORSO, SimpleOpenNI.SKEL_RIGHT_HIP);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP, SimpleOpenNI.SKEL_RIGHT_KNEE);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_KNEE, SimpleOpenNI.SKEL_RIGHT_FOOT);
        kinect.drawLimb(userId, SimpleOpenNI.SKEL_RIGHT_HIP, SimpleOpenNI.SKEL_LEFT_HIP);
        noStroke();
        fill(255,0,0);
        drawJoint(userId, SimpleOpenNI.SKEL_HEAD);
        drawJoint(userId, SimpleOpenNI.SKEL_NECK);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_SHOULDER);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_ELBOW);
        drawJoint(userId, SimpleOpenNI.SKEL_NECK);
        drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_SHOULDER);
        drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_ELBOW);
        drawJoint(userId, SimpleOpenNI.SKEL_TORSO);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_HIP);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_KNEE);
        drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_HIP);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_FOOT);
        drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_KNEE);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_HIP);
        drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_FOOT);
        drawJoint(userId, SimpleOpenNI.SKEL_RIGHT_HAND);
        drawJoint(userId, SimpleOpenNI.SKEL_LEFT_HAND);


    }

    void drawJoint(int userId, int jointID) {
        /*
        PVector joint = new PVector();
        float confidence = kinect.getJointPositionSkeleton(userId, jointID,
                joint);
        if(confidence < 0.8){
            return;
        }
        PVector convertedJoint = new PVector();
        kinect.convertRealWorldToProjective(joint, convertedJoint);
        ellipse(convertedJoint.x, convertedJoint.y, 5, 5);
        */
    }

    //Generate the angle
    float angleOf(PVector one, PVector two, PVector axis) {
        PVector limb = PVector.sub(two, one);
        return degrees(PVector.angleBetween(limb, axis));
    }

    public void ArmsAngle(int userId){
        // get the positions of the three joints of our right arm
        PVector rightHand = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_RIGHT_HAND,rightHand);
        PVector rightElbow = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_RIGHT_ELBOW,rightElbow);
        PVector rightShoulder = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_RIGHT_SHOULDER,rightShoulder);
        // we need right hip to orient the shoulder angle
        PVector rightHip = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_RIGHT_HIP,rightHip);
        // get the positions of the three joints of our left arm
        PVector leftHand = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_LEFT_HAND,leftHand);
        PVector leftElbow = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_LEFT_ELBOW,leftElbow);
        PVector leftShoulder = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_LEFT_SHOULDER,leftShoulder);
        // we need left hip to orient the shoulder angle
        PVector leftHip = new PVector();
        kinect.getJointPositionSkeleton(userId,SimpleOpenNI.SKEL_LEFT_HIP,leftHip);
        // reduce our joint vectors to two dimensions for right side
        PVector rightHand2D = new PVector(rightHand.x, rightHand.y);
        PVector rightElbow2D = new PVector(rightElbow.x, rightElbow.y);
        PVector rightShoulder2D = new PVector(rightShoulder.x,rightShoulder.y);
        PVector rightHip2D = new PVector(rightHip.x, rightHip.y);
        // calculate the axes against which we want to measure our angles
        PVector torsoOrientation = PVector.sub(rightShoulder2D, rightHip2D);
        PVector upperArmOrientation = PVector.sub(rightElbow2D, rightShoulder2D);
        // reduce our joint vectors to two dimensions for left side
        PVector leftHand2D = new PVector(leftHand.x, leftHand.y);
        PVector leftElbow2D = new PVector(leftElbow.x, leftElbow.y);
        PVector leftShoulder2D = new PVector(leftShoulder.x,leftShoulder.y);
        PVector leftHip2D = new PVector(leftHip.x, leftHip.y);
        // calculate the axes against which we want to measure our angles
        PVector torsoLOrientation = PVector.sub(leftShoulder2D, leftHip2D);
        PVector upperArmLOrientation = PVector.sub(leftElbow2D, leftShoulder2D);
        // calculate the angles between our joints for rightside
        RightshoulderAngle = angleOf(rightElbow2D, rightShoulder2D, torsoOrientation);
        RightelbowAngle = angleOf(rightHand2D,rightElbow2D,upperArmOrientation);
        // show the angles on the screen for debugging
        fill(0,0,255);
        scale(1);
        text("Right shoulder: " + (int)(RightshoulderAngle) + "\n" + " Right elbow: " + (int)(RightelbowAngle), 20, 20);
        // calculate the angles between our joints for leftside
        LeftshoulderAngle = angleOf(leftElbow2D, leftShoulder2D, torsoLOrientation);
        LeftelbowAngle = angleOf(leftHand2D,leftElbow2D,upperArmLOrientation);
        // show the angles on the screen for debugging
        fill(0,0,255);
        scale(1);
        text("Left shoulder: " + (int)(LeftshoulderAngle) + "\n" + " Left elbow: " + (int)(LeftelbowAngle), 20, 55);

       // myTextlabelA.setText("Left shoulder: " + (int)(LeftshoulderAngle) + "\n" + " Left elbow: " + (int)(LeftelbowAngle));

        //Arduino serial for legs
        //ArduinoSerialArms();
    }

    protected void drawBackground(){
        fill(UIConstants.MAIN_WINDOW_BACKGROUND);
        rect(0,0,UIConstants.MAIN_WINDOW_WIDTH,UIConstants.MAIN_WINDOW_HEIGHT);
    }

    public void onNewUser(SimpleOpenNI kinect, int userID) {
        println("Start skeleton tracking");
        kinect.startTrackingSkeleton(userID);
        broadcastNetworkMessage("onNewUser:"+ userID);
    }

    public void onLostUser(SimpleOpenNI curContext, int userId) {
        println("onLostUser - userId: " + userId);
    }

    public void controlEvent(ControlEvent theEvent) {
        if (theEvent.getName().equalsIgnoreCase("ShowImageType")){
            this.handleShowImageType(theEvent.getValue());
        }
    }

    private void handleShowImageType(float value) {
        if (value == 0) {
            currentImageType = ImageType.None;
        } else if (value == 1){
            currentImageType = ImageType.Depth;
        } else if (value == 2){
            currentImageType = ImageType.RGB;
        } else if (value == 3){
            currentImageType = ImageType.IR;
        } else if (value == 4){
            currentImageType = ImageType.User;
        }
    }

    void broadcastNetworkMessage(byte[] data){
        if (socketServer!=null){
            socketServer.broadcast(data);
        }
    }

    void broadcastNetworkMessage(String message){
        if (socketServer!=null){
            socketServer.broadcast(message);
        }
    }

    void boradcastLine(PVector v1, PVector v2, String code){
        /*
        if (socketServer!=null) {
            socketServer.broadcast(new BasicLimb(v1, v2, code).toJSON());
        }
        */
    }

}
