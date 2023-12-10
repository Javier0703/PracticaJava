import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

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
        int tablero = 1;
        String archivo = "tableros.txt";
        //Aqui se guardara el tablero que se va a jugar
        String linea = null;
        int tabJugados = 0;
        int tabGanados = 0;

        //Bucle del juego
        while (true) {

            try{
                int contador = 1;
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
                double porcent = (double)tabGanados/tabJugados;
                porcent = porcent*100;
                DecimalFormat formatDec = new DecimalFormat("#.##");
                String numFormat = formatDec.format(porcent);
                System.out.println("¡Vaya! Ya no quedan mas tableros. Estos son tus resultados: ");
                System.out.println("Tableros jugados: " + tabJugados + " | Tableros ganados: " + tabGanados +"\n" + "Porcentaje de victoria: " + numFormat + " %");
                break;
            }

            else{
                //Hay tableros donde jugar
                //StringBuilder donde se guardaran las casillas fijas donde no se podran mover
                StringBuilder casillaBloq = new StringBuilder();
                System.out.println(linea);
                int indiceTablero = 0;
            
                for (int fila = 1; fila < matriz.length; fila++) {
                    for (int col = 1; col < matriz[fila].length; col++) {
                        char model = linea.charAt(indiceTablero++);
                        
                        if (model == ' ') {
                            model = linea.charAt(indiceTablero++);
                            
                        }

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
                //Generada la matriz
                for (int fila = 0; fila < matriz.length; fila++) {
                    for (int col = 0; col < matriz[fila].length; col++) {
                        System.out.print(matriz[fila][col] + "\t");
                    }
                    System.out.println();
                }
                //Ahora generamos el array para compararlo posteriormente
                String[] arrayCasillasBlocked = casillaBloq.toString().split(" ");
                break;
                //Aqui ya empezaria a pedir un Scanner con el que introduzca el usuario algo y lo comparase si son fijas o no, si quedan casillas por resolver... etc
            }

        }
        
    }
        
    
 }
