package com.antell.cloudhands.api.packet.udp.dns;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

/**
 * Responsible Person Record - lists the mail address of a responsible person
 * and a domain where TXT records are available.
 *
 */

public class RPRecord extends Record {

    private static final long serialVersionUID = 8124584364211337460L;

    private Name mailbox;
    private Name textDomain;

    public RPRecord() {
    }

    @Override
    public Record getObject() {
        return new RPRecord();
    }

    /**
     * Creates an RP Record from the given data
     *
     * @param mailbox    The responsible person
     * @param textDomain The address where TXT records can be found
     */
    public RPRecord(Name name, int dclass, long ttl, Name mailbox, Name textDomain) {
        super(name, Type.RP, dclass, ttl);

        this.mailbox = checkName("mailbox", mailbox);
        this.textDomain = checkName("textDomain", textDomain);
    }

    @Override
    public void rrFromWire(DNSInput in) throws IOException {
        mailbox = new Name(in);
        textDomain = new Name(in);
    }

    @Override
    public void rdataFromString(Tokenizer st, Name origin) throws IOException {
        mailbox = st.getName(origin);
        textDomain = st.getName(origin);
    }

    /**
     * Converts the RP Record to a String
     */
    @Override
    public String rrToString() {
        StringBuffer sb = new StringBuffer();
        sb.append(mailbox);
        sb.append(" ");
        sb.append(textDomain);
        return sb.toString();
    }

    @Override
    public XContentBuilder rdataToJson(XContentBuilder cb) throws IOException {

        cb.field("mailbox",mailbox.toString());
        cb.field("textDomain",textDomain.toString());
        return cb;
    }

    /**
     * Gets the mailbox address of the RP Record
     */
    public Name getMailbox() {
        return mailbox;
    }

    /**
     * Gets the text domain info of the RP Record
     */
    public Name getTextDomain() {
        return textDomain;
    }

    @Override
    public void rrToWire(DNSOutput out, Compression c, boolean canonical) {
        mailbox.toWire(out, null, canonical);
        textDomain.toWire(out, null, canonical);
    }

}
