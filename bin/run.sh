./gradlew build -x test

docker kill test_felipe
docker rm test_felipe

cp build/libs/gfn_backend_challenge_felipe-Lucas.jar .

docker build -t "test_felipe" .

docker run -d --net="host" --name test_felipe -p 8080:8080 -e SPRING_PROFILES_ACTIVE=local -t "test_felipe" 

