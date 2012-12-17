package com.bangbits.pocket.model;

import java.util.Date;

public interface PocketMeta {
    String getEncryptionSalt();
    boolean isAdvancedEncryption();
    String getHashSalt();
    String getPasswordHash();
    Date getTimestamp();
    int getVersion();
}
