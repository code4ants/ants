# ants
Leave no ant behind!

##Quick instructions

###checkout sources

###build
	mvn clean install
	deploy web/war to tomcat

###fire up browser and go to http://localhost:8080

###build client and register

	cd client
	mvn assembly:single
	
	./ant-client -f target/classes/Brain.class -q Brain -h http://localhost:8080 -a login
	./ant-client -a upload
	
	curl -X POST http://localhost:8080/game/start

###watch the game unfold in browser
