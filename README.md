# MineCalc
A forge mod that will introduce a calculator in the game. It is for version 1.7.10.
##Developing
To get started you will need to open the command prompt at this repository. Do this by shift-rightclicking in Windows Explorer and clicking "Open command window here" or opening the command prompt and navigating to the repository using the cd command. Next type "gradlew setupDecompWorkspace --refresh-dependencies", wait for that to finish, then type "gradlew eclipse" to finish the installation. After this you can open the "eclipse" folder as an eclipse workspace to begin working. Obviously requires eclipse to do this. You can also try using IntelliJ IDEA, but I'm not sure if that will work here.
##Other useful commands
Using "gradlew build" from your command prompt (that is open at the repository) builds the mod into a distributable .jar and "gradlew runClient" or "gradlew runServer" start a forge client/server with the mod installed.
##To-do
- Register command
- Process command
- Output info
- Create server version
- Create client version (if possible)
