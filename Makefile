SHELL = /bin/bash

JC = javac
JFLAGS = -g -Xlint:all

BIN = bin
SRC = src

CLASSES = $(SRC)/util/NotImplementedException \
	  $(SRC)/z808/Assembler \
	  $(SRC)/z808/Linker \
	  $(SRC)/z808/MacroProcessor \
	  $(SRC)/z808/Processor \
	  $(SRC)/z808/ui/UIz808

.PHONY: doc clean default
default: classes

classes: $(addsuffix .java, $(CLASSES))
	$(JC) $(JFLAGS) -cp $(BIN) $(addsuffix .java, $(CLASSES)) -d $(BIN)

clean:
	$(RM) $(BIN)/**/*.class
