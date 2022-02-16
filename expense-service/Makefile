APP_NAME := expense-service
build :
	docker build . -t $(APP_NAME) --build-arg port=6666

run-test :
	docker run --name $(APP_NAME) -d -p 6666:6666 -rm $(APP_NAME)

run :  build
	docker compose up --remove-orphans