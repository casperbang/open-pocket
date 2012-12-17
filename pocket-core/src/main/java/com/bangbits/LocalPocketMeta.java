package com.bangbits;

import com.bangbits.pocket.PocketException;
import com.bangbits.pocket.model.PocketMeta;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * hash.txt format:
 * passwordhash (i.e. EB5EBF1F7D41A73CD1E89E0BD719FFF405AF2EAC975B51CC2DDC012C5D8FF0A1A591EC806EEBF85921C4517CFF726E2E7EC83FAE5F5AE798C3F9D40D0805C59F)
 * version (i.e.2)
 * advencryption (i.e. True)
 * passwordsalt (i.e. 276164637037466F7569964B39ABEC7783FB5DC8)
 * encryptionsalt (i.e. 552AA6DDFB1A3C2EE86733A93DDE04C4C5F460D6)
 * 
 * 
 */
class LocalPocketMeta implements PocketMeta{

    private final String encryptionSalt;
    private final String passwordHash;
    private final int version;
    private final Date timestamp;
    private final boolean advancedEncryption;
    private final String hashSalt;

        
    public LocalPocketMeta(String localePocketMetaFilePath) {
        
        BufferedReader br = null;
        try {
            final File file = new File(localePocketMetaFilePath);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            passwordHash = br.readLine();
            version = Integer.parseInt(br.readLine());
            timestamp = new Date(file.lastModified());
            advancedEncryption = Boolean.parseBoolean(br.readLine());
            hashSalt = br.readLine();
            encryptionSalt = br.readLine();

        } catch (Exception e) {
            throw new PocketException("Error reading Pocket meta-data file: " + e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                // Ignore
            }
        }
    }
    
    @Override
    public boolean isAdvancedEncryption() {
        return advancedEncryption;
    }

    @Override
    public String getEncryptionSalt() {
        return encryptionSalt;
    }

    @Override
    public String getHashSalt() {
        return hashSalt;
    }

    @Override
    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public int getVersion() {
        return version;
    }    
    
}
