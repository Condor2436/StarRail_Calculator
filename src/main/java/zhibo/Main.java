package zhibo;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ExpCalculator calculator = new ExpCalculator();
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;
        int currentLevel = 0;
        int targetLevel = 0;
        int level1ExpMaterial = 0;
        int level2ExpMaterial = 0;
        int level3ExpMaterial = 0;
        int worldLevel = 0;
        System.out.println("Please input your current level:");
        String nextLine = null;
        while(!isValidInput){
            nextLine = scanner.nextLine();
            try{
                int temp = Integer.parseInt(nextLine);
                isValidInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid input, please input again:");
                System.out.println("Please input your current level:");
                continue;
            }
            currentLevel = Integer.parseInt(nextLine);
            if(currentLevel < 1 || currentLevel > 80) {
                System.out.println("Invalid level, please input again:");
                System.out.println("Remember that your current level should be no less than 1 and no more than 80:");
                isValidInput = false;
            }
        }
        isValidInput = false;
        System.out.println("Please input your target level:");
        nextLine = null;
        while(!isValidInput){
            nextLine = scanner.nextLine();
            try{
                int temp = Integer.parseInt(nextLine);
                isValidInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid input, please input again:");
                System.out.println("Please input your current level:");
                continue;
            }
            targetLevel = Integer.parseInt(nextLine);
            if(targetLevel < currentLevel || targetLevel > 80) {
                System.out.println("Invalid level, please input again:");
                System.out.println("Remember that your target level should be no less than your current level and no more than 80:");
                isValidInput = false;
            }
        }
        isValidInput = false;
        System.out.println("Please input number of EXP material you have:");
        System.out.println("Level 1 EXP material:");
        nextLine = null;
        while(!isValidInput){
            nextLine = scanner.nextLine();
            try{
                int temp = Integer.parseInt(nextLine);
                isValidInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid input, please input again:");
                System.out.println("Please input your current level:");
                continue;
            }
            level1ExpMaterial = Integer.parseInt(nextLine);
            if(level1ExpMaterial < 0) {
                System.out.println("Invalid number, please input again:");
                System.out.println("Remember that the number of EXP material you have should be no less than 0:");
                isValidInput = false;
            }
        }
        isValidInput = false;
        System.out.println("Level 2 EXP material:");
        nextLine = null;
        while(!isValidInput){
            nextLine = scanner.nextLine();
            try{
                int temp = Integer.parseInt(nextLine);
                isValidInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid input, please input again:");
                System.out.println("Please input your current level:");
                continue;
            }
            level2ExpMaterial = Integer.parseInt(nextLine);
            if(level2ExpMaterial < 0) {
                System.out.println("Invalid number, please input again:");
                System.out.println("Remember that the number of EXP material you have should be no less than 0:");
                isValidInput = false;
            }
        }
        isValidInput = false;
        System.out.println("Level 3 EXP material:");
        nextLine = null;
        while(!isValidInput){
            nextLine = scanner.nextLine();
            try{
                int temp = Integer.parseInt(nextLine);
                isValidInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid input, please input again:");
                System.out.println("Please input your current level:");
                continue;
            }
            level3ExpMaterial = Integer.parseInt(nextLine);
            if(level3ExpMaterial < 0) {
                System.out.println("Invalid number, please input again:");
                System.out.println("Remember that the number of EXP material you have should be no less than 0:");
                isValidInput = false;
            }
        }
        isValidInput = false;
        System.out.println("Please input the number of world level you have in:");
        nextLine = null;
        while(!isValidInput){
            nextLine = scanner.nextLine();
            try{
                int temp = Integer.parseInt(nextLine);
                isValidInput = true;
            } catch (NumberFormatException e){
                System.out.println("Invalid input, please input again:");
                System.out.println("Please input your current level:");
                continue;
            }
            worldLevel = Integer.parseInt(nextLine);
            if(worldLevel < 0 || worldLevel > 6) {
                System.out.println("Invalid world level, please input again:");
                System.out.println("Remember that your world level should be no less than 0 and no more than 6:");
                isValidInput = false;
            }
        }
        System.out.println(calculator.calculate(currentLevel, targetLevel, level1ExpMaterial, level2ExpMaterial, level3ExpMaterial, worldLevel));
    }
}
