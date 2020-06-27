Download: https://www.spigotmc.org/resources/telegram-reporter-reborn.80768/

Description:
This plugin allows you to monitor you server via telegram bots. and see the reports in your staff group or your private chat.
Features:
- See your server chats in telegram also its configurable to only receive chats with specific words in telegram.
- Monitor player commands like essentials social spy.
- Report command abuse (/pl , /plugins , ....) also you can prevent this commands to be executed!
- Notify your staff members when they join the game and send them login details contain IP address. (if you are using bungeecord enable this option only in your lobby server)
- Players can report other players with /report <playername> <reason>
- Monitor your new players! (if you are using bungeecord enable this option only in your lobby server)
- You can disable notifications for each part that you want
- Fully customizable (Check the config.yml)

Commands:
```
/trreload : reload plugin
/report <player> <reason>
```

Permissions:
```
permissions:
  tr.*:
    default: op
    description: Give players with op everything by default
    children:
      tr.report: true
      tr.commandspy.bypass: true
      tr.reload: true
      tr.commandabuse.bypass: true
  tr.report:
    default: true
    description: use /report command
  tr.commandspy.bypass:
    default: op
    description: bypass command spy
  tr.reload:
    default: op
    description: /reload command
  tr.commandabuse.bypass:
    default: op
    description: bypass command abuse
```
Installation:
- Put TelegramReporter.jar in your plugin folder
- Restart or reload your server
- Go to TelegramReporter directory and open config.yml
- Put your bot token and set enable option to true
- Put your chat_id(s) in ChatIds section
- Restart or reload your server again
How to make a bot in telegram?
- Start @BotFather in telegram
- Type /newbot and choose a name and username for your bot
- Type /token and put it in config file (tokens are something like this : 327112310:AAFl8ZigTo4OpiLo-kKmnNFqLCztQHPm1zM)

Also you can use this bot to get any chat_id : @getidsbot
