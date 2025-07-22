# 🧑‍🍳 COOKTRACK

COOKTRACK es un sistema de planificación de menús diarios que permite a los usuarios:

- Crear y gestionar recetas
- Asignarlas a un calendario
- Generar automáticamente listas de compras
- Publicar y calificar recetas en un feed

---

## 🚀 **Requisitos previos**

- **Windows 10 o superior** 
- **Java 24 o superior**
- **Maven 3.6 o superior en el PATH**
- **PostgreSQL** configurado con usuario
- **psql** en el PATH (para scripts de inicialización)

---

## ⚙️ **Inicialización del proyecto**

1. Clona el repositorio:

```bash
git clone https://github.com/tuusuario/cooktrack.git
cd cooktrack
```

2. Configura la base de datos ejecutando el script de inicialización:

### 🔹 **En Windows (PowerShell)**

```powershell
.\scripts\init.bat
```

### 🔹 **En Windows (CMD)**

```cmd
scripts\init.bat
```
Esto creará la base de datos, las tablas y un usuario admin:admin.

---

## 🏃‍♂️ **Ejecutar la aplicación**

```bash
mvn javafx:run
```

✔️ Inicia la aplicación JavaFX COOKTRACK.

---

## ✅ **Ejecutar linter y análisis estático**

### 🔹 **Checkstyle (estilo de código)**

```bash
mvn checkstyle:check
```

---

## 🧪 **Ejecutar tests**

### 🔹 **Pruebas unitarias y de integración**

```bash
mvn test
```

---

> Made with ❤️ by Passione team
