cmake_minimum_required(VERSION 3.4.1)

add_library(utxo-jni SHARED
            utxo-jni.c)

# Include libraries needed for mimba-jni lib
target_link_libraries(utxo-jni
                      android
log)
