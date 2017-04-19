package com.zeroturnaround.licensing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class UserLicense
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private byte[] signature;
  private byte[] license;
  private Map<String, Object> dataMap;
  private transient File loadedFromFile;

  public byte[] getSignature()
  {
    return this.signature;
  }

  public void setSignature(byte[] paramArrayOfByte) {
    this.signature = paramArrayOfByte;
  }

  public byte[] getLicense() {
    return this.license;
  }

  public void setLicense(byte[] paramArrayOfByte) {
    this.license = paramArrayOfByte;
  }

  public static UserLicense loadInstance(File paramFile) throws IOException, ClassNotFoundException
  {
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    try {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
      UserLicense localUserLicense1 = (UserLicense)localObjectInputStream.readObject();
      localUserLicense1.loadedFromFile = paramFile;
      return localUserLicense1;
    }
    finally {
      localFileInputStream.close();
    }
  }

  public static UserLicense loadInstance(URL paramURL) throws IOException, ClassNotFoundException
  {
    InputStream localInputStream = paramURL.openStream();
    try {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(localInputStream);
      return (UserLicense)localObjectInputStream.readObject();
    }
    finally {
      localInputStream.close();
    }
  }

  public static UserLicense loadInstance(byte[] paramArrayOfByte) throws IOException, ClassNotFoundException
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
    try {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(localByteArrayInputStream);
      return (UserLicense)localObjectInputStream.readObject();
    }
    finally {
      localByteArrayInputStream.close();
    }
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("UserLicense {");

    for (Map.Entry localEntry : this.dataMap.entrySet()) {
      localStringBuilder.append((String)localEntry.getKey());
      localStringBuilder.append(": ");
      localStringBuilder.append(localEntry.getValue());
      localStringBuilder.append(", ");
    }

    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }

  public File getLoadedFromFile() {
    return this.loadedFromFile;
  }
}