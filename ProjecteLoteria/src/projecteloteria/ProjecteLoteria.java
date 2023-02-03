package projecteloteria;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ProjecteLoteria {

    public static final int TOTALPREMIS = 1807;

    //Declaració scanner
    public static Scanner scan = new Scanner(System.in);

    //MAIN
    public static void main(String[] args) {

        //Dec array numerosPremiats
        int[] NumerosPremiados = new int[TOTALPREMIS];

        genRandNum(NumerosPremiados);

        for (int i = 0; i < NumerosPremiados.length; i++) {
            System.out.println(NumerosPremiados[i]);
        }

        //MAIN MENU
        boolean exit = false;
        while (!exit) {
            System.out.println("-----------Menú d'opcions-----------");
            System.out.println("1. Consultar un número");
            System.out.println("2. Consultar els números premiats");
            System.out.println("3. Sortir del menú");
            int opciones = scan.nextInt();
            switch (opciones) {
                //CONSULTAR NUMERO
                case 1: {

                    //Introducció de numero de Boleto
                    int BoletoInput = validarEntero();

                    //retorna la posicio on es troba el boleto introdiut en cas de que tingui un premi directe
                    int posicio = validarNumero(NumerosPremiados, BoletoInput);

                    //en cas de coincidir el ultim digit del boleto amb el ultim digit del primer premi
                    //retorna valor de premi
                    int reintegro = reintegro(BoletoInput, NumerosPremiados);

                    //Comprovació si el numero Input es anterior o posterior a alguns dels 3 primers premis
                    //retorna valor de premi
                    int numAntIpost = numAntIpost(BoletoInput, NumerosPremiados);

                    //Comprovació si el numero Input coindiceix amb els ultims 2 digits de algun dels 3 primers premis
                    //Retorna valor de premi
                    int ultimasDosCifras = ultimasDosCifras(BoletoInput, NumerosPremiados);

                    //Comprovació si el numero Input coindiceix amb els ultims 3 digits d'algun dels 4 primers premis
                    //Retorna valor de premi
                    int centenas = centenas(BoletoInput, NumerosPremiados);

                    //guarda quins premis'han rebut
                    int[] ArrayPremiAdicional = arrayAproximacions(reintegro, numAntIpost, ultimasDosCifras, centenas);

                    //retorna la suma de els premis adicionals aun array
                    int premiAddicional = premiAdicional(ArrayPremiAdicional);

                    //Retorna valor de premi en euros
                    int premiAconseguit = premi(posicio);

                    if (premiAconseguit >= 0) {

                        ///Menu de premi de bolet
                        if (posicio >= 0) {
                            System.out.println("Enhorabona, el bolet està premiat!!");
                            System.out.println("Vols saber el seu premi?");
                            System.out.println("1. Si");
                            System.out.println("2. No");
                            int premi = scan.nextInt();

                            boolean sortir = false;
                            while (!sortir) {
                                switch (premi) {
                                    case 1: {
                                        textGuanyador(premiAconseguit, BoletoInput, premiAddicional, ArrayPremiAdicional);
                                        sortir = true;
                                         break;
                                    }
                                    case 2:
                                        sortir = true;
                                         break;
                                }
                            }
         

                        } else {
                            //Cas de rebre premis adicionals i no rebre cap premi gran ni pedrea
                            System.out.println("El teu bolet no està premiat");
                            if (premiAddicional > 0 && posicio == -1) {
                                System.out.println("Però tens una part premiada: " + premiAddicional);
                                printProvenenciaPremisAdicionals(ArrayPremiAdicional);

                            }
                        }
                          break;

                    }
                   
                }
             
                case 2: {
                    //Print de numeros Premiats
                    for (int i = 0; i < NumerosPremiados.length; i++) {
                        String LeftZeroCorr = String.format("%05d", NumerosPremiados[i]);

                        System.out.println("Numero de boleto: " + LeftZeroCorr + " Premi: " + premi(i) + "€");

                    }
                }
                break;

                case 3: {
                }
            }
            int menuSortida;
            System.out.println();
            System.out.println("Vols rebre mes informacio sobre el sorteig de nadal?");
            System.out.println("1.Si");
            System.out.println("2.No");
            menuSortida = scan.nextInt();
            if (menuSortida == 2) {
                exit = true;
                break;
            }
        }
    }

    /**
     * @return valor
     */
    static int validarEntero() {
        Scanner scan = new Scanner(System.in);
        int valor;
        System.out.println("Introdueix el teu número de la loteria: ");
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("El boleto es un número. Intenta-ho de nou");
        }
        valor = scan.nextInt();
        return valor;
    }

    public static void genRandNum(int[] array) {
        int numeros;
        Random rnd = new Random();
        for (int i = 0; i < array.length;) {
            //Generador de numeros
            numeros = rnd.nextInt(00001 + 99999);

            //Comprova repeticions
            if (comprovaRep(array, numeros, i)) {
                array[i] = numeros;
                i++;
            }

        }

    }

    static boolean comprovaRep(int[] array, int comprovaRep, int pos) {
        boolean resultat = false;
        for (int i = 0; i <= pos; i++) {
            if (Arrays.stream(array).noneMatch(p -> p == comprovaRep)) {
                resultat = true;
            }
        }
        return resultat;
    }

    static int validarNumero(int[] NumeroPremiados, int BoletoInput) {
        for (int i = 0; i < NumeroPremiados.length; i++) {
            if (NumeroPremiados[i] == BoletoInput) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param posicion, la posicio correspon al premi
     * @return Retorna el premi corresponent
     */
    static int premi(int posicion) {
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
     * @param premi, es el valor del premi
     * @param BoletoInput, es el numero introduit per teclat a consultar
     */
    //static String TextGuanyador ( int premi, int BoletoInput){
    static void textGuanyador(int premi, int BoletoInput, int PremiAdicional, int[] ArrayPremiAdicional) {

        System.out.println();
        System.out.print("Felicitats! Has guanyat ");

        if (premi == 4000000) {
            System.out.print("El primer premi ");
        } else if (premi == 1250000) {
            System.out.print("El segon premi ");
        } else if (premi == 500000) {
            System.out.print("El tercer premi ");
        } else if (premi == 200000) {
            System.out.print("El quart premi ");
        } else if (premi == 60000) {
            System.out.print("El cinque premi ");
        } else if (premi == 1000) {
            System.out.print("La pedrea ");
        }
        System.out.print(", un total de: " + premi + "€ amb el decim: " + BoletoInput);
        if (PremiAdicional > 0 && premi == 0) {
            System.out.println(" i un premi adicional de : " + PremiAdicional + "€");
            printProvenenciaPremisAdicionals(ArrayPremiAdicional);
        }
    }

    /**
     * @return numeros
     */
    static int premiAdicional(int[] TotalPremisAdicionals) {
        int premi = 0;
        for (int totalPremisAdicional : TotalPremisAdicionals) {
            if (totalPremisAdicional > premi) {
                premi = totalPremisAdicional;
            }
        }

        return premi;
    }
    // }

    /**
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el valor del reintegro
     */
    static int reintegro(int numeroConsultar, int[] NumerosPremiados) {
        int reintegro = 0;
        if (NumerosPremiados[0] % 10 == numeroConsultar % 10) {
            reintegro = 200;
        }
        return reintegro;
    }

    /*
     * @param numeroConsultar,  es el numero introduit per teclat  a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el premi  corresponent al numero anterior  i posterior
     */
    /**
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el premi corresponent al numero anterior i posterior
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
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el premi corresponent a les 2 ultimes xifres
     */
    static int ultimasDosCifras(int numeroConsultar, int[] NumerosPremiados) {
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
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna premi corresponent a les centenas
     */
    static int centenas(int numeroConsultar, int[] NumerosPremiados) {
        int PremisAddicionals = 0;

        if (NumerosPremiados[0] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 1000;
        } else if (NumerosPremiados[1] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 1000;
        } else if (NumerosPremiados[2] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 1000;
        } else if (NumerosPremiados[3] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 1000;
        } else if (NumerosPremiados[4] / 100 == numeroConsultar / 100) {
            PremisAddicionals = 1000;
        }
        return PremisAddicionals;
    }
    

    static int[] arrayAproximacions(int Reintegro, int NumAntIpost, int UltimasDosCifras, int Centenas) {
        int[] numeros = new int[4];

        for (int i = 0; i < numeros.length; i++) {
            if (i == 0) {
                numeros[i] = Reintegro;
            }
            if (i == 1) {
                numeros[i] = NumAntIpost;
            }
            if (i == 2) {
                numeros[i] = Centenas;
            }
            if (i == 3) {
                numeros[i] = UltimasDosCifras;
            }

        }
        return numeros;
    }

    public static void printProvenenciaPremisAdicionals(int[] ArrayPremisADD) {

        System.out.println();

        if (ArrayPremisADD[0] > 0) {
            System.out.println("Has rebut " + ArrayPremisADD[0] + "€ de el Reintegro");
        }
        if (ArrayPremisADD[1] > 0) {
            System.out.print("Has rebut " + ArrayPremisADD[1] + "€ de el Numero anterior o posterior al ");

            if (ArrayPremisADD[1] == 20000) {
                System.out.println("primer premi.");
            } else if (ArrayPremisADD[1] == 12500) {
                System.out.println("segon premi.");
            } else if (ArrayPremisADD[1] == 9600) {
                System.out.println("tercer premi.");
            }
        }
        if (ArrayPremisADD[2] > 0) {
            System.out.println("Has rebut " + ArrayPremisADD[2] + "€ de el Centena de un dels primers 4 premis");
        }
        if (ArrayPremisADD[3] > 0) {
            System.out.println("Has rebut " + ArrayPremisADD[3] + "€ de el Ultimes dues xifres d'un dels primers 3 premis");
        }

    }

}   