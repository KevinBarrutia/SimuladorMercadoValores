# Clase Bono 💳

## 🎯 Descripción General
`Bono` representa un **instrumento de deuda** emitido por gobiernos o empresas. El inversor presta dinero y recibe cupones (intereses) periódicos. Es generalmente más seguro que acciones pero con menores ganancias potenciales.

---

## 📋 Atributos

### Atributos Propios de Bono

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `paisEmisor` | String | País que emite el bono (ej: España, Argentina) |
| `rating` | String | Calificación de solvencia (AAA, AA, A, BBB, BASURA) |
| `vencimiento` | int | Años hasta que expira el bono |
| `cuponAnual` | double | Interés anual que genera el bono |

### Constante Estática

```java
private static final ArrayList<String> paisesEmergentes = 
    new ArrayList<>(Arrays.asList("Argentina", "Turquía", 
                                  "Venezuela", "Pakistan", "Nigeria"));
```
Lista de países considerados **emergentes** (mayor riesgo)

### Atributos Heredados de Activo
- `codigo`: Identificador (ej: "ES10", "AR20")
- `nombre`: Nombre descriptivo del bono
- `precioActual`: Precio en mercado secundario
- `historialPrecios`: Últimos 30 precios históricos

---

## 🏗️ Constructores

### Constructor Principal
```java
public Bono(String codigo, String nombre, String paisEmisor, 
            String rating, int vencimiento, double cuponAnual)
```

**Parámetros:**

| Parámetro | Tipo | Ejemplo | Descripción |
|-----------|------|---------|-------------|
| `codigo` | String | "ES10" | Identificador único |
| `nombre` | String | "Bono España 10Y" | Nombre descriptivo |
| `paisEmisor` | String | "España" | País emisor |
| `rating` | String | "AAA" | Grado de confianza |
| `vencimiento` | int | 10 | Años hasta vencimiento |
| `cuponAnual` | double | 3.5 | Interés % anual |

**Ejemplo de creación:**
```java
// Bono seguro de un país desarrollado
Bono bonoEspaña = new Bono("ES10", "Bono España 10Y", 
                           "España", "AAA", 10, 3.5);

// Bono riesgoso de un país emergente
Bono bonoArgentina = new Bono("AR20", "Bono Argentina 20Y", 
                              "Argentina", "BBB", 20, 8.5);
```

**¿Qué pasa en el constructor?**
1. Llama a `Activo(codigo, nombre)` - genera 30 precios históricos
2. Asigna país, rating, vencimiento y cupón anual

---

## 🔧 Métodos

### 1. **calcularVolatilidad()** ⭐

```java
@Override
public double calcularVolatilidad()
```

**Propósito:** Calcula el riesgo del bono según dos factores

**Paso 1: Determinar volatilidad base por Rating**

```java
switch (rating.toUpperCase()) {
    case "AAA":     base = 0.0;      // Ultra seguro (0%)
    case "AA":      base = 0.125;    // Muy seguro (12.5%)
    case "A":       base = 0.25;     // Seguro (25%)
    case "BBB":     base = 0.375;    // Riesgoso (37.5%)
    case "BASURA":  base = 0.5;      // Muy riesgoso (50%)
    default:        base = 0.25;     // Por defecto (25%)
}
```

| Rating | Significado | Volatilidad | Riesgo |
|--------|------------|-------------|--------|
| AAA | Máxima solvencia | 0.0 | ⬇️ Mínimo |
| AA | Muy solvente | 0.125 | ⬇️ Muy bajo |
| A | Solvente | 0.25 | ⬇️ Bajo |
| BBB | Especulativo | 0.375 | ⬆️ Medio |
| BASURA | Altamente especulativo | 0.5 | ⬆️ Muy alto |

**Paso 2: Ajuste por vencimiento**

```java
if (vencimiento < 1) {
    base *= 0.5;  // Reduce a 50% si vence en menos de 1 año
}
```

**Lógica:** Bonos cercanos al vencimiento son menos riesgosos

**Ejemplo:**
```
Bono AAA (España, 10 años):
  base = 0.0 (rating AAA)
  vencimiento = 10 años (no se aplica ajuste)
  volatilidad = 0.0 (Sin riesgo)

Bono BBB (Argentina, 20 años):
  base = 0.375 (rating BBB)
  vencimiento = 20 años (no se aplica ajuste)
  volatilidad = 0.375 (37.5% de riesgo)

Bono BBB a vencer (próximamente):
  base = 0.375
  vencimiento = 0 años (aplica ×0.5)
  volatilidad = 0.1875
```

