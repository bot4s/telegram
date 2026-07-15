# Serverless Echo Example

Build the Scala.js module:

```sh
MILL_VERSION=1.0.6-jvm ./mill examples.serverlessjs[2.13.18].fullLinkJS
```

Copy the linked JavaScript output to `lib/echo_serverless_bot.js` in a `tgcloud` project, then use `handlers/message.js` from this directory as the default-export wrapper. Telegram Serverless resolves imports by module name, so the handler imports `lib/echo_serverless_bot` rather than a relative path.
