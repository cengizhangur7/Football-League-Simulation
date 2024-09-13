package estu.ceng.Interface;

import java.util.Map;

public interface Attributes {
    // Yeteneklerin haritasını döndürür
    Map<String, Integer> getAttributes();

    // Yeteneklerin ortalamasını döndürür
    double getAverage();

    // Yetenekleri yazdıran method
    void printAttributes();
}
