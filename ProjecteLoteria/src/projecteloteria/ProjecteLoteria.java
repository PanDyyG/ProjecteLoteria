package projecteloteria;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ProjecteLoteria {

    //Premis totals
    public static final int TOTALPREMIS = 1806;

    //Declaració scanner
    public static Scanner scan = new Scanner(System.in);

    //MAIN
    public static void main(String[] args) {

        //Dec array numerosPremiats
        int[] NumerosPremiados = new int[TOTALPREMIS];

        GenRandNum(NumerosPremiados);

        //MAIN MENU
        boolean exit = false;
        while (!exit) {
            System.out.println("-----------Menu d'opcions-----------");
            System.out.println("1. Consultar un numero");
            System.out.println("2. Consultar els numeros premiats");
            System.out.println("3. Sortir del menu");
            int opciones = scan.nextInt();
            switch (opciones) {
                //CONSULTAR NUMERO
                case 1: {

                    //Introducció de numero de Boleto
                    int BoletoInput = validarEntero();

                    //retorna la posicio on es troba el boleto introdiut en cas de que tingui un premi directe
                    int posicio = ValidarNumero(NumerosPremiados, BoletoInput);

                    //en cas de coincidir el últim digit del boleto amb el ultim digit del primer premi
                    int reintegro = Reintegro(BoletoInput, NumerosPremiados);

                    //Comprovació si el numero Input és anterior o posterior a alguns dels 3 primers premis
                    int numAntIpost = NumAntIpost(BoletoInput, NumerosPremiados);

                    //Comprovació si el numero Input coindiceix amb els ultims 2 digits d'algun dels 3 primers premis
                    int ultimasDosCifras = UltimasDosCifras(BoletoInput, NumerosPremiados);

                    //Comprovació si el numero Input coindiceix amb els ultims 3 digits d'algun dels 4 primers premis
                    int centenas = Centenas(BoletoInput, NumerosPremiados);

                    //guarda quins premis'han rebut
                    int[] ArrayPremiAdicional = arrayAproximacions(reintegro, numAntIpost, ultimasDosCifras, centenas);

                    //retorna la suma dels premis adicionals en un array
                    int premiAddicional = PremiAddicional(ArrayPremiAdicional);

                    //Retorna valor de premi en euros
                    int premiAconseguit = Premi(posicio);

                    if (premiAconseguit >= 0) {

                        ///Menu de premi de bolet
                        if (posicio >= 0) {
                            System.out.println("Enhorabona, el bolet esta premiat!!");
                            System.out.println("Vols saber el seu premi?");
                            System.out.println("1. Si");
                            System.out.println("2. No");
                            //Variable per elegir el cas del següent switch
                            int premi = scan.nextInt();
                            boolean sortir = false;
                            while (!sortir) {
                                switch (premi) {
                                    case 1: {
                                        //Mostra el text amb els premis corresponents
                                        TextGuanyador(premiAconseguit, BoletoInput, premiAddicional, ArrayPremiAdicional);
                                        sortir = true;

                                    }
                                    case 2:
                                        //Surt del switch 
                                        sortir = true;
                                }
                            }

                        } else {
                            //en cas de rebre premis adicionals i no rebre cap premi gran ni pedrea
                            System.out.print("El teu bolet no esta premiat");
                            //Si el premi és major a 0, pero la posició no correpon a cap premi de sèrie entra
                            if (premiAddicional > 0 && posicio == -1) {
                                System.out.println(", pero has rebut un premi addicional!!");
                                System.out.println("Vols saber el seu premi?");
                                System.out.println("1. Si");
                                System.out.println("2. No");
                                //Variable per elegir el cas del següent switch
                                int premiAdd = scan.nextInt();
                                boolean sortirAdd = false;
                                while (!sortirAdd) {
                                    switch (premiAdd) {
                                        case 1: {
                                            //Mostra tots els premis adicionals aconseguits
                                            System.out.println("Total premis en Aproximacions: " + premiAddicional + "€");
                                            imprimeixPremisAddicionals(ArrayPremiAdicional);
                                            sortirAdd = true;
                                        }
                                        case 2:
                                            //Surt del switch 
                                            sortirAdd = true;
                                    }
                                }

                            }
                        }
                        //Salt de línia
                        System.out.println();
                        break;

                    }

                }
                //Consulta tots els premis
                case 2: {
                    //Print de numeros Premiats

                    for (int i = 0; i < NumerosPremiados.length; i++) {

                        //if per limitar sortida de premis per pantalla
                          if (i == 13) {

                              
                            i = 1805;
                          }
                            
                        /*Amb aquesta linea situarem zeros a la esquerra del 
                        numero INT generat com a bolet per a que sigui del tamany desitjat, 
                        String.format "%05d" ens permetra agregar Zeros a la esquerra fins a un 
                        tamany de numero de 5 digis, en base decimal.*/
                        String CorrecionZero = String.format("%05d", NumerosPremiados[i]);
                        //Mostra els boletos premiats
                        System.out.println("Numero de boleto: " + CorrecionZero + " Premi: " + Premi(i) + "€");

                    }
                }

        
                break;

                case 3: {
                    int menuSortida;
                    System.out.println();
                    System.out.println("Vols rebre mes informacio sobre el sorteig de nadal?");
                    System.out.println("1.Si");
                    System.out.println("2.No");
                    menuSortida = scan.nextInt();
                    if (menuSortida == 2) {
                        exit = true;
                    }
                }
            }
        }
    }

    
    public static int validarEntero() {
        Scanner scan = new Scanner(System.in);
        int valor;
        System.out.println("Introdueix el teu numero de la loteria: ");
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.println("El boleto es un número. Intenta-ho de nou");
        }
        valor = scan.nextInt();
        return valor;
    }

    /**
     * Genera els números aleatoris
     *
     * @param array
     */
    public static void GenRandNum(int[] array) {
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

    /**
     * Comrpova que els numeros aleatoris premiats no es repeteixin
     *
     * @param array
     * @param comprovaRep
     * @param posicio
     * @return Retorna si es repeteix, o si no es repeteix
     */
    public static boolean comprovaRep(int[] array, int comprovaRep, int posicio) {
        boolean resultat = false;
        for (int i = 0; i <= posicio; i++) {
            if (Arrays.stream(array).noneMatch(p -> p == comprovaRep)) {
                resultat = true;
            }
        }
        return resultat;
    }

    /**
     *
     * @param NumerosPremiats del total dels premis per serie
     * @param BoletoInput, valor que hem introdüit per teclat
     * @return Retorna la posició del premi en el que ha coincidit, en cas
     * contrari retorna -1
     */
    public static int ValidarNumero(int[] NumerosPremiats, int BoletoInput) {
        for (int i = 0; i < NumerosPremiats.length; i++) {
            if (NumerosPremiats[i] == BoletoInput) {
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
    public static int Premi(int posicion) {
        int premi = 0;
        if (posicion == 0) {
            premi = 4000000;
        } else if (posicion == 1) {
            premi = 1250000;
        } else if (posicion == 2) {
            premi = 500000;
        } else if (posicion >= 3 && posicion <= 4) {
            premi = 200000;
        } else if (posicion > 4 && posicion <= 12) {
            premi = 60000;
        } else if (posicion > 12) {
            premi = 1000;
        }
        return premi;
    }

    /**
     * @param premi, és el valor del premi
     * @param BoletoInput, es el numero introduit per teclat a consultar Treu
     * per pantalla una informació completa dels premis que hagis guanyat
     */
    public static void TextGuanyador(int premi, int BoletoInput, int PremiAdicional, int[] ArrayPremiAdicional) {

        System.out.println();
        System.out.print("Felicitats! T'ha tocat");

        int premiDecim = 0;

        if (premi == 4000000) {
            premiDecim = 328000;
            System.out.print(" el primer premi");
        } else if (premi == 1250000) {
            premiDecim = 108000;
            System.out.print(" el segon premi");
        } else if (premi == 500000) {
            premiDecim = 48000;
            System.out.print(" el tercer premi");
        } else if (premi == 200000) {
            premiDecim = 20000;
            System.out.print(" el quart premi");
        } else if (premi == 60000) {
            premiDecim = 6000;
            System.out.print(" el cinque premi");
        } else if (premi == 1000) {
            premiDecim = 100;
            System.out.print(" la pedrea ");
        }
        System.out.print(", El numero " + BoletoInput + " ha sigut premiat amb un total de: " + premi + " per serie i has guanyat " + premiDecim);
        if (PremiAdicional > 0 && premi == 0) {
            System.out.println(" i un premi addicional de : " + PremiAdicional + "€");
            imprimeixPremisAddicionals(ArrayPremiAdicional);
        }
    }

    /**
     * @return Retorna la suma del premis d'aproximació que tinguis en el teu
     * bolet en cas de no tindre premi
     */
    public static int PremiAddicional(int[] TotalPremisAdicionals) {
        int premi = 0;
        for (int totalPremisAdicional : TotalPremisAdicionals) {
            if (totalPremisAdicional > premi) {
                premi += totalPremisAdicional;
            }
        }

        return premi;
    }
    // }

    /**
     * @param numeroConsultar, És el número introduit per teclat a consultar
     * @param NumerosPremiados, És el número premiat que surt del sorteig
     * @return Retorna el valor del reintegro
     */
    public static int Reintegro(int numeroConsultar, int[] NumerosPremiados) {
        int reintegro = 0;
        if (NumerosPremiados[0] % 10 == numeroConsultar % 10) {
            reintegro = 20;
        }
        return reintegro;
    }

    /**
     * @param numeroConsultar, És el número introduit per teclat a consultar
     * @param NumerosPremiados, És el número premiat que surt del sorteig
     * @return retorna el premi corresponent al numero anterior i posterior
     */
    public static int NumAntIpost(int numeroConsultar, int[] NumerosPremiados) {
        int PremisAddicionals = 0;

        if (NumerosPremiados[0] - 1 == numeroConsultar || NumerosPremiados[0] + 1 == numeroConsultar) {
            PremisAddicionals = 2000;
        } else if (NumerosPremiados[1] - 1 == numeroConsultar || NumerosPremiados[1] + 1 == numeroConsultar) {
            PremisAddicionals = 1250;
        } else if (NumerosPremiados[2] - 1 == numeroConsultar || NumerosPremiados[2] + 1 == numeroConsultar) {
            PremisAddicionals = 960;
        }
        return PremisAddicionals;
    }

    /**
     * @param numeroConsultar, És el número introduit per teclat a consultar
     * @param NumerosPremiados, És el número premiat que surt del sorteig
     * @return retorna el premi corresponent a les 2 últimes xifres
     */
    public static int UltimasDosCifras(int numeroConsultar, int[] NumerosPremiados) {
        int PremisAddicionals = 0;
        if (NumerosPremiados[0] % 100 == numeroConsultar % 100) {
            PremisAddicionals = 100;
        } else if (NumerosPremiados[1] % 100 == numeroConsultar % 100) {
            PremisAddicionals = 100;
        } else if (NumerosPremiados[2] % 100 == numeroConsultar % 100) {
            PremisAddicionals = 100;
        }
        return PremisAddicionals;
    }

    /**
     * @param numeroConsultar, És el número introduit per teclat a consultar
     * @param NumerosPremiados, És el número premiat que surt del sorteig
     * @return retorna premi corresponent a les centenas
     */
    public static int Centenas(int numeroConsultar, int[] NumerosPremiados) {
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

    /**
     * Pasem cada un dels premis d'aproximació que tenim guardats a les funcions
     * anteriors
     *
     * @param Reintegro
     * @param NumAntIpost
     * @param UltimasDosCifras
     * @param Centenas
     * @return Guardem en ordre els premis d'aproximació en un array
     */
    public static int[] arrayAproximacions(int Reintegro, int NumAntIpost, int UltimasDosCifras, int Centenas) {
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

    /**
     * Treu per pantalla els premis d'aproximació
     *
     * @param ArrayPremisADD, la funció on hem guardat tots els posibles premis
     * d'aproximació
     */
    public static void imprimeixPremisAddicionals(int[] ArrayPremisADD) {

        System.out.println();

        if (ArrayPremisADD[0] > 0) {
            System.out.println("Has rebut " + ArrayPremisADD[0] + "€ de el Reintegro");
        }
        if (ArrayPremisADD[1] > 0) {
            System.out.print("Has rebut " + ArrayPremisADD[1] + "€ de el Numero anterior o posterior al ");

            if (ArrayPremisADD[1] == 2000) {
                System.out.println("primer premi.");
            } else if (ArrayPremisADD[1] == 1250) {
                System.out.println("segon premi.");
            } else if (ArrayPremisADD[1] == 960) {
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
