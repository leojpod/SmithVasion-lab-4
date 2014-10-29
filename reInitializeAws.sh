#!/bin/bash
set -x

instList=`su root -c "aws elb describe-load-balancers --load-balancer-names thimarLB2 | grep INSTANCES | cut -f2 | sed ':a;N;$!ba;s/\n/ /g'"`
instList=`echo $instList | sed -e 's/i-f41f2db6//g' `
instList=`echo $instList | sed -e 's/i-d65e4894//g' `
instList=`echo $instList | sed -e 's/i-9c7066de//g' `
su root -c "aws elb deregister-instances-from-load-balancer --load-balancer-name thimarLB2 --instances $instList"
su root -c "aws ec2 terminate-instances --instance-ids $instList"
