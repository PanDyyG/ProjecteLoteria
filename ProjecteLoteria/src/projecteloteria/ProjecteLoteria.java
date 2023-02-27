package projecteloteria;

// <editor-fold defaultstate="collapsed" desc="Imports">
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    //Declaració scanner
    public static Scanner scan = new Scanner(System.in);
// </editor-fold>
    
    //MAIN
    public static void main(String[] args) {

        int[] NumerosPremiados = ArrayPremiats();

        boolean exit = false; //MAIN MENU
        while (!exit) {
            int opciones = menuOpcions();
            switch (opciones) { //CONSULTAR NUMERO

                case 1: {   //Inicialitzacio funcions
                    int BoletoInput = validarNumeroLoteria();
                    int any = validarNumeroAny();
                    int posicio = ValidarNumero(NumerosPremiados, BoletoInput);
                    int reintegro = Reintegro(BoletoInput, NumerosPremiados);
                    int numAntIpost = NumAntIpost(BoletoInput, NumerosPremiados);
                    int ultimasDosCifras = UltimesDosXifres(BoletoInput, NumerosPremiados);
                    int centenas = Centenas(BoletoInput, NumerosPremiados);
                    int[] ArrayPremiAdicional = arrayAproximacions(reintegro, numAntIpost, ultimasDosCifras, centenas);
                    int premiAddicional = PremiAddicional(ArrayPremiAdicional);
                    int premiAconseguit = Premi(posicio);
                    if (menuPremiAconseguit(premiAconseguit, posicio, BoletoInput, premiAddicional, ArrayPremiAdicional)) {
                        break;
                    }
                }
                case 2: {   //Consulta tots els premis
                    imprimirPremisGrans(NumerosPremiados);
                }
                break;
                case 3: {   //Colla
                    opcionsColla();
                }
                break;
                case 4: {   //Sortir del sorteig
                    exit = menuSortida(exit);
                }
            }
        }
    }
    
