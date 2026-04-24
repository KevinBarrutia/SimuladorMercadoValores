# Clase Criptomoneda 🪙

## 🎯 Descripción General
`Criptomoneda` representa un **activo digital** basado en tecnología blockchain. A diferencia de acciones y bonos, las criptomonedas son muy volátiles y su valor depende principalmente de especulación y adopción de red.

---

## 📋 Atributos

### Atributos Propios de Criptomoneda

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `blockchain` | String | Nombre de la red (ej: "Ethereum Network", "Bitcoin") |
| `maxSupply` | double | Cantidad máxima de monedas que existirán |
| `minadoRestante` | double | Cantidad pendiente de emisión/minería |
| `walletsGrandes` | ArrayList<Double> | Porcentajes de posesión de ballenas (ej: 25.0 = 25%) |

### Concepto de Walletas Grandes
- **Ballena**: Personas/entidades con gran cantidad de la cripto
- Si una persona tiene el 35% de todas las monedas = Riesgo de manipulación
- Esta concentración aumenta la volatilidad

### Atributos Heredados de Activo
- `codigo`: Identificador (ej: "BTC", "ETH")
- `nombre`: Nombre (ej: "Bitcoin", "Ethereum")
- `precioActual`: Precio actual en €/$
- `historialPrecios`: Últimos 30 precios históricos

---

## 🏗️ Constructores

### Constructor Principal
```java
public Criptomoneda(String codigo, String nombre, 
                    String blockchain, double maxSupply, double minadoRestante)
```

**Parámetros:**

| Parámetro | Tipo | Ejemplo | Descripción |
|-----------|------|---------|-------------|
| `codigo` | String | "ETH" | Símbolo de la cripto |
| `nombre` | String | "Ethereum" | Nombre oficial |
| `blockchain` | String | "Ethereum Network" | Nombre de la red |
| `maxSupply` | double | 120000000 | Total máximo de monedas |
| `minadoRestante` | double | 1000000 | Monedas pendientes de crear |

**Ejemplo de creación:**
```java
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", 
                                    "Ethereum Network", 
                                    120000000,  // Max supply
                                    1000000);   // Restante

// El constructor automáticamente:
// 1. Llama a Activo(codigo, nombre)
// 2. Genera 30 precios históricos
// 3. Inicializa walletsGrandes vacía
```

---

## 🔧 Métodos

### 1. **Getters y Setters**

#### `getBlockchain()` / `setBlockchain(String blockchain)`
```java
public String getBlockchain()
public void setBlockchain(String blockchain)
```

#### `getMaxSupply()` / `setMaxSupply(double maxSupply)`
```java
public double getMaxSupply()
public void setMaxSupply(double maxSupply)
```

#### `getMinadoRestante()` / `setMinadoRestante(double minadoRestante)`
```java
public double getMinadoRestante()
public void setMinadoRestante(double minadoRestante)
```

#### `getWalletsGrandes()`
```java
public List<Double> getWalletsGrandes()
```
- Retorna lista de porcentajes de ballenas
- Usado para calcular volatilidad

---

### 2. **addWalletGrande()** 🐋

```java
public void addWalletGrande(double porcentaje)
```

**Propósito:** Registra la posesión de una ballena importante

**Parámetro:**
- `porcentaje`: Valor entre 0 y 100 (ej: 25.0 = 25%)

**Ejemplo de uso:**
```java
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", 
                                    "Ethereum Network", 
                                    120000000, 1000000);

// Simular que una ballena tiene el 35% de ETH
eth.addWalletGrande(35.0);

// Otra ballena con 20%
eth.addWalletGrandes(20.0);

// Ahora walletsGrandes = [35.0, 20.0]
```

**¿Por qué es importante?**
- Concentración alta de posesión = Riesgo de manipulación
- Una ballena podría vender y derrumbar el precio
- Afecta directamente a `calcularVolatilidad()`

---

### 3. **calcularVolatilidad()** ⭐ [La más compleja]

```java
@Override
public double calcularVolatilidad()
```

**Propósito:** Calcula el riesgo considerando:
1. Fluctuaciones de precio
2. Factor característico de criptomonedas
3. Riesgo por concentración de ballenas

**Proceso paso a paso:**

#### Paso 1: Obtener últimos 7 precios
```java
List<Double> ultimos7Precios = getUltimos7Precios();
```

#### Paso 2: Calcular media
```java
double media = 0;
for (double precio : ultimos7Precios) {
    media += precio;
}
media /= ultimos7Precios.size();
```

**Ejemplo:**
```
Precios últimos 7 días: [100, 105, 98, 110, 102, 108, 95]
media = (100+105+98+110+102+108+95) / 7 = 718 / 7 = 102.57
```

