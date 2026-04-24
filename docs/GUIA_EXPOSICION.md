# 📊 GUÍA DE EXPOSICIÓN - Simulador de Mercado de Valores

## 🎯 Objetivo del Proyecto
Crear un **simulador de mercado de valores** que demuestre cómo funciona la negociación de activos financieros en Java, con diferentes tipos de inversores y escenarios de crisis.

---

## 📑 Índice de Exposición (Orden Recomendado)

1. **Contexto y Objetivo** (2 minutos)
2. **Arquitectura del Sistema** (3 minutos)
3. **Las 7 Clases** (10 minutos)
4. **Demostración del Main** (5 minutos)
5. **Flujo Completo de Ejecución** (5 minutos)

---

# 1️⃣ CONTEXTO Y OBJETIVO (Comienza por aquí)

### ¿Qué es el Proyecto?
Un simulador Java que modela un mercado financiero donde:
- ✅ Existen diferentes tipos de activos (Acciones, Bonos, Criptomonedas)
- ✅ Hay inversores con diferentes perfiles de riesgo
- ✅ Los precios fluctúan de forma realista
- ✅ Ocurren eventos de crisis que afectan a todos

### ¿Para Qué Sirve?
Entender cómo:
1. Los inversores toman decisiones según su perfil
2. La volatilidad afecta el riesgo de cada activo
3. Los eventos de mercado impactan diferentemente según el perfil
4. El apalancamiento puede causar quiebras

### ¿A Quién Interesa?
- Estudiantes de Finanzas
- Programadores Java
- Analistas de Sistemas
- Traders principiantes

---

# 2️⃣ ARQUITECTURA DEL SISTEMA (Muestra esto en un diagrama)

## Estructura General

```
                    ┌─────────────────────┐
                    │       MERCADO       │ (Gestor Central)
                    │ - activos[]         │
                    │ - inversores[]      │
                    │ - crisis()          │
                    └─────────────────────┘
                           ▲
                 ┌─────────┼─────────┐
                 │         │         │
            ┌────────┐ ┌────────┐ ┌──────────┐
            │ ACTIVOS│ │ACTIVOS │ │ ACTIVOS  │
            │CONCRETOS│ │ABSTRACTO│ │CONCRETOS│
            └────────┘ └────────┘ └──────────┘
               │          │            │
           ┌───┴──┐   ┌───┴───┐   ┌────┴─────┐
           │      │   │       │   │          │
        ACCION  BONO │ACTIVO  │ CRYPTO
                     │(Base)  │
                     └────────┘
```

## Componentes Clave

### 1. **Clase Activo (ABSTRACTA)**
- ✅ Base común para todos los instrumentos
- ✅ Gestiona historial de precios
- ✅ Define método abstracto: `calcularVolatilidad()`

### 2. **Clases Concretas (Heredan de Activo)**
- **Accion**: Acciones de empresas (Apple, Santander)
- **Bono**: Deuda con cupones (España, Argentina)
- **Criptomoneda**: Activos digitales (Bitcoin, Ethereum)

### 3. **Clase Inversor**
- Tiene capital y portafolio
- Valida compras según perfil (Conservador/Moderado/Agresivo)
- Gestiona listas paralelas: portfolio[] + cantidades[]

### 4. **Clase Mercado**
- Contiene todos los activos
- Contiene todos los inversores
- Ejecuta eventos (crisis)

### 5. **Clase Main**
- Orquesta la simulación
- Crea instancias de todo
- Demuestra el flujo completo

---

# 3️⃣ LAS 7 CLASES (Explica cada una)

## 📊 ACTIVO (Clase Abstracta Base)

### Responsabilidades:
- Generar y gestionar 30 precios históricos
- Calcular tendencias (alcista/bajista/estable)
- Almacenar info básica (código, nombre, precio)

### Atributos Clave:
```java
protected String codigo;           // "AAPL", "BTC"
protected String nombre;           // "Apple", "Bitcoin"
protected double precioActual;     // Precio ahora
protected ArrayList<Double> historialPrecios;  // Últimos 30
```

### Métodos Abstractos:
```java
public abstract double calcularVolatilidad();
// Cada subclase lo implementa diferente
```

### Datos Generados:
- 30 precios entre 10€ y 500€
- Se generan automáticamente en constructor

---

## 💰 ACCION (Heredero de Activo)

