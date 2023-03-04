package projecteloteria;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Classe Clients">
class Cliente {

    int codi;
    String nom;
    int boleto;
    double diners;
    double premi;
}
// </editor-fold>

public class ProjecteLoteria {

// <editor-fold defaultstate="collapsed" desc="Variables Globals">
    //Declaració scanner
    public static Scanner scan = new Scanner(System.in);

    public static final String RUTA = "./";
    public static final String EXTENSIO = ".bin";
    public static final String NOM_FTX_CLIENTS_BIN = "./clientes.bin";
    //Premis totals
    public static final int TOTALPREMIS = 1806;

    public static final int PREMIGORDO = 4000000;
    public static final int SEGONPREMI = 1250000;
    public static final int TERCERPREMI = 500000;
    public static final int QUARTPREMI = 200000;
    public static final int CINQUEPREMI = 60000;
    public static final int PEDREA = 1000;

    public static final String ANSI_RED = "\u001B[31m";
    /*Variable constant del color*/
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String RESET = "\033[0m";

// </editor-fold>
    //MAIN
    public static String any;
    public static String idioma = menuIdioma();

    public static void main(String[] args) throws IOException {

        int[] NumerosPremiados = ArrayPremiats();

        boolean exit = false; //MAIN MENU
        while (!exit) {
            int opciones = menuOpcions(idioma);
            switch (opciones) { //CONSULTAR NUMERO
                case 1: {
                            //Inicialitzacio funcions
                    NumerosPremiados = comprovaAnyExistent(idioma);
                    int BoletoInput = validarNumeroLoteria(idioma);
                    int posicio = ValidarNumero(NumerosPremiados, BoletoInput);
                    int reintegro = Reintegro(BoletoInput, NumerosPremiados);
                    int numAntIpost = NumAntIpost(BoletoInput, NumerosPremiados);
                    int ultimasDosCifras = UltimesDosXifres(BoletoInput, NumerosPremiados);
                    int centenas = Centenas(BoletoInput, NumerosPremiados);
                    int[] ArrayPremiAdicional = arrayAproximacions(reintegro, numAntIpost, ultimasDosCifras, centenas);
                    int premiAddicional = PremiAddicional(ArrayPremiAdicional);
                    int premiAconseguit = Premi(posicio);
                    if (menuPremiAconseguit(premiAconseguit, posicio, BoletoInput, premiAddicional, ArrayPremiAdicional, idioma)) {
                        break;
                    }
                }
                case 2: {   //Consulta tots els premis
                    imprimirPremisGrans(NumerosPremiados, idioma);
                }
                break;
                case 3: {   //Colla
                    opcionsColla(idioma);
                }
                break;
                case 0: {   //Sortir del sorteig
                    exit = menuSortida(exit, idioma);
                }
            }
        }
    }

   
// <editor-fold defaultstate="collapsed" desc="Menus">
    public static int menuOpcions(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        System.out.println(multiIdioma.getString("menuOpcions1"));
        System.out.println(multiIdioma.getString("menuOpcions2"));;
        System.out.println(multiIdioma.getString("menuOpcions3"));
        System.out.println(multiIdioma.getString("menuOpcions4"));
        System.out.println(multiIdioma.getString("menuOpcions5"));
        int opciones = LlegirNumeroEnter(frase);
        return opciones;
    }

    /**
     * Inicialitza la comprovacio per si existeix un fitxer amb el any demanat
     * @param frase
     * @return
     * @throws IOException 
     */
    public static int[] comprovaAnyExistent(String frase) throws IOException {
        int[] NumerosPremiados;
        traduccion multiIdioma = new traduccion(frase);
        System.out.println(multiIdioma.getString("year1"));
        any = scan.next();
        int[] numerosExistentes = leerFichero(any);
        if (numerosExistentes.length == 0) {
            NumerosPremiados = ArrayPremiats();
            String sorteig = sorteigString(NumerosPremiados);
            escribirFichero(sorteig, any);
        } else {
            System.out.println(multiIdioma.getString("year2"));
            NumerosPremiados = numerosExistentes;
        }
        return NumerosPremiados;
    }
    
    public static void opcionsColla(String frase) throws IOException {
        int opcion = mostrarMenuColla(frase);
        while (opcion != 0) {
            switch (opcion) {
                case 1:
                    GrabarClientesBinario(frase);
                    break;
                case 2:
                    LeerClientesBinario(frase);
                    break;
                case 3:
                    LeerInformacioColla(any, frase);
                    break;
            }
            opcion = mostrarMenuColla(frase);
        }
    }

