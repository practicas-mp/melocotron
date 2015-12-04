.SUFFIXES:
.PHONY: all, msg_clases, comp, recomp, clean

JAVASRCS:=$(wildcard **/*.java)
	
all: $(JAVASRCS)
	javac -g $(JAVASRCS)

recomp:
	make clean	
	make comp

$(CLASSES): $(JAVASRCS)
	javac -g $(JAVASRCS)

