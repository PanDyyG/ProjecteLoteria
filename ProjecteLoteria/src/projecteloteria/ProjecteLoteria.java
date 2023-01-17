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
        //int numLoteria = validarEntero("Introduce tu número de la loteria: ");
        int premi1 = premiGordo();
        int premi2 = segonPremi();
        int premi3 = tercerPremi();
        System.out.println(premi1);
        System.out.println(premi2);
        System.out.println(premi3);
    }
    /*public static int LlargadaNum (int num){
        num = scan.nextInt();
        num = String.valueOf(num).length();
        while (num != 5){
           num = scan.nextInt();
        }
        return num;
    }*/
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
    
    static int premiGordo (){
        Random rnd=new Random();
        int aleatori=rnd.nextInt(00001 + 99999);
        return aleatori;
    }
    static int segonPremi (){
        Random rnd=new Random();
        int aleatori=rnd.nextInt(00001 + 99999);
        return aleatori;
    }
    static int tercerPremi (){
        Random rnd=new Random();
        int aleatori=rnd.nextInt(00001 + 99999);
        return aleatori;
    }
}