    public static int mostrarMenuColla(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        int opcion;
        System.out.println(multiIdioma.getString("mostrarMenuColla1"));
        System.out.println(multiIdioma.getString("mostrarMenuColla2"));
        System.out.println(multiIdioma.getString("mostrarMenuColla3"));
        System.out.println(multiIdioma.getString("mostrarMenuColla4"));
        opcion = scan.nextInt();
        scan.nextLine();

        return opcion;
    }

    public static boolean menuSortida(boolean exit, String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        int menuSortida;
        System.out.println();
        System.out.println(multiIdioma.getString("menuSortida1"));
        System.out.println("menuSortida2");
        System.out.println("menuSortida3");
        menuSortida = validarNumeroEnter(frase);
        if (menuSortida == 2) {
            exit = true;
        }
        return exit;
    }

    public static boolean menuPremiAconseguit(int premiAconseguit, int posicio, int BoletoInput, int premiAddicional, int[] ArrayPremiAdicional, String frase) throws IOException {
        boolean sortir = false;
        if (premiAconseguit >= 0) {
            if (posicio >= 0) {  //Menu de premi de bolet
                int premi = textBoletPremiat(frase);
                while (!sortir) {
                    switch (premi) {
                        case 1: {
                            //Mostra el text amb els premis corresponents
                            TextGuanyador(premiAconseguit, BoletoInput, premiAddicional, ArrayPremiAdicional, frase);
                            sortir = true;
                        }
                        case 2:
                            sortir = true; //Surt del switch
                    }
                }
            } else {
                imprimirAproximacions(premiAddicional, posicio, ArrayPremiAdicional, frase);
            }
            System.out.println();  //Salt de línia
            sortir = true;
        }
        return sortir;
    }

    public static int textBoletPremiat(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        System.out.println(ANSI_GREEN + multiIdioma.getString("textBoletPremiat") + " " + RESET);
        System.out.println(multiIdioma.getString("textMenuAproximacions2"));
        System.out.println(multiIdioma.getString("menuSortida2"));
        System.out.println(multiIdioma.getString("menuSortida2"));
        //Variable per elegir el cas del següent switch
        int premi = validarNumeroEnter(frase);
        return premi;
    }

