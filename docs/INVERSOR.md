# Clase Inversor 👤

## 🎯 Descripción General
`Inversor` representa a una **persona que invierte dinero** en el mercado. Gestiona su portafolio de activos, capital disponible y valida sus compras según su perfil de riesgo (conservador, moderado, agresivo).

---

## 📋 Atributos

### Atributos Principales

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `dni` | String | Identificador único del inversor (ej: "12345678A") |
| `capital` | double | Dinero disponible para invertir |
| `perfil` | String | Estrategia de inversión (Conservador/Moderado/Agresivo) |

### Listas Paralelas (Sistema de Portafolio)

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `portfolio` | ArrayList<Activo> | Activos que posee el inversor |
| `cantidades` | ArrayList<Integer> | Cantidad de cada activo (misma posición que portfolio) |

**Concepto: Listas Paralelas**
```
Índice 0:  portfolio[0] = Apple,   cantidades[0] = 5 acciones
Índice 1:  portfolio[1] = Bitcoin, cantidades[1] = 2 monedas
Índice 2:  portfolio[2] = Bono,    cantidades[2] = 10 bonos

Estructura sincronizada: Cada activo tiene su cantidad en el mismo índice
```

---

## 🏗️ Constructores

### Constructor Principal
```java
public Inversor(String dni, double capital, String perfil)
```

**Parámetros:**

| Parámetro | Tipo | Ejemplo | Descripción |
|-----------|------|---------|-------------|
| `dni` | String | "22334455B" | DNI del inversor |
| `capital` | double | 10000.0 | Capital inicial en € |
| `perfil` | String | "Moderado" | Tipo de inversor |

**Perfiles disponibles:**
- `"Conservador"`: Bajo riesgo, seguridad
- `"Moderado"`: Riesgo medio, balance
- `"Agresivo"`: Alto riesgo, máximas ganancias

**Ejemplo de creación:**
```java
Inversor ana = new Inversor("22334455B", 10000.0, "Moderado");
Inversor pedro = new Inversor("88776655C", 5000.0, "Agresivo");
```

**¿Qué inicializa el constructor?**
1. Asigna DNI, capital y perfil
2. Crea `portfolio` como ArrayList vacío
3. Crea `cantidades` como ArrayList vacío
4. El inversor está listo para comprar activos

---

## 🔧 Métodos

### 1. **Getters y Setters**

#### `getDni()` / `setDni(String dni)`
```java
public String getDni()
public void setDni(String dni)
```

#### `getCapital()`
```java
public double getCapital()
```
- Retorna capital disponible actual

#### `setCapital(double capital)`
```java
public void setCapital(double capital)
```
- Permite establecer capital (usado en crisis)

#### `getPerfil()`
```java
public String getPerfil()
```
- Retorna el perfil de riesgo del inversor

#### `setPerfil(String perfil)`
```java
public void setPerfil(String perfil)
```
- Modifica el perfil

---

### 2. **puedeComprar()** 🔐 [Validación de Riesgo]

```java
public boolean puedeComprar(Activo activo, int cantidad)
```

**Propósito:** Valida si el inversor PUEDE comprar según:
1. Si tiene dinero suficiente
2. Si la volatilidad del activo cumple su perfil

**Parámetros:**
- `activo`: El activo a comprar
- `cantidad`: Cuántas unidades quiere comprar

**Retorna:** `true` (puede comprar) o `false` (no puede)

**Lógica paso a paso:**

#### Paso 1: Calcular costo total
```java
double costoTotal = activo.getPrecioActual() * cantidad;
```

Ejemplo:
```
Apple precio: 100€
Cantidad: 5
Costo total: 100 × 5 = 500€
```

#### Paso 2: Verificar capital
```java
if (costoTotal > this.capital) 
    return false;  // No hay dinero suficiente
```

Ejemplo:
```
Costo = 500€
Capital disponible = 300€
500 > 300 → NO puede comprar
```

#### Paso 3: Obtener volatilidad del activo
```java
double vol = activo.calcularVolatilidad();
```

Ejemplo:
```
Apple: volatilidad = 0.8
Bitcoin: volatilidad = 14.0
```

#### Paso 4: Aplicar reglas según perfil

