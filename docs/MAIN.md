# Clase Main - Simulador de Mercado de Valores 🎯

## 📋 Descripción General
`Main` es la clase ejecutable que **orquesta toda la simulación**. Define el escenario, crea todos los activos e inversores, y simula eventos de mercado para demostrar cómo funciona el sistema.

---

## 🚀 Flujo General (Paso a Paso)

```
INICIO
  ↓
1. Inicializar el Mercado (gestor central)
  ↓
2. Crear Activos (Acciones, Cripto, Bonos)
  ↓
3. Registrar Activos en el Mercado
  ↓
4. Crear Inversores (con perfiles diferentes)
  ↓
5. Registrar Inversores en el Mercado
  ↓
6. Mostrar Análisis de Activos
  ↓
7. Simular Compras (validar perfiles de riesgo)
  ↓
8. Ejecutar evento: CRISIS DE MERCADO
  ↓
9. Mostrar Resultados Post-Crisis
  ↓
10. Calcular Ganancias/Pérdidas del Mes
  ↓
FIN
```

---

## 🔍 Análisis Detallado: Línea por Línea

### SECCIÓN 1: INICIALIZACIÓN

```java
Mercado mercado = new Mercado();
```
**¿Qué hace?**
- Crea la instancia central del gestor de mercado
- Inicializa dos ArrayList vacíos: `activos` e `inversores`

**Estado después:**
```
mercado.activos = []
mercado.inversores = []
```

---

### SECCIÓN 2: CREACIÓN DE ACTIVOS

#### 2.1 Crear Acciones

```java
Accion apple = new Accion("AAPL", "Apple Inc.", "Apple Corp", 0.25, "Tecnología", 15000);
Accion santander = new Accion("SAN", "Banco Santander", "Santander Group", 0.15, "Financiero", 50000);
```

**¿Qué pasa en cada línea?**

**Apple:**
- `código`: "AAPL"
- `nombre`: "Apple Inc."
- `empresa`: "Apple Corp"
- `dividendoPorAccion`: 0.25€
- `sector`: "Tecnología" ← IMPORTANTE: dividendo se reduce 30%
- `numAccionesEnCirculacion`: 15000

**Constructor de Accion hace:**
1. Llama a `Activo("AAPL", "Apple Inc.")`
2. `Activo` genera 30 precios aleatorios entre 10€ y 500€
3. Asigna el último precio como `precioActual`
4. Asigna los 5 atributos específicos de Accion

**Resultado:**
```
apple.precioActual = ~XXX€ (random entre 10-500)
apple.historialPrecios = [precio1, precio2, ..., precio30]
```

**Santander:**
- Similar a Apple, pero:
- `dividendoPorAccion`: 0.15€
- `sector`: "Financiero" ← dividendo NO se reduce
- `numAccionesEnCirculacion`: 50000

---

#### 2.2 Crear Criptomonedas

```java
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", "Ethereum Network", 120000000, 1000000);
eth.addWalletGrande(35.0);
```

**¿Qué pasa?**

**Primera línea - Constructor:**
- `código`: "ETH"
- `nombre`: "Ethereum"
- `blockchain`: "Ethereum Network"
- `maxSupply`: 120,000,000 (máximo de ETH)
- `minadoRestante`: 1,000,000 (aún por emitir)

**Constructor hace:**
1. Llama a `Activo("ETH", "Ethereum")`
2. Genera 30 precios históricos
3. Inicializa `walletsGrandes` como ArrayList vacío

**Segunda línea - Agregar ballena:**
```java
eth.addWalletGrande(35.0);
```
- Una ballena posee el 35% de toda la ETH
- Esto aumentará la volatilidad en `calcularVolatilidad()` (+0.5)

---

### SECCIÓN 3: REGISTRAR ACTIVOS EN EL MERCADO

```java
mercado.agregarActivo(apple);
mercado.agregarActivo(santander);
mercado.agregarActivo(eth);
```

**Estado después:**
```
mercado.activos[0] = apple
mercado.activos[1] = santander
mercado.activos[2] = eth
mercado.activos.size() = 3
```

---

### SECCIÓN 4: CREAR INVERSORES

```java
Inversor ana = new Inversor("22334455B", 10000.0, "Moderado");
Inversor pedro = new Inversor("88776655C", 5000.0, "Agresivo");
```

**Ana:**
- `dni`: "22334455B"
- `capital`: 10000€ (presupuesto inicial)
- `perfil`: "Moderado" ← Balance entre riesgo y seguridad

**Constructor inicializa:**
- `portfolio = []` (vacío, sin activos)
- `cantidades = []` (vacío)

**Pedro:**
- `dni`: "88776655C"
- `capital`: 5000€ (presupuesto más bajo)
- `perfil`: "Agresivo" ← Sin restricciones de riesgo

---

### SECCIÓN 5: REGISTRAR INVERSORES EN EL MERCADO

