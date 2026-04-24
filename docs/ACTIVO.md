# Clase Activo 📊

## 🎯 Descripción General
`Activo` es una **clase abstracta base** que representa cualquier instrumento financiero que puede ser comercializado en el mercado. Establece la estructura común para todos los activos del sistema (Acciones, Bonos, Criptomonedas).

---

## 📋 Atributos

### Atributos Protegidos (accesibles para subclases)

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `codigo` | String | Identificador único del activo (ej: "AAPL", "SAN", "ETH") |
| `nombre` | String | Nombre oficial del activo (ej: "Apple Inc.", "Bitcoin") |
| `precioActual` | double | Precio actual del activo en el mercado |
| `historialPrecios` | ArrayList<Double> | Registro histórico de los últimos 30 precios |

### Constantes Estáticas

| Constante | Valor | Descripción |
|-----------|-------|-------------|
| `cantidad` | 30 | Número de precios históricos que se generan al crear un activo |
| `precioMinimo` | 10.0 | Precio mínimo para generar precios aleatorios |
| `precioMaximo` | 500.0 | Precio máximo para generar precios aleatorios |

---

## 🏗️ Constructores

### Constructor Principal
```java
public Activo(String codigo, String nombre)
```

**Parámetros:**
- `codigo`: Identificador único del activo (cadena de texto)
- `nombre`: Nombre descriptivo del activo

**Funcionalidad:**
1. Asigna el código y nombre al activo
2. Inicializa el ArrayList `historialPrecios` como vacío
3. **Automáticamente** llama a `generarHistorialPrecios()` para poblar con 30 valores aleatorios

**Ejemplo de uso:**
```java
Accion apple = new Accion("AAPL", "Apple Inc.", ...);
// Aquí ya tiene 30 precios en su historial
```

---

## 🔧 Métodos

### 1. **Getters y Setters**

#### `getCodigo()`
```java
public String getCodigo()
```
- Retorna el código identificador del activo
- Usado para comparar activos en el portafolio

#### `getNombre()`
```java
public String getNombre()
```
- Retorna el nombre oficial del activo

#### `getPrecioActual()`
```java
public double getPrecioActual()
```
- Retorna el precio actual del activo

#### `setPrecioActual(double precioActual)`
```java
public void setPrecioActual(double precioActual)
```
- Actualiza el precio actual
- Usado cuando hay cambios en el mercado o crisis

---

### 2. **Métodos Abstractos**

#### `calcularVolatilidad()` ⭐ ABSTRACTO
```java
public abstract double calcularVolatilidad()
```

**Propósito:** Calcula el nivel de riesgo o inestabilidad del activo

**Retorna:** Un valor numérico (double) que representa qué tan volátil es el activo

**¿Quién lo implementa?**
- **Accion**: Usa desviación estándar de precios históricos
- **Bono**: Calcula según el rating de crédito (AAA, AA, A, BBB, Basura)
- **Criptomoneda**: Usa desviación estándar más factor multiplicador (2.5) para mayor volatilidad

**Importancia:** Se usa en `Inversor.puedeComprar()` para validar si el inversor puede comprar según su perfil de riesgo

---

### 3. **Métodos de Gestión de Precios**

#### `generarHistorialPrecios()`
```java
public void generarHistorialPrecios()
```

**Proceso paso a paso:**
1. Crea un generador de números aleatorios
2. **Loop 30 veces:**
   - Genera un precio aleatorio entre 10.0 y 500.0
   - Lo añade al `historialPrecios`
3. Asigna el último precio generado como `precioActual`

**Ejemplo de ejecución:**
```
Generado precio 1: 245.32
Generado precio 2: 189.47
...
Generado precio 30: 312.88
precioActual = 312.88
```

---

#### `getUltimos7Precios()`
```java
public List<Double> getUltimos7Precios()
```

**Funcionalidad:**
- Devuelve una nueva lista con **los últimos 7 precios** del historial
- Si hay menos de 7 precios registrados, devuelve todos los disponibles
- Útil para análisis de tendencias a corto plazo

**Cálculo:**
```java
int n = historialPrecios.size();
return new ArrayList<>(historialPrecios.subList(Math.max(0, n - 7), n));
```

**Ejemplo:**
```
Si historialPrecios tiene 30 elementos:
- Retorna elementos desde posición 23 hasta 29 (últimos 7)

Si historialPrecios tiene 5 elementos:
- Retorna elementos desde posición 0 hasta 4 (todos, menos de 7)
```

---

### 4. **Análisis de Tendencias**

#### `tendencia()`
```java
public String tendencia()
```

**Propósito:** Determina la dirección del precio del activo

**Retorna:** Una de estas 3 cadenas:
- `"alcista"` - El mercado está subiendo
- `"bajista"` - El mercado está bajando
- `"estable"` - El mercado es neutral

**Lógica de cálculo:**

| Condición | Tendencia | Explicación |
|-----------|-----------|-------------|
| Promedio últimos 7 días > Promedio últimos 30 días | **Alcista** | Precio subiéndose recientemente |
| Precio actual < 80% del promedio de 30 días | **Bajista** | Precio muy bajo comparado con el promedio |
| Cualquier otro caso | **Estable** | El precio está estable |

**Ejemplo práctico:**
```
historialPrecios: [100, 102, 101, 103, 105, 104, 102, 100, 99, ...]
Promedio 30 días: 102
Promedio últimos 7: 103
Precio actual: 107

¿107 > 80% de 102? Sí (107 > 81.6) → no es bajista
¿103 > 102? Sí → ALCISTA ✅
```

---

## 🔗 Relaciones con Otras Clases

```
Activo (Clase Abstracta Base)
  ├── Accion (Subclase)
  ├── Bono (Subclase)
  └── Criptomoneda (Subclase)

Usado por:
  ├── Mercado (contiene ArrayList<Activo>)
  ├── Inversor (contiene ArrayList<Activo> en su portafolio)
```

---

## 💡 Casos de Uso

### Caso 1: Crear un activo
```java
Accion apple = new Accion("AAPL", "Apple Inc.", ...);
// El constructor de Activo genera automáticamente 30 precios
```

### Caso 2: Consultar volatilidad
```java
double vol = apple.calcularVolatilidad();
if (vol < 1.0) System.out.println("Bajo riesgo");
```

### Caso 3: Ver últimas fluctuaciones
```java
List<Double> ultimos7 = apple.getUltimos7Precios();
// Usar para gráficos o análisis técnico
```

### Caso 4: Determinar si comprar
```java
String tendencia = apple.tendencia();
if (tendencia.equals("alcista")) {
    inversor.comprar(apple, 5);
}
```

---

## 🎓 Conceptos Clave

### ¿Por qué es abstracta?
- No puedes crear directamente `new Activo(...)` 
- Obliga a las subclases a implementar `calcularVolatilidad()`
- Garantiza que cada tipo de activo calcula el riesgo de su propia manera

### ¿Por qué historialPrecios es importante?
- Permite análisis técnico (tendencias, volatilidad)
- Simula cambios reales de precios en el tiempo
- Base para decisiones de inversión

### Volatilidad = Riesgo
- Mayor volatilidad = Mayor riesgo = Posibles ganancias/pérdidas mayores
- Los inversores conservadores evitan activos volátiles
- Los agresivos no tienen restricciones

---

