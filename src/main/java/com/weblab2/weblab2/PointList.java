package com.weblab2.weblab2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PointList implements Serializable {
    private List<Point> pointList;

    public PointList() {
        this.pointList = new ArrayList<>();
    }

    public PointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public void addPoint(Point point){
        this.pointList.add(point);
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }
}
