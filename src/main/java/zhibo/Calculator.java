package zhibo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;


public class Calculator {
    private static final int LEVEL3_EXP = 20000;
    private static final int LEVEL2_EXP = 5000;
    private static final int LEVEL1_EXP = 1000;
    private final int[] characterUpgradeData;
    private final int[] expMaterialData;
    private final int[] expCreditMaterialData;
    private final int[] creditMaterialData;
    private final int[][] ascensionData;
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
        FileInputStream characterDataFile = new FileInputStream("src/main/resources/character_upgrade.xlsx");
        FileInputStream expMaterialDataFile = new FileInputStream("src/main/resources/exp_credit_material.xlsx");
        Workbook characterWorkbook = new XSSFWorkbook(characterDataFile);
        Workbook expMaterialWorkbook = new XSSFWorkbook(expMaterialDataFile);
        characterUpgradeData = new int[81];
        expMaterialData = new int[6];
        expCreditMaterialData = new int[6];
        creditMaterialData = new int[6];
        ascensionData = new int[2][7];
        loadCharacterData(characterWorkbook);
        loadExpAndCreditMaterialData(expMaterialWorkbook);
        characterDataFile.close();
        expMaterialDataFile.close();
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
        FileInputStream characterDataFile = new FileInputStream("src/main/resources/character_upgrade.xlsx");
        FileInputStream expMaterialDataFile = new FileInputStream("src/main/resources/exp_credit_material.xlsx");
        Workbook characterWorkbook = new XSSFWorkbook(characterDataFile);
        Workbook expMaterialWorkbook = new XSSFWorkbook(expMaterialDataFile);
        characterUpgradeData = new int[81];
        expMaterialData = new int[6];
        expCreditMaterialData = new int[6];
        creditMaterialData = new int[6];
        ascensionData = new int[2][7];
        loadCharacterData(characterWorkbook);
        loadExpAndCreditMaterialData(expMaterialWorkbook);
        characterDataFile.close();
        expMaterialDataFile.close();
    }

    public void loadCharacterData(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            Cell valueCell = row.getCell(2);
            if (keyCell != null && valueCell != null) {
                int key = (int) keyCell.getNumericCellValue();
                int value = (int) valueCell.getNumericCellValue();
                characterUpgradeData[key] = value;
            }
        }
        Sheet sheet1 = workbook.getSheetAt(1);
        int i = 1;
        for (Row row : sheet1) {
            Cell star4 = row.getCell(0);
            Cell star5 = row.getCell(1);
            if (star4 != null && star5 != null) {
                ascensionData[0][i] = (int) star4.getNumericCellValue();
                ascensionData[1][i] = (int) star5.getNumericCellValue();
                i++;
            }
        }
    }

    public void loadExpAndCreditMaterialData(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            Cell valueCell = row.getCell(1);
            Cell expCredit = row.getCell(2);
            Cell credit = row.getCell(3);
            if (keyCell != null && valueCell != null) {
                int key = (int) keyCell.getNumericCellValue();
                int value = (int) valueCell.getNumericCellValue();
                int expCreditValue = (int) expCredit.getNumericCellValue();
                int creditValue = (int) credit.getNumericCellValue();
                expMaterialData[key] = value;
                expCreditMaterialData[key] = expCreditValue;
                creditMaterialData[key] = creditValue;
            }
        }
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
        return exp - level3 * LEVEL3_EXP - level2 * LEVEL2_EXP - level1 * LEVEL1_EXP;
    }

    public String calculateTimesInString(int exp, int worldLevel) {
        if (worldLevel <= 0) {
            worldLevel = 1;
        }
        if (worldLevel >= 4) {
            worldLevel = 4;
        }
        int res = exp % expMaterialData[worldLevel + 1] == 0 ? exp / expMaterialData[worldLevel + 1] : exp / expMaterialData[worldLevel + 1] + 1;
        return "You need to complete " + res + " times exp material stage in level" + (worldLevel+1) + ", which costs " + res * 10 + " Trailblaze Power" + System.lineSeparator();
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
        int index = Math.min(worldLevel, 4) + 1;
        double res = (double) credits / creditMaterialData[index];
        return (int) Math.ceil(res);
    }

    public String calculateCreditTimesInString(int exp, int currLevel, int targetLevel) {
        return "You need to complete " + calculateCreditTimes(exp, currLevel, targetLevel) + " times credit stage in level" + (worldLevel+1) + ", which costs " + calculateCreditTimes(exp, currLevel, targetLevel) * 10 + " Trailblaze Power"+System.lineSeparator();
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
