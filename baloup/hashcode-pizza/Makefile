# nom d'archive
ARCHIVE=TP4_Baloup_Badiane.zip


# fichiers binaires à produire
# les prérequis (.o) des binaires sont à définir plus bas dans le fichier
BINARIES = pizza

# commande de compilation
CC      = g++

# options de compilation (.o et linkage)
CFLAGS  = -O3 -std=c++0x







# =========== NE PAS TOUCHER ===========
# commande 'make' sans argument
all: ${BINARIES}

#LDLIBS  = -L${LIBDIR} $(addprefix -l,$(LIBS))

# Compilation séparée
%.o: %.cpp
	${CC} ${CFLAGS} -c $<

clean:
	rm *.o ${BINARIES} || true

# cible utilisée pour le rendu sous forme d'archive
archive: clean
	zip -r ../${ARCHIVE} *

.PHONY: all clean archive

${BINARIES}:
	${CC} ${CFLAGS} -o $@ $^
#${CC} ${CFLAGS} -o $@ $^ ${LDLIBS}
# ======================================




# ----- les exécutables à produire -----
pizza: pizza.o
