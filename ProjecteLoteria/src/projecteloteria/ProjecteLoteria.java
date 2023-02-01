package projecteloteria;

import java.util.Random;
import java.util.Scanner;

public class ProjecteLoteria {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int[] NumerosPremiados = NumeroLoteria();

        //for (int i = 0; i < NumerosPremiados.length; i++) {
        //    System.out.println(NumerosPremiados[i]);
        //}

        boolean exit = false;
        while (!exit) {
            System.out.println("-----------Menú d'opcions-----------");
            System.out.println("1. Consultar un número");
            System.out.println("2. Consultar els números premiats");
            System.out.println("3. Sortir del menú");
            int opciones = scan.nextInt();
            switch (opciones) {
                case 1:
                    int NumeroConsultar = validarEntero("Introdueix el teu número de la loteria: ");
                    int posicion = ValidarNumero(NumerosPremiados, NumeroConsultar);

                    int reintegro = Reintegro(NumeroConsultar, NumerosPremiados, posicion);
                    int premiAconseguit = Premi(posicion);
                    String Text = TextGuanyador(premiAconseguit, NumeroConsultar);
                    int[] ArrayPremiAdicional = PremisAdicionals(reintegro);
                    if (premiAconseguit >= 0) {
                        System.out.println("Enhorabona, el bolet està premiat!!");
                        System.out.println("Vols saber el seu premi?");
                        System.out.println("1. Si");
                        System.out.println("2. No");
                        int premi = scan.nextInt();
                        boolean sortir = false;
                        while (!sortir) {
                            switch (premi) {
                                case 1:
                                    if (posicion >= 0) {
                                        System.out.println(Text);
                                    } else {
                                        System.out.println("Premi adicional: " + premiAconseguit);
                                    }
                                    sortir = true;
                                    break;
                                case 2:
                                    sortir = true;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("El teu bolet no està premiat");
                    }
                    break;
                case 2:

                    break;
                case 3:
                    exit = true;
                    break;
            }
        }
    }

    /**
     *
     * @param mensaje
     * @return
     */
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

    static int[] NumeroLoteria() {
        int numeros[] = new int[1807];

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = GenerarNumero();
        }
        return numeros;
    }

    static int GenerarNumero() {
        Random rnd = new Random();
        int aleatori = rnd.nextInt(00001 + 99999);
        return aleatori;
    }

    static int ValidarNumero(int[] NumeroPremiados, int NumeroConsultar) {
        for (int i = 0; i < NumeroPremiados.length; i++) {
            if (NumeroPremiados[i] == NumeroConsultar) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param posicion, la posicio correspon al premi
     * @return Retorna el premi corresponent
     */
    static int Premi(int posicion) {
        int premi = 0;

        if (posicion == 0) {
            premi = 4000000;
        } else if (posicion == 1) {
            premi = 1250000;
        } else if (posicion == 2) {
            premi = 500000;
        } else if (posicion >= 3 && posicion <= 5) {
            premi = 200000;
        } else if (posicion >= 6 && posicion <= 12) {
            premi = 60000;
        } else if (posicion > 12) {
            premi = 1000;
        }
        return premi;
    }

    /**
     *
     * @param premi, es el valor del premi
     * @param NumeroConsultar, es el numero introduit per teclat  a consultar
     * @return
     */
    static String TextGuanyador(int premi, int NumeroConsultar) {

        String textGuanyador = "";
        if (premi == 4000000) {
            textGuanyador = "Felicitats! Has guanyat primer premi, un total de: "
                    + premi + " amb el decim: " + NumeroConsultar;
        } else if (premi == 1250000) {
            textGuanyador = "Felicitats! Has guanyat el segon premi, un total de: " + premi + " amb el decim: " + NumeroConsultar;
        } else if (premi == 500000) {
            textGuanyador = "Felicitats! Has guanyat el tercer premi, un total de: " + premi + " amb el decim: " + NumeroConsultar;
        } else if (premi == 200000) {
            textGuanyador = "Felicitats! Has guanyat el quart quart, un total de: " + premi + " amb el decim: " + NumeroConsultar;
        } else if (premi == 60000) {
            textGuanyador = "Felicitats! Has guanyat el cinque premi, un total de: " + premi + " amb el decim: " + NumeroConsultar;
        } else if (premi == 1000) {
            textGuanyador = "Felicitats! Has guanyat la pedrea, un total de: " + premi + " amb el decim: " + NumeroConsultar;
        }
        return textGuanyador;
    }
/**
 * 
 * @param Reintegro
 * @return 
 */
    static int[] PremisAdicionals(int Reintegro/*int antIPost, int */) {
        int numeros[] = new int[5];

        for (int i = 0; i < numeros.length; i++) {
            if (i == 0) {
                numeros[i] = Reintegro;
            } else if (i == 1) {

            } else if (i == 2) {

            } else if (i == 3) {

            } else if (i == 4) {

            }

        }
        return numeros;
    }
/**
 * 
 * @param numeroConsultar, es el numero introduit per teclat  a consultar
 * @param NumerosPremiados, es el numero premiat que surt del sorteig
 * @param posicion, la posicio correspon al premi
 * @return  retorna el valor del reintegro
 */
    
    static int Reintegro(int numeroConsultar, int[] NumerosPremiados, int posicion) {
        int reintegro = 0;
        if (NumerosPremiados[0] % 10 == numeroConsultar % 10) {
            reintegro = 20;
        }
        return reintegro;
    }
/**
 * 
 * @param numeroConsultar, es el numero introduit per teclat  a consultar
 * @param NumerosPremiados, es el numero premiat que surt del sorteig
 * @return retorna el premi  corresponent al numero anterior  i posterior
 */
  
    static int numAntIpost(int numeroConsultar, int[] NumerosPremiados) {
        int PremisAddicionals = 0;

        if (NumerosPremiados[0] - 1 == numeroConsultar || NumerosPremiados[0] + 1 == numeroConsultar) {
            PremisAddicionals = 20000;
        } else if (NumerosPremiados[1] - 1 == numeroConsultar || NumerosPremiados[1] + 1 == numeroConsultar) {
            PremisAddicionals = 12500;
        } else if (NumerosPremiados[2] - 1 == numeroConsultar || NumerosPremiados[2] + 1 == numeroConsultar) {
            PremisAddicionals = 9600;
        }

        return PremisAddicionals;
    }

    /**
     * 
     * @param numeroConsultar, es el numero introduit per teclat  a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el premi corresponent a les 2 ultimes  xifres
     */
    static int UltimasDosCifras(int numeroConsultar, int[] NumerosPremiados) {
        int PremisAddicionals = 0;

        if (NumerosPremiados[0] % 100 == numeroConsultar % 100) {
            PremisAddicionals = 1000;
        } else if (NumerosPremiados[1] % 100 == numeroConsultar % 100) {
            PremisAddicionals = 1000;
        } else if (NumerosPremiados[2] % 100 == numeroConsultar % 100) {
            PremisAddicionals = 1000;
        }
        return PremisAddicionals;
    }

    /**
     *
     * @param numeroConsultar, es el numero introduit per teclat  a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna premi corresponent a les centenas
     */
    static int centenas(int numeroConsultar, int[] NumerosPremiados) {
        int PremisAddicionals = 0;

        if (NumerosPremiados[0] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 100;
        } else if (NumerosPremiados[1] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 100;
        } else if (NumerosPremiados[2] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 100;
        } else if (NumerosPremiados[3] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 100;
        } else if (NumerosPremiados[4] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 100;
        }
        return PremisAddicionals;
    }
}
