build: *.java
	javac *.java
	echo Main-Class: Driver > MANIFEST.MF
	jar -cvmf MANIFEST.MF hw1.jar Driver.class
	java -classpath ../hw1.jar:. Driver

clean: *.class
	rm *.class
	