### Características Especiales:
- Paga dividendos (ingresos periódicos)
- Pertenece a una empresa en sector específico
- El sector AFECTA los dividendos

### Atributos:
```java
public String empresa;                    // "Apple Corp"
public double dividendoPorAccion;        // 0.25€
public String sector;                    // "Tecnología"
public int numAccionesEnCirculacion;     // 15000
```

### Lógica Especial - Dividendos:
```
SI sector == "Tecnología":
    dividendoAnual = dividendoPorAccion × 4 × 0.70
    (Reducido 30% porque tech reinvierte)
SINO:
    dividendoAnual = dividendoPorAccion × 4
    (Dividendo completo)
```

### Volatilidad:
- Usa desviación estándar de 30 precios
- Típicamente: 0.5-1.5 (bajo a medio)

**Ejemplo:**
```
Apple: Volatilidad ≈ 0.8
Santander: Volatilidad ≈ 1.2
```

---

## 💳 BONO (Heredero de Activo)

### Características Especiales:
- Instrumento de deuda (más seguro que acciones)
- Rating de solvencia (AAA, AA, A, BBB, BASURA)
- Riesgo país (emergentes = más riesgo)
- Vencimiento (afecta volatilidad)

### Atributos:
```java
public String paisEmisor;     // "España", "Argentina"
public String rating;         // "AAA", "BBB"
public int vencimiento;       // 10 años
public double cuponAnual;     // 3.5% interés
```

### Tabla de Volatilidades por Rating:

| Rating | Volatilidad | Significado |
|--------|-------------|-------------|
| AAA | 0.0 | Ultra seguro |
| AA | 0.125 | Muy seguro |
| A | 0.25 | Seguro |
| BBB | 0.375 | Especulativo |
| BASURA | 0.5 | Muy riesgoso |

### Riesgo País:
```
Países emergentes: Argentina, Turquía, Venezuela, Pakistan, Nigeria

Si es emergente:
  Rating se reduce 1 nivel (AAA → AA)
  Refleja riesgo político adicional
```

### Ejemplo:
```
Bono España AAA: vol = 0.0 (muy seguro)
Bono Argentina AAA: vol = 0.125 (reducido por emergente)
```

---

## 🪙 CRIPTOMONEDA (Heredero de Activo)

### Características Especiales:
- **Extremadamente volátil** (factor multiplicador 2.5)
- Riesgo por concentración de ballenas
- Suministro limitado (maxSupply)
- Red blockchain propia

### Atributos:
```java
private String blockchain;          // "Ethereum Network"
private double maxSupply;          // 120,000,000
private double minadoRestante;     // 1,000,000
private ArrayList<Double> walletsGrandes;  // [35.0, 20.0]
```

### Fórmula de Volatilidad:
```
volatilidad = desviación_estándar × 2.5 + (ballenas > 20% ? 0.5 : 0)
```

### Ejemplo:
```
Bitcoin sin ballenas grandes:
  vol ≈ 12.0

Ethereum con ballena del 35%:
  vol ≈ 14.0 (0.5 extra por concentración)
```

---

## 👤 INVERSOR

### Responsabilidades:
- Gestionar capital disponible
- Mantener portafolio de activos
- Validar compras según perfil de riesgo
- Calcular ganancias/pérdidas

### Atributos:
```java
private String dni;                    // "22334455B"
private double capital;               // 10000€ disponibles
private String perfil;                // "Moderado"

// Listas paralelas (mismo índice = misma acción)
private ArrayList<Activo> portfolio;   // [Apple, Bitcoin, Bono]
private ArrayList<Integer> cantidades; // [5, 2, 10]
```

### Los 3 Perfiles:

#### CONSERVADOR (Bajo Riesgo)
```
Compra SOLO si: volatilidad < 1.0
Ejemplo: Bonos seguros, Acciones estables
Rechaza: Criptomonedas, Bonos riesgosos
```

#### MODERADO (Balance)
```
Compra SI: (volatilidad < 2.0) O (tendencia = alcista)
Ejemplo: Acciones sólidas si tendencia positiva
Rechaza: Criptomonedas bajistas
```

#### AGRESIVO (Alto Riesgo)
```
Compra TODO sin restricciones
Ejemplo: Criptomonedas, Bonos especulativos
Ventaja: Máximas ganancias potenciales
Riesgo: Quiebra en crisis
```

