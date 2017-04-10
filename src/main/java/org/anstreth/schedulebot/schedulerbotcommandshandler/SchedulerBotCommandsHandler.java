package org.anstreth.schedulebot.schedulerbotcommandshandler;

import lombok.extern.log4j.Log4j;
import org.anstreth.schedulebot.schedulerbotcommandshandler.handlers.*;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulebotservice.MessageSender;
import org.anstreth.schedulebot.schedulerbotcommandshandler.response.ScheduleResponse;
import org.anstreth.schedulebot.schedulerformatter.SchedulerFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j
public class SchedulerBotCommandsHandler {
    private final SchedulerFormatter schedulerFormatter;
    private final ScheduleRequestHandlersSupplier scheduleRequestHandlersSupplier;

    @Autowired
    SchedulerBotCommandsHandler(ScheduleRequestHandlersSupplier scheduleRequestHandlersSupplier, SchedulerFormatter schedulerFormatter) {
        this.scheduleRequestHandlersSupplier = scheduleRequestHandlersSupplier;
        this.schedulerFormatter = schedulerFormatter;
    }

    public void handleRequest(ScheduleRequest userRequest, MessageSender messageSender) {
        log.info("Handling request: " + userRequest.getMessage());
        SchedulerRequestHandler requestHandler = scheduleRequestHandlersSupplier.getHandlerForCommand(userRequest.getMessage());
        ScheduleResponse response = requestHandler.handle(userRequest);
        response.formatAndSend(schedulerFormatter, messageSender);
    }

}
