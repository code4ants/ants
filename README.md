# ants
Leave no ant behind!

##Quick instructions
	
###checkout sources
	git clone git@github.com:lordsoftheants/ants.git

###build
	mvn clean install
	deploy web/war to tomcat

###fire up browser and go to http://localhost:8080
	google-chrome http://localhost:8080

###build client and register
	cd client
	mvn assembly:single
	
	./ant-client -f target/classes/Brain.class -q Brain -u benishor -h http://localhost:8080 
	./ant-client -a login
	./ant-client -a upload
	
###start the game
	curl -X POST http://localhost:8080/game/start

###watch the game unfold in browser
