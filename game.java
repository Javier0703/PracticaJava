//Librerias que utilizaremos
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class game{
    //Numero filas y columnas
    public static final int FILAS = 6, COLS = 6;
    //Variables por usar
    public static final String[] VAR = {"","x","o","u"};
    //Letras y numeros en la matriz
    public static final String LETRA = "A", NUM = "1", ARCHIVO = "tableros.txt";

    public static void main(String[] args){
        //Generamos la matriz y la rellenamos
        String[][] matriz = new String[FILAS+1][COLS+1];
        RellenarMatriz(matriz);

        int tablero = 0, tabJugados = 0, tabGanados = 0;
        String linea = null;

        boolean seguirJugando = true, respuesta = false;

        while (seguirJugando) {

            try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
                int contador = 0;
                while (contador <= tablero) {
                    linea = br.readLine();
                    if (contador == tablero && linea != null) {
                        break;
                    }
                    contador++;
                }
            }

            catch (FileNotFoundException e) {
                //No encuentra el archivo
                System.out.println("No se encuentra el archivo " + e.getMessage());
                break;
            } 

            catch (IOException e) {
                //Error de entrada o salida
                System.out.println("Hay un error inesperado en la entrada o salida de los datos " + e.getMessage());
                break;
            }

            //Si la linea es null, no quedan mas tableros por jugar.
            if (linea == null) {
                System.out.println("¡Vaya! Ya no quedan mas tableros");
                ResumenPartidas(tabGanados, tabJugados);
                break;
            }

            else{
                //Hay tableros donde jugar
                String casillasBloq = "";
                casillasBloq = UtilizarTablero(linea, matriz, casillasBloq);
                ImprimirMatriz(matriz);
                System.out.println(casillasBloq);

                break;



                //in.close();
            }
        }
    }

    
    private static void RellenarMatriz(String[][] matriz){
        //Rellenamos la fila
        char leter = LETRA.charAt(0);
        for (int c = 1; c < COLS+1; c++) {
            char l = (char)(leter + c - 1);
            matriz[0][c] = "" + l;  
        }

        //Rellenamos la columna
        for (int f = 0; f < FILAS; f++) {
            int c = Integer.parseInt(NUM);
            matriz[f+1][0] = String.valueOf(c+f);
        }
    } 
    
    private static void ImprimirMatriz(String[][] matriz){
        for (int f = 0; f< FILAS + 1; f++) {
            for (int c = 0; c < COLS +1 ; c++) {
                    System.out.print(matriz[f][c] + "\t");
            }
            System.out.println();
        }
    }

    private static void ResumenPartidas(int tabGanados, int tabJugados ){
        double porcent = (double)tabGanados/tabJugados;
        porcent = porcent*100;
        DecimalFormat formatDec = new DecimalFormat("#.##");
        String numFormat = formatDec.format(porcent);
        System.out.println("Estos son tus resultados:");
        System.out.println("Tableros jugados: " + tabJugados + " | Tableros ganados: " + tabGanados +"\n" + "Porcentaje de victoria: " + numFormat + " %");
    } 
    
    private static String UtilizarTablero(String linea, String[][] matriz, String casillasBloq){
        linea = linea.replaceAll(" ","");
        int indiceTablero = 0; matriz[0][0]="";
        for (int f = 1; f<FILAS+1; f++) {
            for (int c = 1; c<COLS+1; c++) {
                int val = linea.charAt(indiceTablero++) - '0';
                matriz[f][c] = VAR[val];
                if (matriz[f][c] != VAR[0]) {
                    casillasBloq += matriz[f][0]+""+matriz[0][c]+" ";
                }
                //Guardamos las casillas bloqueadas
            }
        }
        return casillasBloq;
    }


}