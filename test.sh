#!/usr/bin/env bash
./gradlew clean
./gradlew jar
standardOutput=$(java -jar quine/build/libs/quine.jar)
fileContents=$(<quine/src/main/kotlin/com/nightlynexus/kotlinpoetquine/Quine.kt)
if [[ ${standardOutput} == ${fileContents} ]]; then
    exit 0
fi
exit 1
