#!/bin/bash

wget https://bootstrap.pypa.io/get-pip.py
python get-pip.py
rm get-pip.py
pip install awscli
# get auto completion on bash
echo "complete -C /usr/local/bin/aws_completer aws" >> ~/.bashrc
