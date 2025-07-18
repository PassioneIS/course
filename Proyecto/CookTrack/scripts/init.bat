@echo off
echo === Verificando Java ===
java -version

echo === Verificando PostgreSQL ===
psql --version
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] No se pudo verificar psql. Asegúrate de que PostgreSQL esté instalado y psql en PATH.
    exit /b 1
)

:: Moverse al directorio raíz del proyecto
cd /d "%~dp0.."

:: Pedir usuario
set /p pg_user=Ingrese el usuario de PostgreSQL:

:: Pedir contraseña
set /p pg_pass=Ingrese la contraseña de PostgreSQL:

echo === Generando archivo hibernate.properties con credenciales ingresadas ===
(
echo hibernate.connection.username=%pg_user%
echo hibernate.connection.password=%pg_pass%
) > ./config/hibernate.properties

:: Configurar variable de entorno temporal para psql
set PGPASSWORD=%pg_pass%

:: Crear base de datos si no existe
echo === Creando base de datos cooktrack_dev si no existe ===
psql -U %pg_user% -tc "SELECT 1 FROM pg_database WHERE datname = 'cooktrack_dev'" | findstr 1 > nul
if %ERRORLEVEL% NEQ 0 (
    psql -U %pg_user% -c "CREATE DATABASE cooktrack_dev;"
    echo Base de datos cooktrack_dev creada.
) else (
    echo La base de datos cooktrack_dev ya existe.
)

:: Ejecutar schema.sql
echo === Ejecutando schema.sql ===
psql -U %pg_user% -d cooktrack_dev -f sql\init.sql
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo al ejecutar schema.sql.
    exit /b 1
)

echo === Borrando usuario admin (si existe) ===
psql -U %pg_user% -d cooktrack_dev -c "DELETE FROM users WHERE name = 'admin';"

:: Limpiar variable de entorno
set PGPASSWORD=

echo === Verificando Maven e instalando dependencias===
mvn clean install
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo en mvn clean install.
    exit /b 1
)
