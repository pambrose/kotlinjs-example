
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
	./gradlew wrapper --gradle-version=6.7.1 --distribution-type=bin