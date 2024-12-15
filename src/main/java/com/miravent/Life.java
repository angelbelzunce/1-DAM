package com.miravent;

//Creamos la clase Life con la matriz booleana y las variables de poblacion y generacion de tipo estaticas para poder usarlas en varios metodos.
public class Life {
    static boolean[][] grid;
    static int population = 0;
    static int generation = 0;

    //Creamos el metodo inicializa con los parametros para crear la estructura que vamos a darle al tablero.
    public static void inicializa(int numCeldas, int numCeldas1) {
        grid = new boolean[numCeldas][numCeldas1];
        //llamamos al metodo fillRandom que se encargará de rellenar la matriz con posiciones true o false de manera aleatoria.
        fillRandom(grid);

    }

    private static void fillRandom(boolean[][] grid) {
        // creamos un contador para llevar la cuenta de los elementos de valor true que integran la matriz recibida por parametro.

        //Bucle for para recorrer todas las posiciones de la matriz que rellenamos con true o false dependiendo del resultado que nos proporcione el metodo Math.random
        //  (0 = false y 1 = true). Por cada true el contador de poblacion aumentara en 1.
        int contadorPoblacion = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if ((int) (Math.random() * 2) == 1) {
                    grid[i][j] = true;
                    contadorPoblacion++;
                }
            }
        }population = contadorPoblacion;
        // igualamos la variable population (inicializada en 0) al contador de poblacion obtenido tras el recuento de casillas true del bucle for.

    }

        // el metodo contar vecinos se encarga de comprobar cuantas casillas vivas colindan con la posicion enviada desde el metodo que veremos a continuacion
        // que recorre todas las posiciones de la matriz principal y retornará un entero que se almacenará en la variable vecinosVivos.
    private static int contarVecinos(int i, int j) {
        //creamos una variable para llevar la cuenta del numero de casillas vivas (true) que colindan con la posicion indicada por el  metodo siguiente (siguientePoblacion)
        int vecinosVivos = 0;
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k >= 0 && k < grid.length && l >= 0 && l < grid[0].length) {
                    if ((k != i || l != j) && grid[k][l]) {
                        vecinosVivos++;
                    }
                }
            }
        }
        return vecinosVivos;
    }

    // El metodo siguientePoblacion se encarga de actualizar la nueva generación de la poblacion teniendo en cuenta las reglas de superviviencia y muerte.
    //Ademas cuando termina el bucle se actualiza la matriz inicial, con la segunda generacion (Nueva matriz creada a partir de las reglas de supervivencia y muerte
    public static void siguienteGeneracion() {
        boolean[][] nuevoTablero = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < nuevoTablero.length; i++) {
            for (int j = 0; j < nuevoTablero[i].length; j++) {
                if (grid[i][j]) {       // cuando una celula pasa de estar viva a estar muerta se resta 1 a la poblacion que teniamos
                    if (contarVecinos(i, j) < 2 || contarVecinos(i, j) > 3) {
                        nuevoTablero[i][j] = false;
                        population--;
                    } else {
                        nuevoTablero[i][j] = true;
                    }

                }
                if (!grid[i][j]) {
                    if (contarVecinos(i, j) == 3) {
                        nuevoTablero[i][j] = true;
                        population++;       //Cuando una casilla muerta pasa a estar viva se suma 1 a la poblacion que teniamos
                    } else {
                        nuevoTablero[i][j] = false;
                    }
                }
            }
        }   // cuando acaba de generar la nueva matriz el contador de generacion aumenta en uno por cada vez que pasemos a una nueva matriz
        generation++;
        grid = nuevoTablero;    // actualizamos los valores de la matriz anterior con los valores de la nueva.
    }

    public static void init() {     //En el metodo init reiniciamos los valores para una nueva partida

        grid = new boolean[grid.length][grid[0].length];
        population = getPopulation();
        fillRandom(grid);
        generation = 0;
    }

    public static void tick() {     //el metodo tic unicamente se encarga de llamar al metodo siguienteGeneracion para crear el siguiente tablero del juego
        siguienteGeneracion();
    }

    public static boolean[][] getGrid() {       //Envia la matriz conforme se va actualizando para mostrarlo en la interfaz grafica
        return grid;
    }

    public static int getPopulation() {     //Envia la poblacion viva de esa generacion conforme se va actualizando para mostrarlo en la interfaz grafica
        return population;
    }

    public static int getGeneration() {     //Envia la generacion conforme se va actualizando para mostrarlo en la interfaz grafica
        return generation;
    }
}
