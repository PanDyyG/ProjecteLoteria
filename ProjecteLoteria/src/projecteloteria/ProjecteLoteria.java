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

    public static void main(String[] args) {
        int numLoteria = validarEntero("Introduce tu número de la loteria: ");
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
    static int validarEntero(String mensaje){
        Scanner scan = new Scanner(System.in);
        int valor;
        System.out.println(mensaje);
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("El bolete es un número. Intentalo de nuevo");
        }
        valor = scan.nextInt();
        return valor;
    }
    
}
