# RESUMEN EJECUTIVO - Simulador de Mercado de Valores 🎯

## 📋 Tabla de Referencia Rápida

### Las 7 Clases en 1 Minuto

| Clase | Propósito | Característica Clave |
|-------|-----------|----------------------|
| **Activo** | Base abstracta | Genera 30 precios históricos, define volatilidad |
| **Accion** | Acciones de empresa | Dividendos con descuento por sector (Tech -30%) |
| **Bono** | Deuda de países | Rating (AAA-BASURA), riesgo país emergente |
| **Criptomoneda** | Activos digitales | Volatilidad ×2.5, riesgo por ballenas grandes |
| **Inversor** | Persona que invierte | 3 perfiles: Conservador (seg), Moderado (balance), Agresivo (riesgo) |
| **Mercado** | Gestor central | Contiene todos los activos e inversores, ejecuta crisis |
| **Main** | Orquestador | Simula compras, crisis, y resultado final |

---

## 💡 Conceptos Clave

### Volatilidad (= Riesgo)
```
Bono AAA: 0.0      → Ultra seguro → Solo dinero crece lentamente
Accion: 0.5-1.5    → Seguro      → Dinero crece moderadamente
Cripto: 10-15      → Muy riesgoso → Dinero puede crecer mucho o perderse
```

### Los 3 Perfiles de Inversor
```
CONSERVADOR:  vol < 1.0              (Solo seguros)
MODERADO:     vol < 2.0 O alcista    (Balance)
AGRESIVO:     sin restricciones      (Todo permitido)
```

### Crisis
```
1. Todos los precios bajan 20-40%
2. Inversores AGRESIVOS quiebran (capital = 0€)
3. Otros inversores sobreviven pero más pobres
```

---

## 🎬 Flujo del Main (10 pasos)

```
1. mercado = new Mercado()
2. Crear Apple, Santander, ETH
3. mercado.agregarActivo(apple, santander, eth)
4. Crear Ana (Moderada, 10000€), Pedro (Agresivo, 5000€)
5. mercado.agregarInversor(ana, pedro)
6. Mostrar dividendos y volatilidades
7. Ana intenta comprar ETH → RECHAZA (demasiado volátil)
8. Pedro compra 2 ETH → ACEPTA (agresivo)
9. mercado.crisis() → Precios bajan, Pedro quiebra
10. ana.simularMes() → Calcular ganancias/pérdidas
```

---

## 📊 Tabla Comparativa de Activos

| Activo | Volatilidad | Riesgo | Dividendo | Para Quién |
|--------|-------------|--------|-----------|-----------|
| Bono AAA (España) | 0.0 | Mínimo | 1.5% | Conservadores |
| Accion (Apple) | 0.8 | Bajo | 0.7€ | Moderados |
| Criptomoneda (ETH) | 14.0 | Máximo | 0% | Agresivos |

---

## 💼 Métodos Críticos

### Accion: `dividendoAnual()`
```java
SI sector == "Tecnología":
    return dividendoPorAccion × 4 × 0.70    // -30%
SINO:
    return dividendoPorAccion × 4           // Normal
```

### Criptomoneda: `calcularVolatilidad()`
```java
volatilidad = desv_estándar × 2.5 + (ballena > 20% ? 0.5 : 0)
```

### Inversor: `puedeComprar(Activo, cantidad)`
```java
Conservador: vol < 1.0
Moderado:    vol < 2.0 OR tendencia = alcista
Agresivo:    true (siempre)
```

### Mercado: `crisis()`
```java
Para cada activo:    precio *= random(0.60, 0.80)
Para cada inversor:  if (agresivo) capital = 0
```

---

## 🎓 Conceptos OOP Demorados

✅ **Herencia**: Accion, Bono, Cripto heredan de Activo
✅ **Polimorfismo**: Cada activo calcula volatilidad diferente
✅ **Encapsulación**: Datos privados/protegidos + getters/setters
✅ **Composición**: Mercado contiene Activos e Inversores
✅ **Listas Paralelas**: Inversor.portfolio[] + Inversor.cantidades[]

---

## ❓ Preguntas Típicas de Exposición

### "¿Por qué Ana rechaza ETH?"
R: Ana es Moderada. Puede comprar SI vol < 2.0 O es alcista. ETH tiene vol ≈ 14 y probablemente tendencia bajista. No cumple.

