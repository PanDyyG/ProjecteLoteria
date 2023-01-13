/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projecteloteria;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author ausias
 */
public class ProjecteLoteria {
    public static Scanner scan = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("Introduce tu número de la loteria: ");
        int numLoteria = 0;
        int resultat = LlargadaNum(numLoteria);
    }
    public static int LlargadaNum (int num){
        num = scan.nextInt();
        num = String.valueOf(num).length();
        while (num != 5){
           num = scan.nextInt();
        }
        return num;
    }
    
}
