
clean:
	./gradlew clean

build:
	./gradlew build -xtest

run:
	./gradlew run

tests:
	./gradlew check

versioncheck:
	./gradlew dependencyUpdates

depends:
	./gradlew dependencies

upgrade-wrapper:
	./gradlew wrapper --gradle-version=6.8.2 --distribution-type=bin