package util;

import android.util.Log;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * byte Operation tools
 */
public class ByteUtil {

    /**
     * Replace the length data of desByte at the index in srcBytes．
     *
     * @param srcBytes
     * @param desByte
     * @param position
     * @return byte[]
     */
    public static void replaceByte(byte[] srcBytes, byte desByte, int position) {
        if (!notNull(srcBytes)) {
            // no array to replace
            Log.e("ByteUtil", "no array to replace");
            return;
        }
        if (position < 0) {
            Log.e("ByteUtil", "the position is out of the array");
            return;
        }
        if (position >= srcBytes.length) {
            Log.e("ByteUtil", "the position is out of the array");
            return;
        }
        // replace array
        srcBytes[position] = desByte;
        return;
    }

    /**
     * Replace the length data with the length of desBytes at the index in srcBytes．
     *
     * @param srcBytes
     * @param desBytes
     * @param position The position of the initial replacement
     * @return byte[]
     */
    public static void replaceBytes(byte[] srcBytes, byte[] desBytes, int position) {
        if (!notNull(srcBytes)) {
            // no array to replace
            Log.e("ByteUtil", "no array to replace");
            return;
        }
        if (!notNull(desBytes)) {
            // no array to replace
            Log.e("ByteUtil", "desBytes is null");
            return;
        }
        if (position < 0) {
            Log.e("ByteUtil", "the position is out of the array");
            return;
        }
        if ((position + desBytes.length) > srcBytes.length) {
            Log.e("ByteUtil", "the position is out of the array");
            return;
        }
        // replace array
        for (int i = 0; i < desBytes.length; i++) {
            srcBytes[position + i] = desBytes[i];
        }
        return;
    }

    /**
     * Two byte arrays are superimposed. Add desBytes to srcBytes
     *
     * @param srcBytes
     * @param desBytes
     * @return byte[]
     */
    public static byte[] addBytes(byte srcBytes, byte desBytes) {

        // copy array
        byte[] returnArray = new byte[2];
        returnArray[0] = srcBytes;
        returnArray[1] = desBytes;

        return returnArray;
    }

    /**
     * Two byte arrays are superimposed. Add desBytes to srcBytes
     *
     * @param srcBytes
     * @param desBytes
     * @return byte[]
     */
    public static byte[] addBytes(byte[] srcBytes, byte[] desBytes) {
        if (!notNull(srcBytes) && !notNull(desBytes)) {
            // no array to add
            return null;
        }
        if (notNull(srcBytes) && !notNull(desBytes)) {
            return srcBytes;
        }

        if (!notNull(srcBytes) && notNull(desBytes)) {
            return desBytes;
        }
        // copy array
        byte[] returnArray = new byte[srcBytes.length + desBytes.length];
        System.arraycopy(srcBytes, 0, returnArray, 0, srcBytes.length);
        System.arraycopy(desBytes, 0, returnArray, srcBytes.length, desBytes.length);
        return returnArray;
    }

    /**
     * Add a desByte to srcBytes
     *
     * @param srcBytes
     * @param desByte
     * @return byte[]
     */
    public static byte[] addBytes(byte[] srcBytes, byte desByte) {
        byte[] desByteArray = new byte[]{desByte};
        return addBytes(srcBytes, desByteArray);
    }

    /**
     *
     * Add a desByte to srcBytes
     * @param srcBytes
     * @param desBytes
     * @return byte[]
     */
    public static byte[] addBytes(byte srcBytes, byte[] desBytes) {
        byte[] srcBytesArray = new byte[]{srcBytes};
        return addBytes(srcBytesArray, desBytes);
    }

    /**
     * copy byte array , from index to end
     *
     * @param srcBytes The byte array to be copied
     * @param startIndex  length
     * @return byte[] Return the copied array
     */
    public static byte[] copyBytesToEnd(byte[] srcBytes, int startIndex) {
        if (null !=srcBytes) {
            int srcLength = srcBytes.length;
            if (srcLength > startIndex) {
                return copyBytes(srcBytes, startIndex, srcLength - startIndex);
            }
        }
        return null;
    }
    /**
     * copy byte array , from 0
     *
     * @param srcBytes 被copy 的字节数组
     * @param length   　长度
     * @return byte[] 返回拷贝后的数组
     */
    public static byte[] copyBytesToIndexFromStart(byte[] srcBytes, int length) {
        if (null !=srcBytes) {
            int srcLength = srcBytes.length;
            if (srcLength > length) {
                return copyBytes(srcBytes, 0, length);
            }
        }
        return null;
    }

