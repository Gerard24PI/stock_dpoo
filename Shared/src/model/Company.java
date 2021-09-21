package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable {
    private String name;
    private int nShares;
    private float shareValue;
    private ArrayList<Candle> candles;
    private float sample;

    public Company(String name, int nShares, float shareValue) {
        this.name = name;
        this.nShares = nShares;
        this.shareValue = shareValue;
        this.candles = null;
        this.sample = 0;
    }

    public String getName() {
        return name;
    }

    public float getShareValue() {
        return shareValue;
    }

    public int getnShares() {
        return nShares;
    }

    public void setShareValue(float shareValue) {
        this.shareValue = shareValue;
    }

    public void setCandles(ArrayList<Candle> candles) {
        this.candles = candles;
    }

    public void setSample(float sample) {
        this.sample = sample;
    }

    public ArrayList<Candle> getCandles() {
        return candles;
    }

    public float getSample() {
        return sample;
    }

    @Override
    public String toString() {
        return name + " (" + shareValue + ")";
    }
}
