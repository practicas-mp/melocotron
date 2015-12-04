
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

monitor_classes: $(wildcard monitor/*.class)
	javac -g monitor/*.java -d .

clean:
	rm -rf *.class monitor/*.class scd-p02-ejemplos.zip