    /**
     * Insert a byte into the byte array
     *
     * @param srcBytes
     * @param desByte
     * @param index    It is recommended that index is less than or equal to the length of srcBytes, if it is greater than, then add it directly after
     * @return byte[]
     */
    public static byte[] insertByte(byte[] srcBytes, byte desByte, int index) {
        if (!notNull(srcBytes)) {
            //At this time, the source array is null, then directly create an array and return
            return new byte[]{desByte};
        }
        byte[] desByteArray = new byte[]{desByte};
        int srcLength = srcBytes.length;
        if (srcLength <= index) {
            //Insert directly behind
            return addBytes(srcBytes, desByteArray);
        } else {
            // copy array
            byte[] returnArray = new byte[srcBytes.length + 1];
            System.arraycopy(srcBytes, 0, returnArray, 0, index);
            System.arraycopy(desByteArray, 0, returnArray, index, desByteArray.length);
            System.arraycopy(srcBytes, index, returnArray, index + 1, srcLength - index);
            return returnArray;
        }
    }

    /**
     * Insert a desBytes array into the srcBytes array
     *
     * @param srcBytes
     * @param desBytes
     * @param index    It is recommended that index is less than or equal to the length of srcBytes, if it is greater than, then add it directly after
     * @return byte[]
     */
    public static byte[] insertBytes(byte[] srcBytes, byte[] desBytes, int index) {
        if (!notNull(srcBytes) && !notNull(desBytes)) {
            // no array to add
            return null;
        }
        if (notNull(srcBytes) && !notNull(desBytes)) {
            return srcBytes;
        }

        if (!notNull(srcBytes) && notNull(desBytes)) {
            return desBytes;
        }
        int srcLength = srcBytes.length;
        if (srcLength <= index) {
            //Insert directly behind
            return addBytes(srcBytes, desBytes);
        } else {
            // copy array
            byte[] returnArray = new byte[srcBytes.length + desBytes.length];
            System.arraycopy(srcBytes, 0, returnArray, 0, index);
            System.arraycopy(desBytes, 0, returnArray, index, desBytes.length);
            System.arraycopy(srcBytes, index, returnArray, index + desBytes.length, srcLength -
                    index);
            return returnArray;
        }
    }

    /**
     * byte array, self-exclusive or. . every item xor 0xff. For example 0x01 its XOR is 0xFE
     *
     * @param bytes 　source
     * @return byte[] Byte array after XOR
     * @throws Exception abnormal the byte array is null
     */
    public static byte[] xorItself(byte[] bytes) throws Exception {
        if (!notNull(bytes)) {
            throw new Exception("the byte array is null");
        }
        byte[] returnBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            returnBytes[i] = (byte) (bytes[i] ^ 0xff);
        }
        return returnBytes;
    }

    /**
     * Determine whether the array is not null or the length is not 0
     *
     * @param bytes
     * @return return true,if byte array is not null;
     */
