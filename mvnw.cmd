@echo off
setlocal
set MAVEN_HOME=%~dp0.mvn\apache-maven-3.9.9
if exist "C:\Program Files\Java\jdk-23\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-23"
call "%MAVEN_HOME%\bin\mvn.cmd" %*
