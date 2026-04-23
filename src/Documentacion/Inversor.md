# 📈 Documentación de Clase: Inversor

La clase `Inversor` actúa como el gestor de portafolio en el simulador. 
Su función principal es validar compras basándose en **perfiles psicológicos de riesgo** y ejecutar simulaciones de mercado.

## 1. Estructura de Datos y Atributos

| Atributo | Tipo | Descripción |
| :--- | :--- | :--- |
| `dni` | `String` | Identificador legal del inversor. |
| `capital` | `double` | Dinero líquido disponible para realizar compras. |
| `perfil` | `String` | Define la tolerancia al riesgo (`Conservador`, `Moderado`, `Agresivo`). |
| `portfolio` | `Map<Activo, Integer>` | Diccionario que almacena el **Objeto Activo** y la **Cantidad** de unidades poseídas. |

---

## 2. Lógica de Decisión: Método `puedeComprar()`

Este es el método más crítico. No solo verifica si el inversor tiene dinero, sino que filtra el activo según su **volatilidad** y **tendencia**.

### Reglas por Perfil:
1. **Conservador**:
    * **Filtro:** Solo acepta activos con volatilidad **menor a 1.0**.
    * **Objetivo:** Preservar el capital evitando fluctuaciones fuertes.
2. **Moderado**:
    * **Filtro:** Acepta volatilidad **menor a 2.0**.
    * **Excepción:** Si la volatilidad es alta pero la `tendencia()` es **"alcista"**, permite la compra.
3. **Agresivo**:
    * **Filtro:** Ninguno.
    * **Objetivo:** Maximizar ganancias aceptando cualquier nivel de riesgo.

---

## 3. Simulación y Rebalanceo

### Método `simularMes()`
Este método hace que el tiempo "pase" en el simulador:
1. **Recorrido:** Itera sobre cada activo en el `portfolio`.
2. **Cálculo de Cambio:** Calcula un nuevo precio basado en la volatilidad del activo. A mayor volatilidad, mayor es el rango de cambio (subida o bajada).
3. **Plusvalía/Minusvalía:** Calcula la diferencia entre el valor anterior y el nuevo para determinar si el inversor ganó o perdió dinero ese mes.

### Método `rebalancear()`
Sigue una estrategia de gestión de carteras clásica:
* **Cortar Pérdidas:** Vende activos que han caído más de un **10%** en el trimestre.
* **Maximizar Ganancias:** Si el capital lo permite, compra más unidades de activos "ganadores" (subida > **15%**).

---

## 4. Conceptos de Programación Aplicados

* **Polimorfismo**: El inversor trata a todos los objetos como `Activo`. No le importa si es una `Criptomoneda`, una `Accion` o un `Bono`; simplemente llama a `.calcularVolatilidad()` y Java ejecuta la fórmula correcta para cada caso.
* **Encapsulamiento**: El `capital` y el `portfolio` son privados. Solo se pueden modificar mediante métodos controlados como `comprar()`, evitando que el dinero se modifique sin registro.
* **Colecciones (Map)**: El uso de `Map<Activo, Integer>` permite una búsqueda rápida de cuántas unidades tenemos de un activo específico sin tener que recorrer listas largas.

---
> **Nota de Implementación:** El éxito del inversor depende directamente de la precisión del método `tendencia()` y `calcularVolatilidad()` definidos en las clases de los activos.