**CONSERVADOR** (Máxima seguridad)
```java
case "conservador":
    return vol < 1.0;  // Solo activos estables (vol < 1)
```

| Volatilidad | Decisión | Ejemplo |
|-------------|----------|---------|
| < 1.0 | ✅ COMPRA | Bono AAA (vol 0.0), Apple (vol 0.8) |
| ≥ 1.0 | ❌ RECHAZA | Bitcoin (vol 14.0), Bono BBB (vol 0.375) |

**MODERADO** (Balance de riesgo)
```java
case "moderado":
    return vol < 2.0 || activo.tendencia().equals("alcista");
    // Compra si volatilidad < 2.0 
    // O si es tendencia alcista (aunque sea volátil)
```

| Volatilidad | Tendencia | Decisión |
|-------------|-----------|----------|
| 0.8 | Cualquiera | ✅ COMPRA (vol < 2.0) |
| 1.5 | Alcista | ✅ COMPRA (alcista compensa riesgo) |
| 3.0 | Bajista | ❌ RECHAZA (vol > 2.0 y bajista) |
| 14.0 | Alcista | ✅ COMPRA (alcista lo justifica) |

**AGRESIVO** (Máximo riesgo permitido)
```java
case "agresivo":
    return true;  // TODO está permitido
```

**No hay restricciones:** Puede comprar cualquier cosa

#### Paso 5: Default
```java
default:
    return false;  // Perfil no reconocido
```

---

### 3. **comprar()** 💳 [Ejecución de Compra]

```java
public void comprar(Activo a, int cantidad)
```

**Propósito:** Ejecuta la compra si `puedeComprar()` retorna true

**Proceso paso a paso:**

#### 1. Validar si puede comprar
```java
if (puedeComprar(a, cantidad)) {
    // Continuar con la compra
} else {
    System.out.println("No se cumple el perfil de riesgo...");
    return;
}
```

#### 2. Restar del capital
```java
this.capital -= (a.getPrecioActual() * cantidad);
```

Ejemplo:
```
Capital antes: 1000€
Compra Apple 5 unidades × 100€ = 500€
Capital después: 1000 - 500 = 500€
```

#### 3. Buscar si ya posee el activo
```java
int indiceEncontrado = -1;
for (int i = 0; i < portfolio.size(); i++) {
    if (portfolio.get(i).getCodigo().equals(a.getCodigo())) {
        indiceEncontrado = i;
        break;
    }
}
```

**Escenarios:**
- Si `indiceEncontrado = -1`: Activo nuevo (no está en portafolio)
- Si `indiceEncontrado ≥ 0`: Ya posee este activo

#### 4A: Si YA posee el activo
```java
if (indiceEncontrado != -1) {
    int cantidadNueva = cantidades.get(indiceEncontrado) + cantidad;
    cantidades.set(indiceEncontrado, cantidadNueva);
}
```

**Ejemplo:**
```
portfolio[1] = Apple, cantidades[1] = 5
Compra 3 acciones más de Apple
→ cantidades[1] = 5 + 3 = 8
```

#### 4B: Si es NUEVO
```java
else {
    portfolio.add(a);
    cantidades.add(cantidad);
}
```

**Ejemplo:**
```
Compra Bitcoin por primera vez
portfolio[3] = Bitcoin
cantidades[3] = 2
```

#### 5. Confirmar
```java
System.out.println("Compra exitosa: " + cantidad + " de " + a.getNombre());
```

---

### 4. **simularMes()** 📈 [Cálculo de Ganancias/Pérdidas]

```java
public double simularMes()
```

**Propósito:** Simula fluctuaciones de precios durante un mes y calcula ganancias/pérdidas

**Retorna:** Plusvalía/Minusvalía total del mes

**Proceso paso a paso:**

#### 1. Inicializar balance
```java
double balanceMes = 0;
```

#### 2. Iterar sobre cada activo en el portafolio
```java
for (int i = 0; i < portfolio.size(); i++) {
    Activo activo = portfolio.get(i);
    int cantidad = cantidades.get(i);
    
    // Calcular ganancias/pérdidas para este activo
}
```

