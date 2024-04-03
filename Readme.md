# Cron Expression Parser

Driver class is CronExpressionParser.java

To run follow below steps -
- run: mvn clean install
- CronParser-1.0-SNAPSHOT.jar will be created in target folder
- Now run below command in terminal:
- ```java -cp target\CronParser-1.0-SNAPSHOT.jar org.example.CronExpressionParser <your_cron_expression>```
- example command for this expression - "*/15 0 1,15  * 1-5 /usr/bin/find"
- ```java -cp target\CronParser-1.0-SNAPSHOT.jar org.example.CronExpressionParser "*/15 0 1,15  * 1-5 /usr/bin/find"```

Alternatively, you can also pass the arguments by editing the configuration from the coding software, intellij, for example.