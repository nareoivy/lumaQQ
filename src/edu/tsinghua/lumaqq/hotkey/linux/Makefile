JAVA_HOME=/usr/java/jdk1.5.0_04
KEY_BINDER_JNI = edu_tsinghua_lumaqq_hotkey_KeyBinder

INCLUDE=-I/usr/include/gtk-2.0 -I/usr/lib/gtk-2.0/include -I/usr/include/atk-1.0 -I/usr/include/cairo -I/usr/include/pango-1.0 -I/usr/include/glib-2.0 -I/usr/lib/glib-2.0/include -I$(JAVA_HOME)/include/linux -I$(JAVA_HOME)/include

SRC=eggaccelerators.h eggaccelerators.c tomboykeybinder.h tomboykeybinder.c $(KEY_BINDER_JNI).h $(KEY_BINDER_JNI).c

all : libkeybinder.so

libkeybinder.so : $(SRC)
	gcc -fPIC -shared $(SRC) -o libkeybinder.so $(INCLUDE) 

clean :
	rm -fr *.o *.so