---

### 2. **riesgoPais()** 🌍

```java
public String riesgoPais()
```

**Propósito:** Ajusta el rating según el país de origen

**¿Por qué es importante?** 
- Mismo rating en diferentes países = Diferente riesgo real
- Un bono BBB de Argentina es más riesgoso que uno de USA

**Lógica:**

```java
SI el país está en paisesEmergentes:
    Reduce el rating un nivel:
    - AAA → AA
    - AA → A
    - A → BBB
    - Otros → "basura"
    
SI NO:
    Retorna el rating sin cambios
```

**Ejemplo práctico:**

```
Países emergentes: ["Argentina", "Turquía", "Venezuela", "Pakistan", "Nigeria"]

Bono España AAA:
  riesgoPais() = "AAA" (no cambia, no es emergente)

Bono Argentina AAA:
  riesgoPais() = "AA" (reduce 1 nivel por ser emergente)

Bono Turquía BBB:
  riesgoPais() = "basura" (reduce 1 nivel)
```

---

## 📊 Tabla Comparativa de Volatilidades

| Situación | Rating | Vencimiento | Volatilidad Base | Ajuste | Final | Significado |
|-----------|--------|-------------|------------------|--------|-------|------------|
| Bono seguro | AAA | 10 años | 0.0 | - | **0.0** | Muy seguro |
| Bono corto plazo | AA | <1 año | 0.125 | ×0.5 | **0.0625** | Muy bajo riesgo |
| Bono especulativo | BBB | 10 años | 0.375 | - | **0.375** | Medio riesgo |
| Bono basura emergente | BASURA | 5 años | 0.5 | - | **0.5** | Alto riesgo |

---

## 💡 Caso de Uso Completo

```java
// 1. Crear bonos de diferentes riesgos
Bono bonoAlemán = new Bono("DE10", "Bono Alemania", 
                           "Alemania", "AAA", 10, 1.5);
                           
Bono bonoArgentina = new Bono("AR20", "Bono Argentina", 
                              "Argentina", "BBB", 20, 12.0);

// 2. Comparar riesgos
System.out.println("Volatilidad Alemania: " + bonoAlemán.calcularVolatilidad());
// Output: 0.0 (sin riesgo)

System.out.println("Volatilidad Argentina: " + bonoArgentina.calcularVolatilidad());
// Output: 0.375 (riesgo del 37.5%)

System.out.println("Rating ajustado Argentina: " + bonoArgentina.riesgoPais());
// Output: basura (BBB se reduce por ser emergente)

// 3. Inversor conservador evita Argentina
Inversor inversor = new Inversor("12345678A", 5000, "Conservador");

if (bonoAlemán.calcularVolatilidad() < 1.0) {
    inversor.comprar(bonoAlemán, 10); // Compra sin problemas
}

if (bonoArgentina.calcularVolatilidad() < 1.0) {
    inversor.comprar(bonoArgentina, 5); // No cumple el perfil
} else {
    System.out.println("Demasiado riesgoso para perfil conservador");
}
```

---

## 🔗 Relaciones con Otras Clases

```
Bono
  ├── Extiende: Activo
  └── Usado por:
      ├── Mercado (almacena en ArrayList<Activo>)
      ├── Inversor (guarda en portafolio)
      └── Main (se puede crear y negociar)
```

---

## 🎓 Conceptos Clave

### Rating de Crédito (S&P)
- **AAA**: Mejor calificación, menor riesgo
- **AA-BBB**: Inversión de grado
- **BB o inferior**: Especulativo/Bonos basura
- **BASURA**: Alto riesgo de impago

### Riesgo País
- Argentina, Venezuela, Turquía son **emergentes** (más riesgosas)
- Reducen su rating un nivel automáticamente
- Compensan con cupones más altos (12% vs 1.5%)

### Vencimiento
- Bonos a corto plazo: Menos volátiles
- Bonos a largo plazo: Más expuestos a cambios económicos

### Cupón vs Precio
- Cupón fijo: Paga el mismo interés siempre
- Precio fluctúa: En mercado secundario puede valer más/menos
- Historialmente: Simula estas variaciones

---

