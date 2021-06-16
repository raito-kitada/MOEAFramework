#!/bin/bash

OS_NAME=`uname | sed -e 's/CYGWIN.*/win32/g' \
                     -e 's/MINGW32.*/win32/g' \
                     -e 's/SunOS.*/solaris/g' \
                     -e 's/NetBSD/netbsd/g' \
                     -e 's/GNU\/kFreeBSD/kfreebsd/g' \
                     -e 's/FreeBSD/freebsd/g' \
                     -e 's/OpenBSD/openbsd/g' \
                     -e 's/Darwin.*/darwin/g' \
                     -e 's/Linux.*/linux/g'`

CFLAGS="-O3"

echo $OS_NAME
if [ "$OS_NAME" == "win32" ]; then
    ARCH_NAME=`uname -m | sed 's/i.86/i386/g'`
    LIB_PREFIX=""
    LIB_POSTFIX=".dll"
elif [ "$OS_NAME" == "linux" ] || [ "$OS_NAME" == *"bsd"* ]; then
    ARCH_NAME=`uname -m | sed 's/i.86/i386/g'`
    LIB_PREFIX="lib"
    LIB_POSTFIX=".so"
    CFLAGS="${CFLAGS} -fPIC"
elif [ "$OS_NAME" == "solaris" ]; then
    ARCH_NAME=`uname -p`
    LIB_PREFIX="lib"
    LIB_POSTFIX=".so"
elif [ "$OS_NAME" == "darwin" ]; then
    ARCH_NAME=`arch`
    LIB_PREFIX="lib"
    LIB_POSTFIX=".dylib"
else
    echo "Unsupported operating system!"
    exit
fi

DIRECTORY=native/${OS_NAME}-${ARCH_NAME}
rm -rf "${DIRECTORY}"
mkdir -p "${DIRECTORY}"
echo gcc -shared -s ${CFLAGS} -o "${DIRECTORY}/${LIB_PREFIX}borg${LIB_POSTFIX}" ../../borg.c ../../mt19937ar.c
gcc -shared -s ${CFLAGS} -o "${DIRECTORY}/${LIB_PREFIX}borg${LIB_POSTFIX}" ../../borg.c ../../mt19937ar.c
