SHELL = /bin/bash

JC = javac
JFLAGS = -g -Xlint:all -Xdiags:verbose

JD = javadoc
JDFLAG = -Xdoclint:all

JR = jar
JR_FLAG = cmvf
JR_MANIFEST = META-INF/MANIFEST.MF
TARGET = Simulator-z808

BIN = bin
SRC = src
DOC = doc

PACKAGES =\
	util \
	z808/memory \
	z808/command \
	z808/command/directive \
	z808/command/instruction \
	z808 \
	z808/ui

PKS = $(addprefix $(BIN)/, $(addsuffix .pkt, $(PACKAGES)))

define clean_regex
	find bin/ -name '$(1)' -exec rm {} \;
endef

.PHONY: all build clean jar
all: build

jar: $(TARGET).jar

$(TARGET).jar: build
	cd $(BIN);$(JR) $(JR_FLAG) $(JR_MANIFEST) $(TARGET).jar $(addsuffix /*.class, $(PACKAGES));mv $(TARGET).jar ..

build: $(PKS)

bin/%.pkt: src/%
	$(JC) $(JFLAGS) -cp $(BIN) $^/*.java -d $(BIN)
	@touch $@

clean:
	$(call clean_regex,*.pkt)
	$(call clean_regex,*.class)
	$(RM) -f $(TARGET).jar
	$(RM) -r $(DOC)/*