### Método `comprar()`:
```
1. Valida si puedeComprar()
2. Resta dinero del capital
3. Agrega/Actualiza en portfolio
4. Sincroniza cantidades
```

---

## 🏛️ MERCADO

### Responsabilidades:
- Gestionar lista de todos los activos
- Gestionar lista de todos los inversores
- Ejecutar eventos globales (crisis)

### Atributos:
```java
private ArrayList<Activo> activos;        // [Apple, Bono, ETH]
private ArrayList<Inversor> inversores;   // [Ana, Pedro]
```

### Métodos Importantes:

#### `agregarActivo()` / `agregarInversor()`
```
Simplemente añade a las listas
```

#### `crisis()` ⚠️ [El evento más importante]
```
PASO 1: Todos los activos pierden 20-40%
  FOR cada activo:
    precio *= (1 - random(0.20, 0.40))

PASO 2: Inversores agresivos quiebran
  FOR cada inversor:
    IF perfil == "agresivo":
      capital = 0  ← QUIEBRA
```

---

## 🚀 MAIN (Orquestador)

### Flujo (En orden):

1. **Crear Mercado** → Contenedor vacío
2. **Crear Activos** → Apple, Santander, ETH
3. **Registrar Activos** → `mercado.agregarActivo()`
4. **Crear Inversores** → Ana (Moderada), Pedro (Agresivo)
5. **Registrar Inversores** → `mercado.agregarInversor()`
6. **Análisis** → Mostrar dividendos y volatilidades
7. **Compras** → Ana rechaza ETH, Pedro compra
8. **Crisis** → `mercado.crisis()`
9. **Resultados** → Mostrar estado final
10. **Simulación** → Calcular ganancias del mes

---

# 4️⃣ DEMOSTRACIÓN DEL MAIN (Ejecución Comentada)

## Salida Esperada:

```
=== ANÁLISIS DE ACTIVOS ===
Dividendo Anual Apple (Tecnología -30%): $0.7
Dividendo Anual Santander (Normal): $0.6
Volatilidad Acción (Estable): 0.847
Volatilidad Cripto (Riesgosa): 14.23

=== SIMULACION DE COMPRAS ===
Ana intenta comprar ETH: No se cumple el perfil de riesgo o capital insuficiente.
Pedro intenta comprar ETH: Compra exitosa: 2 de Ethereum

=== EVENTO DE MERCADO: CRISIS ===
Precio Apple antes de crisis: $234.56
Precio Apple después de crisis: $156.78
Capital de Pedro tras crisis (Agresivo): $0
Capital de Ana tras crisis (Moderada): $10000

=== RESULTADO FINAL DE CARTERAS ===
Plusvalía/Minusvalía de Ana este mes: $45.32
```

## Análisis de Resultados:

**¿Por qué Ana no compra ETH?**
- Ana es Moderada (vol < 2.0 O alcista)
- ETH tiene vol ≈ 14.0
- Tendencia de ETH probablemente bajista
- No cumple condiciones → RECHAZA

**¿Por qué Pedro sí compra?**
- Pedro es Agresivo
- Sin restricciones → COMPRA

**¿Qué pasa en la crisis?**
- Todos los precios bajan 20-40%
- Pedro (Agresivo) quiebra (capital = 0)
- Ana (Moderada) no se afecta (no tenía cripto)

---

# 5️⃣ FLUJO COMPLETO DE EJECUCIÓN

## Diagrama de Flujo

