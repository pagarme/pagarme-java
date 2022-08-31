default: build

build:
	docker build -t pagarme-java .

run:
	docker run -it --rm pagarme-java

.PHONY: build run
