
#!/bin/bash
AMI=ami-9efd56e9
aws_output_creation=/home/ubuntu/aws_output_creation
aws_output=/home/ubuntu/aws_output
su root -c "aws ec2 run-instances --image-id $AMI --count 1 --instance-type t2.micro --key-name 14\ _LP1_KEY_\ D7001D_thimar-4 --subnet-id subnet-47ca2d1e > $aws_output_creation"
line=$(head -n 2 $aws_output_creation)
echo $line > $aws_output
instance=$(cut -d" " -f 10 $aws_output)
echo $instance
rm aws_output_creation aws_output
