# Compilation
default:
	javac -d bin -cp src src/main/arda/*.java

run:
	java -Dsun.java2d.d3d=false -cp bin main.arda.App

# Destruction
clean:
	rm -rf bin
