# testSplitCamel

This repository contains Apache Camel YAML DSL tests that verify how exceptions behave when thrown inside a `split` component and how they propagate back to the main route.

It also documents behavior related to variables, properties, and headers when using `split`.

## 🎯 Purpose

This project tests two main scenarios:

### 1️⃣ Exception Propagation from `split` ✅

- Verifies that an exception thrown inside a `split` block, and `split` block inside `finaly`
- Can be propagated back to the main route
- Can be caught and handled there
- Both test scenarios pass successfully 

### 2️⃣ Exchange State After `split`  ❌

Tests whether:

- Variables
- Exchange properties
- Headers

set inside the `split` block are accessible in the main route.

🔎 Result:  
They appear empty in the main route, as if they were never set.

This repository demonstrates that behavior clearly.

### Solution AggregationStrategy

With implementation of basic PropertyPreservingAggregationStrategy.java file which basically saves proprties from
split communication even when exception is thrown. With AggStrat u could save even variables, headers...

PropertyPreservingAggregationStrategy.java 

U can register this file as Bean and add it as AggregationStrategy

```bash
- beans:
    - name: propertyAggregator
      type: PropertyPreservingAggregationStrategy

...
- split:
    ...
    - aggregationStrategy: propertyAggregator
```

---

## 📁 Test Files

### Exception propagation tests

- `test-split-exception-throw.camel.yaml`
- `test-caller-exception-catch.camel.yaml`

### Exception propagation from `finally`

- `test-split-exception-throw-from-finally.camel.yaml`
- `test-caller-exception-catch-finally.camel.yaml`

### Exception propagation with AggregationStrategy

- `test-split-exception-throw-agg.camel.yaml`
- `test-caller-exception-catch-agg.camel.yaml`

---

## 🚀 How to Run

Make sure you have **Camel JBang** installed.

### Run basic exception propagation test:

```bash
camel run test-split-exception-throw.camel.yaml test-caller-exception-catch.camel.yaml
```

### Run exception propagation test from finally:
```bash
camel run test-split-exception-throw-from-finally.camel.yaml test-caller-exception-catch-finally.camel.yaml
```

### Run exception propagation test with AggregationStrategy:
```bash
camel run test-split-exception-throw-agg.camel.yaml test-caller-exception-catch-agg.camel.yaml PropertyPreservingAggregationStrategy.java application.properties
```