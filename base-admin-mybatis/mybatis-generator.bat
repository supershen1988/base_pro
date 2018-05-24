@echo off
echo [Pre-Requirement] Makesure install JDK 8.0+ and set the JAVA_HOME.
echo [Pre-Requirement] Makesure install Maven 3.0.3+ and set the PATH.

set MVN=mvn

if errorlevel 1 goto error

echo [Step 1] run gt-web project in dev mode.

call %MVN% mybatis-generator:generate

if errorlevel 1 goto error


goto end
:error
echo Error Happen!!
:end
pause