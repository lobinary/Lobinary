package com.l.test.web.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtils.class);

	public static byte[] serialize(Object obj) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(outputStream);
			oos.writeObject(obj);
			return outputStream.toByteArray();
		} catch (IOException e) {
			LOGGER.error("serialize object fail, io exception!", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					LOGGER.error("close ObjectOutputStream object error!", e);
				}
			}
		}

		return null;
	}

	public static Object deserialize(byte[] bytes) {
		ObjectInputStream ois = null;
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(inputStream);
			return ois.readObject();
		} catch (IOException e) {
			LOGGER.error("deserialize object fail, io exception!", e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("deserialize object fail, class not found!", e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					LOGGER.error("close ObjectInputStream object error!", e);
				}
			}
		}
		return null;
	}

}