    public static void textMenuAproximacions(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        System.out.println(ANSI_GREEN + multiIdioma.getString("textMenuAproximacions1") + " " + RESET);
        System.out.println(multiIdioma.getString("textMenuAproximacions2"));
        System.out.println(multiIdioma.getString("menuSortida2"));
        System.out.println(multiIdioma.getString("menuSortida3"));
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Idioma">
    public static String menuIdioma() {
        System.out.println("En quin idioma vols executar el programa, escriu la abreviatura?");
        System.out.println("ca=Catala");
        System.out.println("es=Español");
        String opciones = idiomaValido();
        return opciones;

    }

    public static String idiomaValido() {
        String idioma = "";
        while (!idioma.equals("ca") && !idioma.equals("es")) {
            System.out.print("Introdueix una opcio valida: ");
            idioma = scan.nextLine();

        }
        return idioma;
    }

    public static class traduccion {

        private Properties properties;

        public traduccion(String idioma) throws FileNotFoundException, IOException {
            properties = new Properties();
            properties.load(new FileInputStream(idioma + ".txt"));
        }

        public String getString(String key) {
            return properties.getProperty(key);
        }
    }
    // </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Premis + Loteria">
    public static int[] ArrayPremiats() {
        //Dec array numerosPremiats
        int[] NumerosPremiados = new int[TOTALPREMIS];
        GenRandNum(NumerosPremiados);
        return NumerosPremiados;
    }

    public static int LlegirNumeroEnter(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        boolean entradaIncorrecta = true;
        int num = 0;
        while (entradaIncorrecta) {
            System.out.print(multiIdioma.getString("validar"));
            if (scan.hasNextInt()) {
                num = scan.nextInt();
                entradaIncorrecta = false;
            } else {
                scan.next();
            }
        }
        return num;
    }

    public static int validarNumeroEnter(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        boolean entradaIncorrecta = true;
        int num = 0;
        while (entradaIncorrecta) {
            System.out.print(multiIdioma.getString("validar"));

            if (scan.hasNextInt()) {
                num = scan.nextInt();
                if (num == 1 || num == 2) {
                    entradaIncorrecta = false;
                }
            } else {
                scan.next();
            }
        }
        return num;
    }

    public static void imprimirAproximacions(int premiAddicional, int posicio, int[] ArrayPremiAdicional, String frase) throws IOException {
        //en cas de rebre premis adicionals i no rebre cap premi gran ni pedrea
        traduccion multiIdioma = new traduccion(frase);
        System.out.print(ANSI_RED + multiIdioma.getString("imprimirAproximacions1") + " " + RESET);
        //Si el premi és major a 0, pero la posició no correpon a cap premi de sèrie entra
        if (premiAddicional > 0 && posicio == -1) {
            textMenuAproximacions(frase);
            int premiAdd = validarNumeroEnter(frase); //Variable per elegir el cas del següent switch
            boolean sortirAdd = false;
            while (!sortirAdd) {
                switch (premiAdd) {
                    case 1: {
                        //Mostra tots els premis adicionals aconseguits
                        System.out.println(multiIdioma.getString("imprimirAproximacions2") + " " + ANSI_GREEN + premiAddicional + multiIdioma.getString("correcioZeros3") + " " + RESET);
                        imprimeixPremisAddicionals(ArrayPremiAdicional, frase);
                        sortirAdd = true;
                    }
                    case 2:
                        sortirAdd = true;
                    //Surt del switch
                }
            }

        }
    }

    public static void imprimirPremisGrans(int[] NumerosPremiados, String frase) throws IOException {
        //Print de numeros Premiats

        for (int i = 0; i < NumerosPremiados.length; i++) {

            //if per limitar sortida de premis per pantalla
            if (i == 13) {

                i = 1805;
            }

            correcioZeros(NumerosPremiados, i, frase);

        }
    }

    public static void correcioZeros(int[] NumerosPremiados, int i, String frase) throws IOException {
        /*Amb aquesta linea situarem zeros a la esquerra del
        numero INT generat com a bolet per a que sigui del tamany desitjat,
        String.format "%05d" ens permetra agregar Zeros a la esquerra fins a un
        tamany de numero de 5 digis, en base decimal.*/
        traduccion multiIdioma = new traduccion(frase);
        String CorrecionZero = String.format("%05d", NumerosPremiados[i]);
        //Mostra els boletos premiats
        System.out.println(multiIdioma.getString("correcioZeros1") + " " + CorrecionZero + multiIdioma.getString("correcioZeros2") + " " + Premi(i) + multiIdioma.getString("correcioZeros3") + " ");
    }

    public static int validarNumeroLoteria(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        int valor;
        System.out.print(multiIdioma.getString("validarNumeroLoteria"));
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.print(multiIdioma.getString("validarNumeroLoteria"));
        }
        valor = scan.nextInt();
        return valor;
    }

