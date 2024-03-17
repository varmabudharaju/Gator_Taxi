JCC = javac

default: jar

jar: gatorTaxi.class
	@echo "Manifest-Version: 1.0" >> manifest.mf
	@echo "Main-Class: gatorTaxi" >> manifest.mf
	@echo "" >> manifest.mf
	jar cmf manifest.mf gatorTaxi.jar *.class *.java
	@echo "#!/usr/bin/java -jar" > gatorTaxi
	cat gatorTaxi.jar >> gatorTaxi
	chmod +x gatorTaxi

gatorTaxi.class: gatorTaxi.java
	$(JCC) *.java
clean:
	rm -f *.class
	rm -rf manifest.mf
	rm -rf *.jar