//    public static boolean notNull(byte[] bytes) {
//        if (bytes == null || bytes.length <= 0) {
//            return false;
//        }
//        return true;
//    }

    /**
     * This method only counts the byte null from the back until there is no null. <br/> Does not support the removal of the intermediate null character <br/> if you want to remove all null
     * common, you can use {@link #removeAllNullBytes(byte[])} for that
     *
     * @param bytes
     * @return byte[]
     */
    public static byte[] removeNULLByte(byte[] bytes) {
        if (notNull(bytes)) {
            // If the first one is null byte, the following data will not be done. Since there is data about 00 sent in the later period, it is shielded here
//            if (bytes[0] == 0) {
//                return null;
//            }

            int copyEndIndex = 0;
            //This for loop only finds the index of the last byte of the current byte that is not null.
            for (int i = bytes.length - 1; i >= 0; i--) {
                if (bytes[i] == 0x00) {
                    continue;
                } else {
                    copyEndIndex = i;
                    break;
                }
            }
            if (copyEndIndex >= 0) {
                // copy and return,
                //Since you need to include the last one, you need + 1
                byte[] returnBytes = new byte[copyEndIndex + 1];
                System.arraycopy(bytes, 0, returnBytes, 0, copyEndIndex + 1);
                return returnBytes;
            }

        }
        return null;
    }

    public static boolean byteArrayIsNull(byte[] bytes) {
        if (!notNull(bytes)) {
            return true;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * remove bytes from src bytes,
     * @param bytes
     * @param removeCounts
     * @return
     */
    public static byte[] removeBytesFromTail(byte[] bytes, int removeCounts) {
        if (notNull(bytes)) {
            int copyLength = bytes.length - removeCounts;
            if (copyLength <= 0) {
                return null;
            }
            return copyBytes(bytes, 0, copyLength);
        }
        return null;
    }

    /**
     * This method only counts the byte 0xff from the back until there is no 0xff. <br/> Does not support the middle 0xff character removal operation <br/>
     *
     * @param bytes
     * @return byte[]
     */
    public static byte[] removeFFByte(byte[] bytes) {
        if (notNull(bytes)) {
            int copyEndIndex = 0;
            byte lastFF = (byte) 0xff;
            //This for loop only finds the index of the last byte of the current byte that is not 0xff
            for (int i = bytes.length - 1; i < bytes.length; i--) {
                if (bytes[i] == lastFF) {
                    continue;
                } else {
                    copyEndIndex = i;
                    break;
                }
            }
            if (copyEndIndex > 0) {
                // copy and return,
                //Since the last one needs to be included, it needs + 1
                byte[] returnBytes = new byte[copyEndIndex + 1];
                System.arraycopy(bytes, 0, returnBytes, 0, copyEndIndex + 1);
                return returnBytes;
            }

        }
        return null;
    }

    /**
     * Remove the null character at the end of the byte array and look for it in equal parts. <br/> Does not support the removal of the intermediate null character<br/> if you want to remove all null
     * common, you can use {@link #removeAllNullBytes(byte[])} for that
     *
     * @param bytes
     * @param equalPartsNumber Quantity per aliquot
     * @return byte[]
     */
    public static byte[] removeNULLByte(byte[] bytes, int equalPartsNumber) {
        int copyCopyEndIndex = getLastNotNullIndex(bytes, 0, equalPartsNumber, bytes.length);
        Log.e("ByteUtil", "the copy size is " + copyCopyEndIndex);
        if (copyCopyEndIndex > 0) {
            //The value of the copy index must be included, so +1
            byte[] returnBytes = new byte[copyCopyEndIndex + 1];
            System.arraycopy(bytes, 0, returnBytes, 0, copyCopyEndIndex + 1);
            return returnBytes;
        }
        return null;
    }

    private static int getLastNotNullIndex(byte[] bytes, int startIndex, int equalPartsNumber,
                                           int loopNumber) {
        if (notNull(bytes)) {
            if (startIndex >= bytes.length) {
                //The starting traversal point is greater than the length of the array, then return directly null
                return 0;
            }

            // Number of aliquots needed to recycle。
            // for example， the loopNumber is 10, the equalPartsNumber is 5, so the forPartNumber
            // is 2, if the equalPartsNumber is 4, the forPartNumber is 2
            int forPartNumber = loopNumber / equalPartsNumber;
            int remain = loopNumber % equalPartsNumber;

            if (forPartNumber <= 0) {
                //It means that it is impossible to continue the segmentation, then just loop the remaining data directly
                //Find index values that are not null
                int copyEndIndex = startIndex;
                for (int i = 0; i < loopNumber; i++) {
                    if (bytes[i + startIndex] != 0) {
                        copyEndIndex = i + startIndex;
                        continue;
                    } else {
                        break;
                    }
                }
                return copyEndIndex;
            }

            //Due to the calculation method of forPartNumber, when taking byte value here, it will not overflow
            for (int i = 0; i < forPartNumber; i++) {

                if (bytes[startIndex + equalPartsNumber - 1] == 0) {
                    return getLastNotNullIndex(bytes, startIndex, equalPartsNumber,
                            equalPartsNumber - 1);
                } else {
                    startIndex = ((i + 1) * equalPartsNumber) - 1;
                }
                if (i == forPartNumber - 1) {
                    if (remain > 0) {
                        return getLastNotNullIndex(bytes, startIndex, equalPartsNumber,
                                remain);
                    }
                }

            }

        }
        return 0;
    }

    /**
     * Remove all null characters in the byte array. <br/> Supports the removal of the middle null character
     *
     * @param bytes
     * @return String
     */
    public static byte[] removeAllNullBytes(byte[] bytes) {
        if (notNull(bytes)) {
            //The non-null index value of the current new array
            int newArrayIndex = 0;
            byte[] copyBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != 0) {
                    copyBytes[newArrayIndex] = bytes[i];
                    newArrayIndex++;
                }
            }
            return removeNULLByte(copyBytes);
        }
        return null;
    }

    /**
     * ASCII converted to hex string for example , "0" ascii is 30 , so convert to string is "30"
     *
     * @param str
     * @return String
     */
    public static String asciiToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    /**
     * @param bytes
     * @return String
     */
    public static String byteArrayToHexArray(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        if (notNull(bytes)) {
            for (int i = 0; i < bytes.length; i++) {
                sb.append(byteToHexString(bytes[i]));
            }
//            Log.e("ByteUtil","the hex array is " + sb.toString());
        } else {
            Log.e("ByteUtil", "the common array is null");
        }
        return sb.toString();
    }

    /**
     * 16The hexadecimal string is converted into ascii code, ascii is our commonly used string。
     *
     * @param hex
     * @return String
     */
    public static String hexStringToASCII(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char) decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * hex The string is converted to an int value.<br/> The maximum value of the string"7fffffff"
     *
     * @param hex
     * The maximum length is 8, which corresponds to four bytes of int, but cannot exceed the maximum value of 0x7fffffff
     * @return if return -1 , it means the hex is not correct
     */
    public static int hexToInt(String hex) {
        int decimal = -1;
        if (hex != null && !"".equals(hex)) {
            decimal = Integer.parseInt(hex, 16) & 0xff;
        }
        return decimal;
    }


    /**
     * copy byte Array
     *
     * @param srcBytes
     * @param start    start index
     * @param length   copy length
     * @return byte[]
     */
    public static byte[] copyBytes(byte[] srcBytes, int start, int length) {
        if (notNull(srcBytes)) {
            if (start >= 0 && length > 0) {
                if (length <= srcBytes.length - start) {
                    //copy array
                    byte[] returnBytes = new byte[length];
                    System.arraycopy(srcBytes, start, returnBytes, 0, length);
                    return returnBytes;
                }
            }
        }
        return null;
    }

    /**
     * copy (srcBytes) Array to a specified array (desBytes).
     * Note here the length of srcBytes + start must be less than or equal to the length of desBytes
     *
     * @param srcBytes Array being copied
     * @param desBytes Purpose to store the array
     * @param start    The start position of the second parameter (desBytes),
     * @return
     */
    public static void copyBytes(byte[] srcBytes, byte[] desBytes, int start) {
        if (!notNull(srcBytes)) {
            Log.e("ByteUtil", "Uncopied array");
            return;
        }
        if (!notNull(desBytes)) {
            Log.e("ByteUtil", "Target is null");
            return;
        }
        if ((srcBytes.length + start) > desBytes.length) {
            Log.e("ByteUtil", "The copied array and the starting position are too long");
            return;
        }
        System.arraycopy(srcBytes, 0, desBytes, start, srcBytes.length);
    }

    /**
     * copy (srcBytes) Array to a specified array (desBytes).
     * Note here the length of srcBytes + start must be less than or equal to the length of desBytes
     *
     * @param srcBytes Array being copied
     * @param desBytes Purpose to store the array
     * @param start    The start position of the second parameter (desBytes),
     * @return
     */
    public static void copyBytes(byte[] srcBytes, int srcIndex, byte[] desBytes, int start, int length) {
        if (!notNull(srcBytes)) {
            Log.e("ByteUtil", "Uncopied array");
            return;
        }
        if (!notNull(desBytes)) {
            Log.e("ByteUtil", "Target is null");
            return;
        }
        if ((length + start) > desBytes.length) {
            Log.e("ByteUtil", "The copied array and the starting position are too long");
            return;
        }
        System.arraycopy(srcBytes, srcIndex, desBytes, start, length);
    }

    /**
     * copy all byte Array
     *
     * @param srcBytes
     * @return byte[]
     */
    public static byte[] copyAllBytes(byte[] srcBytes) {
        return copyBytes(srcBytes, 0, srcBytes.length);
    }

    /**
     * byte self-exclusive. For example, the exclusive OR of 0x01 is 0xFE
     *
     * @param bte
     * @return byte
     * @throws Exception
     */
    public static byte xorItself(byte bte) throws Exception {
        return (byte) (bte ^ 0xff);
    }

    /**
     * Xor and byte array XOR. XOR operation from the first to the last.
     *
     * @param bytes
     * @return int
     * @throws Exception
     */
    public static int xor(byte[] bytes, int xor) throws Exception {
        if (!notNull(bytes)) {
            throw new Exception("the byte array is null");
        }

        int xorInt = xor;
        for (int i = 0; i < bytes.length; i++) {
            xorInt = xorInt ^ bytes[i];
        }
        return xorInt;
    }

    /**
     * Xor and byte array XOR. XOR from the first to the last.
     *
     * @param bytes
     * @return int
     * @throws Exception
     */
    public static int xor(byte[] bytes, byte xor) throws Exception {
        if (!notNull(bytes)) {
            throw new Exception("the byte array is null");
        }

        int xorInt = xor;
        for (int i = 0; i < bytes.length; i++) {
            xorInt = xorInt ^ bytes[i];
        }
        return xorInt;
    }

    /**
     * Unsigned integer calculation, position on the left, high-order on the right
     *
     * @param bytes
     * @return int
     */
    public static int symbolBytesToInt(byte[] bytes) {
        int returnV = 0;
        if (notNull(bytes)) {
            for (int i = 0; i < bytes.length; i++) {
                int add = bytes[i] << ((i + 1) * 2);
                returnV = returnV + add;
            }
        }
        return returnV;
    }

    /**
     * Determine whether the checksum is legal
     *
     * @param hexData hex Type string
     * @param hexCode hex Type string
     * @return boolean
     */
    public static boolean verifyCode(String hexData, String hexCode) {
        if (hexData == null || hexData.length() <= 0) {
            return false;
        }
        int fori = hexData.length() % 2 == 0 ? hexData.length() / 2 : (hexData.length() / 2) + 1;
        int xor = 0;
        for (int i = 0; i < fori; i++) {
            xor = xor ^ hexToInt(hexData.substring(2 * i, 2 * i + 2));
//            xor = xor ^ Integer.parseInt(Cards.substring(2*i,2*i+2));
        }
        return xor == hexToInt(hexCode);
    }

    public static int createCode(byte[] bytes, int cmd) {

        if (!notNull(bytes)) {
            throw new NullPointerException(" the bytes is null");
        }
        return createCode(new String(bytes), cmd);
    }

    /**
     * xor Operation to obtain checksum
     *
     * @param hexData
     * @param cmd
     * @return int
     */
    public static int createCode(String hexData, int cmd) {
        if (hexData == null || hexData.length() <= 0) {
            throw new NullPointerException("Cards is null");
        }
        int fori = hexData.length() % 2 == 0 ? hexData.length() / 2 : (hexData.length() / 2) + 1;
        int xor = cmd;
        for (int i = 0; i < fori; i++) {
            xor = xor ^ hexToInt(hexData.substring(2 * i, 2 * i + 2));
        }
        return xor;
    }

    /**
     * Convert an int less than 255 into byte
     * This method is outdated and is not recommended, please use{@link #intToByteTwo(int)}
     *
     * @param value
     * @return byte
     * @throws Exception
     */
    @Deprecated
    public static byte intToByte(int value) throws Exception {
        if (value > 255) {
            throw new Exception("can not convert int value to byte when int value is greater than" +
                    " 255");
        }
        return (byte) value;
    }

    /**
     * Convert an int to byte
     *
     * @param value
     * @return byte
     */
    public static byte intToByteTwo(int value) {
        int byteint = value & 0xff;
        return new Integer(byteint).byteValue();
    }

    /**
     * String of type int is converted to char
     *
     * @param intStr
     * @return char
     */
    public static char intStringToChar(String intStr) {
        if (!ByteUtil.notNull(intStr)) {
            intStr = "0";
        }
        int ivalue = Integer.parseInt(intStr);
        byte reByte = new Integer(ivalue).byteValue();
        return (char) reByte;
    }

    /**
     * A hexadecimal string of length 2 is converted to char
     *
     * @param hexStr
     * @return char
     */
    public static char hexStringToChar(String hexStr) {
        if (!ByteUtil.notNull(hexStr)) {
            hexStr = "00";
        }
        byte reByte = hexStringToByte(hexStr);
        return (char) reByte;
    }

    /**
     * Convert byte array to hexadecimal string
     *
     * @param b byte[] Byte array to be converted
     * @return String Hexadecimal string
     */
    public static String byteArrayToHex(byte[] b) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * Convert byte array to hexadecimal string
     *
     * @param b Bytes to be converted
     * @return String Hexadecimal string
     */
    public static final String byteToHex(byte b) {
        StringBuilder sb = new StringBuilder();
        String stmp = Integer.toHexString(b & 0xff);
        if (stmp.length() == 1) {
            sb.append("0").append(stmp);
        } else {
            sb.append(stmp);
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Convert a byte into a hexadecimal string
     *
     * @param bte
     * @return String
     */
    public static String byteToHexString(byte bte) {
//     * This method relies on{@link DatatypeConverter#printHexBinary(byte[])}
        String hexString = byteToHex(bte);
//        String hexString = DatatypeConverter.printHexBinary(new byte[]{bte});
        return hexString.toUpperCase();
    }

    /**
     * Convert an int into a hexadecimal string (only one byte is supported)
     *
     * @param intValue
     * @return String
     */
    public static String intToSingleHexString(int intValue) {
        String hexString = null;
        try {
            hexString = byteToHexString(intToByte(intValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString;
    }

    /**
     * Convert an int into a hexadecimal string (only one byte is supported)
     *
     * @param intValue
     * @return String
     */
    public static String intToHexString(int intValue) {
        String hexString = null;
        try {
            hexString = byteArrayToHexString(intToByteArrayHighToLow(intValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString;
    }

    /**
     * Judge list is not null
     *
     * @param list
     * @return boolean
     */
//    public static boolean notNull(List<?> list) {
//        if (list != null && list.size() > 0) {
//            return true;
//        }
//        return false;
//    }

    /**
     * Generate checksum, hex string
     *
     * @param hexData hex Type string
     * @param hexCmd  hex Type string
     * @return Returns a hexadecimal (HEX) type string
     */
    public static String createCode(String hexData, String hexCmd) {
        int xor = createCode(hexData, hexToInt(hexCmd));
        try {
            return byteToHexString(intToByteTwo(xor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * int Turn byte array high bit in front, low bit in back
     *
     * @param res
     * @return byte[]
     */
    public static byte[] intToByteArrayHighToLow(int res) {
        byte[] targets = new byte[4];

        targets[3] = (byte) (res & 0xff);// Lowest bit
        targets[2] = (byte) ((res >> 8) & 0xff);// Second lowest
        targets[1] = (byte) ((res >> 16) & 0xff);// Second highest
        targets[0] = (byte) (res >>> 24);// Most significant bit, right shift without sign。
        return targets;
    }

    /**
     * int Turn byte array high bit in front, low bit in back
     *
     * @param res
     * @return byte[]
     */
    public static byte[] intToByteArrayHighToLow(int res, int byteLength) {
        if (byteLength >= 4 || byteLength <= 0) {
            byteLength = 4;
        }

        byte[] targets = new byte[byteLength];
        for (int i = 0; i < byteLength; i++) {
            int rigthMove = (byteLength - 1 - i) * 8;
            if (rigthMove > 0) {
                if (i == 0) {
                    targets[i] = (byte) ((res >>> rigthMove) & 0xff);
                } else {
                    targets[i] = (byte) ((res >> rigthMove) & 0xff);
                }
            } else {
                targets[i] = (byte) (res & 0xff);
            }
        }
//        byte[] targets = new byte[4];

//        targets[3] = (byte) (res & 0xff);// Lowest bit
//        targets[2] = (byte) ((res >> 8) & 0xff);// Second lowest
//        targets[1] = (byte) ((res >> 16) & 0xff);// Second highest
//        targets[0] = (byte) (res >>> 24);// Most significant bit, right shift without sign.
        return targets;
    }

    /**
     * int Turn byte array high bit in front, low bit in back
     *
     * @param res
     * @return byte[]
     */
    public static byte[] intToByteArrayLowToHigh(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// Lowest bit
        targets[1] = (byte) ((res >> 8) & 0xff);// Second lowest
        targets[2] = (byte) ((res >> 16) & 0xff);// Second highest
        targets[3] = (byte) (res >>> 24);// Most significant bit, right shift without sign。
        return targets;
    }

    /**
     * int to byte array high order first, low order
     *
     * @param res
     * @return byte[]
     */
    public static byte[] intToByteArrayLowToHigh(int res, int byteLength) {
        if (byteLength >= 4 || byteLength <= 0) {
            byteLength = 4;
        }

        byte[] targets = new byte[byteLength];
        for (int i = 0; i < byteLength; i++) {
            int rigthMove = i * 8;
            if (rigthMove > 0) {
                if (i == 3) {
                    targets[i] = (byte) ((res >>> rigthMove) & 0xff);
                } else {
                    targets[i] = (byte) ((res >> rigthMove) & 0xff);
                }
            } else {
                targets[i] = (byte) (res & 0xff);
            }
        }
//        targets[0] = (byte) (res & 0xff);
//        targets[1] = (byte) ((res >> 8) & 0xff);
//        targets[2] = (byte) ((res >> 16) & 0xff);
//        targets[3] = (byte) (res >>> 24);
        return targets;
    }

    /**
     * From byte array to int, the high bit is in front and the low bit is in the back.
     *
     * @param b
     * @return int
     */
    public static int byteArrayToIntHighToLow(byte[] b) {
//        byte[] a = new byte[4];
//        int i = a.length - 1, j = b.length - 1;
//        for (; i >= 0; i--, j--) {//Copy data from the end of b (that is, the low-order bit of the int value)
//            if (j >= 0)
//                a[i] = b[j];
//            else
//                a[i] = 0;//If b.length is less than 4, add 0 to the high bit
//        }
//        int v0 = (a[0] & 0xff) << 24;//&0xff converts the byte value to int without difference, to avoid Java automatic type promotion, it will retain the high sign bit
//        int v1 = (a[1] & 0xff) << 16;
//        int v2 = (a[2] & 0xff) << 8;
//        int v3 = (a[3] & 0xff);
        int returnValue = 0;
        if (notNull(b)) {
            if (b.length > 4) {
                return returnValue;
            }
            for (int k = 0; k < b.length; k++) {
                int leftOffset = (b.length - 1 - k) * 8;
                returnValue = returnValue + ((b[k] & 0xff) << leftOffset);
            }
        }
        return returnValue;
//        return v0 + v1 + v2 + v3;
    }

    /**
     * byte The array is converted to int with the low bit first and the high bit low.
     *
     * @param b
     * @return int
     */
    public static int byteArrayToIntLowToHigh(byte[] b) {

        int returnValue = 0;
        if (notNull(b)) {
            if (b.length > 4) {
                //The array is too large, return 0 directly
                return returnValue;
            }
            for (int i = 0; i < b.length; i++) {
                int leftOffset = i * 8;
                returnValue = returnValue + ((b[i] & 0xff) << leftOffset);
            }
        }
        return returnValue;
    }

    /**
     * byte Turn int, unsigned turn
     *
     * @param b
     * @return int
     */
    public static int byteToInt(byte b) {
        return b & 0xff;
    }

    /**
     * int Convert char, note that only the first char is taken here
     *
     * @param val
     * @return char
     */
    public static char intToChar(int val) {
        return (char) (val & 0xff);
    }

    /**
     * hexStr convert char, note that this is a single char string
     *
     * @param hexStr
     * @return char default 0x00;
     */
    public static char intToChar(String hexStr) {
        if (notNull(hexStr)) {
            byte hesByte = new Integer(hexToInt(hexStr)).byteValue();
//            return hesByte;
        }
        return 0x00;
    }

    /**
     * Reverse byte array
     *
     * @param srcBytes
     * @return byte[]
     */
    public static byte[] reverseByteArray(byte[] srcBytes) {
        if (notNull(srcBytes)) {
            byte[] returnBytes = new byte[srcBytes.length];
            for (int i = srcBytes.length - 1; i >= 0; i--) {
                returnBytes[returnBytes.length - 1 - i] = srcBytes[i];
            }
            return returnBytes;
        }
        return null;
    }

    /**
     * hex string reverse
     *
     * @param hexStr
     * @return String
     */
    public static String reverseHexString(String hexStr) {
        if (notNull(hexStr)) {
            int fori = hexStr.length() / 2;
            StringBuilder sb = new StringBuilder();
            for (int i = fori - 1; i >= 0; i--) {
                sb.append(hexStr.substring(2 * i, 2 * i + 2));
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * Byte is reversed (~), not the same as reverse．
     *
     * @param src 　source
     * @return byte negated result
     */
    public static byte naByte(byte src) {
        return (byte) ~src;
    }

    /**
     * Invert byte array(~)
     *
     * @param src 　source
     * @return byte[] Returned result set
     */
    public static byte[] naBytes(byte[] src) {
        byte[] returnBytes = null;
        if (notNull(src)) {
            returnBytes = new byte[src.length];
            for (int i = 0; i < src.length; i++) {
                returnBytes[i] = naByte(src[i]);
            }
        }
        return returnBytes;
    }

    public static boolean notNull(Object object) {
        if (object instanceof String) {
            String sObj = (String) object;
            if (sObj != null && sObj.length() > 0) {
                return true;
            }
        } else if (object instanceof List) {
            List<?> listObj = (List<?>) object;
            if (listObj != null && listObj.size() > 0) {
                return true;
            }
        } else if (object instanceof Map) {
            Map<?, ?> mapObj = (Map<?, ?>) object;
            if (mapObj != null && mapObj.size() > 0) {
                return true;
            }
        } else if (object instanceof Set) {
            Set<?> setObj = (Set<?>) object;
            if (setObj != null && setObj.size() > 0) {
                return true;
            }
        } else if (object instanceof boolean[]) {
            boolean[] objArray = (boolean[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof byte[]) {
            byte[] objArray = (byte[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof char[]) {
            char[] objArray = (char[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof double[]) {
            double[] objArray = (double[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof float[]) {
            float[] objArray = (float[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof int[]) {
            int[] objArray = (int[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof long[]) {
            long[] objArray = (long[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof short[]) {
            short[] objArray = (short[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else if (object instanceof String[]) {
            String[] objArray = (String[]) object;
            if (objArray != null && objArray.length > 0) {
                return true;
            }
        } else {
            if (object != null) {
                return true;
            }
        }
        return false;
    }

    private static boolean arrayIsNotNull(boolean[] objects) {
        if (objects != null && objects.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * Convert a hexadecimal string into a normal string。
     *
     * @param hexStr
     * @return String
     */
//     * Rely on{@link DatatypeConverter#parseHexBinary(String)}Method, so this package needs to be imported, otherwise it will not compile and pass
    public static String hexStringToString(String hexStr) {
        if (notNull(hexStr)) {
//            byte[] hexBytes = DatatypeConverter.parseHexBinary(hexStr);
            byte[] hexBytes = hexStringToByteArray(hexStr);
            return new String(hexBytes);
        }
        return null;
    }

    /**
     * Convert hexadecimal string to byte array
     *
     * @param hex
     * @return the array of byte
     */
    public static final byte[] hexStringToByteArray(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    /**
     * Convert a hexadecimal string of length 2 to a single byte
     *
     * @param hex
     * @return the array of byte
     */
    public static final byte hexStringToByte(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }

        int byteint = Integer.parseInt(hex, 16) & 0xFF;
        byte reByte = new Integer(byteint).byteValue();
        return reByte;
    }

    /**
     * Convert byte array to hex string
     *
     * @param src
     * @return String
     */
    public static String byteArrayToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * char Array into byte array
     *
     * @param chars
     * @return byte[]
     */
    public static byte[] charArrayToByteArray(char[] chars) {
        //Because the annotated method has problems in handling negative bytes, it is not recommended to use
//        Charset cs = Charset.forName("UTF-8");
//        CharBuffer cb = CharBuffer.allocate(chars.length);
//        cb.put(chars);
//        cb.flip();
//        ByteBuffer bb = cs.encode(cb);
//        return removeNULLByte(bb.array());
        byte[] reBytes = null;
        if (notNull(chars)) {
            reBytes = new byte[chars.length];
            for (int i = 0; i < chars.length; i++) {
                reBytes[i] = (byte) chars[i];
            }
        }
        return reBytes;
    }

    /**
     * char Characters such as'a' are converted to bytes, // because the method will have problems when processing negative bytes, it is not recommended to use. You can use{@link #charToOxByte(char)} 代替
     *
     * @param chr
     * @return byte[]
     */
    public static byte charToByte(char chr) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(chr);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array()[0];
    }

    /**
     * @param objs
     * @return boolean
     */
//    public static boolean notNull(char[] objs) {
//        if (objs != null && objs.length > 0) {
//            return true;
//        }
//        return false;
//    }

    /**
     * byte Convert array to char array
     *
     * @param bytes
     * @return char[]
     */
    public static char[] byteArrayToCharArray(byte[] bytes) {
        //Because the method of commenting will have problems in processing negative bytes, it is directly converted
//        Charset cs = Charset.forName("UTF-8");
//        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
//        bb.put(bytes);
//        bb.flip();
//        CharBuffer cb = cs.decode(bb);
//        return cb.array();
        char[] reChars = null;
        if (notNull(bytes)) {
            reChars = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                reChars[i] = (char) bytes[i];
            }
        }
        return reChars;
    }

    /**
     * byte Converted to char'a', this method will cause problems when converting negative numbers, so it is not recommended to use
     *
     * @param bte
     * @return char
     */
    public static char byteToChar(byte bte) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(1);
        bb.put(bte);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array()[0];
    }

    /**
     * byte Converted to char(0x)
     *
     * @param bte
     * @return char
     */
    public static char byteToOxChar(byte bte) {
        return (char) bte;
    }

    /**
     * byte Converted to char
     *
     * @param car
     * @return byte
     */
    public static byte charToOxByte(char car) {
        return (byte) car;
    }

    public static boolean compareString(String one, String two) {
        if (!notNull(one) && !notNull(two)) {
            return true;
        }
        if (!notNull(one) || !notNull(two)) {
            return false;
        }
        return one.equals(two);
    }


}
