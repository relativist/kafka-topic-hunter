#!/bin/bash


kafkaServer=192.168.100.24:9092
kafkaApi=~/soft/kafka-3.4.1-src/bin/

cd $kafkaApi || exit

topics=$(./kafka-topics.sh --bootstrap-server $kafkaServer --list)

./kafka-topics.sh --bootstrap-server $kafkaServer --alter --topic raw-emd --partitions 7
./kafka-topics.sh --bootstrap-server $kafkaServer --alter --topic raw-oms --partitions 7
./kafka-topics.sh --bootstrap-server $kafkaServer --alter --topic raw-oms-big --partitions 7

for topic in $topics; do
  if [[ $topic == oms-* ]] || [[ $topic == semd-* ]]; then
    echo 'Repartition: '$topic
    ./kafka-topics.sh --bootstrap-server $kafkaServer --alter --topic $topic --partitions 7
  fi
done

echo "DONE."