```java
mercado.agregarInversor(ana);
mercado.agregarInversor(pedro);
```

**Estado después:**
```
mercado.inversores[0] = ana
mercado.inversores[1] = pedro
mercado.inversores.size() = 2
```

---

### SECCIÓN 6: ANÁLISIS DE ACTIVOS

```java
System.out.println("=== ANÁLISIS DE ACTIVOS ===");

System.out.println("Dividendo Anual Apple (Tecnología -30%): $" + apple.dividendoAnual());
System.out.println("Dividendo Anual Santander (Normal): $" + santander.dividendoAnual());
```

**¿Qué calcula?**

**Apple (Tecnología - Reducción 30%):**
```
dividendoAnual = 0.25 × 4 × 0.70 = 0.70€
Salida: "Dividendo Anual Apple: $0.7"
```

**Santander (Financiero - Sin reducción):**
```
dividendoAnual = 0.15 × 4 = 0.60€
Salida: "Dividendo Anual Santander: $0.6"
```

**¿Por qué diferente?** Las empresas tech reinvierten ganancias en lugar de distribuir dividendos

---

```java
System.out.println("Volatilidad Acción (Estable): " + apple.calcularVolatilidad());
System.out.println("Volatilidad Cripto (Riesgosa): " + eth.calcularVolatilidad());
```

**¿Qué calcula?**

**Apple:**
- Usa desviación estándar de 30 precios históricos
- Típicamente: ~0.8 (bajo a medio)
- Salida: "Volatilidad Acción (Estable): 0.847"

**ETH:**
- Usa desviación estándar + factor 2.5 (cripto) + penalización ballena
- Con ballena del 35%: +0.5 adicional
- Típicamente: ~14.0 (muy alto)
- Salida: "Volatilidad Cripto (Riesgosa): 14.23"

---

### SECCIÓN 7: SIMULACIÓN DE COMPRAS

```java
System.out.println("\n=== SIMULACION DE COMPRAS ===");

System.out.print("Ana intenta comprar ETH: ");
ana.comprar(eth, 1);
```

**¿Qué sucede en `ana.comprar(eth, 1)`?**

1. **Validación: `puedeComprar(eth, 1)`**
   ```
   Costo = precioActual × cantidad
   Capital = 10000€
   Volatilidad ETH ≈ 14.0
   
   ¿Tiene dinero? Sí (10000 > precio)
   ¿Cumple perfil Moderado?
     Moderado: vol < 2.0 O tendencia = alcista
     14.0 < 2.0? NO
     ¿Tendencia alcista? Depende (probabilístico)
   
   → Si tendencia es bajista: RECHAZA
   → Si tendencia es alcista: COMPRA
   ```

2. **Salida esperada (caso típico):**
   ```
   "No se cumple el perfil de riesgo o capital insuficiente."
   Ana no compra (capital sigue en 10000€)
   ```

---

```java
System.out.print("Pedro intenta comprar ETH: ");
pedro.comprar(eth, 2);
```

**¿Qué sucede en `pedro.comprar(eth, 2)`?**

1. **Validación: `puedeComprar(eth, 2)`**
   ```
   Perfil = Agresivo
   Agresivo: return true (sin restricciones)
   
   → COMPRA APROBADA
   ```

2. **Ejecución de compra:**
   - `capital -= (precioActual × 2)`
   - Si precioActual ≈ 100€: capital = 5000 - 200 = 4800€
   - `portfolio[0] = eth`
   - `cantidades[0] = 2`

3. **Salida:**
   ```
   "Compra exitosa: 2 de Ethereum"
   ```

---

### SECCIÓN 8: EVENTO CRISIS

```java
System.out.println("\n=== EVENTO DE MERCADO: CRISIS ===");

System.out.println("Precio Apple antes de crisis: $" + apple.getPrecioActual());
mercado.crisis();
System.out.println("Precio Apple después de crisis: $" + apple.getPrecioActual());
```

**¿Qué hace `mercado.crisis()`?**

1. **Para TODOS los activos:**
   ```
   porcentajePerdida = random entre 20% y 40%
   
   Apple:
     Precio antes: 200€
     Pérdida: 25%
     Precio después: 200 × 0.75 = 150€
   
   ETH:
     Precio antes: 100€
     Pérdida: 35%
     Precio después: 100 × 0.65 = 65€
   ```

2. **Para TODOS los inversores agresivos:**
   ```
   Si perfil == "agresivo":
     capital = 0€
     Salida: "Inversor DNI 88776655C ha quebrado."
   ```

---

### SECCIÓN 9: RESULTADOS POST-CRISIS

```java
System.out.println("Capital de Pedro tras crisis (Agresivo): $" + pedro.getCapital());
System.out.println("Capital de Ana tras crisis (Moderada): $" + ana.getCapital());
```

**Resultados esperados:**
```
Capital de Pedro: 0€ (QUEBRÓ - es agresivo)
Capital de Ana: ~9700-9900€ (disminuyó pero sobrevivió)
```

