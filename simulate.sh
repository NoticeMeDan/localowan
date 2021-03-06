#!/bin/bash
echo "Simulating messages with 'Hej' as payload, every 30 seconds..."

while :
do
  ttn-lw-cli simulate uplink \
     --lorawan-version=1.1.0 \
     --lorawan-phy-version=1.0.1 \
     --gateway-id=test-gateway \
     --dev-addr=0192EFE9 \
     --f-nwk-s-int-key=A1B02BD98BBB6175B7EE0901639002E3 \
     --s-nwk-s-int-key=739C5B160C869248F139B364BF1E9F7B \
     --nwk-s-enc-key=9619B7B3E16F72910D916623296D2EFA \
     --app-s-key=E6A4F29F557A8A1F73BF763931D62433 \
     --f-cnt=$RANDOM \
     --f-port=2 \
     --frm-payload=48656A

     sleep 30
done
