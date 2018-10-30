---
layout: post
title: Splunk Alerts on Slack
selected: blog
---

<a href="/assets/posts/2017-11-06-splunk-alerts-on-slack/aws-splunk-slack.png">
    <img src="/assets/posts/2017-11-06-splunk-alerts-on-slack/aws-splunk-slack.png" alt="Splunk, AWS and Slack" class="left" />
</a>

For those using log tools such as [Splunk](https://splunk.com), you can setup alerts. These will run queries every so
often and trigger actions when conditions are met e.g. count of events surpasses a threshold.

This post is about pushing those alerts from Splunk to Slack. When an alarm is triggered,
a JSON request is made by Splunk to a URL you provide. This gives the benefit of your own customisation of
messages.


## Reasons
You can find applications on the Splunk app store to post alerts to Slack. However you may have issues when running
such apps on a cluster. Or you may have permission issues in an enterprise environment. In my situation, I ran into both
problems.

## Flow
1. Splunk invokes an AWS API Gateway endpoint.
2. The AWS API Gateway endpoint invokes an AWS Lambda function.
3. The AWS Lambda function sends a message to a Slack inbound web hook (and delivers the message your own desired Slack channel).


## Step 1 - Create Slack Inbound Webhook
Go to the following page, whilst logged-in to your Slack workspace:

<https://slack.com/apps/A0F7XDUAZ-incoming-webhooks?page=1>

Or alternatively, from Slack's website:
- Configure apps
- Custom integrations (sidebar)
- Incoming WebHooks

More information about inbound web hooks can be found here:

<https://api.slack.com/incoming-webhooks>

Once you've setup an inbound webhook, copy the <i>Webhook URL</i> for the next step. Let's pretend it's:

<https://hooks.slack.com/services/XXXXX/YYYY/ZZZZZZZZZZZZZZ>


## Step 2 - Setup AWS Lambda Function
Create a new Lambda function from scratch:

<https://console.aws.amazon.com/lambda/home?region=us-east-1#/create/new>

For this example, we'll call it <i>splunk alert</i>.

Then paste in the following, but edit the request options with parts of the <i>Webhook URL</i> from the
previous step:

<pre class="brush: javascript">
var util = require("util");
var https = require("https");

exports.handler = (event, context, callback) => {
    console.log('Received event:', JSON.stringify(event, null, 2));

    // request headers - needs to be configured with Slack inbound hook
    var request_options = {
      hostname: 'hooks.slack.com',
      path: '/services/XXXXX/YYYY/ZZZZZZZZZZZZZZ',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }
    };

    // open up the connection
    var req = https.request(request_options, function(result) {
        callback(null, 'Success');
    });

    req.on('error', function(err) {
        console.log('Error, with: ' + err.message);
        callback('Error, with: ' + err.message);
    });

    // parse json from request to this lambda
    var json = JSON.parse(event.body);

    // read data from splunk
    var result = json["result"];
    var searchLink = json["results_link"];
    var searchName = json["search_name"];

    // convert splunk result fields into fields for slack message
    var fields = [];
    for (var key in result)
    {
        var item = {
            "title" : key,
            "value" : result[key],
            "short" : true
        };
        fields.push(item);
    }

    // build message
    var message = {
    	"channel": "#slack-channel",
    	"username": "monkeyboy",
    	"icon_emoji": ":monkey:",
        "attachments":[
            {
                "pretext" : ":banana: *" + searchName + "* :banana:",
                "title" : "Click to view search results",
                "title_link" : searchLink,
                "fields" : fields,
                "color" : "danger",
                "mrkdwn_in" : ["pretext"]
            }
        ]
    };

    // write message data
    req.write(JSON.stringify(message));

    // close connection
    req.end();

};
</pre>

Towards the end is the JSON object `message`, which is the Slack message written to the Slack inbound webhook. This can
be heavily customised.

Docs on Slack message format:
- <https://api.slack.com/docs/message-formatting>
- <https://api.slack.com/docs/message-attachments>
- <https://api.slack.com/docs/messages/builder>

Once you're happy with your Lambda, publish it. At the top, go to <i>Actions</i> and select <i>Publish new version</i>.

## Step 3 - Create AWS API Gateway Trigger
On your AWS Lambda function page, go to the <i>Triggers</i> tab and add a trigger.

You will then see a dotted box, click it and select <i>API Gateway</i>.

Then will in the form with an <i>API name</i> (such as <i>splunk-alerts</i>) and set the <i>Security</i> to
<i>Open</i> (although you may want to change this later):

<a href="/assets/posts/2017-11-06-splunk-alerts-on-slack/add-trigger-aws.png">
    <img src="/assets/posts/2017-11-06-splunk-alerts-on-slack/add-trigger-aws.png" alt="Add trigger dialogue on AWS" />
</a>

You should now have API Gateway available as a trigger. Click the arrow icon to show the endpoint's URL.


## Step 4 - Test Trigger
Let's say the trigger URL is:

<https://xyz.execute-api.us-east-1.amazonaws.com/prod/splunk-alerts>

Just make a POST request with the following test payload:

<pre class="brush: javascript">
{
  "result": {
    "test" : "value"
    "count" : "8",
    "host": "xyz"
  },
  "sid": "test_search_id",
  "results_link": "https://google.com",
  "search_name": "example alert name",
  "owner": "foobar",
  "app": "search"
}
</pre>

In Chrome I use the <i>Postman</i> app, but this is simple enough to achieve with cURL as well:

<pre class="brush: bash">
echo "{  \"result\": {\"test\" : \"value\"\"count\" : \"8\",\"host\": \"xyz\"  },  \"sid\": \"test_search_id\",  \"results_link\": \"https://google.com\",  \"search_name\": \"example alert name\",  \"owner\": \"foobar\",  \"app\": \"search\"}"
| curl -d @- "https://xyz.execute-api.us-east-1.amazonaws.com/prod/splunk-alerts"
</pre>

## Step 5 - Splunk Alert
Run a query on the Splunk search application. Once it has finished loading, select <i>Save As</i> and then <i>Alert</i>.

Configure the alert as needed; useful docs:

<https://docs.splunk.com/Documentation/SplunkCloud/6.6.3/Alert/Alertexamples>

After the alert is setup, add a <i>Webhook</i> action and set the URL to the endpoint created earlier.


## Summary
You should now have <i>monkeyboy</i> to save the day:

<a href="/assets/posts/2017-11-06-splunk-alerts-on-slack2.png">
    <img src="/assets/posts/2017-11-06-splunk-alerts-on-slack/monkey-boy.png" alt="monkeyboy on Slack" />
</a>
