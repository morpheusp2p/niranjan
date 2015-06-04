/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pcap_new;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jnetpcap.Pcap;  
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JFlow;
import org.jnetpcap.packet.JFlowKey;
import org.jnetpcap.packet.JFlowMap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.JScanner;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

/**
 *
 * @author Admin
 */
public class Pcap_new {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String filename = "C:\\Users\\Admin\\Documents\\pcap\\tcp-ecn-sample.pcap";
        final StringBuilder errbuf = new StringBuilder();
        
        final Pcap pcap = Pcap.openOffline(filename, errbuf);
        if (pcap == null) {  
            System.err.println(errbuf); // Error is stored in errbuf if any  
            return;  
        }  
        
        pcap.loop(20, new JPacketHandler<StringBuilder>() {  
            final Tcp tcp = new Tcp();
            final Http http = new Http(); 
            
            public void nextPacket(JPacket packet, StringBuilder errbuf) {
                if(packet.hasHeader(tcp.ID)){
                    packet.getHeader(tcp);
                    
                    System.out.printf("tcp.dst_port=%d%n", tcp.destination());  
                    System.out.printf("tcp.src_port=%d%n", tcp.source());  
                    System.out.printf("tcp.ack=%x%n", tcp.ack()); 
                }
                
                if (packet.hasHeader(tcp)) {  
                    System.out.printf("tcp header::%s%n", tcp.toString());  
                }  
  
                if (packet.hasHeader(tcp) && packet.hasHeader(http)) {  
                    System.out.printf("http header::%s%n", http);  
                }
                
                System.out.printf("frame #%d%n", packet.getFrameNumber());  
            }
        },errbuf);
        
        JScanner.getThreadLocal().setFrameNumber(0);  
        
        final PcapPacket packet = new PcapPacket(JMemory.POINTER);  
        final Tcp tcp = new Tcp();
        
        for (int i = 0; i < 5; i++) {  
            pcap.nextEx(packet);  
  
            if (packet.hasHeader(tcp)) {  
                System.out.printf("#%d seq=%08X%n", packet.getFrameNumber(), tcp.seq());  
            }  
        }
        
        final Map<JFlowKey, JFlow> flows = new HashMap<JFlowKey, JFlow>();  
  
        for (int i = 0; i < 50; i++) {  
            pcap.nextEx(packet);  
            final JFlowKey key = packet.getState().getFlowKey();  
  
            JFlow flow = flows.get(key);  
            if (flow == null) {  
                flows.put(key, flow = new JFlow(key));  
            }
            
            flow.add(new PcapPacket(packet));  
        }  
        
        for (JFlow flow : flows.values()) {  
  
            if (flow.isReversable()) {  
                List<JPacket> forward = flow.getForward();  
                for (JPacket p : forward) {  
                    System.out.printf("%d, ", p.getFrameNumber());  
                }  
                System.out.println();  
  
                List<JPacket> reverse = flow.getReverse();  
                for (JPacket p : reverse) {  
                    System.out.printf("%d, ", p.getFrameNumber());  
                }  
            } else {  
                    for (JPacket p : flow.getAll()) {  
                    System.out.printf("%d, ", p.getFrameNumber());  
                }  
            }  
            System.out.println();  
        }  
        
        JFlowMap superFlowMap = new JFlowMap();  
        
        pcap.loop(Pcap.LOOP_INFINITE, superFlowMap, null);  
  
        System.out.printf("superFlowMap::%s%n", superFlowMap);  
  
        pcap.close();  
    }
}