#!/bin/bash

TOKEN="$1"
echo --data-urlencode "url=\"\"" https://api.telegram.org/bot$TOKEN/setWebhook
