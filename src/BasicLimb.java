import com.jsoniter.output.JsonStream;
import processing.core.PVector;

public class BasicLimb {

    public PVector p1;
    public PVector p2;
    public String limbName;

    public BasicLimb(PVector p1, PVector p2, String limbName) {
        this.p1 = p1;
        this.p2 = p2;
        this.limbName = limbName;
    }

    public String toJSON(){
        return JsonStream.serialize(this);
    }
}
