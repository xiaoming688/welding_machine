package com.welding.web.netty.service;

/**
 * @author MM
 */
public interface IMessageService<String> {

    Object handle(String requestId, String message);

    String getCode();

}
