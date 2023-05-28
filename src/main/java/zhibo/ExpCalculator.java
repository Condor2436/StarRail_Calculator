package zhibo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ExpCalculator {
    private static final int LEVEL3_EXP = 20000;
    private static final int LEVEL2_EXP = 5000;
    private static final int LEVEL1_EXP = 1000;

    private final int[] characterUpgradeData;
    private final int[] expMaterialData;
    private int cumulativeEXP = 0;
    private int existLevel3 = 0;
    private int existLevel2 = 0;
    private int existLevel1 = 0;
    private int worldLevel = 1;

    public ExpCalculator() throws IOException {
        FileInputStream characterDataFile = new FileInputStream("src/main/resources/character_upgrade.xlsx");
        FileInputStream expMaterialDataFile = new FileInputStream("src/main/resources/exp_material.xlsx");
        Workbook characterWorkbook = new XSSFWorkbook(characterDataFile);
        Workbook expMaterialWorkbook = new XSSFWorkbook(expMaterialDataFile);
        characterUpgradeData = new int[81];
        expMaterialData = new int[6];
        loadCharacterData(characterWorkbook);
        loadExpMaterialData(expMaterialWorkbook);
        characterDataFile.close();
        expMaterialDataFile.close();
    }

    public ExpCalculator(int cumulativeEXP, int existLevel3, int existLevel2, int existLevel1, int worldLevel) throws IOException {
        this.cumulativeEXP = cumulativeEXP;
        this.existLevel3 = existLevel3;
        this.existLevel2 = existLevel2;
        this.existLevel1 = existLevel1;
        this.worldLevel = worldLevel;
        FileInputStream characterDataFile = new FileInputStream("src/main/resources/character_upgrade.xlsx");
        FileInputStream expMaterialDataFile = new FileInputStream("src/main/resources/exp_material.xlsx");
        Workbook characterWorkbook = new XSSFWorkbook(characterDataFile);
        Workbook expMaterialWorkbook = new XSSFWorkbook(expMaterialDataFile);
        characterUpgradeData = new int[81];
        expMaterialData = new int[6];
        loadCharacterData(characterWorkbook);
        loadExpMaterialData(expMaterialWorkbook);
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
    }

    public void loadExpMaterialData(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            Cell valueCell = row.getCell(1);
            if (keyCell != null && valueCell != null) {
                int key = (int) keyCell.getNumericCellValue();
                int value = (int) valueCell.getNumericCellValue();
                expMaterialData[key] = value;
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

    public String calculateTimes(int exp, int worldLevel) {
        if (worldLevel <= 0) {
            worldLevel = 1;
        }
        if (worldLevel >= 4) {
            worldLevel = 4;
        }
        int res = exp % expMaterialData[worldLevel] == 0 ? exp / expMaterialData[worldLevel] : exp / expMaterialData[worldLevel] + 1;
        return "You need to complete " + res + " times in level" + worldLevel + ", which is " + res * 10 + " Trailblaze Power";
    }

    public String calculate(int currLevel, int targetLevel, int existLevel3, int existLevel2, int existLevel1, int worldLevel) {
        StringBuilder sb = new StringBuilder();
        cumulativeEXP += calculateCharacterExp(currLevel, targetLevel);
        this.existLevel1 = existLevel1;
        this.existLevel2 = existLevel2;
        this.existLevel3 = existLevel3;
        this.worldLevel = worldLevel;
        sb.append("Total exp you need is ").append(cumulativeEXP).append(System.lineSeparator());
        int exp = reduceExistExpMaterial(cumulativeEXP, existLevel3, existLevel2, existLevel1);
        sb.append("Based on your existing exp material, you need to collect ").append(exp).append(" exp").append(System.lineSeparator());
        return sb.append(convertExpToMaterial(exp) + calculateTimes(exp, worldLevel)).toString();
    }

    public String calculate(int currLevel, int targetLevel) {
        StringBuilder sb = new StringBuilder();
        cumulativeEXP += calculateCharacterExp(currLevel, targetLevel);
        sb.append("Total exp you need is ").append(cumulativeEXP).append(System.lineSeparator());
        int exp = reduceExistExpMaterial(cumulativeEXP, existLevel3, existLevel2, existLevel1);
        sb.append("Based on your existing exp material, you need to collect ").append(exp).append(" exp").append(System.lineSeparator());
        return sb.append(convertExpToMaterial(exp) + calculateTimes(exp, worldLevel)).toString();
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
        int res = exp % expMaterialData[worldLevel] == 0 ? exp / expMaterialData[worldLevel] : exp / expMaterialData[worldLevel] + 1;
        return res;
    }

    public void reset() {
        cumulativeEXP = 0;
        existLevel1 = 0;
        existLevel2 = 0;
        existLevel3 = 0;
        worldLevel = 1;
    }
}
