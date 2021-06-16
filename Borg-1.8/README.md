# 実行方法

1. jna.jarが必要
システムにインストールされてなければインストールする

Ubuntuだとlibjna-javaに入っている

/usr/share/java/jna.jar

をMOEAFrameworkの[Property] - [Java Build Path]の
LibrariesのClasspathにjna.jarを追加する

import com.sun.jna.Callback;
import com.sun.jna.Function;

の赤線がなくなればOK

2. Test.javaの実行には、libborg.soが必要
binにシンボリックリンクが作ってある。

libborg.so -> ../Borg-1.8/plugins/Java/native/linux-x86_64/libborg.so*

リンクが切れている場合は、../Borg-1.8/plugins/Javaの

build_native.sh

を実行する
