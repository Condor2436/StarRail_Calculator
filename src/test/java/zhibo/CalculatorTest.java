package zhibo;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {


    @Test
    void getAscensionCost() throws IOException {
        int[][] credit = {{3200, 9600, 22400, 54400, 118400, 246400}, {4000, 12000, 28000, 68000, 148000, 308000}};
        Map<Integer, Integer> starMap = new HashMap<>();
        starMap.put(4, 0);
        starMap.put(5, 1);
        Calculator calculator = new Calculator();
        assertEquals(credit[starMap.get(4)][0], calculator.getAscensionCost(4, 1, 21));
        assertEquals(credit[starMap.get(4)][1], calculator.getAscensionCost(4, 1, 31));
        assertEquals(credit[starMap.get(4)][2], calculator.getAscensionCost(4, 1, 41));
        assertEquals(credit[starMap.get(4)][3], calculator.getAscensionCost(4, 1, 51));
        assertEquals(credit[starMap.get(4)][4], calculator.getAscensionCost(4, 1, 61));
        assertEquals(credit[starMap.get(4)][5], calculator.getAscensionCost(4, 1, 71));
        assertEquals(credit[starMap.get(5)][0], calculator.getAscensionCost(5, 1, 21));
        assertEquals(credit[starMap.get(5)][1], calculator.getAscensionCost(5, 1, 31));
        assertEquals(credit[starMap.get(5)][2], calculator.getAscensionCost(5, 1, 41));
        assertEquals(credit[starMap.get(5)][3], calculator.getAscensionCost(5, 1, 51));
        assertEquals(credit[starMap.get(5)][4], calculator.getAscensionCost(5, 1, 61));
        assertEquals(credit[starMap.get(5)][5], calculator.getAscensionCost(5, 1, 71));
        assertEquals(credit[starMap.get(4)][1] - credit[starMap.get(4)][0], calculator.getAscensionCost(4, 21, 31));
        assertEquals(credit[starMap.get(4)][2] - credit[starMap.get(4)][1], calculator.getAscensionCost(4, 31, 41));
        assertEquals(credit[starMap.get(4)][3] - credit[starMap.get(4)][2], calculator.getAscensionCost(4, 41, 51));
        assertEquals(credit[starMap.get(4)][4] - credit[starMap.get(4)][3], calculator.getAscensionCost(4, 51, 61));
        assertEquals(credit[starMap.get(4)][5] - credit[starMap.get(4)][4], calculator.getAscensionCost(4, 61, 71));
        assertEquals(credit[starMap.get(5)][1] - credit[starMap.get(5)][0], calculator.getAscensionCost(5, 21, 31));
        assertEquals(credit[starMap.get(5)][2] - credit[starMap.get(5)][1], calculator.getAscensionCost(5, 31, 41));
        assertEquals(credit[starMap.get(5)][3] - credit[starMap.get(5)][2], calculator.getAscensionCost(5, 41, 51));
        assertEquals(credit[starMap.get(5)][4] - credit[starMap.get(5)][3], calculator.getAscensionCost(5, 51, 61));
        assertEquals(credit[starMap.get(5)][5] - credit[starMap.get(5)][4], calculator.getAscensionCost(5, 61, 71));
        assertEquals(credit[starMap.get(4)][2] - credit[starMap.get(4)][0], calculator.getAscensionCost(4, 21, 41));
        assertEquals(credit[starMap.get(4)][3] - credit[starMap.get(4)][1], calculator.getAscensionCost(4, 31, 51));
        assertEquals(credit[starMap.get(4)][4] - credit[starMap.get(4)][2], calculator.getAscensionCost(4, 41, 61));
        assertEquals(credit[starMap.get(4)][5] - credit[starMap.get(4)][3], calculator.getAscensionCost(4, 51, 71));
        assertEquals(credit[starMap.get(5)][2] - credit[starMap.get(5)][0], calculator.getAscensionCost(5, 21, 41));
        assertEquals(credit[starMap.get(5)][3] - credit[starMap.get(5)][1], calculator.getAscensionCost(5, 31, 51));
        assertEquals(credit[starMap.get(5)][4] - credit[starMap.get(5)][2], calculator.getAscensionCost(5, 41, 61));
        assertEquals(credit[starMap.get(5)][5] - credit[starMap.get(5)][3], calculator.getAscensionCost(5, 51, 71));
        assertEquals(credit[starMap.get(4)][3] - credit[starMap.get(4)][0], calculator.getAscensionCost(4, 21, 51));
        assertEquals(credit[starMap.get(4)][4] - credit[starMap.get(4)][1], calculator.getAscensionCost(4, 31, 61));
        assertEquals(credit[starMap.get(4)][5] - credit[starMap.get(4)][2], calculator.getAscensionCost(4, 41, 71));
        assertEquals(credit[starMap.get(5)][3] - credit[starMap.get(5)][0], calculator.getAscensionCost(5, 21, 51));
        assertEquals(credit[starMap.get(5)][4] - credit[starMap.get(5)][1], calculator.getAscensionCost(5, 31, 61));
        assertEquals(credit[starMap.get(5)][5] - credit[starMap.get(5)][2], calculator.getAscensionCost(5, 41, 71));
        assertEquals(credit[starMap.get(4)][4] - credit[starMap.get(4)][0], calculator.getAscensionCost(4, 21, 61));
        assertEquals(credit[starMap.get(4)][5] - credit[starMap.get(4)][1], calculator.getAscensionCost(4, 31, 71));
        assertEquals(credit[starMap.get(5)][4] - credit[starMap.get(5)][0], calculator.getAscensionCost(5, 21, 61));
        assertEquals(credit[starMap.get(5)][5] - credit[starMap.get(5)][1], calculator.getAscensionCost(5, 31, 71));
        assertEquals(credit[starMap.get(4)][5] - credit[starMap.get(4)][0], calculator.getAscensionCost(4, 21, 71));
        assertEquals(credit[starMap.get(5)][5] - credit[starMap.get(5)][0], calculator.getAscensionCost(5, 21, 71));
        Calculator cal1 = new Calculator(0, 0, 0, 4,0,4,true);
        assertEquals(credit[starMap.get(4)][0]-credit[starMap.get(4)][0], cal1.getAscensionCost(4, 20, 21));
        assertEquals(credit[starMap.get(4)][1]-credit[starMap.get(4)][0], cal1.getAscensionCost(4, 20, 31));
        assertEquals(credit[starMap.get(4)][2]-credit[starMap.get(4)][0], cal1.getAscensionCost(4, 20, 41));
        assertEquals(credit[starMap.get(4)][3]-credit[starMap.get(4)][0], cal1.getAscensionCost(4, 20, 51));
        assertEquals(credit[starMap.get(4)][4]-credit[starMap.get(4)][0], cal1.getAscensionCost(4, 20, 61));
        assertEquals(credit[starMap.get(4)][5]-credit[starMap.get(4)][0], cal1.getAscensionCost(4, 20, 71));
        assertEquals(credit[starMap.get(4)][1]-credit[starMap.get(4)][1], cal1.getAscensionCost(4, 30, 31));
        assertEquals(credit[starMap.get(4)][2]-credit[starMap.get(4)][1], cal1.getAscensionCost(4, 30, 41));
        assertEquals(credit[starMap.get(4)][3]-credit[starMap.get(4)][1], cal1.getAscensionCost(4, 30, 51));
        assertEquals(credit[starMap.get(4)][4]-credit[starMap.get(4)][1], cal1.getAscensionCost(4, 30, 61));
        assertEquals(credit[starMap.get(4)][5]-credit[starMap.get(4)][1], cal1.getAscensionCost(4, 30, 71));
        assertEquals(credit[starMap.get(4)][2]-credit[starMap.get(4)][2], cal1.getAscensionCost(4, 40, 41));
        assertEquals(credit[starMap.get(4)][3]-credit[starMap.get(4)][2], cal1.getAscensionCost(4, 40, 51));
        assertEquals(credit[starMap.get(4)][4]-credit[starMap.get(4)][2], cal1.getAscensionCost(4, 40, 61));
        assertEquals(credit[starMap.get(4)][5]-credit[starMap.get(4)][2], cal1.getAscensionCost(4, 40, 71));
        assertEquals(credit[starMap.get(4)][3]-credit[starMap.get(4)][3], cal1.getAscensionCost(4, 50, 51));
        assertEquals(credit[starMap.get(4)][4]-credit[starMap.get(4)][3], cal1.getAscensionCost(4, 50, 61));
        assertEquals(credit[starMap.get(4)][5]-credit[starMap.get(4)][3], cal1.getAscensionCost(4, 50, 71));
        assertEquals(credit[starMap.get(4)][4]-credit[starMap.get(4)][4], cal1.getAscensionCost(4, 60, 61));
        assertEquals(credit[starMap.get(4)][5]-credit[starMap.get(4)][4], cal1.getAscensionCost(4, 60, 71));
        assertEquals(credit[starMap.get(4)][5]-credit[starMap.get(4)][5], cal1.getAscensionCost(4, 70, 71));
        Calculator cal2 = new Calculator(0, 0, 0, 4,0,5,true);
        assertEquals(credit[starMap.get(5)][0]-credit[starMap.get(5)][0], cal2.getAscensionCost(5, 20, 21));
        assertEquals(credit[starMap.get(5)][1]-credit[starMap.get(5)][0], cal2.getAscensionCost(5, 20, 31));
        assertEquals(credit[starMap.get(5)][2]-credit[starMap.get(5)][0], cal2.getAscensionCost(5, 20, 41));
        assertEquals(credit[starMap.get(5)][3]-credit[starMap.get(5)][0], cal2.getAscensionCost(5, 20, 51));
        assertEquals(credit[starMap.get(5)][4]-credit[starMap.get(5)][0], cal2.getAscensionCost(5, 20, 61));
        assertEquals(credit[starMap.get(5)][5]-credit[starMap.get(5)][0], cal2.getAscensionCost(5, 20, 71));
        assertEquals(credit[starMap.get(5)][1]-credit[starMap.get(5)][1], cal2.getAscensionCost(5, 30, 31));
        assertEquals(credit[starMap.get(5)][2]-credit[starMap.get(5)][1], cal2.getAscensionCost(5, 30, 41));
        assertEquals(credit[starMap.get(5)][3]-credit[starMap.get(5)][1], cal2.getAscensionCost(5, 30, 51));
        assertEquals(credit[starMap.get(5)][4]-credit[starMap.get(5)][1], cal2.getAscensionCost(5, 30, 61));
        assertEquals(credit[starMap.get(5)][5]-credit[starMap.get(5)][1], cal2.getAscensionCost(5, 30, 71));
        assertEquals(credit[starMap.get(5)][2]-credit[starMap.get(5)][2], cal2.getAscensionCost(5, 40, 41));
        assertEquals(credit[starMap.get(5)][3]-credit[starMap.get(5)][2], cal2.getAscensionCost(5, 40, 51));
        assertEquals(credit[starMap.get(5)][4]-credit[starMap.get(5)][2], cal2.getAscensionCost(5, 40, 61));
        assertEquals(credit[starMap.get(5)][5]-credit[starMap.get(5)][2], cal2.getAscensionCost(5, 40, 71));
        assertEquals(credit[starMap.get(5)][3]-credit[starMap.get(5)][3], cal2.getAscensionCost(5, 50, 51));
        assertEquals(credit[starMap.get(5)][4]-credit[starMap.get(5)][3], cal2.getAscensionCost(5, 50, 61));
        assertEquals(credit[starMap.get(5)][5]-credit[starMap.get(5)][3], cal2.getAscensionCost(5, 50, 71));
        assertEquals(credit[starMap.get(5)][4]-credit[starMap.get(5)][4], cal2.getAscensionCost(5, 60, 61));
        assertEquals(credit[starMap.get(5)][5]-credit[starMap.get(5)][4], cal2.getAscensionCost(5, 60, 71));
        assertEquals(credit[starMap.get(5)][5]-credit[starMap.get(5)][5], cal2.getAscensionCost(5, 70, 71));
    }
}