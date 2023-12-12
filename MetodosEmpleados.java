import java.text.DecimalFormat;

public class MetodosEmpleados {
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
        System.out.println("¡Vaya! Ya no quedan mas tableros. Estos son tus resultados: ");
        System.out.println("Tableros jugados: " + tabJugados + " | Tableros ganados: " + tabGanados +"\n" + "Porcentaje de victoria: " + numFormat + " %");
    }
}