### "¿Por qué Pedro compra?"
R: Pedro es Agresivo. Sin restricciones. Compra todo.

### "¿Por qué quiebra Pedro en la crisis?"
R: Crisis reduce precio 30%. Pedro compró con palanca 2x (deuda). Pérdida 60% > capital original. No puede pagar. Quiebra.

### "¿Qué es volatilidad?"
R: Medida de riesgo. Desviación estándar de precios históricos. Mayor volatilidad = mayor fluctuación = mayor riesgo.

### "¿Por qué -30% en Tech?"
R: Lógica de negocio real: empresas tech reinvierten ganancias en R&D. No distribuyen dividendos completos.

---

## 📈 Salida del Main (Líneas Clave)

```
Dividendo Anual Apple: $0.7      (0.25 × 4 × 0.70)
Dividendo Anual Santander: $0.6  (0.15 × 4)
Volatilidad Apple: 0.847         (Desv. estándar)
Volatilidad ETH: 14.23           (Desv. est. × 2.5 + ballena)

Ana intenta comprar ETH: No se cumple el perfil
Pedro intenta comprar ETH: Compra exitosa

Precio Apple antes: $234.56
Precio Apple después: $156.78 (-33%)
Capital Pedro tras crisis: $0 (QUEBRÓ)
Capital Ana tras crisis: $10000 (Intacto)

Plusvalía Ana este mes: $45.32
```

---

## 🎯 Énfasis Principales

### Para Profesores
"El proyecto integra OOP avanzado (herencia, polimorfismo) con lógica de negocio realista (volatilidad, crisis, apalancamiento)."

### Para Compañeros de Clase
"Veréis cómo diferentes decisiones financieras llevan a resultados muy diferentes. El agresivo gana mucho o quiebra. El moderado es más seguro."

### Para Entrevistas
"Demuestro manejo de:
- Clases abstractas y polimorfismo
- Herencia multinivel
- ArrayList y listas paralelas
- Lógica empresarial compleja
- Simulación de eventos aleatorios"

---

## 📁 Estructura de Archivos Generados

```
docs/
├── ACTIVO.md              (2000 palabras - Clase base abstracta)
├── ACCION.md              (1500 palabras - Acciones con dividendos)
├── BONO.md                (1800 palabras - Bonos con rating país)
├── CRIPTOMONEDA.md        (1600 palabras - Cripto con ballenas)
├── INVERSOR.md            (2000 palabras - Inversores y perfiles)
├── MERCADO.md             (1200 palabras - Gestor central y crisis)
├── MAIN.md                (2500 palabras - Flujo completo)
├── GUIA_EXPOSICION.md     (4000 palabras - Cómo exponer)
└── RESUMEN_EJECUTIVO.md   (Este archivo - Referencia rápida)
```

---

## ⏱️ Timing para Exposición

| Sección | Tiempo | Archivo |
|---------|--------|---------|
| Introducción | 2 min | GUIA_EXPOSICION.md (sección 1) |
| Arquitectura | 3 min | GUIA_EXPOSICION.md (sección 2) |
| Clase Activo | 2 min | ACTIVO.md |
| Clase Accion | 1 min | ACCION.md (resumen rápido) |
| Clase Bono | 1 min | BONO.md (resumen rápido) |
| Clase Cripto | 1 min | CRIPTOMONEDA.md (resumen rápido) |
| Clase Inversor | 2 min | INVERSOR.md |
| Clase Mercado | 1 min | MERCADO.md |
| Ejecución Main | 5 min | MAIN.md + código real |
| Demo + Preguntas | 2 min | En vivo |
| **TOTAL** | **20 min** | |

---

## 🚀 Checklist Final

- [ ] Todos los MD creados en carpeta /docs/
- [ ] Código Java compila sin errores
- [ ] Main.java ejecuta correctamente
- [ ] Salida de consola coincide con explicación
- [ ] He memorizado los 10 pasos del Main
- [ ] Puedo explicar por qué Ana rechaza y Pedro compra
- [ ] Entiendo la fórmula de volatilidad de cada clase
- [ ] Tengo clara la lógica de crisis
- [ ] Puedo mostrar diagrama en pizarra
- [ ] Listo para responder preguntas

---

