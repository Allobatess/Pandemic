## Ejecutar código

Para ejecutar el código abre la consola de tu equipo en la ruta ~/Pandemic-main.
Allí, ejecuta:
1. "javac -encoding UTF-8 -d bin src\estructurasDatos\*.java src\mundo\*.java src\mundo\cartas\*.java src\mundo\roles\*.java src\datosObjetos\*.java src\juego\mazo\*.java src\juego\*.java" 
2. "java -cp bin juego.Main".
   
Con esto el juego inicializará su ejecución

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.
