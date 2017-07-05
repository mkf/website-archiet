#!/bin/bash

while true; do 
	finger archiet | grep -aie unread -e mail | grep read > mailfinger
	sleep 20
done
