.PHONY: install test

install:
	javac -d bin src/*.java && cd bin && jar cvfm ./CIRI-Full_v2.1.2.jar MANIFEST.MF *.class && rm *.class

test:
	java -jar ./bin/CIRI-Full_v2.1.2.jar 