// <editor-fold defaultstate="collapsed" desc="Menus">
    public static int menuOpcions() {
        System.out.println("-----------Menu d'opcions-----------");
        System.out.println("1. Consultar un numero");
        System.out.println("2. Consultar els numeros premiats");
        System.out.println("3. Consultar o afegir una colla");
        System.out.println("4. Sortir del menu");
        int opciones = LlegirNumeroEnter();
        return opciones;
    }

    public static void opcionsColla() {
        int opcion = mostrarMenuColla();
        while (opcion != 0) {
            switch (opcion) {
                case 1:
                    GrabarClientesBinario();
                    break;
                case 2:
                    LeerClientesBinario();
                    break;
                case 3:
                    LeerInformacioColla();
                    break;
            }
            opcion = mostrarMenuColla();
        }
    }

    public static int mostrarMenuColla() {
        int opcion;
        System.out.println("1.- Afegir un client a la colla");
        System.out.println("2.- Consultar els clients de la colla");
        System.out.println("3.- Consultar informació de la colla");
        System.out.println("0.- Sortir del menú de colles");
        System.out.print("Tria una opció:");

        opcion = scan.nextInt();
        scan.nextLine();

        return opcion;
    }

    public static boolean menuSortida(boolean exit) {
        int menuSortida;
        System.out.println();
        System.out.println("Vols rebre mes informacio sobre el sorteig de nadal?");
        System.out.println("1.Si");
        System.out.println("2.No");
        menuSortida = validarNumeroEnter();
        if (menuSortida == 2) {
            exit = true;
        }
        return exit;
    }

    public static boolean menuPremiAconseguit(int premiAconseguit, int posicio, int BoletoInput, int premiAddicional, int[] ArrayPremiAdicional) {
        boolean sortir = false;
        if (premiAconseguit >= 0) {
            if (posicio >= 0) {  //Menu de premi de bolet
                int premi = textBoletPremiat();
                while (!sortir) {
                    switch (premi) {
                        case 1: {
                            //Mostra el text amb els premis corresponents
                            TextGuanyador(premiAconseguit, BoletoInput, premiAddicional, ArrayPremiAdicional);
                            sortir = true;
                        }
                        case 2:
                            sortir = true; //Surt del switch
                    }
                }
            } else {
                imprimirAproximacions(premiAddicional, posicio, ArrayPremiAdicional);
            }
            System.out.println();  //Salt de línia
            sortir = true;
        }
        return sortir;
    }

    public static int textBoletPremiat() {
        System.out.println(ANSI_GREEN + "Enhorabona, el bolet esta premiat!!" + RESET);
        System.out.println("Vols saber el seu premi?");
        System.out.println("1. Si");
        System.out.println("2. No");
        //Variable per elegir el cas del següent switch
        int premi = validarNumeroEnter();
        return premi;
    }
    
    public static void textMenuAproximacions() {
        System.out.println(ANSI_GREEN + ", pero has rebut un premi addicional!!" + RESET);
        System.out.println("Vols saber el seu premi?");
        System.out.println("1. Si");
        System.out.println("2. No");
    }
    
    // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Idioma">
    public static String menuIdioma() {
        System.out.println("En quin idioma vols executar el programa, escriu la abreviatura?");
        System.out.println("1.Catala");
        System.out.println("2.Español");
        String opciones = idiomaValido();
        return opciones;

    }

    public static String idiomaValido() {
        String idioma = "";
        while (!idioma.equals("ca") && !idioma.equals("en")) {
            idioma = scan.nextLine();
            System.out.println("Introdueix una opcio valida: ");
        }
        return idioma;
    }

    public static ResourceBundle cargarRecursos(String idioma) {
        Locale locale = new Locale(idioma);
        return ResourceBundle.getBundle("strings", locale);
    }
    // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Premis + Loteria">
    public static int[] ArrayPremiats() {
        //Dec array numerosPremiats
        int[] NumerosPremiados = new int[TOTALPREMIS];
        GenRandNum(NumerosPremiados);
        return NumerosPremiados;
    }
    
    public static int LlegirNumeroEnter() {
        boolean entradaIncorrecta = true;
        int num = 0;
        while (entradaIncorrecta) {
            System.out.print("Introdueix una opcio valida: ");
            if (scan.hasNextInt()) {
                num = scan.nextInt();
                entradaIncorrecta = false;
            } else {
                scan.next();
            }
        }
        return num;
    }
    
    public static int validarNumeroEnter() {
        boolean entradaIncorrecta = true;
        int num = 0;
        while (entradaIncorrecta) {
            System.out.print("Introdueix una opcio valida: ");
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
    
    public static void imprimirAproximacions(int premiAddicional, int posicio, int[] ArrayPremiAdicional) {
        //en cas de rebre premis adicionals i no rebre cap premi gran ni pedrea
        System.out.print(ANSI_RED + "El teu bolet no esta premiat!" + RESET);
        //Si el premi és major a 0, pero la posició no correpon a cap premi de sèrie entra
        if (premiAddicional > 0 && posicio == -1) {
            textMenuAproximacions();
            int premiAdd = validarNumeroEnter(); //Variable per elegir el cas del següent switch
            boolean sortirAdd = false;
            while (!sortirAdd) {
                switch (premiAdd) {
                    case 1: {
                        //Mostra tots els premis adicionals aconseguits
                        System.out.println("Total premis en Aproximacions: " + ANSI_GREEN + premiAddicional + "€" + RESET);
                        imprimeixPremisAddicionals(ArrayPremiAdicional);
                        sortirAdd = true;
                    }
                    case 2:
                        sortirAdd = true;   //Surt del switch
                }
            }

        }
    }
    
    public static void imprimirPremisGrans(int[] NumerosPremiados) {
        //Print de numeros Premiats

        for (int i = 0; i < NumerosPremiados.length; i++) {

            //if per limitar sortida de premis per pantalla
            if (i == 13) {

                i = 1805;
            }

            correcioZeros(NumerosPremiados, i);

        }
    }

    public static void correcioZeros(int[] NumerosPremiados, int i) {
        /*Amb aquesta linea situarem zeros a la esquerra del
        numero INT generat com a bolet per a que sigui del tamany desitjat,
        String.format "%05d" ens permetra agregar Zeros a la esquerra fins a un
        tamany de numero de 5 digis, en base decimal.*/
        String CorrecionZero = String.format("%05d", NumerosPremiados[i]);
        //Mostra els boletos premiats
        System.out.println("Numero de boleto: " + CorrecionZero + " Premi: " + Premi(i) + "€");
    }

    public static int validarNumeroLoteria() {

        int valor;
        System.out.print("Introdueix el teu numero de la loteria: ");
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.print("Introdueix el teu numero de la loteria: ");
        }
        valor = scan.nextInt();
        return valor;
    }

    public static int validarNumeroAny() {

        int valor;
        System.out.print("Introdueix l'any del sorteig de la loteria: ");
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.print("Introdueix l'any del sorteig de la loteria: ");
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
    public static void TextGuanyador(int premi, int BoletoInput, int PremiAdicional, int[] ArrayPremiAdicional) {

        System.out.println();
        System.out.print("Felicitats! T'ha tocat");

        int premiDecim = 0;

        if (premi == PREMIGORDO) {
            premiDecim = 328000;
            System.out.print(" el primer premi.");
        } else if (premi == SEGONPREMI) {
            premiDecim = 108000;
            System.out.print(" el segon premi.");
        } else if (premi == TERCERPREMI) {
            premiDecim = 48000;
            System.out.print(" el tercer premi.");
        } else if (premi == QUARTPREMI) {
            premiDecim = 20000;
            System.out.print(" el quart premi.");
        } else if (premi == CINQUEPREMI) {
            premiDecim = 6000;
            System.out.print(" el cinque premi.");
        } else if (premi == PEDREA) {
            premiDecim = 100;
            System.out.print(" la pedrea.");
        }
        System.out.println("");
        System.out.print("El numero " + BoletoInput
                + " ha sigut premiat amb un total de: " + ANSI_GREEN + premi + "€" + RESET + " per serie i has guanyat " + ANSI_GREEN + premiDecim + "€" + RESET);
        if (PremiAdicional > 0 && premi == 0) {
            System.out.println(ANSI_GREEN + " i un premi addicional de : " + PremiAdicional + "€" + RESET);
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
     */
    public static void imprimeixPremisAddicionals(int[] ArrayPremisADD) {

        System.out.println();

        if (ArrayPremisADD[0] > 0) {
            System.out.println("Has rebut " + ANSI_GREEN + ArrayPremisADD[0] + "€" + RESET + " de el Reintegro");
        }
        if (ArrayPremisADD[1] > 0) {
            System.out.print("Has rebut " + ANSI_GREEN + ArrayPremisADD[1] + "€" + RESET + " de el Numero anterior o posterior al ");

            if (ArrayPremisADD[1] == 2000) {
                System.out.println("primer premi.");
            } else if (ArrayPremisADD[1] == 1250) {
                System.out.println("segon premi.");
            } else if (ArrayPremisADD[1] == 960) {
                System.out.println("tercer premi.");
            }
        }
        if (ArrayPremisADD[2] > 0) {
            System.out.println("Has rebut " + ANSI_GREEN + ArrayPremisADD[2] + "€" + RESET + " de el Centena de un dels primers 4 premis");
        }
        if (ArrayPremisADD[3] > 0) {
            System.out.println("Has rebut " + ANSI_GREEN + ArrayPremisADD[3] + "€" + RESET + " de el Ultimes dues xifres d'un dels primers 3 premis");
        }
        
    }// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Binari">
    /**
     * Funcion que abre un fichero y, opcionalmente, lo crea si no existe
     *
     * @param nomFichero Nombre del fichero a abrir
     * @param crear Si lo que queremos crear en el caso que no exista
     * @return File con el fichero que se ha abierto o null si no existe o no se
     * ha podido crear
     */
    public static File AbrirFichero(String nomFichero, boolean crear) {
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

    public static Cliente PedirDatosCliente() {
        Cliente c = new Cliente();
        System.out.print("Codi: ");
        c.codi = scan.nextInt();
        scan.nextLine();
        if (c.codi != 0) {
            System.out.print("Nom: ");
            c.nom = scan.nextLine();
            System.out.print("Boleto: ");
            c.boleto = scan.nextInt();
            System.out.print("Diners ficats: ");
            c.diners = validarDinersFicats();
        } else {
            c = null;
        }
        return c;
    }

    public static double validarDinersFicats() {

        Double valor = scan.nextDouble();
        while (!scan.hasNextInt() || valor > 60 || valor % 5 != 0) {
            System.out.print("Els diners no són vàlids. Torna a provar: ");
            valor = scan.nextDouble();
        }
        return valor;
    }

    public static void EscribirDatosCliente(Cliente c) {
        System.out.println("Codi: " + c.codi);
        System.out.println("Nom: " + c.nom);
        System.out.println("Boleto: " + c.boleto);
        System.out.println("Diners: " + c.diners);
    }

    public static void GrabarClientesBinario() {
        DataOutputStream dos = AbrirFicheroEscrituraBinario(NOM_FTX_CLIENTS_BIN, true, true);

        Cliente cli = PedirDatosCliente();
        while (cli != null) {
            GrabarDatosClienteBinario(dos, cli);
            cli = PedirDatosCliente();
        }

        CerrarFicheroBinario(dos);

    }

    public static DataOutputStream AbrirFicheroEscrituraBinario(String nomFichero, boolean crear, boolean blnAnyadir) {
        DataOutputStream dos = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileOutputStream writer;
            try {
                writer = new FileOutputStream(f, blnAnyadir);
                // PrintWriter para poder escribir más comodamente
                dos = new DataOutputStream(writer);
            } catch (IOException ex) {
                Logger.getLogger(ProjecteLoteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dos;
    }

    public static void CerrarFicheroBinario(DataOutputStream dos) {
        try {
            dos.flush();
            dos.close();
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

    public static void LeerClientesBinario() {
        DataInputStream dis = AbrirFicheroLecturaBinario(NOM_FTX_CLIENTS_BIN, true);

        Cliente cli = LeerDatosClienteBinario(dis);
        while (cli != null) {
            EscribirDatosCliente(cli);
            cli = LeerDatosClienteBinario(dis);
        }

        CerrarFicheroBinario(dis);
    }
    
    public static void LeerInformacioColla() {
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
        System.out.println(contarClients + " " + contarDiners + " " + contarPremi);
        CerrarFicheroBinario(dis);
    }
    
    public static DataInputStream AbrirFicheroLecturaBinario(String nomFichero, boolean crear) {
        DataInputStream dis = null;
        File f = AbrirFichero(nomFichero, crear);

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

    public static void BorrarFichero(String filename) {
        File f = new File(NOM_FTX_CLIENTS_BIN);
        f.delete();
    }
          // </editor-fold>
}
