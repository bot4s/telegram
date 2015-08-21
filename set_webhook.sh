#!/bin/bash

TOKEN="$1"
WEBHOOK_URL="$2"
curl --data-urlencode "url=$WEBHOOK_URL" https://api.telegram.org/bot$TOKEN/setWebhook
