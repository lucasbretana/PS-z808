SHELL = /bin/bash

JC = javac
JFLAGS = -g -Xlint:all

JD = javadoc
JDFLAG = -Xdoclint:all

BIN = bin
SRC = src
DOC = doc

FILES = util/NotImplementedException \
				z808/Command \
				z808/command/Directive \
				z808/command/Instruction \
				z808/command/instruction/Add \
				z808/command/instruction/Sub \
				z808/Assembler \
				z808/Linker \
				z808/MacroProcessor \
				z808/Processor \
				z808/ui/UIz808

PACKAGES = util \
					 z808 \
					 z808/command \
					 z808/command/instruction \
					 z808/ui

JVS = $(addprefix $(SRC)/, $(addsuffix .java , $(FILES)))
CLS = $(addprefix $(BIN)/, $(addsuffix .class, $(FILES)))
PKS = $(addprefix $(BIN)/, $(addsuffix .pkt, $(PACKAGES)))

.PHONY: all build clean test doc
all: build

build: buildByPackage

.PHONY: buildAll buildByClasses buildByPackage
buildByPackage: $(PKS)
buildByClasses: $(CLS)
buildAll:
	$(JC) $(JFLAGS) -cp $(BIN) $(JVS) -d $(BIN)

bin/%.pkt: src/%
	@echo "Building for $@ with $^"
	$(JC) $(JFLAGS) -cp $(BIN) $^/*.java -d $(BIN)
	@touch $@

bin/%.class: src/%.java
	@echo "Building for $@ with $<"
	$(JC) $(JFLAGS) -cp $(BIN) $< -d $(BIN)

clean:
	$(RM) $(BIN)/*.pkt
	$(RM) $(BIN)/*/*.class
	$(RM) $(BIN)/*/*.pkt
	$(RM) $(BIN)/*/*/*.class
	$(RM) -r $(DOC)/*
