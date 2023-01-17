package projecteloteria;

import java.util.Random;
import java.util.Scanner;

public class ProjecteLoteria {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        final int PREMI1= 4000000;
        final int PREMI2= 1250000;
        final int PREMI3= 500000;
        final int PREMI4= 200000;
        final int PREMI5= 60000;
        final int PEDRE= 1000;
        
        
        int premiAconseguit= 0;
        int numLoteria = validarEntero("Introdueix el teu número de la loteria: ");
        int premi1 = premiGordo();
        int premi2 = segonPremi();
        int premi3 = tercerPremi();
        
        /*final int DOSPREMIS = 2;
        int premi4[] = new int [DOSPREMIS];
        
        int premi5;
        
        final int PEDREA = 1794; 
        int pedrea[] = new int[PEDREA];*/
        
        if (numLoteria == premi1) {
            premiAconseguit += PREMI1;
        }
        else if (numLoteria == premi2){
            premiAconseguit += PREMI2;
        }
        else if (numLoteria == premi3){
            premiAconseguit += PREMI3;
        }
       
        
        System.out.println("El número introduit " + numLoteria + " té el següent premi: " + premiAconseguit);
        System.out.println("Primer Premi:" + premi1);
        System.out.println("Segon Premi: "+ premi2);
        System.out.println("Tercer Premi: " + premi3);
        System.out.println("Quart Premi: ");
        System.out.println("Cinque Premi: ");
    }

    /*public static int LlargadaNum (int num){
        num = scan.nextInt();
        num = String.valueOf(num).length();
        while (num != 5){
           num = scan.nextInt();
        }
        return num;
    }*/
    static int validarEntero(String mensaje) {
        Scanner scan = new Scanner(System.in);
        int valor;
        System.out.println(mensaje);
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("El boleto es un número. Intenta-ho de nou");
        }
        valor = scan.nextInt();
        return valor;
    }

    static int premiGordo() {
        Random rnd = new Random();
        int aleatori = rnd.nextInt(00001 + 99999);
        return aleatori;
    }

    static int segonPremi() {
        Random rnd = new Random();
        int aleatori = rnd.nextInt(00001 + 99999);
        return aleatori;
    }

    static int tercerPremi() {
        Random rnd = new Random();
        int aleatori = rnd.nextInt(00001 + 99999);
        return aleatori;
    }
    
    public static int Pedrea(int pedrea[]) {
        for (int i = 0; i < pedrea.length; i++) {

            pedrea[i] = (int) (Math.random() * 99999 + 1);
        }
        return 0;
    }
}
