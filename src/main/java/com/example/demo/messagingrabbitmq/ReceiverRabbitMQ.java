package com.example.demo.messagingrabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class ReceiverRabbitMQ {
    private CountDownLatch latch = new CountDownLatch(1);
    /* For convenience, this POJO also has a CountDownLatch.
       This lets it signal that the message has been received.
       This is something you are not likely to implement in a production application. */

    public void receiveMessage(String msg) {
        System.out.println("Received <" + msg + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
/* The Receiver is a POJO that defines a method for receiving messages.
   When you register it to receive messages, you can name it anything you want. */



// Resolved the issue by starting the web server:
// After, brew install rabbitmq
// 1 - In terminal, cd /
// 2 - In terminal, cd /usr/local/Cellar/rabbitmq/3.8.16/sbin
// 3 - In terminal, open rabbitmq-server (open up another terminal & start up server)
/* Last login: Thu Jun 10 16:26:09 on ttys011

The default interactive shell is now zsh.
To update your account to use zsh, please run `chsh -s /bin/zsh`.
For more details, please visit https://support.apple.com/kb/HT208050.
abenitezMBP:~ abenitez$ /usr/local/Cellar/rabbitmq/3.8.16/sbin/rabbitmq-server ; exit;
Configuring logger redirection

  ##  ##      RabbitMQ 3.8.16
  ##  ##
  ##########  Copyright (c) 2007-2021 VMware, Inc. or its affiliates.
  ######  ##
  ##########  Licensed under the MPL 2.0. Website: https://rabbitmq.com

  Doc guides: https://rabbitmq.com/documentation.html
  Support:    https://rabbitmq.com/contact.html
  Tutorials:  https://rabbitmq.com/getstarted.html
  Monitoring: https://rabbitmq.com/monitoring.html

  Logs: /usr/local/var/log/rabbitmq/rabbit@localhost.log
        /usr/local/var/log/rabbitmq/rabbit@localhost_upgrade.log

  Config file(s): (none)

  Starting broker... completed with 6 plugins. */