./kafka-configs.sh --bootstrap-server 192.168.100.24:9092 \
                --alter --entity-type topics \
                --entity-name raw-oms-big \
                --add-config max.message.bytes=304857600