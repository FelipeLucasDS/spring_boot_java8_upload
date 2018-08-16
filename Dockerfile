FROM openjdk:8-jre-alpine

ENV HOME=/home/gfn_backend_challenge_felipe-Lucas

WORKDIR $HOME

ADD gfn_backend_challenge_felipe-Lucas.jar gfn_backend_challenge_felipe-Lucas.jar

CMD ["java", "-jar", "gfn_backend_challenge_felipe-Lucas.jar"]