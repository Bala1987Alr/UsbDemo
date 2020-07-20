package util;

import android.util.Log;


public class QRJoint {
    static StringBuffer alipayBuffer = new StringBuffer();
    /**
     * Get complete QRCode
     *
     * @param buffer
     * @param size
     * @return
     */
    public static String getAlipayQRCode(byte[] buffer, int size) {
        if (buffer == null || buffer.length <= 0) {
            return null;
        }
        // Splicing Alipay code data
        if (size <= 0) {
            return null;
        }
        byte[] realData = new byte[size];
        System.arraycopy(buffer, 0, realData, 0, size);
        String bufString = ByteUtil.byteArrayToHexString(realData);
        alipayBuffer.append(bufString);
        // Judge whether to finish
        if (alipayBuffer.length() <= 8) {
            return null;
        }
        if (!alipayBuffer.toString().substring(alipayBuffer.toString().length() - 4).equals("0D0A")) {
            return null;
        } else {
            String qrcode = alipayBuffer.substring(0, alipayBuffer.lastIndexOf("0D0A"));
            Log.e("QRJoint","[[,Before interception:" + alipayBuffer.toString());
            Log.e("QRJoint","[[,:After interception" + qrcode);
            alipayBuffer.delete(0, alipayBuffer.length());
            return qrcode;
        }
    }
}
