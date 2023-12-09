import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


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
                //ArrayList donde se guardaran las casillas fijas donde no se podran mover
                List<String> casillaBloq = new ArrayList<String>();
                System.out.println(linea);
                int indiceTablero = 0;
            
                for (int fila = 1; fila < matriz.length; fila++) {
                    for (int col = 1; col < matriz[fila].length; col++) {
                        char model = linea.charAt(indiceTablero++);
                        
                        if (model == ' ') {
                            model = linea.charAt(indiceTablero++);
                            
                        }

                        if (model == '1'){
                            matriz[fila][col] = 'o';
                            casillaBloq.add("1");
                        }

                        else if (model == '2') {
                            matriz[fila][col] = 'x';
                            casillaBloq.add("1");
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
                
                for(int i = 0; i != casillaBloq.size(); ++i){
                    System.out.print(casillaBloq.get(i));
                }
                  
                break;
            }

        }
        
    }
        
    
 }
