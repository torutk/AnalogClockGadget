@echo off

REM increment the version number each creation.
set APP_VERSION=0.5.6

%JAVA_HOME%\bin\jpackage ^
--type msi ^
--win-upgrade-uuid fe287b25-e03d-4744-90f4-96b05dc36bf0 ^
--win-menu-group "High Bridge" ^
--win-menu ^
--win-shortcut ^
--app-version %APP_VERSION% ^
--description "Analog Clock on desktop" ^
--name "AnalogClock" ^
--dest build\installer ^
--vendor "High Bridge" ^
--module-path build\libs ^
--module com.torutk.gadget.analogclock ^
--java-options "-Xms32m -Xmx64m -Xss256k -XX:TieredStopAtLevel=1 -XX:CICompilerCount=2 -XX:CompileThreshold=1500 -XX:InitialCodeCacheSize=160k -XX:ReservedCodeCacheSize=32m -XX:MetaspaceSize=12m -XX:+UseSerialGC" ^
--verbose
