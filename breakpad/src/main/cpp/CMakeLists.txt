cmake_minimum_required(VERSION 3.4.1)

include_directories(breakpad/src breakpad/src/common/android/include)
# libbugly.so
add_library(breakpadcrash-lib SHARED breakpad.cpp)

add_subdirectory(breakpad)

# 链接ndk中的log库
target_link_libraries(breakpadcrash-lib  breakpad log)