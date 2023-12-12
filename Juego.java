import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;


//Para detectar el Enter
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class Juego{
    public static void main(String[] args) {
        char[][] matriz = new char[7][7];

        //Fila 1: A-F | Columna 1: 1-6 
        for (int col = 1; col < matriz.length; col++) {
            matriz[0][col] = (char) ('A' + col - 1);
        }
        for (int fila = 1; fila < matriz.length; fila++) {
            matriz[fila][0] = (char) ('0' + fila);
        }

        //Se comenzara con el primer tablero
        int tablero = 0;
        String archivo = "tableros.txt";
        //Aqui se guardara el tablero que se va a jugar
        String linea = null;
        int tabJugados = 0;
        int tabGanados = 0;

        //Bucle del juego
        while (true) {

            try{
                int contador = 0;
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                while (contador <= tablero) {
                    linea = br.readLine();
                    if (contador == tablero && linea != null) {
                        br.close();
                        break;
                    }
                    contador++;
                }
                br.close();
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
            
            if (linea == null) {
                //Aqui es que no quedan tableros pàra jugar (se han jugado todos), lo que mostrará las estadísticas.
                MetodosEmpleados.ResumenPartidas(tabGanados, tabJugados);
                break;
            }

            else{
                //Hay tableros donde jugar
                StringBuilder casillaBloq = new StringBuilder();
                linea = linea.replaceAll(" ", "");
                int indiceTablero = 0;
                for (int fila = 1; fila < matriz.length; fila++) {
                    for (int col = 1; col < matriz[fila].length; col++) {
                        char model = linea.charAt(indiceTablero++);
                        
                        if (model == '1' || model == '2'){
                            if(model == '1'){
                                matriz[fila][col] = 'o';
                            }
                            else{
                               matriz[fila][col] = 'x'; 
                            }
                            int cFila = fila;
                            String letra;
                            switch (col) {
                                case 1:
                                    letra = "A"; 
                                    break;
                                case 2:
                                    letra = "B"; 
                                    break;
                                case 3:
                                    letra = "C"; 
                                    break;
                                case 4:
                                    letra = "D";
                                    break;
                                case 5:
                                    letra = "E";
                                    break;   
                                default:
                                    letra = "F";
                                    break;
                            }
                            String concat = String.valueOf(cFila)+letra+" ";
                            casillaBloq.append(concat);
                        }
                    }
                }  

                //Generar la matriz
                MetodosEmpleados.ImprimirMatriz(matriz);

                //Ahora generamos el array para compararlo posteriormente
                String[] arrayCasillasBlocked = casillaBloq.toString().split(" ");

                while (true) {
                    //Recorremos la Matriz para ver si esta vacía o no
                    int count = 0;
                    for (int fila = 1; fila < matriz.length; fila++) {
                        for (int col = 1; col < matriz[fila].length; col++) {
                            if (matriz[fila][col] == 'x' || matriz[fila][col] == 'o') {
                                count++;
                            } 
                        }
                    }

                    //Si es 36, esta llena
                    Scanner in = new Scanner(System.in); 
                    if (count == 36){
                        System.out.println("Pulse tecla intro si ha terminado:");
                        System.out.print("Jugada:" + "\t");
                    }
                    else{
                        //Comprobar si la entrada es correcta
                        System.out.print("Jugada: ");
                    }

                    String casilla = in.nextLine();
                        if (casilla.length() == 2) {
                            char primerCaracter = casilla.charAt(0);
                            char segundoCaracter = casilla.charAt(1);
                            if (primerCaracter >= '1' && primerCaracter <= '6' &&
                            segundoCaracter >= 'A' && segundoCaracter <= 'F') {
                                //Comprobar si la casilla está bloqueada
                                int cent = 0;
                                for (int i=0; i<arrayCasillasBlocked.length; i++){

                                    if (arrayCasillasBlocked[i].equals(casilla)){
                                        cent++;
                                    }
                                }

                                if (cent == 1){
                                   System.out.println("Esta casilla está bloqueada."); 
                                }

                                else{
                                    //La jugada puede proseguir, ya que no está bloqueada.
                                    int v1 = (Character.getNumericValue(casilla.charAt(0)));
                                    int v2;
                                    switch (casilla.charAt(1)) {
                                        case 'A':
                                            v2 = 1; break;
                                        case 'B':
                                            v2 = 2; break;
                                        case 'C':
                                            v2 = 3; break;
                                        case 'D':
                                            v2 = 4; break;
                                        case 'E':
                                            v2 = 5; break;    
                                        default:
                                            v2 = 6; break;  
                                    }

                                    if (matriz[v1][v2] == 'x'){
                                        matriz[v1][v2] = 'o';
                                    }
                                    else{
                                        matriz[v1][v2] = 'x';
                                    }

                                    //Guardar los movimientos del usuario
                                }
                                //Generar la Matriz
                                MetodosEmpleados.ImprimirMatriz(matriz);
                                
                            }

                            else{
                                System.out.println("La entrada no es válida");
                            }
                        }

                        else{
                            System.out.println("La entrada no es válida");
                        }

                    in.close();    
                }
                

            }
            

        }       
        
    }

}

 