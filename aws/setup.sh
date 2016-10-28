#!/bin/bash

echo "Enter access key:"
read AWS_ACCESS_KEY

echo "Enter secret key:"
read AWS_SECRET_KEY

(   cd ansible/group_vars && \
    echo -e "aws_access_key: ${AWS_ACCESS_KEY}\naws_secret_key: ${AWS_SECRET_KEY}\n" >> all && \
    ansible-vault encrypt all
)
