SHELL = /bin/bash

JC = javac
JFLAGS = -g -Xlint:all

JD = javadoc
JDFLAG = -Xdoclint:all

BIN = bin
SRC = src
DOC = doc

CLASSES = $(SRC)/util/NotImplementedException \
	  $(SRC)/z808/Assembler \
	  $(SRC)/z808/Linker \
	  $(SRC)/z808/MacroProcessor \
	  $(SRC)/z808/Processor \
	  $(SRC)/z808/ui/UIz808

.PHONY: doc clean default
default: classes

build: $(addsuffix .java, $(CLASSES))
	$(JC) $(JFLAGS) -cp $(BIN) $(addsuffix .java, $(CLASSES)) -d $(BIN)

doc: $(addsuffix .java, $(CLASSES))
	$(JD) $(JDFLAGS) -cp $(BIN) $(addsuffix .java, $(CLASSES)) -d $(DOC)

clean:
	$(RM) $(BIN)/**/*.class
	$(RM) -r $(DOC)/*
