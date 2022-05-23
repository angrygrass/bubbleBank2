package nl.hva.miw.c27.team1.cryptobanking.utilities.authentication;

public class ByteArrayToHexHelper {
    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (byte b : byteArray) {
            hexStringBuffer.append(byteToHex(b));
        }
        return hexStringBuffer.toString();
    }

    private static String byteToHex(byte num) {
        int maskValue = (int) num & 0xFF;
        String result = Integer.toHexString(maskValue);
        //padding if result is odd
        if (result.length() % 2 == 1) {
            result = "0" + result;
        }
        return result;
    }
}
