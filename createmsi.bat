@echo off

javapackager -deploy -native msi ^
-v ^
-outdir dist -outfile AnalogClockGadget ^
-p dist -m AnalogClockGadget/com.torutk.gadget.analogclock.AnalogClockApp ^
-name "AnalogClock" ^
-BappVersion=0.4.2 ^
-title "Analog Clock Gadget" ^
-vendor Takahashi ^
-description "Analog Clock on desktop" ^
-BjvmOptions=-Xms32m ^
-BjvmOptions=-Xms128m ^
-BjvmOptions=-Xss256k ^
-BjvmOptions=-XX:TieredStopAtLevel=1 ^
-BjvmOptions=-XX:CICompilerCount=2 ^
-BjvmOptions=-XX:CompileThreshold=1500 ^
-BjvmOptions=-XX:InitialCodeCacheSize=160k ^
-BjvmOptions=-XX:ReservedCodeCacheSize=32m ^
-BjvmOptions=-XX:+UseSerialGC
