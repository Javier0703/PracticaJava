import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import java.util.Scanner;

//import java.text.DecimalFormat;

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
        //Variables para saber si ha respondido y para saber si quiere seguir jugando o no
        int cierreJuego = 0;
        int respuesta = 0;

        //Bucle del juego
        while (cierreJuego == 0) {
            System.out.println("Cargando tablero " + (tablero+1));

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
                System.out.println("¡Vaya! Ya no quedan mas tableros");
                ResumenPartidas(tabGanados, tabJugados);
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
                            String letra = " ";
                            letra = IntToString(col, letra);
                            String concat = String.valueOf(cFila)+letra+" ";
                            casillaBloq.append(concat);
                        }
                    }
                }  

                //Generar la matriz
                ImprimirMatriz(matriz);

                //Ahora generamos el array para compararlo posteriormente
                String[] arrayCasillasBlocked = casillaBloq.toString().split(" ");

                //StringBuilder de los movimientos realizados
                StringBuilder movimientos = new StringBuilder();

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
                                        break;
                                    }
                                }

                                if (cent == 1){
                                   System.out.println("Esta casilla está bloqueada."); 
                                }

                                else{
                                    //La jugada puede proseguir, ya que no está bloqueada.
                                    int v1 = (Character.getNumericValue(casilla.charAt(0)));
                                    int v2 = 0;
                                    v2 = CharToInt(casilla.charAt(1), v2);
                                    
                                    //Las separamos de esta manera porque asi sabemos la jugada que hemos hecho.
                                    int j;

                                    if (matriz[v1][v2] == 'x'){
                                        matriz[v1][v2] = 'o';
                                        j=1;
                                    }
                                    else if(matriz[v1][v2] == 'o'){
                                        matriz[v1][v2] = 'x';
                                        j=2;
                                    }
                                    else{
                                        matriz[v1][v2] = 'x';
                                        j=0;
                                    }

                                    //Si la j vale 0; esa casilla estaba vacia, el 1 pasa de X -> O... (Para saber 
                                    //las jugadas y deshacerlas mas facilmente)

                                    //Guardar los movimientos del usuario
                                    movimientos.append(casilla+j);
                                }
                                //Generar la Matriz
                                ImprimirMatriz(matriz);
                                
                            }

                            else{
                                System.out.println("La entrada no es válida");
                            }

                        }
                        

                        else if (casilla.equals("-")) {
                            if (movimientos.length() == 0) {
                                System.out.println("No hay movimientos por deshacer");
                            } 

                            else{
                                //Deshacer los cambios
                                String eliminarJugada = movimientos.toString().substring(movimientos.length() - 3);
                                int index = movimientos.lastIndexOf(eliminarJugada);
                                String movimientosActuales = movimientos.toString().substring(0, index);
                                StringBuilder mov = new StringBuilder(movimientosActuales);
                                movimientos = mov;
                                char eJ1 = eliminarJugada.charAt(0);
                                char eJ2 = eliminarJugada.charAt(1);
                                char eJ3 = eliminarJugada.charAt(2);

                                int v2 = 0;
                                v2 = CharToInt(eJ2, v2);
                                
                                int numero = Character.getNumericValue(eJ1);

                                if (eJ3 == '0') {
                                    matriz[numero][v2] = ' ';
                                }

                                else if(eJ3 == '1'){
                                    matriz[numero][v2] = 'x';
                                }

                                else{
                                    matriz[numero][v2] = 'o';
                                }
                            }   
                            ImprimirMatriz(matriz);                               
                        }

                        else if (casilla.isEmpty() && count == 36) {
                            int r = 0;
                            int sumarX = 0;
                            int sumarO = 0;
                            Comprobacion.Comprobar(matriz,sumarO,sumarX,r,tabJugados,tabGanados);

                            while (true) {
                                System.out.println("¿Quiere volver a jugar?");
                                String resp = in.nextLine().toUpperCase();
                                if(resp.toUpperCase().equals("SI")){
                                    respuesta++;
                                    break;
                                }

                                else if(resp.toUpperCase().equals("NO")){
                                    respuesta++;
                                    cierreJuego++;
                                    break;
                                }
                                else{
                                  System.out.println("Entrada no valida. Responda SI o NO por favor.");  
                                }
                            }

                        }

                        else{
                            System.out.println("La entrada no es válida");
                        }  
                    
                    //Saber si quiere jugar o no

                    if (respuesta == 1) {
                        respuesta = 0;
                        for (int col = 1; col<matriz.length; col++){
                            for (int fila = 1; fila<matriz.length; fila++){
                                matriz[fila][col] = '\0';
                            }
                        }
                        tablero++;
                        if (cierreJuego == 1) {
                           ResumenPartidas(tabGanados, tabJugados);   
                        }
                        break;
                    }
                } 
            }
        }               
    }

    public static void ImprimirMatriz(char[][] matriz){
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int col = 0; col < matriz[fila].length; col++) {
                    System.out.print(matriz[fila][col] + "\t");
            }
            System.out.println();
        }
    }

    public static void ResumenPartidas(int tabGanados, int tabJugados ){
        double porcent = (double)tabGanados/tabJugados;
        porcent = porcent*100;
        DecimalFormat formatDec = new DecimalFormat("#.##");
        String numFormat = formatDec.format(porcent);
        System.out.println("Estos son tus resultados:");
        System.out.println("Tableros jugados: " + tabJugados + " | Tableros ganados: " + tabGanados +"\n" + "Porcentaje de victoria: " + numFormat + " %");
    }
    
    public static String IntToString(int col, String letra){
        switch (col) {
            case 1: letra = "A"; break;
            case 2: letra = "B"; break;
            case 3: letra = "C"; break;
            case 4: letra = "D"; break;
            case 5: letra = "E"; break;   
            default: letra = "F"; break;
            }
        return letra;    
    }

    public static Integer CharToInt(char eJ2, int v2){
         switch (eJ2) {
            case 'A': v2 = 1; break;
            case 'B': v2 = 2; break;
            case 'C': v2 = 3; break;
            case 'D': v2 = 4; break;
            case 'E': v2 = 5; break;    
            default: v2 = 6; break;  
        }
        return v2;
    }

    public static void VolverAJugar(int respuesta, int cierreJuego, Scanner in){
        while (true) {
            System.out.println("¿Quiere volver a jugar?");
            String resp = in.nextLine().toUpperCase();
            if(resp.toUpperCase().equals("SI")){
                respuesta++;
                break;
            }

            else if(resp.toUpperCase().equals("NO")){
                respuesta++;
                cierreJuego++;
                break;
            }
            else{
                System.out.println("Entrada no valida. Responda SI o NO por favor.");  
            }
        }
    }
}