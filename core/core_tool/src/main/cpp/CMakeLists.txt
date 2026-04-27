cmake_minimum_required(VERSION 3.22.1)

project("jamendo_native")

add_library(
        native-lib # 库名称，对应 System.loadLibrary("native-lib")
        SHARED
        native-lib.cpp # 源文件
)

find_library(log-lib log)

target_link_libraries(
        native-lib
        ${log-lib}
)