---

### SECCIÓN 10: SIMULACIÓN MENSUAL

```java
double plusvaliaAna = ana.simularMes();
System.out.println("Plusvalía/Minusvalía de Ana este mes: $" + plusvaliaAna);
```

**¿Qué sucede en `simularMes()`?**

1. **Para cada activo en el portafolio de Ana:**
   ```
   Tenía: 5 acciones de Apple
   Precio actual (post-crisis): 150€
   
   Simula fluctuación random: +/- 1.5% (según volatilidad)
   Nuevo precio: 150 × 1.015 = 152.25€
   
   Ganancia: (152.25 - 150) × 5 = 11.25€
   ```

2. **Retorna ganancia total del mes:**
   ```
   plusvaliaAna = 11.25€ (o negativo si hay pérdidas)
   ```

---

## 📊 Tabla de Estados del Sistema

### Estado Inicial

| Entidad | Atributo | Valor |
|---------|----------|-------|
| Ana | Capital | 10000€ |
| Ana | Perfil | Moderado |
| Ana | Portafolio | Vacío |
| Pedro | Capital | 5000€ |
| Pedro | Perfil | Agresivo |
| Pedro | Portafolio | Vacío |
| Apple | Precio | ~XX€ (random) |
| ETH | Precio | ~XX€ (random) |

### Estado Después de Compras

| Entidad | Atributo | Valor |
|---------|----------|-------|
| Ana | Capital | 10000€ (no compró) |
| Ana | Portafolio | Vacío |
| Pedro | Capital | ~4800€ (compró 2 ETH) |
| Pedro | Portafolio | 2x ETH |

### Estado Post-Crisis

| Entidad | Atributo | Valor |
|---------|----------|-------|
| Ana | Capital | ~9700-9900€ |
| Ana | Portafolio | Vacío (no fue afectada) |
| Pedro | Capital | 0€ ¡QUEBRÓ! |
| Pedro | Portafolio | 2x ETH (bajaron 30-40%) |
| Apple | Precio | -20-40% |
| ETH | Precio | -20-40% |

---

## 🎓 Lecciones del Simulador

### Lección 1: Volatilidad Importa
- Apple (vol ~0.8): Relativamente segura
- ETH (vol ~14): Extremadamente volátil
- Ana (Moderada) evita ETH → Decisión correcta
- Pedro (Agresivo) compra ETH → Riesgo calculado

### Lección 2: Palancamiento es Peligroso
- Pedro tiene 5000€ pero actúa como si tuviera 10000€
- Crisis del 30% × Palanca 2x = Pérdida del 60%
- 60% de 10000€ = 6000€ de pérdida
- 6000€ > 5000€ → Quiebra (no puede pagar deuda)

### Lección 3: Perfil de Riesgo Funciona
- Conservador: Nunca compra nada volátil
- Moderado: Compra solo si hay señales positivas
- Agresivo: Compra todo sin restricciones
- En crisis: Conservador/Moderado sobreviven, Agresivo quiebra

### Lección 4: Diversificación Protege
- Ana con solo Apple en portafolio: -20-40% de pérdida
- Si Ana tuviera Bono + Apple: pérdidas menores (bono estable)

---

## 🔗 Flujo de Ejecución (Diagrama)

```
main()
  ├─ mercado = new Mercado()
  │
  ├─ CREAR ACTIVOS
  │  ├─ apple = new Accion(...)
  │  ├─ santander = new Accion(...)
  │  ├─ eth = new Criptomoneda(...)
  │  └─ eth.addWalletGrande(35.0)
  │
  ├─ REGISTRAR ACTIVOS
  │  ├─ mercado.agregarActivo(apple)
  │  ├─ mercado.agregarActivo(santander)
  │  └─ mercado.agregarActivo(eth)
  │
  ├─ CREAR INVERSORES
  │  ├─ ana = new Inversor(...)
  │  └─ pedro = new Inversor(...)
  │
  ├─ REGISTRAR INVERSORES
  │  ├─ mercado.agregarInversor(ana)
  │  └─ mercado.agregarInversor(pedro)
  │
  ├─ ANÁLISIS (println)
  │  ├─ Dividendos
  │  └─ Volatilidades
  │
  ├─ SIMULACIÓN DE COMPRAS
  │  ├─ ana.comprar(eth, 1)  → Rechaza
  │  └─ pedro.comprar(eth, 2) → Acepta
  │
  ├─ CRISIS
  │  └─ mercado.crisis()
  │      ├─ Baja todos los precios -20-40%
  │      └─ Pedro quiebra (capital = 0)
  │
  ├─ RESULTADOS POST-CRISIS (println)
  │  ├─ Precios finales
  │  └─ Capitales finales
  │
  └─ SIMULACIÓN MENSUAL
     └─ ana.simularMes()  → Retorna plusvalía/minusvalía

FIN
```

---

