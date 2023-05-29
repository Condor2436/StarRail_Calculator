package zhibo;

import java.io.IOException;


public class Calculator {
    private static final int LEVEL3_EXP = 20000;
    private static final int LEVEL2_EXP = 5000;
    private static final int LEVEL1_EXP = 1000;
    private final int[] characterUpgradeData = {0, 0, 200, 500, 1000, 1600, 2650, 4320, 6640, 9700, 13560, 18300, 24010, 30740, 38570, 47570, 57810, 69360, 82280, 96640, 112510, 129090, 145930, 163030, 180400, 198040, 215950, 234140, 252620, 271380, 290420, 309760, 329390, 349320, 369540, 390070, 410910, 432050, 453500, 475260, 497340, 519730, 545280, 574420, 607250, 643850, 684320, 728750, 777230, 829860, 886730, 947920, 1013540, 1083670, 1158410, 1237860, 1322100, 1411220, 1505330, 1604520, 1708870, 1815110, 1926940, 2044470, 2167790, 2297000, 2432200, 2573490, 2720970, 2874750, 3034920, 3214180, 3414100, 3635020, 3877280, 4141210, 4427160, 4735460, 5066460, 5420500, 5797920};
    private final int[] expMaterialData = {0, 18000, 24000, 30000, 37000, 45000};
    private final int[] expCreditMaterialData = {0, 500, 600, 800, 1000, 1200};
    private final int[] creditMaterialData = {0, 9500, 12500, 20000, 24000};
    private final int[][] ascensionData = {{0, 3200, 9600, 22400, 54400, 118400, 246400}, {0, 4000, 12000, 28000, 68000, 148000, 308000}};
    private int cumulativeEXP = 0;
    private int cumulativeCredit = 0;
    private int existLevel3 = 0;
    private int existLevel2 = 0;
    private int existLevel1 = 0;
    private int existCredit = 0;
    private int worldLevel = 1;
    private int star = 4;
    private boolean isAscension;

    public Calculator() throws IOException {
        isAscension = false;
    }

    public Calculator(int existLevel3, int existLevel2, int existLevel1, int worldLevel, int existCredit, int star, boolean isAscension) throws IOException {
        this.existLevel3 = existLevel3;
        this.existLevel2 = existLevel2;
        this.existLevel1 = existLevel1;
        this.worldLevel = worldLevel;
        this.existCredit = existCredit;
        this.star = star;
        this.isAscension = isAscension;
    }

    public String convertExpToMaterial(int exp) {
        if (exp <= 0) {
            return "level3 material:0" + System.lineSeparator() +
                    "level2 material:0" + System.lineSeparator() +
                    "level1 material:0" + System.lineSeparator();
        }
        int level3 = exp / LEVEL3_EXP;
        exp %= LEVEL3_EXP;
        int level2 = exp / LEVEL2_EXP;
        exp %= LEVEL2_EXP;
        int level1 = exp / LEVEL1_EXP;
        exp %= LEVEL1_EXP;
        if (exp > 0) {
            level1++;
        }
        return "level3 material:" + level3 + System.lineSeparator() +
                "level2 material:" + level2 + System.lineSeparator() +
                "level1 material:" + level1 + System.lineSeparator();
    }

    public int calculateCharacterExp(int currLevel, int targetLevel) {
        return characterUpgradeData[targetLevel] - characterUpgradeData[currLevel];
    }

    public int reduceExistExpMaterial(int exp, int level3, int level2, int level1) {
        return Math.max(exp - level3 * LEVEL3_EXP - level2 * LEVEL2_EXP - level1 * LEVEL1_EXP, 0);
    }

    public String calculateTimesInString(int exp, int worldLevel) {
        if (worldLevel <= 0) {
            worldLevel = 1;
        }
        if (worldLevel >= 4) {
            worldLevel = 4;
        }
        int res = exp % expMaterialData[worldLevel + 1] == 0 ? exp / expMaterialData[worldLevel + 1] : exp / expMaterialData[worldLevel + 1] + 1;
        return "You need to complete " + res + " times exp material stage in level" + (worldLevel + 1) + ", which costs " + res * 10 + " Trailblaze Power" + System.lineSeparator();
    }

    public int calculateTimes(int exp, int worldLevel) {
        if (worldLevel <= 0) {
            worldLevel = 1;
        }
        if (worldLevel >= 4) {
            worldLevel = 4;
        }
        return exp % expMaterialData[worldLevel + 1] == 0 ? exp / expMaterialData[worldLevel + 1] : exp / expMaterialData[worldLevel + 1] + 1;
    }

    public int getAscensionCost(int star, int currLevel, int targetLevel) {
        int[] ascensionTable = {0, 0, 1, 2, 3, 4, 5, 6};
        int max = Math.min((targetLevel - 1) / 10, 7);
        int min = isAscension ? Math.min((currLevel - 1) / 10, 7) + 1 : Math.min((currLevel - 1) / 10, 7);
        return star == 4 ? ascensionData[0][ascensionTable[max]] - ascensionData[0][ascensionTable[min]] : ascensionData[1][ascensionTable[max]] - ascensionData[1][ascensionTable[min]];
    }


    public String calculateCreditInString(int exp, int currLevel, int targetLevel) {
        int ascensionCost = getAscensionCost(star, currLevel, targetLevel);
        double credit = exp * 0.1 + ascensionCost;
        int credits = (int) Math.ceil(credit);
        return "Totally, you need to collect at least " + credits + " credits";
    }

