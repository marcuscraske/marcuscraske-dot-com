# Amazon Web Services (AWS) Deployment
This directory is used for AWS deployments.

## Credentials
You will need to put the following vars:
````
AWS_ACCESS_KEY_ID
AWS_ACCESS_KEY
````

In a vault file:
````
(cd ansible/roles/setup/vars && ansible-vault create key.yml)
````

This is ignored by Git.

## Dependencies
````
sudo apt-get install ansible python-pip
sudo pip install boto
````

## Useful Resources
Much credit owed to the following for CloudFormation:

<https://raw.githubusercontent.com/mikery/aws-system-administration/master/cloudformation_stacks/s3_static_website_with_cloudfront_and_route_53/s3_static_website_with_cloudfront_and_route_53.template>
