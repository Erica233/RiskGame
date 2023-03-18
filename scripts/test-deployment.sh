#!/bin/bash

#annoying thing about docker:
#hard to tell when service inside it is actually up and running..
#have to just try to connect.
x=""
count=0
while [ "$x" == "" ]
do
    if [ "$count" == "20" ]
    then
        echo "Giving up after 20 attempts to connect!"
        exit 1
    fi
    x=`netcat -N -w 1 localhost 12345 < /dev/null`
    let count=count+1
    sleep 5
done

echo "Connected after $count attempts"


nc -N localhost 12345 > testoutput <<EOF
EOF
cat > expectedoutput <<EOF
EOF

diff testoutput expectedoutput

