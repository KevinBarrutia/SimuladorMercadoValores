# Clase Accion 📈

## 🎯 Descripción General
`Accion` representa una **acción de una empresa** que cotizan en bolsa. Extiende a `Activo` y añade características específicas como dividendos, sector industrial y cantidad de acciones en circulación.

---

## 📋 Atributos

### Atributos Propios de Accion

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `empresa` | String | Nombre de la empresa que emite la acción |
| `dividendoPorAccion` | double | Dinero que genera cada acción periódicamente (ej: 0.25€) |
| `sector` | String | Sector industrial (Tecnología, Financiero, etc.) |
| `numAccionesEnCirculacion` | int | Cantidad total de acciones emitidas por la empresa |

### Atributos Heredados de Activo
- `codigo`: Identificador (ej: "AAPL", "SAN")
- `nombre`: Nombre de la acción
- `precioActual`: Precio actual en bolsa
- `historialPrecios`: Últimos 30 precios históricos

---

## 🏗️ Constructores

### Constructor Principal
```java
public Accion(String codigo, String nombre, String empresa, 
              double dividendoPorAccion, String sector, int numAccionesEnCirculacion)
```

**Parámetros:**

| Parámetro | Tipo | Ejemplo | Descripción |
|-----------|------|---------|-------------|
| `codigo` | String | "AAPL" | Símbolo bursátil único |
| `nombre` | String | "Apple Inc." | Nombre oficial de la acción |
| `empresa` | String | "Apple Corp" | Razón social de la empresa |
| `dividendoPorAccion` | double | 0.25 | Dividendo que paga cada acción (en €) |
| `sector` | String | "Tecnología" | Sector económico |
| `numAccionesEnCirculacion` | int | 15000 | Acciones totales emitidas |

**Ejemplo de creación:**
```java
Accion apple = new Accion("AAPL", "Apple Inc.", "Apple Corp", 
                          0.25, "Tecnología", 15000);
Accion santander = new Accion("SAN", "Banco Santander", 
                              "Santander Group", 0.15, "Financiero", 50000);
```

**¿Qué pasa en el constructor?**
1. Llama al constructor de `Activo` con código y nombre
2. El constructor de `Activo` genera automáticamente 30 precios históricos
3. Asigna todos los atributos específicos de Accion

---

## 🔧 Métodos

### 1. **Getters y Setters**

#### `getEmpresa()` / `setEmpresa(String empresa)`
```java
public String getEmpresa()
public void setEmpresa(String empresa)
```
- Obtiene/modifica el nombre de la empresa
- Usado cuando cambia la información corporativa

#### `getDividendoPorAccion()` / `setDividendoPorAccion(double dividendoPorAccion)`
```java
public double getDividendoPorAccion()
public void setDividendoPorAccion(double dividendoPorAccion)
```
- Obtiene/modifica el dividendo por acción
- Importante para inversores que buscan ingresos

#### `getSector()` / `setSector(String sector)`
```java
public String getSector()
public void setSector(String sector)
```
- Obtiene/modifica el sector industrial
- **IMPORTANTE**: Afecta el cálculo del dividendo anual

#### `getNumAccionesEnCirculacion()` / `setNumAccionesEnCirculacion(int numAccionesEnCirculacion)`
```java
public int getNumAccionesEnCirculacion()
public void setNumAccionesEnCirculacion(int numAccionesEnCirculacion)
```
- Obtiene/modifica la cantidad de acciones emitidas
- Afecta el valor total de mercado (capitalización)

---

### 2. **calcularVolatilidad()** ⭐

```java
@Override
public double calcularVolatilidad()
```

**Propósito:** Calcula el riesgo de la acción usando **desviación estándar**

**¿Cómo funciona? (Paso a paso)**

1. **Obtener datos:**
   ```
   Si historialPrecios está vacío → retorna 0.0
   Si hay precios → continúa
   ```

