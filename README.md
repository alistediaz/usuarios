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
#### Parámetro: ###
En el `body` del request, debe ir un objeto `JSON` con la siguiente estructura:

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


#### Respuesta no exitosa del Endpoint: ####
| Campo JSON | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `error` | `Array de ErrorStruct` | Arreglo de errores detectados por el Endpoint |


#### Tipo `ErrorStruct`: ####
| Campo JSON | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `timestamp` | `date` | Fecha del error |
| `codigo` | `number` | Código del error |
| `detail` | `string` | Detalle del error |


## Uso/Ejemplos

```javascript
//-------------------------------
//--- Ejemplo de resquest exitoso
//-------------------------------

HTTP Method: POST
Endpoint: /sign-up

request body: {
    "name": "Richard",
    "email": "richard@java.com",
    "password": "L2asfx7k",
    "phones": [
        {
            "number": "87650009",
            "cityCode": "7",
            "countryCode": "25"
        },
        {
            "number": "87650019",
            "cityCode": "6",
            "countryCode": "24"
        }
    ]
}

response body: {
    "id": 1,
    "name": "Richard",
    "email": "richard@java.com",
    "password": "L2asfx7k",
    "created": "2023-12-03T23:31:54.026+00:00",
    "lastLogin": "2023-12-03T23:31:54.026+00:00",
    "phones": [
        {
            "id": 1,
            "number": 87650009,
            "cityCode": 7,
            "countryCode": "25"
        },
        {
            "id": 2,
            "number": 87650019,
            "cityCode": 6,
            "countryCode": "24"
        }
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSaWNoYXJkIiwiaWF0IjoxNzAxNjQ2MzE0LCJleHAiOjE3MDE2NjQzMTR9.2VnBAUzUyongVe-IthX0id7g4rvoU4VrwwziFzZvR-s",
    "active": true
}

//----------------------------------
//--- Ejemplo de resquest no exitoso
//----------------------------------

HTTP Method: POST
Endpoint: /sign-up

request body: {
    "name": "Richard",
    "email": "rich@ard@java.com",
    "password": "L2a4545sfx7k",
    "phones": [
        {
            "number": 87650009,
            "cityCode": 7,
            "countryCode": "25"
        },
        {
            "number": 87650019,
            "cityCode": 6,
            "countryCode": "24"
        }
    ]
}

response body: {
    "error": [
        {
            "timestamp": "2023-12-03T20:37:21.7835988",
            "codigo": 1,
            "detail": "Formato de email no válido"
        },
        {
            "timestamp": "2023-12-03T20:37:21.7835988",
            "codigo": 2,
            "detail": "La contraseña no cumple con las reglas"
        },
        {
            "timestamp": "2023-12-03T20:37:21.8034043",
            "codigo": 3,
            "detail": "Usuario ya existe: Richard"
        }
    ]
}
```

##
#### Login usuario

```http
  GET /login
```
#### Parámetro: ###
En el `Header` del request, debe ir un valor de tipo `string` de nombre `Authorization` con la siguiente estructura:

| HTTP Header | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `Authorization` | `string` | **Required**. `"Bearer "` + el token JWT obtenido desde el Endpoint `/sign-up` |

#### Respuesta exitosa del Endpoint: ####
| Campo JSON | Tipo     | Descripción                |
| :-------- | :------- | :------------------------- |
| `id` | `number` | ID de Usuario |
| `name` | `string` | Nombre de usuario |
| `email` | `string` | Email de usuario |
| `password` | `string` | Contraseña de usuario |
| `created` | `date` | Fecha de creación del registro |
| `lastLogin` | `date` | Fecha del último acceso del usuario al sistema |
| `token` | `string` | Token JWT de acceso actualizado |
| `active` | `boolean` | Indica si el usuario está activo en el sistema |
| `phones` | `Array de Phones` | Arreglo que contiene información de números telefónicos del usuario |


## Uso/Ejemplos

```javascript
//-----------------------
//--- Ejemplo de resquest
//-----------------------

HTTP Method: GET
Endpoint: /login

Header:
Authorization = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSaWNoYXJkIiwiaWF0IjoxNzAxNjQ2ODc4LCJleHAiOjE3MDE2NjQ4Nzh9.4NpZ3s0FGYd21CVrNJkVMsKy10Ve5g6ogssDDdh-hfY"
request body: {}// vacío

response body: {
    "id": 1,
    "created": "2023-12-03T23:41:18.681+00:00",
    "lastLogin": "2023-12-03T23:41:18.681+00:00",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSaWNoYXJkIiwiaWF0IjoxNzAxNjQ3ODczLCJleHAiOjE3MDE2NjU4NzN9.NhH2Y8mOTEWYJflIF_P6hoUBDBAn08OwXaPeZuHGmUk",
    "name": "Richard",
    "email": "richard@java.com",
    "password": "L2asfx7k",
    "phones": [
        {
            "id": 1,
            "number": 87650009,
            "cityCode": 7,
            "countryCode": "25"
        },
        {
            "id": 2,
            "number": 87650019,
            "cityCode": 6,
            "countryCode": "24"
        }
    ],
    "active": true
}
```
