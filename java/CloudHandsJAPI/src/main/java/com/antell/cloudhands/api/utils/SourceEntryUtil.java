package com.antell.cloudhands.api.utils;

import com.antell.cloudhands.api.packet.ARPPacket;
import com.antell.cloudhands.api.packet.ICMPPacket;
import com.antell.cloudhands.api.packet.TCPSession;
import com.antell.cloudhands.api.packet.UDPSession;
import com.antell.cloudhands.api.packet.tcp.http.HTTPSession;
import com.antell.cloudhands.api.packet.tcp.mail.MailSession;
import com.antell.cloudhands.api.packet.udp.dns.DNSSession;
import com.antell.cloudhands.api.source.SourceEntry;

/**
 * Created by dell on 2018/6/27.
 */
public class SourceEntryUtil {

    private SourceEntryUtil(){}

    public static boolean isHttpSession(SourceEntry sourceEntry){

        return sourceEntry instanceof HTTPSession;
    }

    public static HTTPSession toHttpSession(SourceEntry sourceEntry){

        return (HTTPSession)sourceEntry;
    }

    public static boolean isHttpSessionWithAttack(SourceEntry sourceEntry){

        HTTPSession httpSession = toHttpSession(sourceEntry);

        return httpSession.getSecMatchResult()!=null;
    }

    public static boolean isMailSession(SourceEntry sourceEntry){

        return sourceEntry instanceof MailSession;
    }

    public static MailSession toMailSession(SourceEntry sourceEntry){

        return (MailSession)sourceEntry;
    }

    public static boolean isMailSessionWithAttack(SourceEntry sourceEntry){

        MailSession mailSession = toMailSession(sourceEntry);
        return mailSession.getSecMatchResult()!=null;
    }

    public static boolean isTCPSession(SourceEntry sourceEntry){

        return sourceEntry instanceof TCPSession;
    }

    public static TCPSession toTCPSession(SourceEntry sourceEntry){

        return (TCPSession)sourceEntry;
    }

    public static boolean isUDPSession(SourceEntry sourceEntry){

        return sourceEntry instanceof UDPSession;
    }

    public static UDPSession toUDPSession(SourceEntry sourceEntry){

        return (UDPSession)sourceEntry;
    }

    public static boolean isDNSSession(SourceEntry sourceEntry){

        return sourceEntry instanceof DNSSession;
    }

    public static DNSSession toDNSSession(SourceEntry sourceEntry){

        return (DNSSession)sourceEntry;
    }

    public static boolean isArpPacket(SourceEntry sourceEntry){

        return sourceEntry instanceof ARPPacket;
    }

    public static ARPPacket toARPPacket(SourceEntry sourceEntry){

        return (ARPPacket) sourceEntry;
    }

    public static boolean isICMPPacket(SourceEntry sourceEntry){

        return sourceEntry instanceof ICMPPacket;
    }

    public static ICMPPacket toICMPPacket(SourceEntry sourceEntry){

        return (ICMPPacket) sourceEntry;
    }

    public static void setIPPairs(SourceEntry sourceEntry,IPPairs ipPairs){

        if(isArpPacket(sourceEntry)){
            ARPPacket arpPacket = toARPPacket(sourceEntry);

            ipPairs.setSrcIP(IPUtils.ipv4Str(arpPacket.getSip()));
            ipPairs.setDstIP(IPUtils.ipv4Str(arpPacket.getTip()));
        }else if(isICMPPacket(sourceEntry)){

            ICMPPacket icmpPacket = toICMPPacket(sourceEntry);
            ipPairs.setSrcIP(IPUtils.ipv4Str(icmpPacket.getSip()));
            ipPairs.setDstIP(IPUtils.ipv4Str(icmpPacket.getTip()));
        } else if(isTCPSession(sourceEntry)){

            TCPSession tcpSession = toTCPSession(sourceEntry);
            ipPairs.setSrcIP(IPUtils.ipv4Str(tcpSession.getReqIP()));
            ipPairs.setDstIP(IPUtils.ipv4Str(tcpSession.getResIP()));
        }else if(isUDPSession(sourceEntry)){
            UDPSession udpSession = toUDPSession(sourceEntry);

            ipPairs.setSrcIP(IPUtils.ipv4Str(udpSession.getReqIP()));
            ipPairs.setDstIP(IPUtils.ipv4Str(udpSession.getResIP()));
        }else if(isDNSSession(sourceEntry)){

            DNSSession dnsSession = toDNSSession(sourceEntry);
            ipPairs.setSrcIP(IPUtils.ipv4Str(dnsSession.getSessionEntry().getReqIP()));
            ipPairs.setDstIP(IPUtils.ipv4Str(dnsSession.getSessionEntry().getResIP()));
        }else if(isHttpSession(sourceEntry)){

            HTTPSession httpSession = toHttpSession(sourceEntry);
            ipPairs.setSrcIP(IPUtils.ipv4Str(httpSession.getSessionEntry().getReqIP()));
            ipPairs.setDstIP(IPUtils.ipv4Str(httpSession.getSessionEntry().getResIP()));
        }else if(isMailSession(sourceEntry)){
            MailSession mailSession = toMailSession(sourceEntry);
            ipPairs.setSrcIP(IPUtils.ipv4Str(mailSession.getSessionEntry().getReqIP()));
            ipPairs.setDstIP(IPUtils.ipv4Str(mailSession.getSessionEntry().getResIP()));
        }

    }


}