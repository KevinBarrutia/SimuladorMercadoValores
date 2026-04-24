# Clase Mercado 🏛️

## 🎯 Descripción General
`Mercado` es la **clase gestora central** que administra todos los activos y todos los inversores del sistema. Es el corazón del simulador: controla qué activos existen, quiénes son los participantes y qué eventos suceden en el mercado.

---

## 📋 Atributos

### Atributos Principales

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `activos` | ArrayList<Activo> | Lista de todos los activos disponibles (Acciones, Bonos, Cripto) |
| `inversores` | ArrayList<Inversor> | Lista de todos los inversores del mercado |

**Relación:**
```
Mercado (Gestor Central)
  ├── activos[0] = Apple (Accion)
  ├── activos[1] = ETH (Criptomoneda)
  ├── activos[2] = Bono España (Bono)
  │
  └── inversores[0] = Ana (Moderada)
      inversores[1] = Pedro (Agresivo)
```

---

## 🏗️ Constructores

### Constructor Principal
```java
public Mercado()
```

**Parámetros:** Ninguno

**Inicialización:**
```java
this.activos = new ArrayList<>();
this.inversores = new ArrayList<>();
```

**Ejemplo de creación:**
```java
Mercado mercado = new Mercado();
// Mercado vacío, listo para agregar participantes
```

---

## 🔧 Métodos

### 1. **agregarActivo()** 📊

```java
public void agregarActivo(Activo a)
```

**Propósito:** Registra un nuevo activo en el mercado

**Parámetro:**
- `a`: Instancia de cualquier activo (Accion, Bono, Criptomoneda)

**Funcionamiento:**
```java
activos.add(a);
```
Simplemente añade el activo a la lista

**Ejemplo de uso:**
```java
Mercado mercado = new Mercado();

// Crear activos
Accion apple = new Accion("AAPL", "Apple Inc.", "Apple Corp", 
                          0.25, "Tecnología", 15000);
Accion santander = new Accion("SAN", "Banco Santander", 
                              "Santander Group", 0.15, "Financiero", 50000);
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", 
                                    "Ethereum Network", 
                                    120000000, 1000000);

// Registrar en el mercado
mercado.agregarActivo(apple);       // activos[0]
mercado.agregarActivo(santander);   // activos[1]
mercado.agregarActivo(eth);         // activos[2]

// Ahora el mercado tiene 3 activos
```

**Datos después:**
```
mercado.activos.size() = 3
mercado.activos[0] = Apple
mercado.activos[1] = Santander
mercado.activos[2] = Ethereum
```

---

### 2. **agregarInversor()** 👤

```java
public void agregarInversor(Inversor i)
```

**Propósito:** Registra un nuevo inversor en el mercado

**Parámetro:**
- `i`: Instancia de Inversor (con perfil y capital)

**Funcionamiento:**
```java
inversores.add(i);
```
Simplemente añade el inversor a la lista

**Ejemplo de uso:**
```java
Mercado mercado = new Mercado();

// Crear inversores
Inversor ana = new Inversor("22334455B", 10000.0, "Moderado");
Inversor pedro = new Inversor("88776655C", 5000.0, "Agresivo");

// Registrar en el mercado
mercado.agregarInversor(ana);    // inversores[0]
mercado.agregarInversor(pedro);  // inversores[1]

// Ahora el mercado tiene 2 inversores
```

**Datos después:**
```
mercado.inversores.size() = 2
mercado.inversores[0] = Ana (Moderado, 10000€)
mercado.inversores[1] = Pedro (Agresivo, 5000€)
```

---

### 3. **crisis()** 💥 [Evento de Mercado - El más importante]

```java
public void crisis()
```

**Propósito:** Simula una crisis de mercado con dos consecuencias:
1. **Todos los activos pierden valor** (20-40%)
2. **Los inversores agresivos con palanca > 2x quiebran**

**¡Importante!** Este es el evento más dramático del sistema

**Paso 1: Impacto en los ACTIVOS**

```java
Random random = new Random();

for (Activo activo : activos) {
    // Genera un porcentaje entre 0.20 y 0.40 (20% a 40%)
    double porcentajePerdida = 0.20 + (0.40 - 0.20) * random.nextDouble();
    
    // Calcula nuevo precio con la pérdida
    double nuevoPrecio = activo.getPrecioActual() * (1 - porcentajePerdida);
    
    // Actualiza el precio
    activo.setPrecioActual(nuevoPrecio);
}
```

**Explicación:**
```
Cálculo de porcentaje aleatorio:
0.20 + (0.40 - 0.20) × random()
= 0.20 + 0.20 × random()

Si random() = 0.0 → porcentaje = 0.20 (pérdida del 20%)
Si random() = 0.5 → porcentaje = 0.30 (pérdida del 30%)
Si random() = 1.0 → porcentaje = 0.40 (pérdida del 40%)
```