```
INICIO
  │
  ├─→ Mercado mercado = new Mercado()
  │   (Gestor central vacío)
  │
  ├─→ CREAR 3 ACTIVOS
  │   ├─ Apple (Accion, Sector Tecnología)
  │   ├─ Santander (Accion, Sector Financiero)
  │   └─ ETH (Criptomoneda, Ballena del 35%)
  │
  ├─→ REGISTRAR ACTIVOS EN MERCADO
  │   (mercado.activos = [Apple, Santander, ETH])
  │
  ├─→ CREAR 2 INVERSORES
  │   ├─ Ana (Moderada, 10000€)
  │   └─ Pedro (Agresivo, 5000€)
  │
  ├─→ REGISTRAR INVERSORES EN MERCADO
  │   (mercado.inversores = [Ana, Pedro])
  │
  ├─→ MOSTRAR ANÁLISIS
  │   ├─ Dividendos (0.7€ Apple, 0.6€ Santander)
  │   └─ Volatilidades (0.847 Apple, 14.23 ETH)
  │
  ├─→ ANA INTENTA COMPRAR ETH
  │   ├─ Validación: vol > 2.0 y tendencia bajista
  │   └─ RECHAZA (no cumple perfil Moderado)
  │
  ├─→ PEDRO COMPRA 2 ETH
  │   ├─ Validación: Agresivo = sin restricciones
  │   ├─ ACEPTA
  │   └─ Capital: 5000 → 4800€ (aproximado)
  │
  ├─→ CRISIS GLOBAL
  │   ├─ Apple: 234€ → 156€ (-33%)
  │   ├─ ETH: 100€ → 65€ (-35%)
  │   ├─ Santander: 150€ → 100€ (-33%)
  │   │
  │   └─ IMPACTO EN INVERSORES:
  │       ├─ Ana: No se afecta (sin portafolio)
  │       └─ Pedro: capital = 0€ ¡QUIEBRA!
  │           (Salida: "Inversor DNI 88776655C ha quebrado.")
  │
  ├─→ MOSTRAR RESULTADOS POST-CRISIS
  │   ├─ Precios finales: Todos bajaron
  │   └─ Capitales: Pedro = 0€, Ana = 10000€
  │
  ├─→ SIMULACIÓN MENSUAL DE ANA
  │   ├─ Simulación de fluctuaciones
  │   ├─ Cálculo de ganancias/pérdidas
  │   └─ Salida: "Plusvalía/Minusvalía: $XX.XX"
  │
  └─→ FIN
```

---

# 📈 CONCLUSIONES PEDAGÓGICAS

## Lo Que Enseña Este Proyecto

### 1. **Polimorfismo**
- Clase abstracta `Activo` con múltiples implementaciones
- Cada subclase tiene su propia `calcularVolatilidad()`

### 2. **Herencia**
- Accion, Bono, Criptomoneda heredan de Activo
- Comparten estructura común, comportamientos únicos

### 3. **Encapsulación**
- Datos protegidos/privados
- Acceso controlado mediante getters/setters

### 4. **Composición**
- Mercado contiene Activos e Inversores
- Inversores poseen Activos en su portafolio

### 5. **Estructuras de Datos**
- ArrayLists para colecciones dinámicas
- Listas paralelas para coordinar datos

### 6. **Lógica Empresarial**
- Validaciones según perfil de inversor
- Cálculos de volatilidad complejos
- Simulación de eventos de mercado

### 7. **Manejo de Aleatoriedad**
- `Random` para precios y crisis
- Simula incertidumbre del mundo real

---

# 🎤 TIPS PARA EXPONER

## Sección 1: Comienza Fuerte
> "Este proyecto simula un mercado de valores real en Java. Veremos cómo inversores con diferentes perfiles toman decisiones, y cómo una crisis afecta diferente a cada uno."

## Sección 2: Explica la Arquitectura
> "Hay 7 clases. Las 3 principales son: Activo (base abstracta), Inversor (quien invierte), y Mercado (que los une)."

## Sección 3: Clave: Volatilidad
> "La volatilidad es el concepto central. Apple tiene vol 0.8, Bitcoin 14. Los conservadores evitan Bitcoin, los agresivos lo compran."

## Sección 4: El Punto Climático
> "Cuando ocurre la crisis, todos pierden 20-40%. Pero los inversores agresivos quiebran porque usan palanca - apalancamiento."

## Sección 5: Cierra con Aprendizaje
> "Este proyecto enseña polimorfismo, herencia, y lógica de negocios reales en Java."

---

# 📋 CHECKLIST PARA EXPONER

- [ ] Mostrar estructura de carpetas
- [ ] Abrir cada clase en el IDE
- [ ] Ejecutar el Main
- [ ] Mostrar salida de consola
- [ ] Explicar cada línea del Main
- [ ] Hacer preguntas: "¿Por qué Ana rechaza ETH?"
- [ ] Enfatizar la crisis y quiebra de Pedro
- [ ] Mostrar código de `calcularVolatilidad()` en cada clase
- [ ] Explicar listas paralelas en Inversor
- [ ] Conclusión: Polimorfismo + Herencia + Lógica empresarial

---

