package com.bisleri.bottleforchange.async;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface EachExceptionsHandler {
    void handleIOException(IOException var1);

    void handleMalformedURLException(MalformedURLException var1);

    void handleProtocolException(ProtocolException var1);

    void handleUnsupportedEncodingException(UnsupportedEncodingException var1);
}