#### Paso 3: Calcular varianza
```java
double varianza = 0;
for (double precio : ultimos7Precios) {
    varianza += Math.pow(precio - media, 2);
}
varianza /= ultimos7Precios.size();
```

**Cálculos intermedios:**
```
(100 - 102.57)² = 6.61
(105 - 102.57)² = 5.90
(98 - 102.57)² = 20.90
... (4 más)
varianza = suma / 7 ≈ 29.27
```

#### Paso 4: Volatilidad base (desviación estándar)
```java
double volatilidad = Math.sqrt(varianza);
// volatilidad ≈ √29.27 ≈ 5.41
```

#### Paso 5: Aplicar factor criptomoneda
```java
volatilidad *= 2.5;  // ¡Multiplicar por 2.5!
// volatilidad ≈ 5.41 × 2.5 ≈ 13.53
```

**¿Por qué 2.5?** Las criptomonedas son naturalmente 2.5x más volátiles que otros activos

#### Paso 6: Penalización por ballenas
```java
for (double wallet : walletsGrandes) {
    if (wallet > 20) {  // Si ballena tiene >20%
        volatilidad += 0.5;  // Suma 0.5 al riesgo
        break;  // Solo una vez
    }
}
```

**Lógica:** Si hay una ballena con >20%, se suma 0.5 al riesgo (y termina)

#### Resultado final:
```
Si no hay ballena >20%: volatilidad ≈ 13.53

Si hay ballena >20%:   volatilidad ≈ 13.53 + 0.5 = 14.03
```

---

## 📊 Ejemplo Completo: Ethereum vs Bitcoin

```java
// Crear Ethereum
Criptomoneda eth = new Criptomoneda("ETH", "Ethereum", 
                                    "Ethereum Network", 
                                    120000000, 1000000);
eth.addWalletGrande(35.0);  // Una ballena tiene 35%

// Crear Bitcoin
Criptomoneda btc = new Criptomoneda("BTC", "Bitcoin", 
                                    "Bitcoin Network", 
                                    21000000, 500000);
// Sin ballenas grandes

// Comparar volatilidades
System.out.println("Volatilidad ETH: " + eth.calcularVolatilidad());
// Output: ~14.03 (incluye penalización)

System.out.println("Volatilidad BTC: " + btc.calcularVolatilidad());
// Output: ~13.53 (sin penalización)

System.out.println("Walletas grandes ETH: " + eth.getWalletsGrandes());
// Output: [35.0]

System.out.println("Max supply BTH: " + btc.getMaxSupply());
// Output: 21000000
```

---

## 💰 Características Especiales de Criptomonedas

### Max Supply vs Minado Restante
```
Ethereum:
  maxSupply = 120,000,000
  minadoRestante = 1,000,000
  → Casi todo ya está en circulación (99% de 120M)

Bitcoin:
  maxSupply = 21,000,000
  minadoRestante = 500,000
  → Falta emitir ~2.4% (límite de 21M)
```

### Impacto en el Precio
- Menos monedas restantes = Mayor escasez = Posible subida
- Más monedas restantes = Más inflación = Posible bajada

### Ballenas vs Distribuición
```
Ethereum con 35% en ballena:
  ✓ Muy concentrado
  ✓ Alto riesgo de manipulación
  ✗ Mayor volatilidad

Ethereum distribuido:
  ✓ Menor riesgo
  ✗ Menos volatilidad especulativa
  ✓ Más "saludable" como red
```

---

## 🔗 Relaciones con Otras Clases

```
Criptomoneda
  ├── Extiende: Activo
  └── Usado por:
      ├── Mercado (almacena en ArrayList<Activo>)
      ├── Inversor (compra/vende por riesgo alto)
      └── Main (para comparar con acciones y bonos)
```

---

## 🎓 Conceptos Clave

### Volatilidad: La Clave de Cripto
- **Más volátil que acciones** (factor 2.5)
- **Mucho más volátil que bonos**
- **Mayor riesgo = Mayor potencial de ganancia/pérdida**

### Ballenas Importan
- Si el 35% está en 1 persona → Control total
- Si el 2% está disperso en 1000 personas → Poder compartido
- La concentración se refleja en volatilidad adicional

### Blockchain vs Cripto
- **Blockchain**: Tecnología (ej: Ethereum Network)
- **Criptomoneda**: El token que se negocia (ej: ETH)

### Comparación de Riesgos

| Activo | Volatilidad | Riesgo | Para Quién |
|--------|-------------|--------|-----------|
| Bono AAA | 0.0 | Mínimo | Conservadores |
| Acción | 1-2 | Medio | Moderados |
| Criptomoneda | 10-15 | Alto | Agresivos |

---

