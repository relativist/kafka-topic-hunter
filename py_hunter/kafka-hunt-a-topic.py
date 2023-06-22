import json

from kafka import KafkaConsumer

# pip install kafka-python

# To consume latest messages and auto-commit offsets
consumer = KafkaConsumer('raw-oms-big',
                         group_id='python3',
                         auto_offset_reset='earliest',
                         enable_auto_commit=False,
                         value_deserializer=lambda m: json.loads(m.decode('utf-8')),
                         # consumer_timeout_ms=1000,
                         bootstrap_servers=['192.168.100.24:9092'])

counter = 1
for message in consumer:
    print("%d %s" % (counter, message.value['fileName']))
    counter += 1
    f = open("kafka-msg.txt", "w")
    f.write((str(message.value)))
    f.close()
    # print("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition,
    #                                      message.offset, message.key,
    #                                      message.value))
