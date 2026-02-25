# Cómo subir el proyecto a GitHub (paso a paso)

## 1. Crear el repositorio en GitHub (si no lo hiciste)

- Entra a https://github.com → Inicia sesión
- Clic en **"+"** → **"New repository"**
- **Repository name:** por ejemplo `accesorios-para-motos` o `reto-microservicios`
- **Description:** (opcional) "Reto microservicios - Plataforma de pedidos - Sprint 1"
- **Public**
- **No** marques "Add README" (ya tienes código)
- **Create repository**
- Copia la **URL** del repo (ej: `https://github.com/NAHOMII11/accesorios-para-motos.git`)

---

## 2. Abrir Git Bash en la carpeta del proyecto

- Clic derecho en la carpeta **`C:\reto_microservicios`**
- Elige **"Git Bash Here"** (si no aparece, abre Git Bash y escribe: `cd /c/reto_microservicios`)

---

## 3. Comandos (ejecuta uno por uno)

**Inicializar Git en la carpeta (solo la primera vez):**
```bash
git init
```

**Ver qué archivos se van a subir:**
```bash
git status
```

**Añadir todos los archivos:**
```bash
git add .
```

**Crear el primer commit (guardar punto):**
```bash
git commit -m "Sprint 1: API Gateway, Orders Service Crear Pedido, docker-compose"
```

**Poner nombre a tu rama (con tu nombre):**
```bash
git branch -M nahomi
```

**Conectar con tu repositorio de GitHub** (cambia la URL por la de TU repo):
```bash
git remote add origin https://github.com/NAHOMII11/TU-NOMBRE-REPO.git
```
Ejemplo si tu repo se llama `accesorios-para-motos`:
```bash
git remote add origin https://github.com/NAHOMII11/accesorios-para-motos.git
```

**Subir el código a GitHub (rama nahomi):**
```bash
git push -u origin nahomi
```

Te pedirá usuario y contraseña de GitHub. Si pide contraseña, usa un **Personal Access Token** (GitHub ya no acepta la contraseña normal en este paso).

---

## 4. Si ya habías hecho `git init` antes

Si al hacer `git remote add origin ...` sale "remote origin already exists", borra el remoto y vuelve a añadirlo:

```bash
git remote remove origin
git remote add origin https://github.com/NAHOMII11/TU-NOMBRE-REPO.git
git push -u origin nahomi
```

---

## Resumen de qué hace cada comando

| Comando | Qué hace |
|--------|----------|
| `git init` | Convierte la carpeta en un repositorio Git |
| `git status` | Muestra archivos nuevos o modificados |
| `git add .` | Prepara todos los archivos para el commit |
| `git commit -m "mensaje"` | Guarda esos archivos en un "commit" con un mensaje |
| `git branch -M nahomi` | Nombra tu rama "nahomi" |
| `git remote add origin URL` | Asocia este proyecto con tu repo en GitHub |
| `git push -u origin nahomi` | Envía los commits a GitHub en tu rama |
