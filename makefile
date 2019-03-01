build:
	mkdir -p bin
	javac -d bin -cp src:res:lib/* src/fr/main/Main.java

run:
	java -cp bin:res:lib/* -Djava.library.path=res/natives fr.main.Main

clean:
	rm -r -f bin/*

.PHONY: build run clean
