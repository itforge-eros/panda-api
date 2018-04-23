start:
	mkdir -p data/postgresql
	docker stack deploy -c docker-compose.yml panda-stack
stop:
	docker stack rm panda-stack
createdb:
	createuser -s panda-api -h 127.0.0.1 -U postgres
	dropdb --if-exists "panda-api-development" -h 127.0.0.1 -U postgres
	createdb "panda-api-development" -O panda-api -E utf8 -h 127.0.0.1 -U postgres
