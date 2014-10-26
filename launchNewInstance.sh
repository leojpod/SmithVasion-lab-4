#!/bin/bash


if [ "$#" -ne 1 ]; then
	echo "Usage : number of instance to launch in parameter"
	exit 1
fi
AMI=ami-6803aa1f
NUMBER=$1
aws ec2 run-instances --image-id $AMI --count $NUMBER --instance-type t2.micro --key-name 14\ _LP1_KEY_\ D7001D_thimar-4 --subnet-id subnet-47ca2d1e > aws_output
line=$(head -n 2 aws_output)
echo $line > aws_output
cut -d" " -f 10 aws_output
rm aws_output
