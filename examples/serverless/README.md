# Serverless Echo Example

Build a tgcloud-ready module tree:

```sh
./mill 'examples.serverlessjs[2.13.18].tgcloudBundle'
```

The result is written to `out/examples/serverlessjs/2.13.18/tgcloudBundle.dest`:

```text
handlers/message.js
lib/echo_serverless_bot.js
schema.js
```

Create a tgcloud project and copy the bundle into it:

```sh
npm create @tgcloud/bot my-bot
cp -R out/examples/serverlessjs/2.13.18/tgcloudBundle.dest/. my-bot/
```

Continue with Telegram's [getting started guide](https://core.telegram.org/bots/serverless#getting-started) to connect, test, and deploy the project.
