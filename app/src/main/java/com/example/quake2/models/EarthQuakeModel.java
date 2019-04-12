package com.example.quake2.models;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;


public class EarthQuakeModel implements Parcelable {

    private String itemId;
    private String title;
    private String description;
    private String location;
    private String link;
    private String category;
    private String depth;
    private Double depthNumber;

    private Double severity;
    private String magnitude;
    private String latString;
    private String longString;
    private Float latNumber;
    private Double longNumber;
    private String publishedDate;

    public String getLatString() {
        return latString;
    }

    public void setLatString(String latString) {
        this.latString = latString;
    }

    public String getLongString() {
        return longString;
    }

    public void setLongString(String longString) {
        this.longString = longString;
    }

    public Float getLatNumber() {
        String lat1 = getLatString();
        Float latNumber = Float.valueOf(lat1);

        return latNumber;
    }

    public void setLatNumber(Float latNumber) {
        this.latNumber = latNumber;
    }

    public Float getLongNumber() {
        String long1 = getLongString();
        Float latNumber = Float.valueOf(long1);

        return latNumber;
    }

    public void setLongNumber(Double longNumber) {
        this.longNumber = longNumber;
    }

    public int getDepthNumber() {
        String depth1 = getDepth().trim();
        Integer depthNumber = Integer.valueOf(depth1);

        return depthNumber;
    }

    public void setDepthNumber(Double depthNumber) {
        this.depthNumber = depthNumber;
    }


    // add a unique ID to the object if one does not exist
    public String getItemId() {
        if (itemId == null) {
            itemId = UUID.randomUUID().toString();
        };
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


    // get the location information from the description information passed from pull parser
    public String getLocation() {

        String xmlDescription = getDescription();
        location = xmlDescription.substring(xmlDescription.indexOf("Location:")+ 10, xmlDescription.lastIndexOf("Lat/long")-2);
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getDepth() {

        String xmlDescription = getDescription();
        depth = xmlDescription.substring(xmlDescription.indexOf("Depth")+ 6, xmlDescription.lastIndexOf("km"));

        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getMagnitude() {

        String xmlDescription = getDescription();
        magnitude = xmlDescription.substring(xmlDescription.indexOf("Magnitude")+ 12);

        //magnitude.indexOf("Magnitude");

        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.link);
        dest.writeString(this.category);
        dest.writeString(this.publishedDate);
        dest.writeString(this.latString);
        dest.writeString(this.longString);
    }

    public EarthQuakeModel() {
    }

    protected EarthQuakeModel(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.link = in.readString();
        this.category = in.readString();
        this.publishedDate = in.readString();
        this.latString = in.readString();
        this.longString = in.readString();



    }

    public static final Creator<EarthQuakeModel> CREATOR = new Creator<EarthQuakeModel>() {
        @Override
        public EarthQuakeModel createFromParcel(Parcel source) {
            return new EarthQuakeModel(source);
        }

        @Override
        public EarthQuakeModel[] newArray(int size) {
            return new EarthQuakeModel[size];
        }
    };

    // changing the string value into a number this will allow better sorting later
    public Double getSeverity() {
        String severity1 = getMagnitude();
         Double severity = Double.valueOf(severity1);
         return severity;
    }

    public void setSeverity(Double severity) {
        this.severity = severity;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