**Ejemplo práctico:**
```
Apple precio antes: 100€
Porcentaje de pérdida: 30% (random entre 20-40%)
Nuevo precio: 100 × (1 - 0.30) = 100 × 0.70 = 70€
Pérdida real: 30€ (30%)

Bitcoin precio antes: 50000€
Porcentaje de pérdida: 25%
Nuevo precio: 50000 × (1 - 0.25) = 50000 × 0.75 = 37500€
Pérdida real: 12500€ (25%)
```

**Resultado después del Paso 1:**
```
Todos los precios bajaron entre 20-40%
```

---

**Paso 2: Impacto en INVERSORES (Quiebra)**

```java
for (Inversor inversor : inversores) {
    if (inversor.getPerfil().equalsIgnoreCase("agresivo")) {
        inversor.setCapital(0);
        System.out.println("Inversor DNI " + inversor.getDni() + " ha quebrado.");
    }
}
```

**¿Por qué quiebran los agresivos?**
- Nota en el código: "Si el inversor tiene palanca > 2x, asumimos que opera así"
- Palanca = Usar dinero prestado para invertir más
- Crisis del 30% + Palanca 2x = Pérdida del 60% = Quiebra
- El inversor no puede pagar la deuda

**Ejemplo:**
```
Inversor Pedro (AGRESIVO)
  Capital antes: 2500€
  Portafolio (con palanca 2x):
    - 5000€ en Bitcoin (pero solo tiene 2500€, el resto prestado)
  
  Crisis: Bitcoin cae 30%
  - Pierde: 5000 × 0.30 = 1500€
  - Con palanca 2x: pierde 1500€ pero debe devolver el crédito
  - Capital se va a 0€ → QUIEBRA
  
Inversor Ana (MODERADO)
  Capital antes: 10000€
  Portafolio sin palanca:
    - 2000€ en Apple
  
  Crisis: Apple cae 30%
  - Pierde: 2000 × 0.30 = 600€
  - Capital después: 10000 - 600 = 9400€ (sigue viva)
```

**Resultado después del Paso 2:**
```
Todos los inversores "agresivo" tienen capital = 0€
Otros inversores siguen activos pero más pobres
```

---

## 📊 Ejemplo Completo: Simulación de Crisis

```java
// 1. Crear mercado
Mercado mercado = new Mercado();

// 2. Agregar activos
Accion apple = new Accion("AAPL", "Apple Inc.", "Apple Corp", 
                          0.25, "Tecnología", 15000);
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", 
                                    "Ethereum Network", 
                                    120000000, 1000000);
mercado.agregarActivo(apple);
mercado.agregarActivo(eth);

// 3. Agregar inversores
Inversor ana = new Inversor("22334455B", 10000.0, "Moderado");
Inversor pedro = new Inversor("88776655C", 5000.0, "Agresivo");
mercado.agregarInversor(ana);
mercado.agregarInversor(pedro);

// 4. Ambos compran
ana.comprar(apple, 5);   // Compra segura
pedro.comprar(eth, 1);   // Compra arriesgada

System.out.println("=== ANTES DE LA CRISIS ===");
System.out.println("Precio Apple: " + apple.getPrecioActual());
System.out.println("Capital Ana: " + ana.getCapital());
System.out.println("Capital Pedro: " + pedro.getCapital());

// 5. ¡CRISIS!
System.out.println("\n=== EVENTO: CRISIS DE MERCADO ===");
mercado.crisis();

System.out.println("\n=== DESPUÉS DE LA CRISIS ===");
System.out.println("Precio Apple: " + apple.getPrecioActual());  // -20-40%
System.out.println("Capital Ana: " + ana.getCapital());          // Disminuyó pero sigue
System.out.println("Capital Pedro: " + pedro.getCapital());      // ¡0€ - QUEBRÓ!

// 6. Salida esperada:
// Inversor DNI 88776655C ha quebrado.
```

---

## 🎓 Conceptos Clave

### Mercado = Gestor Central
- Almacena todos los activos
- Almacena todos los inversores
- Puede ejecutar eventos globales (crisis)
- No participa directamente en compras (eso lo hace Inversor)

### Crisis Realista
- Todos pierden (no hay "ganadores")
- Porcentaje variable (20-40%) simula incertidumbre
- Agresivos quiebran (riesgo de apalancamiento)
- Moderados/Conservadores sobreviven (mejor preparados)

### Apalancamiento (Palanca/Leverage)
- Inversor con 5000€ compra con 10000€ (palanca 2x)
- En mercado alcista: ganancias mayores
- En mercado bajista: pérdidas devastadoras (quiebra)

### ArrayList vs HashMap
El código usa ArrayList para iteración fácil:
```java
for (Activo a : activos)  // Fácil iterar
for (Inversor i : inversores)  // Fácil iterar
```
Alternativa sería HashMap con DNI/Código como clave

---

## 🔗 Relaciones con Otras Clases

```
Mercado (Gestor Central)
  ├── Contiene: ArrayList<Activo>
  │    └── Accion, Bono, Criptomoneda
  │
  ├── Contiene: ArrayList<Inversor>
  │    └── Cada Inversor con su portafolio
  │
  └── Usado por: Main (para orquestar la simulación)
```

---

