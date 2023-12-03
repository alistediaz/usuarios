# Microservicio Usuarios #

## Pasos para ejecutar el proyecto: ##

1.- Clonar el repositorio desde GitHub: `gh repo clone alistediaz/usuarios`

2.- Abrir la consola del sistema, por ejemplo: `CMD.exe`

3.- En la consola, navegar al directorio del proyecto.

4.- Ejecutar el comando: `gradlew bootRun`

5.- Realizar llamadas de acuerdo a los endpoints definidos en el documento: `"/sign-up" y "/login"`


## API Reference

#### Sign Up usuario

```http
  POST /sign-up
```

| Campo JSON | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required**. Nombre de usuario |
| `email` | `string` | **Required**. Email de usuario |
| `password` | `string` | **Required**. Contraseña de usuario |
| `phones` | `Array de Phones` | Arreglo que contiene información de números telefónicos del usuario |


#### Tipo `Phones`: ####
| Campo JSON | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `number` | `number` | **Required**. Número telefónico |
| `cityCode` | `number` | **Required**. Código de ciudad |
| `countryCode` | `string` | **Required**. Código de país |


#### Respuesta exitosa del Endpoint: ####
| Campo JSON | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `id` | `number` | ID de Usuario |
| `name` | `string` | Nombre de usuario |
| `email` | `string` | Email de usuario |
| `password` | `string` | Contraseña de usuario |
| `created` | `date` | Fecha de creación del registro |
| `lastLogin` | `date` | Fecha del último acceso del usuario al sistema |
| `token` | `string` | Token JWT de acceso |
| `active` | `boolean` | Indica si el usuario está activo en el sistema |
| `phones` | `Array de Phones` | Arreglo que contiene información de números telefónicos del usuario |
