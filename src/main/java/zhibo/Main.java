package zhibo;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ExpCalculator calculator = new ExpCalculator();
        Scanner scanner = new Scanner(System.in);
        int status = 0;// 0 is restart, 1 is cumulative, 2 is quit
        while(true){
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
            if(status==0){
                calculator.reset();
                System.out.println("Please input number of EXP material you have:");
                System.out.println("Level 1 EXP material:");
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
            }
            isValidInput = false;
            if(status == 0){
                System.out.println(calculator.calculate(currentLevel, targetLevel, level1ExpMaterial, level2ExpMaterial, level3ExpMaterial, worldLevel));
            } else {
                System.out.println(calculator.calculate(currentLevel, targetLevel));
            }
            System.out.println("Do you want to start your next calculator or continue with the current one? (0 for reset, 1 for cumulative, 2 for quit)");
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
                status = Integer.parseInt(nextLine);
                if(status != 0 && status != 1 && status != 2) {
                    System.out.println("Invalid status, please input again:");
                    System.out.println("Remember that your status should be 0 or 1 or 2:");
                    isValidInput = false;
                }
            }
            if(status == 2){
                System.out.println("Thank you for using this calculator!");
                break;
            }
        }

    }
}
