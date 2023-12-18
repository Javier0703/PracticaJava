public class Comprobacion {
    public static void Comprobar(char[][] matriz, int sumarO, int sumarX, int r, int tabJugados, int tabGanados){
        //Comprobar si hay tres X en las filas seguidas
        for (int fila = 1; fila<matriz.length; fila++){
            for (int col = 1; col<matriz.length; col++){
                if (matriz[fila][col] == 'x') {
                    sumarX++;
                    if (sumarO > 0) {
                        sumarO--;
                    }
                }

                else{
                    sumarO++;
                    if(sumarX > 0){
                        sumarX--;
                    }
                }

                if (sumarO == 3 || sumarX == 3){
                    break;
                }
            }

            if (sumarX == 3 || sumarO == 3) {
                System.out.println("¡Tablero Fallado!");
                System.out.println("Has incumplido una norma (no puede haber 3 'x' o 3 'o' contiguas en horizontal)");
                r++;
                break;
            }

            sumarO = 0;
            sumarX = 0;
        }

        sumarX = 0;
        sumarO = 0;

        //Comprobar si hay dos X en Columnas seguidas
        if (r == 0) {
            for (int col = 1; col<matriz.length; col++){
                for (int fila = 1; fila<matriz.length; fila++){
                    if (matriz[fila][col] == 'x') {
                        sumarX++;
                        if (sumarO > 0) {
                            sumarO--;
                        }
                    }

                    else{
                        sumarO++;
                        if(sumarX > 0){
                            sumarX--;
                        }
                    }

                    if (sumarO == 3 || sumarX == 3){
                        break;
                    }

                }

                if (sumarX == 3 || sumarO == 3) {
                    System.out.println("¡Tablero Fallado!");
                    System.out.println("Has incumplido una norma (no puede haber 3 'x' o 3 'o' contiguas en vertical)");
                    r++;
                    break;
                }

                sumarO = 0;
                sumarX = 0;
            } 
        }

        sumarO = 0;
        sumarX = 0;


        //Validar Si las casillas son las mismas X y O
        if(r == 0){
            for (int fila = 1; fila<matriz.length; fila++){
                for (int col = 1; col<matriz.length; col++){
                    if (matriz[fila][col] == 'x') {
                        sumarX++;
                    }
                    else{
                        sumarO++;
                    }
                }

                if (sumarO != sumarX) {
                    System.out.println("¡Tablero Fallado!");
                    System.out.println("Debe de haber la misma cantidad de X que de O en cada linea");
                    r++;
                    break;
                }

                sumarO = 0;
                sumarX = 0;
            }
        }
        

        if (r == 0) {
            for (int col = 1; col<matriz.length; col++){
                for (int fila = 1; fila<matriz.length; fila++){
                    if (matriz[fila][col] == 'x') {
                        sumarX++;
                    }
                    else{
                        sumarO++;
                    }
                }

                if (sumarO != sumarX) {
                    System.out.println("¡Tablero Fallado!");
                    System.out.println("Debe de haber la misma cantidad de X que de O en cada columna");
                    r++;
                    break;
                }
                sumarO = 0;
                sumarX = 0;
            }  
            
        }

        //Validar si las columnas y filas no hay repetida

        //filaOCol es la cadena que guardaremos (1 fila / col) para luego agregarlas a un array y comparar si son iguales
        //Lineas

        StringBuilder filaOCol = new StringBuilder();
        String[] cadenas = new String[6];
        int c = 0;

        if (r == 0){

            for (int fila = 1; fila<matriz.length; fila++){
                for (int col = 1; col<matriz.length; col++){
                    filaOCol.append(matriz[fila][col]);
                }
                cadenas[fila-1] = filaOCol.toString();
                filaOCol.setLength(0);
            }

            //Comparamos el array
            for (int v = 0; v<cadenas.length; v++){
                for (int w = 0; w<cadenas.length; w++){
                    if (v != w) {
                        if (cadenas[v].equals(cadenas[w])) {
                            c++;
                            break;
                        }
                    }
                }

                if (c != 0) {
                    System.out.println("¡Tablero Fallado!");
                    System.out.println("No puede haber 2 filas / columnas iguales");
                    r++;
                    break;
                }
            }

        }
        
        cadenas = null;
        cadenas = new String[6];
        c = 0;

        //Columnas
        if (r == 0) {
            for (int col = 1; col<matriz.length; col++){
            for (int fila = 1; fila<matriz.length; fila++){
                filaOCol.append(matriz[fila][col]);
            }
            cadenas[col-1] = filaOCol.toString();
            filaOCol.setLength(0);
            } 

            //Comparamos el valor
            for (int v = 0; v<cadenas.length; v++){
                for (int w = 0; w<cadenas.length; w++){
                    if (v != w) {
                        if (cadenas[v].equals(cadenas[w])) {
                            c++;
                            break;
                        }
                    }
                }

                if (c != 0) {
                    System.out.println("¡Tablero Fallado!");
                    System.out.println("No puede haber 2 filas / columnas iguales");
                    r++;
                    break;
                }
            }
        }

        tabJugados++;

        if (r == 0) {
            tabGanados++;
            System.out.println("¡Tablero conseguido!");
        }
        }
}
