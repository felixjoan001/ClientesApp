Datos importantes antes de ejecutar

descargar la base de datos adventurewoks2019
https://github.com/Microsoft/sql-server-samples/releases/download/adventureworks/AdventureWorks2019.bak

API NODE
1.- abrir puertos
2.- conocer nuestra ip usando win+r -> cmd ->ipconfig -> usaremos la ipv4

3.- modificar el archivo index reemplazando la ip con la nuestra ClientesApp -> Api_Node -> src
![image](https://github.com/user-attachments/assets/820a0f94-5d88-4a13-b721-6064a52af26b)

4.- modificar el archivo connection.js reemplazando la contraseña por la nuestra
ClientesApp -> Api_Node -> src -> database
![image](https://github.com/user-attachments/assets/2ca5a905-b067-4238-8fed-2a9f9e769846)

ejecutar el Api_Node (importante tener node.js instalado)
1.- abrimos una terminal em la carpeta ClientesApp -> Api_Node
2.- ejecutamos el comando  npm i nodemon
3.- ejecutamos el comando npm run dev
![image](https://github.com/user-attachments/assets/86311d2a-5e23-4d9d-bcce-e965a0cbbe04)

4.- en caso de que falle borrar la carpeta node_modules, ejecutar comando npm i y seguir pasos 2 y 3

APLICACION ANDROID 

1.- Abrimos el proyecto Android Studio
2.- cambiamos la ip de nuestro API, app-> kotlin+java -> com.example.aw_person -> API
3.- cambiar la ip de network security, app-> res -> xml -> network_security_config.xml
4.- run app
5.- si todo esta bien configurado debera verse asi
![image](https://github.com/user-attachments/assets/b5776960-de2a-4858-be5e-981f9326375c)

![image](https://github.com/user-attachments/assets/2c8c7ff7-f214-4062-be8f-1aa4e01cac21)

![image](https://github.com/user-attachments/assets/69b6e934-11d6-4aba-97ce-0aa465e65824)

