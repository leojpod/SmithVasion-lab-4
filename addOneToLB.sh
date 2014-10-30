#!/bin/bash

# Launch a new aws instance from $AMI image
AMI=ami-541db523
#number of new instances
NUMBER=1
aws_output_creation=/home/ubuntu/aws_output_creation
aws_output=/home/ubuntu/aws_output
i=$NUMBER

while [ "$i" -ne 0 ]; do
su root -c "aws ec2 run-instances --image-id $AMI --count 1 --instance-type t2.micro --key-name 14\ _LP1_KEY_\ D7001D_thimar-4 --subnet-id subnet-47ca2d1e > $aws_output_creation"
line=$(head -n 2 $aws_output_creation)
echo $line > $aws_output
instance=$(cut -d" " -f 10 $aws_output)
echo $instance
rm aws_output_creation aws_output

# add the previous instance to the load balancer
su root -c "aws elb register-instances-with-load-balancer --instances $instance --load-balancer-name thimarLB2"
((i=$i-1))
done
