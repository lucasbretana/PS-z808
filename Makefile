SHELL = /bin/bash

JC = javac
JFLAGS = -g -Xlint:all

JD = javadoc
JDFLAG = -Xdoclint:all

BIN = bin
SRC = src
DOC = doc

FILES =	\
				util/ExecutionException \
				util/NotImplementedException \
				z808/Address \
				z808/Memory \
				z808/command/Command \
				z808/command/directive/Directive \
				z808/command/directive/DW \
				z808/command/instruction/Instruction \
				z808/command/instruction/AddDX \
				z808/MacroProcessor \
				z808/Assembler \
				z808/Linker \
				z808/Processor \
				z808/ui/UIz808 \
				z808/MainTest

PACKAGES = util \
					 z808/command \
					 z808/command/directive \
					 z808/command/instruction \
					 z808 \
					 z808/ui

JVS = $(addprefix $(SRC)/, $(addsuffix .java , $(FILES)))
CLS = $(addprefix $(BIN)/, $(addsuffix .class, $(FILES)))
PKS = $(addprefix $(BIN)/, $(addsuffix .pkt, $(PACKAGES)))

define clean_regex
	find bin/ -name '$(1)' -exec rm {} \;
endef

.PHONY: all build clean test doc
all: build

build: buildByClasses

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
	$(call clean_regex,*.pkt)
	$(call clean_regex,*.class)
	$(RM) -r $(DOC)/*
