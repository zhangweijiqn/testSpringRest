package com.zwj.utils;


import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 序列化对象工具类
 * 
 * @author liyonghui
 */
public class SerializeUtils {
	private static final Logger LOG = Logger.getLogger(SerializeUtils.class);

	private SerializeUtils() {
	}
	
	public static byte[] serialize(Object o) {
        if (o == null) {
            return null;
        }
        
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(o);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
        	closeIO(os);
        	closeIO(bos);
        }
        return rv;
    }
	
	private static void closeIO(OutputStream os) {
		if ( os != null ) {
			try {
            	os.close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}
}