2. **Calcular promedio (media):**
   ```
   media = suma de todos los precios / cantidad de precios
   
   Ejemplo con 5 precios: [100, 102, 101, 103, 100]
   media = (100+102+101+103+100) / 5 = 506/5 = 101.2
   ```

3. **Calcular desviaciones al cuadrado:**
   ```
   Para cada precio:
   desviacion² = (precio - media)²
   
   [100-101.2]² = 1.44
   [102-101.2]² = 0.64
   [101-101.2]² = 0.04
   [103-101.2]² = 3.24
   [100-101.2]² = 1.44
   ```

4. **Calcular varianza y desviación estándar:**
   ```
   Suma de desviaciones² = 1.44 + 0.64 + 0.04 + 3.24 + 1.44 = 6.8
   varianza = 6.8 / 5 = 1.36
   volatilidad = √1.36 = 1.166
   ```

5. **Retorna:** 1.166 (grado de volatilidad)

**Interpretación:**
- **Volatilidad < 0.5**: Muy estable (Bajo riesgo)
- **Volatilidad 0.5-1.5**: Moderada (Riesgo medio)
- **Volatilidad > 1.5**: Alta (Alto riesgo)

---

### 3. **dividendoAnual()** 💰

```java
public double dividendoAnual()
```

**Propósito:** Calcula cuánto dinero genera anualmente una acción en dividendos

**Lógica especial:**

```
SI sector == "Tecnología"
    dividendoAnual = dividendoPorAccion × 4 × 0.70
    (Se reduce a 70% por política de reinversión)
    
SI NO (otro sector cualquiera)
    dividendoAnual = dividendoPorAccion × 4
    (Dividendo normal sin reducción)
```

**¿Por qué 4?** Representa 4 trimestres en un año

**Ejemplo práctico:**

```
Apple (Tecnología):
  dividendoPorAccion = 0.25€
  dividendoAnual = 0.25 × 4 × 0.70 = 0.70€ por acción

Santander (Financiero):
  dividendoPorAccion = 0.15€
  dividendoAnual = 0.15 × 4 = 0.60€ por acción
```

**En el Main se usa así:**
```java
System.out.println("Dividendo Anual Apple: $" + apple.dividendoAnual());
// Output: Dividendo Anual Apple: $0.7
```

---

## 📊 Ejemplo Completo de Uso

```java
// Crear una acción de Apple
Accion apple = new Accion(
    "AAPL",                    // Código
    "Apple Inc.",              // Nombre
    "Apple Corp",              // Empresa
    0.25,                      // Dividendo por acción
    "Tecnología",              // Sector
    15000                      // Acciones en circulación
);

// Consultar información
System.out.println("Empresa: " + apple.getEmpresa());
System.out.println("Precio actual: $" + apple.getPrecioActual());
System.out.println("Volatilidad: " + apple.calcularVolatilidad());
System.out.println("Dividendo anual: $" + apple.dividendoAnual());
System.out.println("Tendencia: " + apple.tendencia());

// Si es alcista, un inversor podría comprar
if (apple.tendencia().equals("alcista")) {
    inversor.comprar(apple, 5); // Compra 5 acciones
}
```

---

## 🔗 Relaciones con Otras Clases

```
Accion
  ├── Extiende: Activo
  └── Usado por:
      ├── Mercado (almacena en ArrayList<Activo>)
      ├── Inversor (guarda en portafolio)
      └── Main (crea instancias y las prueba)
```

---

## 🎓 Conceptos Clave

### Sector vs Dividendo
- Las acciones de **Tecnología** tienen dividendos reducidos (70%)
- Esto es realista: las empresas tech reinvierten ganancias
- Los bancos reparten dividendos completos

### Volatilidad de Acciones
- Generalmente **MÁS ESTABLE** que criptomonedas
- Generalmente **MÁS VOLÁTIL** que bonos
- Depende de la salud financiera de la empresa

### Historial de Precios
- Se genera automáticamente en el constructor del padre
- Simula 30 días de negociación
- Base para análisis técnico

---

