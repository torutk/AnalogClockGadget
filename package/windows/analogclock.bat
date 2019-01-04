@echo off
set JLINK_VM_OPTIONS=-Xms32m -Xmx64m -Xss256k ^
-XX:TieredStopAtLevel=1 -XX:CICompilerCount=2 -XX:CompileThreshold=1500 ^
-XX:InitialCodeCacheSize=160k -XX:ReservedCodeCacheSize=32m ^
-XX:MetaspaceSize=12m ^
-XX:+UseSerialGC
set DIR=%~dp0
"%DIR%\java" %JLINK_VM_OPTIONS% ^
-m com.torutk.gadget.analogclock/com.torutk.gadget.analogclock.AnalogClockApp ^
--scale=0.7 --x=0 --y=0 --fps=4 ^
%*