#### 3. Para cada activo: guardar precio anterior
```java
double precioAnterior = activo.getPrecioActual();
```

Ejemplo: `precioAnterior = 100€`

#### 4. Simular fluctuación aleatoria
```java
double factorCambio = (Math.random() * 2 - 1) * (activo.calcularVolatilidad() / 100);
```

**Explicación:**
- `Math.random()`: Genera número entre 0.0 y 1.0
- `Math.random() * 2 - 1`: Rango -1.0 a 1.0
- `activo.calcularVolatilidad() / 100`: Factor de volatilidad como porcentaje

**Ejemplo:**
```
Volatilidad = 5.0
Factor = (0.6 * 2 - 1) * (5 / 100)
       = (0.2) * 0.05
       = 0.01 → +1% de cambio
```

#### 5. Calcular nuevo precio
```java
double nuevoPrecio = precioAnterior * (1 + factorCambio);
```

**Ejemplo:**
```
Precio anterior: 100€
Factor cambio: 0.01 (+1%)
Nuevo precio: 100 × (1 + 0.01) = 101€
```

#### 6. Actualizar precio en el activo
```java
activo.setPrecioActual(nuevoPrecio);
```

#### 7. Calcular ganancia/pérdida
```java
balanceMes += (nuevoPrecio - precioAnterior) * cantidad;
```

**Ejemplo:**
```
Ganancia por acción: 101 - 100 = 1€
Cantidad poseída: 5
Ganancia total: 1 × 5 = 5€
```

#### 8. Retornar balance del mes
```java
return balanceMes;  // Total ganancia/pérdida acumulada
```

---

## 📊 Ejemplo Completo de Inversión

```java
// 1. Crear dos inversores con diferentes perfiles
Inversor ana = new Inversor("22334455B", 10000.0, "Moderado");
Inversor pedro = new Inversor("88776655C", 5000.0, "Agresivo");

// 2. Crear activos
Accion apple = new Accion("AAPL", "Apple Inc.", "Apple Corp", 
                          0.25, "Tecnología", 15000);
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", 
                                    "Ethereum Network", 
                                    120000000, 1000000);
eth.addWalletGrande(35.0);

// 3. Ana (Moderada) intenta comprar Apple
System.out.print("Ana intenta comprar Apple: ");
ana.comprar(apple, 10);
// Output: Compra exitosa: 10 de Apple Inc.
// Capital Ana: 10000 - (100 × 10) = 9000

// 4. Ana intenta comprar Ethereum (demasiado riesgoso)
System.out.print("Ana intenta comprar ETH: ");
ana.comprar(eth, 1);
// Output: No se cumple el perfil de riesgo o capital insuficiente.
// Capital Ana: 9000 (sin cambios)

// 5. Pedro (Agresivo) compra Ethereum sin problemas
System.out.print("Pedro intenta comprar ETH: ");
pedro.comprar(eth, 2);
// Output: Compra exitosa: 2 de Ethereum
// Capital Pedro: 5000 - (precio × 2)

// 6. Simular mes
System.out.println("Capital Ana: " + ana.getCapital());
System.out.println("Capital Pedro: " + pedro.getCapital());

double gananciaAna = ana.simularMes();
double ganancia Pedro = pedro.simularMes();

System.out.println("Ganancia Ana este mes: " + gananciaAna + "€");
System.out.println("Ganancia Pedro este mes: " + gananciaPedro + "€");
```

---

## 🎓 Conceptos Clave

### Listas Paralelas
- Mantienen sincronización: mismo índice = misma información
- Alternativa a usar un Map o clase Posición
- Requiere cuidado al agregar/eliminar

### Perfil de Riesgo
- **Conservador**: Evita volatilidad (vol < 1.0)
- **Moderado**: Balance, pero compra volátiles si son alcistas
- **Agresivo**: Sin restricciones, busca máximas ganancias

### Capital vs Portafolio
- **Capital**: Dinero disponible para nuevas compras
- **Portafolio**: Activos ya comprados (no es dinero, es propiedad)

### Simulación de Mercado
- `simularMes()` usa aleatoriedad para simular incertidumbre real
- Mayor volatilidad = Mayores fluctuaciones
- Realista: el futuro es incierto

---

