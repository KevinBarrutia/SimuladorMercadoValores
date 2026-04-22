# Contenido del archivo Markdown detallado
markdown_content = """# Documentación Técnica: Sistema de Gestión de Activos y Criptomonedas

Este documento detalla la estructura, lógica y propósito de las clases Java `Activo` y `Criptomoneda`.

---

## 1. Clase Abstracta: `Activo`
Es la clase base del sistema. Define la estructura mínima que debe tener cualquier activo financiero antes de ser analizado. Al ser abstracta, no se pueden crear objetos directamente de ella.

### Atributos
| Atributo | Tipo | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `codigo` | `String` | `protected` | Identificador único corto (ej: "BTC", "ETH"). |
| `nombre` | `String` | `protected` | Nombre completo del activo. |
| `precioActual` | `double` | `protected` | Último precio registrado en el mercado. |
| `historialPrecios` | `ArrayList<Double>` | `protected` | Lista con los últimos 30 precios simulados. |

### Constructor
* **`Activo(String codigo, String nombre)`**:
    * Asigna la identificación básica.
    * Inicializa la lista de historial.
    * **Ejecuta `generarHistorialPrecios()`**, asegurando que el activo nazca con datos de mercado.

### Métodos
* **`generarHistorialPrecios()`**: Genera 30 valores aleatorios entre 10.0 y 500.0. Actualiza el `precioActual` con el último valor de la serie.
* **`getUltimos7Precios()`**: Retorna una sublista con los datos más recientes. Es la base para el análisis de volatilidad.
* **`calcularVolatilidad()` (Abstracto)**: Define la obligación para las clases hijas de implementar su propio algoritmo de riesgo.

---

## 2. Clase: `Criptomoneda`
Clase que extiende de `Activo`. Modela el comportamiento específico de activos digitales en una red Blockchain.

### Atributos Específicos
| Atributo | Tipo | Acceso | Descripción |
| :--- | :--- | :--- | :--- |
| `blockchain` | `String` | `private` | Red de operación (ej: "Ethereum", "Polygon"). |
| `maxSupply` | `double` | `private` | Límite máximo de monedas que pueden existir. |
| `minadoRestante` | `double` | `private` | Cantidad de monedas por emitir. |
| `walletsGrandes` | `ArrayList<Double>` | `private` | Lista de porcentajes controlados por grandes inversores. |

### Método: `calcularVolatilidad()`
Implementa un análisis de riesgo avanzado basado en estadística:

1.  **Media**: Calcula el promedio de los últimos 7 precios.
2.  **Varianza**: Promedio de las distancias al cuadrado entre cada precio y la media.
3.  **Desviación Estándar**: Raíz cuadrada de la varianza.
4.  **Ajuste Cripto**: Multiplica el resultado por **2.5** debido a la naturaleza volátil del mercado digital.
5.  **Riesgo de Centralización**: Suma un **+0.5** adicional si detecta que alguna billetera posee más del 20% del activo.

### Método: `centralizacion()`
Clasifica el activo según la concentración de sus billeteras:
* **Alta**: Si la wallet principal tiene más del 30%.
* **Media**: Si las 3 principales suman más del 50%.
* **Baja**: Si el capital está distribuido de forma más equitativa.

---
*Documentación generada para el Proyecto de Programación Orientada a Objetos.*
"""