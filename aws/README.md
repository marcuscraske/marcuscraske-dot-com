# Amazon Web Services (AWS) Deployment
This directory is used for AWS deployments.

## Credentials
You will need to put the following vars into `ansible/group_vars/all`:
````
aws_access_key
aws_secret_key
````

Or just run `setup.sh`.

Your newly created file is ignored by Git.

## Dependencies
````
sudo apt-get install ansible python-pip
sudo pip install boto
````

## Useful Resources
Much credit owed to the following for CloudFormation:

<https://raw.githubusercontent.com/mikery/aws-system-administration/master/cloudformation_stacks/s3_static_website_with_cloudfront_and_route_53/s3_static_website_with_cloudfront_and_route_53.template>
