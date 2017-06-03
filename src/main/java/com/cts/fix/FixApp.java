package com.cts.fix;

import quickfix.*;
import quickfix.Application;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.NewOrderSingle;


/**
 * Created by fy on 2017/5/30.
 */
public class FixApp extends MessageCracker implements Application{
    public void onCreate(SessionID sessionId) {
        System.out.println(sessionId +" on create");
    }

    public void onLogon(SessionID sessionId) {
        System.out.println(sessionId + " on logon");
    }

    public void onLogout(SessionID sessionId) {
        System.out.println(sessionId.getTargetCompID()+" logout");
    }

    public void toAdmin(Message message, SessionID sessionId) {

    }

    public void toApp(Message message, SessionID sessionId) throws DoNotSend {

    }

    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {

    }

    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("Receiver fromApp..  " + message);
        crack(message, sessionId); // calls onMessage(..,..)
    }

    @Override
    public void onMessage(NewOrderSingle order, SessionID sessionID) {
        System.out.println("Receiver onMessage..  " + order);
    }
}