    public int calculateCredit(int exp, int currLevel, int targetLevel) {
        int ascensionCost = getAscensionCost(star, currLevel, targetLevel);
        double credit = exp * 0.1 + ascensionCost;
        return (int) Math.ceil(credit);
    }

    public int reduceExistCreditAndGainFromExp(int exp) {
        return cumulativeCredit - existCredit - calculateTimes(exp, worldLevel) * expCreditMaterialData[worldLevel + 1];
    }

    public int calculateCreditTimes(int exp, int currLevel, int targetLevel) {
        int credits = calculateCredit(exp, currLevel, targetLevel);
        int index = Math.min(worldLevel, 4);
        double res = (double) credits / creditMaterialData[index];
        return (int) Math.ceil(res);
    }

    public String calculateCreditTimesInString(int exp, int currLevel, int targetLevel) {
        return "You need to complete " + calculateCreditTimes(exp, currLevel, targetLevel) + " times credit stage in level" + (worldLevel + 1) + ", which costs " + calculateCreditTimes(exp, currLevel, targetLevel) * 10 + " Trailblaze Power" + System.lineSeparator();
    }

    public String calculate(int currLevel, int targetLevel, int existLevel3, int existLevel2, int existLevel1, int worldLevel, int existCredit) {
        StringBuilder sb = new StringBuilder();
        cumulativeEXP += calculateCharacterExp(currLevel, targetLevel);
        cumulativeCredit += calculateCredit(calculateCharacterExp(currLevel, targetLevel), currLevel, targetLevel);
        this.existLevel1 = existLevel1;
        this.existLevel2 = existLevel2;
        this.existLevel3 = existLevel3;
        this.worldLevel = worldLevel;
        sb.append("Total exp you need is ").append(cumulativeEXP).append(System.lineSeparator());
        sb.append(calculateCreditInString(cumulativeEXP, currLevel, targetLevel)).append(System.lineSeparator());
        int exp = reduceExistExpMaterial(cumulativeEXP, existLevel3, existLevel2, existLevel1);
        int credit = reduceExistCreditAndGainFromExp(exp);
        sb.append("Based on your existing exp material, you need to collect ").append(exp).append(" exp").append(System.lineSeparator());
        sb.append("Based on your existing credits and the credit you will gain from the exp material stage, you need to collect ").append(credit).append(" credits").append(System.lineSeparator());
        return sb.append(convertExpToMaterial(exp)).append(calculateTimesInString(exp, worldLevel)).append(calculateCreditTimesInString(exp, currLevel, targetLevel)).toString();
    }

    public String calculate(int currLevel, int targetLevel) {
        StringBuilder sb = new StringBuilder();
        cumulativeEXP += calculateCharacterExp(currLevel, targetLevel);
        cumulativeCredit += calculateCredit(calculateCharacterExp(currLevel, targetLevel), currLevel, targetLevel);
        sb.append("Total exp you need is ").append(cumulativeEXP).append(System.lineSeparator());
        sb.append(calculateCreditInString(cumulativeEXP, currLevel, targetLevel)).append(System.lineSeparator());
        int exp = reduceExistExpMaterial(cumulativeEXP, existLevel3, existLevel2, existLevel1);
        int credit = reduceExistCreditAndGainFromExp(exp);
        sb.append("Based on your existing exp material, you need to collect ").append(exp).append(" exp").append(System.lineSeparator());
        sb.append("Based on your existing credits and the credit you will gain from the exp material stage, you need to collect ").append(credit).append(" credits").append(System.lineSeparator());
        return sb.append(convertExpToMaterial(exp)).append(calculateTimesInString(exp, worldLevel)).append(calculateCreditTimesInString(exp, currLevel, targetLevel)).toString();
    }

    public int[] getExpMaterialNum(int currLevel, int targetLevel, int existLevel3, int existLevel2, int existLevel1) {
        int exp = calculateCharacterExp(currLevel, targetLevel);
        exp = reduceExistExpMaterial(exp, existLevel3, existLevel2, existLevel1);
        if (exp <= 0) {
            return new int[]{0, 0, 0};
        }
        int level3 = exp / LEVEL3_EXP;
        exp %= LEVEL3_EXP;
        int level2 = exp / LEVEL2_EXP;
        exp %= LEVEL2_EXP;
        int level1 = exp / LEVEL1_EXP;
        exp %= LEVEL1_EXP;
        if (exp > 0) {
            level1++;
        }
        return new int[]{level3, level2, level1};
    }

    public int getExpTimes(int exp, int worldLevel) {
        if (worldLevel <= 0) {
            worldLevel = 1;
        }
        if (worldLevel >= 4) {
            worldLevel = 4;
        }
        return exp % expMaterialData[worldLevel] == 0 ? exp / expMaterialData[worldLevel] : exp / expMaterialData[worldLevel] + 1;
    }

    public void reset() {
        cumulativeEXP = 0;
        existLevel1 = 0;
        existLevel2 = 0;
        existLevel3 = 0;
        worldLevel = 1;
        cumulativeCredit = 0;
        existCredit = 0;
    }

    public void didAscension() {
        this.isAscension = true;
    }

    public void resetAscension() {
        this.isAscension = false;
    }
}
