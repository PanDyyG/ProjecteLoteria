package projecteloteria;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class ProjecteLoteria {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        int[] NumerosPremiados = NumeroLoteria();

        for (int i = 0; i < NumerosPremiados.length; i++) {
            System.out.println(NumerosPremiados[i]);
        }
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

                    int reintegro = Reintegro(NumeroConsultar, NumerosPremiados);
                    int numAntIpost = NumAntIpost(NumeroConsultar, NumerosPremiados);
                    int ultimasDosCifras = UltimasDosCifras(NumeroConsultar, NumerosPremiados);
                    int centenas = Centenas(NumeroConsultar, NumerosPremiados);

                    int[] ArrayPremiAdicional = TotalPremisAdicionals(reintegro, numAntIpost, ultimasDosCifras, centenas);
                    int premiAdicional = PremiAdicional(ArrayPremiAdicional);
                    int premiAconseguit = Premi(posicion);
                    int decimPremiat = premiAdicional;
                    String Text = TextGuanyador(premiAconseguit, NumeroConsultar, premiAdicional);

                    if (posicion >= 0) {
                        System.out.println("Enhorabona, el bolet està premiat!!");
                        System.out.println("Vols saber el seu premi?");
                        System.out.println("1. Si");
                        System.out.println("2. No");
                        int premi = scan.nextInt();
                        boolean sortir = false;
                        while (!sortir) {
                            switch (premi) {
                                case 1:
                                    System.out.println(Text);
                                    sortir = true;
                                    break;
                                case 2:
                                    sortir = true;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("El teu bolet no està premiat");
                        if (premiAconseguit > 0 && posicion == -1) {
                            System.out.println("Però tens una part premiada: " + premiAdicional);
                        }
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

    static boolean comprovaRep(int[] array, int comprovaRep, int pos) {
        boolean resultat = false;
        for (int i = 0; i <= pos; i++) {
            if (Arrays.stream(array).noneMatch(p -> p == comprovaRep)) {
                resultat = true;
            }
        }
        return resultat;
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

        final int PREMIO_1 = 4000000;
        final int PREMIO_2 = 1250000;
        final int PREMIO_3 = 500000;
        final int PREMIO_4 = 200000;
        final int PREMIO_5 = 60000;
        final int PEDREA = 1000;
        int premi = 0;

        if (posicion == 0) {
            premi = PREMIO_1;
        } else if (posicion == 1) {
            premi = PREMIO_2;
        } else if (posicion == 2) {
            premi = PREMIO_3;
        } else if (posicion >= 3 && posicion <= 5) {
            premi = PREMIO_4;
        } else if (posicion >= 6 && posicion <= 12) {
            premi = PREMIO_5;
        } else if (posicion > 12) {
            premi = PEDREA;
        }
        return premi;
    }

    /**
     *
     * @param premi, es el valor del premi
     * @param NumeroConsultar, es el numero introduit per teclat a consultar
     * @return
     */
    static int PremiAdicional(int[] TotalPremisAdicionals) {
        int premi = 0;
        for (int totalPremisAdicional : TotalPremisAdicionals) {
            if (totalPremisAdicional > premi) {
                premi = totalPremisAdicional;
            }
        }

        return premi;
    }
static void TextGuanyador(int premi, int BoletoInput, int PremiAdicional, int[] ArrayPremiAdicional) {
        final int PREMIO_1 = 4000000;
        final int PREMIO_2 = 1250000;
        final int PREMIO_3 = 500000;
        final int PREMIO_4 = 200000;
        final int PREMIO_5 = 60000;
        final int PEDREA = 1000;
        
        System.out.println();
        System.out.print("Felicitats! Has guanyat ");

        if (premi == PREMIO_1) {
            System.out.print("El primer premi ");
        } else if (premi == PREMIO_2) {
            System.out.print("El segon premi ");
        } else if (premi == PREMIO_3) {
            System.out.print("El tercer premi ");
        } else if (premi == PREMIO_4) {
            System.out.print("El quart premi ");
        } else if (premi == PREMIO_5) {
            System.out.print("El cinque premi ");
        } else if (premi == PEDREA) {
            System.out.print("La pedrea ");
        }
        System.out.print(", un total de: " + premi + "€ amb el decim: " + BoletoInput);
        if (PremiAdicional > 0 && premi == 0) {
            System.out.println(" i un premi adicional de : " + PremiAdicional + "€");
            PrintProvenenciaPremisAdicionals(ArrayPremiAdicional);
        }
    }

    static int[] TotalPremisAdicionals(int Reintegro, int NumAntIpost, int UltimasDosCifras, int Centenas) {
        int numeros[] = new int[4];

        for (int i = 0; i < numeros.length; i++) {
            if (i == 0) {
                numeros[i] = Reintegro;
            } else if (i == 1) {
                numeros[i] = NumAntIpost;
            } else if (i == 2) {
                numeros[i] = UltimasDosCifras;
            } else if (i == 3) {
                numeros[i] = Centenas;
            }
        }
        return numeros;
    }

    static int PremiAddicional(int[] TotalPremisAdicionals) {
        int premi = 0;
        for (int totalPremisAdicional : TotalPremisAdicionals) {
            if (totalPremisAdicional > premi) {
                premi = totalPremisAdicional;
            }
        }

        return premi;
    }

    /**
     *
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @param posicion, la posicio correspon al premi
     * @return retorna el valor del reintegro
     */
    static int Reintegro(int numeroConsultar, int[] NumerosPremiados) {
        final int REINTEGRO = 20;
        int total = 0;
        if (NumerosPremiados[0] % 10 == numeroConsultar % 10) {
            total = REINTEGRO;
        }
        return total;
    }

    /**
     *
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el premi corresponent al numero anterior i posterior
     */
    static int NumAntIpost(int numeroConsultar, int[] NumerosPremiados) {
        final int PREMIOEXTRA_1 = 20000;
        final int PREMIOEXTRA_2 = 12500;
        final int PREMIOEXTRA_3 = 9600;

        int PremisAddicionals = 0;

        if (NumerosPremiados[0] - 1 == numeroConsultar || NumerosPremiados[0] + 1 == numeroConsultar) {
            PremisAddicionals = PREMIOEXTRA_1;
        } else if (NumerosPremiados[1] - 1 == numeroConsultar || NumerosPremiados[1] + 1 == numeroConsultar) {
            PremisAddicionals = PREMIOEXTRA_2;
        } else if (NumerosPremiados[2] - 1 == numeroConsultar || NumerosPremiados[2] + 1 == numeroConsultar) {
            PremisAddicionals = PREMIOEXTRA_3;
        }

        return PremisAddicionals;
    }

    /**
     *
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna el premi corresponent a les 2 ultimes xifres
     */
    static int UltimasDosCifras(int numeroConsultar, int[] NumerosPremiados) {
        final int ULTIMA2CIFRA=1000;
        int PremisAddicionals = 0;
        if (NumerosPremiados[0] % 100 == numeroConsultar % 100) {
            PremisAddicionals = ULTIMA2CIFRA;
        } else if (NumerosPremiados[1] % 100 == numeroConsultar % 100) {
            PremisAddicionals = ULTIMA2CIFRA;
        } else if (NumerosPremiados[2] % 100 == numeroConsultar % 100) {
            PremisAddicionals = ULTIMA2CIFRA;
        }
        return PremisAddicionals;
    }

    /**
     *
     * @param numeroConsultar, es el numero introduit per teclat a consultar
     * @param NumerosPremiados, es el numero premiat que surt del sorteig
     * @return retorna premi corresponent a les centenas
     */
    static int Centenas(int numeroConsultar, int[] NumerosPremiados) {
        final int PREMIOCENTENA=100;
        int PremisAddicionals = 0;
        if (NumerosPremiados[0] / 100 == numeroConsultar / 100) {
            PremisAddicionals = PREMIOCENTENA;
        } else if (NumerosPremiados[1] / 100 == numeroConsultar / 100) {
            PremisAddicionals = PREMIOCENTENA;
        } else if (NumerosPremiados[2] / 100 == numeroConsultar / 100) {
            PremisAddicionals = PREMIOCENTENA;
        } else if (NumerosPremiados[3] / 100 == numeroConsultar / 100) {
            PremisAddicionals = PREMIOCENTENA;
        } else if (NumerosPremiados[4] / 100 == numeroConsultar / 100) {
            PremisAddicionals = PREMIOCENTENA;
        }
        return PremisAddicionals;
    }
    /**
     * Funcio que imprimeix els premis adicionals
     * @param ArrayPremisADD que compleixi una condició més gran i igual perquè imprimeixi el resultat.
     */
    public static void PrintProvenenciaPremisAdicionals(int[] ArrayPremisADD) {

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
            System.out.println("Has rebut " + ArrayPremisADD[3] + "��� de el Ultimes dues xifres d'un dels primers 3 premis");
        }

    }
}