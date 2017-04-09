JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java
CLASSES =\
		ANode.java \
		BinaryHeap.java \
		DaryHeap.java \
		PairingHeap.java \
        encoder.java \
		decoder.java
MAIN = encoder
default: classes
classes: $(CLASSES:.java=.class)
clean:
	find . -type f | xargs -n 5 touch
	$(RM) *.class
