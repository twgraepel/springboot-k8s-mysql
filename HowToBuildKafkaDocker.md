#How I built my Kafka docker image

https://docs.confluent.io/current/quickstart/cos-docker-quickstart.html

1.  Get the confluent version
    1.  git clone https://github.com/confluentinc/cp-all-in-one
    1.  cd cp-all-in-one
    1.  git checkout 5.5.0-post
1.  Build the image
    1.  cd cp-all-in-one-community
    1.  docker-compose up -d --build
    1.  docker-compose ps
    
    
# Create the Topics
docker-compose exec broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic mytopic1
