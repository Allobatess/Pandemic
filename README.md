## Ejecutar código

Con esto el juego inicializará su ejecución

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.


## Submission

- Course group: DS3
- Type: Project
- Student GitHub username or assigned team number: team-03 (@Allobatess, @edmuc, @conocidoComo)
- Assignment identifier or project milestone: Proyecto — Entrega 2
- Submission folder: `DS3/teams/team-03/projects/entrega-02/`
- Contributors and their roles: integrantes del team-03

## Compilación y ejecución (Windows CMD)

Para ejecutar el código abre la consola de tu equipo en la ruta ~/Pandemic-main.
Allí, ejecuta:

1. Para limpiar y compilar todos los paquetes desde la carpeta raíz del proyecto:
```cmd
if exist bin rmdir /s /q bin && mkdir bin && javac -encoding UTF-8 -d bin src\estructurasDatos\*.java src\datosObjetos\*.java src\mundo\*.java src\mundo\cartas\*.java src\mundo\roles\*.java src\juego\mazo\*.java src\juego\*.java
``` 

2. Para activar UTF-8 en la consola y ejecutar el juego:

```cmd
chcp 65001 && java -cp bin juego.Main
```

Como resultado se mostrará el menú interactivo para empezar a jugar.

## Checklist

- [x] I read and followed the applicable task instructions (or explained why this is not an assignment submission).
- [x] I used the correct course group and individual/team folder.
- [x] I changed only authorized files.
- [x] The submission README follows the task-specific format (or the general guide when none is specified); authorship and validation are documented in the required locations.
- [x] I ran the relevant checks and recorded their actual results above.
- [x] No credentials, private student records, or generated build files are included.
