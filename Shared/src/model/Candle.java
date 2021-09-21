package model;

import java.io.Serializable;

public class Candle implements Serializable {
    private float maxPrice;
    private float minPrice;
    private float openPrice;
    private float closePrice;

    public Candle(float maxPrice, float minPrice, float openPrice, float closePrice) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public float getClosePrice() {
        return closePrice;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                '}';
    }
}
