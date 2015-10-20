package ua.com.thinkmobiles.dyinglight.entities;

/**
 * Created by CAT_Caterpiller on 11.10.2015.
 */
public class Item {
    private int mId;
    private int mColor;
    private int mImage;
    private int mAngleStart;
    private int mAngleStop;

    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int _color) {
        mColor = _color;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int _image) {
        mImage = _image;
    }

    public int getAngleStart() {
        return mAngleStart;
    }

    public void setAngleStart(int _angleStart) {
        mAngleStart = _angleStart;
    }

    public int getAngleStop() {
        return mAngleStop;
    }

    public void setAngleStop(int _angleStop) {
        mAngleStop = _angleStop;
    }
}