    public static int validarNumeroAny(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        int valor;
        System.out.print(multiIdioma.getString("validarNumeroAny"));
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.print(multiIdioma.getString("validarNumeroAny"));
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
            premi = PREMIGORDO;
        } else if (posicion == 1) {
            premi = SEGONPREMI;
        } else if (posicion == 2) {
            premi = TERCERPREMI;
        } else if (posicion >= 3 && posicion <= 4) {
            premi = QUARTPREMI;
        } else if (posicion > 4 && posicion <= 12) {
            premi = CINQUEPREMI;
        } else if (posicion > 12) {
            premi = PEDREA;
        }
        return premi;
    }

    /**
     * @param premi, és el valor del premi
     * @param BoletoInput, es el numero introduit per teclat a consultar Treu
     * per pantalla una informació completa dels premis que hagis guanyat
     */
    public static void TextGuanyador(int premi, int BoletoInput, int PremiAdicional, int[] ArrayPremiAdicional, String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        System.out.println();
        System.out.print(multiIdioma.getString("TextGuanyador1") + " ");

        int premiDecim = 0;

        if (premi == PREMIGORDO) {
            premiDecim = 328000;
            System.out.print(multiIdioma.getString("TextGuanyador2") + " ");
        } else if (premi == SEGONPREMI) {
            premiDecim = 108000;
            System.out.print(multiIdioma.getString("TextGuanyador3") + " ");
        } else if (premi == TERCERPREMI) {
            premiDecim = 48000;
            System.out.print(multiIdioma.getString("TextGuanyador4") + " ");
        } else if (premi == QUARTPREMI) {
            premiDecim = 20000;
            System.out.print(multiIdioma.getString("TextGuanyador5") + " ");
        } else if (premi == CINQUEPREMI) {
            premiDecim = 6000;
            System.out.print(multiIdioma.getString("TextGuanyador6") + " ");
        } else if (premi == PEDREA) {
            premiDecim = 100;
            System.out.print(multiIdioma.getString("TextGuanyador7") + " ");
        }
        System.out.println("");
        System.out.print(multiIdioma.getString("TextGuanyador8") + " " + BoletoInput + " "
                + multiIdioma.getString("TextGuanyador9") + " " + ANSI_GREEN + premi + multiIdioma.getString("correcioZeros3") + " " + RESET + multiIdioma.getString("TextGuanyador10") + " " + ANSI_GREEN + premiDecim + multiIdioma.getString("correcioZeros3") + RESET);
        if (PremiAdicional > 0 && premi == 0) {
            System.out.println(ANSI_GREEN + multiIdioma.getString("TextGuanyador11") + " " + PremiAdicional + multiIdioma.getString("correcioZeros3") + RESET);
            imprimeixPremisAddicionals(ArrayPremiAdicional, frase);
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
    public static int UltimesDosXifres(int numeroConsultar, int[] NumerosPremiados) {
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
     * @param frase
     * @throws java.io.IOException
     */
    public static void imprimeixPremisAddicionals(int[] ArrayPremisADD, String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        System.out.println();

        if (ArrayPremisADD[0] > 0) {
            System.out.println(multiIdioma.getString("imprimeixPremisAddicionals1") + " " + ANSI_GREEN + ArrayPremisADD[0] + multiIdioma.getString("correcioZeros3") + " " + RESET + multiIdioma.getString("imprimeixPremisAddicionals2") + " ");
        }
        if (ArrayPremisADD[1] > 0) {
            System.out.print(multiIdioma.getString("imprimeixPremisAddicionals1") + " " + ANSI_GREEN + ArrayPremisADD[1] + multiIdioma.getString("correcioZeros3") + " " + RESET + multiIdioma.getString("imprimeixPremisAddicionals3") + " ");

            if (ArrayPremisADD[1] == 2000) {
                System.out.println(multiIdioma.getString("TextGuanyador2") + " ");
            } else if (ArrayPremisADD[1] == 1250) {
                System.out.println(multiIdioma.getString("TextGuanyador3") + " ");
            } else if (ArrayPremisADD[1] == 960) {
                System.out.println(multiIdioma.getString("TextGuanyador4") + " ");
            }
        }
        if (ArrayPremisADD[2] > 0) {
            System.out.println(multiIdioma.getString("imprimeixPremisAddicionals1") + " " + ANSI_GREEN + ArrayPremisADD[2] + multiIdioma.getString("correcioZeros3") + " " + RESET + multiIdioma.getString("imprimeixPremisAddicionals4") + " ");
        }
        if (ArrayPremisADD[3] > 0) {
            System.out.println(multiIdioma.getString("imprimeixPremisAddicionals1") + " " + ANSI_GREEN + ArrayPremisADD[3] + multiIdioma.getString("correcioZeros3") + " " + RESET + multiIdioma.getString("imprimeixPremisAddicionals5") + " ");
        }

    }// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Sorteig Any existent">    
    /**
     * Convierte un array de enteros en una cadena de texto
     *
     * @param boleto
     * @return
     */
    public static String sorteigString(int[] boleto) {
        String result = "";
        for (int i = 0; i < boleto.length; i++) {
            result += boleto[i] + " ";
        }
        return result;
    }

    /**
     * Abre un nuevo fichero para el año pedido, para los boletos
     *
     * @param nomFichero
     * @param crear
     * @return
     */
    public static DataOutputStream abrirFicheroEscritura(String nomFichero, boolean crear) {
        DataOutputStream dos = null;
        File File = new File(nomFichero);

        if (!File.exists()) {
            if (crear) {
                try {
                    File.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
                    File = null;
                }
            } else {
                File = null;
            }
        }

        if (File != null) {
            try {
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(File)));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dos;
    }

    /**
     * Cierra un fichero cuando es necesario
     *
     * @param dos
     */
    public static void cerrarFichero(DataOutputStream dos) {
        try {
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Escribe en el fichero,si no existe el fichero del año crea el nombre del
     * fichero (año) y su contenido
     *
     * @param text
     * @param any
     */
    public static int[] escribirFichero(String text, String any) {
        String nomFitxer = RUTA + any + EXTENSIO;
        File File = new File(nomFitxer);
        int[] numeros = null;

        if (File.exists()) {
            numeros = abrirArchivoSiExiste(File, numeros);
        } else {
            numeros = crearNuevoArxivo(nomFitxer, text, numeros);
        }

        return numeros;
    }

    /**
     * Devuelve los boletos del fichero del año pedido, i utiliza una funcion
     * para llenar la array con los boletos
     *
     * @param File
     * @param numeros
     * @return
     * @throws NumberFormatException
     */
    public static int[] abrirArchivoSiExiste(File File, int[] numeros) throws NumberFormatException {
        numeros = llenarArrayBoletosFichero(File, numeros);
        return numeros;
    }

    /**
     * Si no existe el año pedido se crea un nuevo fichero del "año" con el
     * sorteo.
     *
     * @param nomFitxer
     * @param text
     * @param numeros
     * @return
     * @throws NumberFormatException
     */
    public static int[] crearNuevoArxivo(String nomFitxer, String text, int[] numeros) throws NumberFormatException {
        // Si el archivo no existe, creamos uno nuevo y escribimos el sorteo
        DataOutputStream dos = abrirFicheroEscritura(nomFitxer, true);
        if (dos != null) {
            try {
                String[] numerosTexto = text.split(" ");
                numeros = new int[numerosTexto.length];
                for (int i = 0; i < numerosTexto.length; i++) {
                    numeros[i] = Integer.parseInt(numerosTexto[i]);
                    dos.writeBytes(numerosTexto[i] + "\n");
                }
                cerrarFichero(dos);
            } catch (IOException ex) {
                Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return numeros;
    }

    /**
     * Lee el fichero para ver si el fichero del anyo existe
     *
     * @param any
     * @return
     */
    public static int[] leerFichero(String any) {
        String nomFitxer = RUTA + any + EXTENSIO;
        File File = new File(nomFitxer);
        int[] numeros = null;

        if (File.exists()) {
            numeros = llenarArrayBoletosFichero(File, numeros);
        } else {
            // Si el archivo no existe, devolvemos un array vacío
            numeros = new int[0];
        }

        return numeros;
    }

    /**
     * Llena un array con los boletos del sorteo de ese año
     *
     * @param File
     * @param numeros
     * @return
     * @throws NumberFormatException
     */
    public static int[] llenarArrayBoletosFichero(File File, int[] numeros) throws NumberFormatException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(File));
            String linea;
            int cantidadNumeros = 0;
            while ((linea = br.readLine()) != null) {
                cantidadNumeros++;
            }
            br.close();

            numeros = new int[cantidadNumeros];
            BufferedReader br2 = new BufferedReader(new FileReader(File));
            int i = 0;
            while ((linea = br2.readLine()) != null) {
                int numero = Integer.parseInt(linea.trim());
                numeros[i] = numero;
                i++;
            }
            br2.close();
        } catch (IOException ex) {
            Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numeros;
    }

// </editor-fold>
  
    
    
// <editor-fold defaultstate="collapsed" desc="Colles">
    /**
     * Funcion que abre un fichero y, opcionalmente, lo crea si no existe
     *
     * @param nomFichero Nombre del fichero a abrir
     * @param crear Si lo que queremos crear en el caso que no exista
     * @return File con el fichero que se ha abierto o null si no existe o no se
     * ha podido crear
     */
    
    public static DataOutputStream AbrirFicheroEscrituraSorteigBinario(String nomFichero, boolean crear, boolean blnAnyadir) {
        DataOutputStream dos = null;
        File file = new File(nomFichero);

        if (!file.exists()) {
            if (crear) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
                    file = null;
                }
            } else {
                file = null;
            }
        }

        if (file != null) {
            try {
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, blnAnyadir)));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dos;
    }
    
    public static File AbrirFicheroSorteig(String nomFichero, boolean crear) {
        File result = null;

        result = new File(nomFichero);

        if (!result.exists()) {
            if (crear) {
                try {
                    result.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
                    result = null;
                }
            } else {
                result = null;
            }
        }

        return result;
    }

    public static String PedirLineaTeclado() {
        return scan.nextLine();
    }

    public static Cliente PedirDatosCliente(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        Cliente c = new Cliente();
        System.out.print(multiIdioma.getString("llegirDades1") + " ");
        c.codi = scan.nextInt();
        scan.nextLine();
        if (c.codi != 0) {
            System.out.print(multiIdioma.getString("llegirDades2") + " ");
            c.nom = scan.nextLine();
            System.out.print(multiIdioma.getString("llegirDades3") + " ");
            c.boleto = scan.nextInt();
            System.out.print(multiIdioma.getString("llegirDades4") + " ");
            c.diners = validarDinersFicats(frase);
            System.out.println(multiIdioma.getString("PedirDatosCliente") + " ");
        } else {
            c = null;
        }
        return c;
    }

    /**
     *
     * @param frase
     * @return
     */
    public static double validarDinersFicats(String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        Double valor = scan.nextDouble();
        while (!scan.hasNextInt() || valor > 60 || valor % 5 != 0) {
            System.out.print(multiIdioma.getString("validarDinersFicats") + " ");
            valor = scan.nextDouble();
        }
        return valor;
    }

    public static void llegirDades(Cliente c, String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        System.out.println(multiIdioma.getString("llegirDades1") + " " + c.codi);
        System.out.println(multiIdioma.getString("llegirDades2") + " " + c.nom);
        System.out.println(multiIdioma.getString("llegirDades3") + " " + c.boleto);
        System.out.println(multiIdioma.getString("llegirDades4") + " " + c.diners);
    }

    public static void GrabarClientesBinario(String frase) throws IOException {
        DataOutputStream dos = AbrirFicheroEscrituraSorteigBinario(NOM_FTX_CLIENTS_BIN, true, true);

        Cliente cli = PedirDatosCliente(frase);
        while (cli != null) {
            GrabarDatosClienteBinario(dos, cli);
            cli = PedirDatosCliente(frase);
        }

        CerrarFicheroBinario(dos);

    }

    public static void CerrarFicheroBinario(DataOutputStream dos) {
        try {
            try (dos) {
                dos.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void CerrarFicheroBinario(DataInputStream dis) {
        try {
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void GrabarDatosClienteBinario(DataOutputStream dos, Cliente cli) {
        try {
            dos.writeInt(cli.codi);
            dos.writeUTF(cli.nom);
            dos.writeInt(cli.boleto);
            dos.writeDouble(cli.diners);
        } catch (IOException ex) {
            Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void LeerClientesBinario(String frase) throws IOException {
        DataInputStream dis = AbrirFicheroLecturaBinario(NOM_FTX_CLIENTS_BIN, true);

        Cliente cli = LeerDatosClienteBinario(dis);
        while (cli != null) {
            llegirDades(cli, frase);
            cli = LeerDatosClienteBinario(dis);
        }

        CerrarFicheroBinario(dis);
    }

    public static void LeerInformacioColla(String any, String frase) throws IOException {
        traduccion multiIdioma = new traduccion(frase);
        DataInputStream dis = AbrirFicheroLecturaBinario(NOM_FTX_CLIENTS_BIN, true);
        double contarPremi = 0;
        int contarClients = 0;
        double contarDiners = 0;
        Cliente cli = LeerDatosClienteBinario(dis);
        while (cli != null) {
            contarClients = contarClients + 1;
            contarPremi = contarPremi + cli.premi;
            contarDiners = contarDiners + cli.diners;
            cli = LeerDatosClienteBinario(dis);
        }
        System.out.println(multiIdioma.getString("LeerInformacioColla1") + " " + any + multiIdioma.getString("LeerInformacioColla2") + " " + contarClients + multiIdioma.getString("LeerInformacioColla3") + " " + contarDiners + multiIdioma.getString("LeerInformacioColla4") + " " + contarPremi);
        CerrarFicheroBinario(dis);
    }

    public static DataInputStream AbrirFicheroLecturaBinario(String nomFichero, boolean crear) {
        DataInputStream dis = null;
        File f = abrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileInputStream reader;
            try {
                reader = new FileInputStream(f);
                // PrintWriter para poder escribir más comodamente
                dis = new DataInputStream(reader);
            } catch (IOException ex) {
                Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dis;
    }

    public static File abrirFichero(String nomFichero, boolean crear) {
        File result = new File(nomFichero);
        if (!result.exists()) {
            if (crear) {
                try {
                    result.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
                    result = null;
                }
            } else {
                result = null;
            }
        }
        return result;
    }

    public static Cliente LeerDatosClienteBinario(DataInputStream dis) {
        Cliente cli = new Cliente();

        try {
            cli.codi = dis.readInt();
            cli.nom = dis.readUTF();
            cli.boleto = dis.readInt();
            cli.diners = dis.readDouble();

        } catch (IOException ex) {
            cli = null;
        }
        return cli;
    }
    // </editor-fold>
}