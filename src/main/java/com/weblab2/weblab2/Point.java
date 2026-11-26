package com.weblab2.weblab2;

import java.time.Duration;
import java.time.Instant;

public class Point {
    private double x;
    private double y;
    private double r;
    private boolean result;
    Instant time;
    double execTime;


    public Point(double x, double y, double r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.time = Instant.now();
    }

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = Instant.now();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isResult() {
        return result;
    }

    public Instant getTime(){
        return time;
    }

    public double getExecTime(){
        return execTime;
    }

    public void setExecTime(Instant start){
        Instant end = Instant.now();
        this.execTime = Duration.between(start, end).toNanos() / 1000000.0;
    }
}